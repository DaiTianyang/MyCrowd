package com.atguigu.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.entity.AdminExample.Criteria;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.mvc.constant.CrowdConstant;
import com.atguigu.crowd.mvc.exception.LoginAcctAlreadyInUseByUpdateException;
import com.atguigu.crowd.mvc.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.mvc.exception.LoginFailedException;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	@Override
	public void saveAdmin(Admin admin) {
		//进行密码加密
		String userPswd = admin.getUserPswd();
		//userPswd = CrowdUtil.md5(userPswd);
		//盐值加密
		userPswd = passwordEncoder.encode(userPswd);
		admin.setUserPswd(userPswd);
		
		//添加创建新用户的时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = format.format(date);
		admin.setCreateTime(createTime);
		
		//保存数据
		try {
			adminMapper.insert(admin);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("异常的全类名时："+getClass().getName().toString());
			
			//判断如果异常类型是账号重复则抛出该异常
			if (e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
	}

	@Override
	public List<Admin> getAll() {
		// TODO Auto-generated method stub
		List<Admin> selectByExample = adminMapper.selectByExample(new AdminExample());
		return selectByExample;
	}

	@Override
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

		// 1.根据登录账号查询Admin对象

		// 1)创建AdminExample对象
		AdminExample adminExample = new AdminExample();
		// 2)创建Criteria对象
		Criteria criteria = adminExample.createCriteria();
		// 3)再Criteria对象中封装查询条件
		criteria.andLoginAcctEqualTo(loginAcct);
		// 4)调用AdminMapper的方法执行查询
		List<Admin> list = adminMapper.selectByExample(adminExample);
		// 2.判读Admin对象是否为空
		if (list == null || list.size() == 0) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}

		if (list.size() > 1) {

			throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);

		}
		// 3.如果为空抛出异常
		Admin admin = list.get(0);
		if (admin == null) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}

		// 4.如果不为空将数据库密码从Admin对象中取出
		String password = admin.getUserPswd();
		// 5.将表单提交的密码进行加密
		String md5PswdFrom = CrowdUtil.md5(userPswd);
		// 6.将加密后的密码和数据库密码进行比较
		if (!Objects.equals(password, md5PswdFrom)) {
			// 7.如果不一致抛出异常
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		// 8.如果一致返回Admin对象
		return admin;
	}

	@Override
	public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
		
		//1.调用PageHelper静态方法开启分页
		PageHelper.startPage(pageNum, pageSize);
		
		//2.执行查询
		List<Admin> list = adminMapper.selectAdminByKeyword(keyword);
		
		//3.封装到PageInfo对象中
		return new PageInfo<>(list);
	}

	@Override
	public void removeAdmin(Integer adminId) {
		adminMapper.deleteByPrimaryKey(adminId);
	}

	@Override
	public Admin getAdminById(Integer adminId) {
		
		return adminMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public void updateAdmin(Admin admin) {
		
		//updateByPrimaryKeySelective更改数据不为bull的项
		try {
			adminMapper.updateByPrimaryKeySelective(admin);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("异常的全类名时："+getClass().getName().toString());
			//判断如果异常类型是账号重复则抛出该异常
			if (e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseByUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
	}

	@Override
	public void deleteAll(List<Integer> id) {
		
		AdminExample example = new AdminExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(id);
		adminMapper.deleteByExample(example);
	}

	@Override
	public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
		//旧数据
		//adminId roleID
		//	1		2(删除)
		//	1		3(删除)
		//	1		4
		//新数据
		//adminID roleID
		//	1		4(源数据)
		//	1		5(新数据)
		//	1		6(新数据)
		//步骤思路：先根据adminId删除其原有的所有信息，再根据roleIdList重进进行添加数据
		
		//1.先根据adminId进行删除
		adminMapper.deleteRelationship(adminId);
		//2.根据roleIdList和adminId进行保存
		if(roleIdList!=null&&roleIdList.size()>0){
			adminMapper.insertNewRelationship(adminId,roleIdList);
			
		}
	}
	/**
	 * 登录·新
	 * 
	 */
	@Override
	public Admin getAdminByLoginAcct(String username) {
		
		AdminExample example = new AdminExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andLoginAcctEqualTo(username);
		
		List<Admin> list = adminMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			return null;
		}
		Admin admin = list.get(0);
		
		
		return admin;
	}

}
