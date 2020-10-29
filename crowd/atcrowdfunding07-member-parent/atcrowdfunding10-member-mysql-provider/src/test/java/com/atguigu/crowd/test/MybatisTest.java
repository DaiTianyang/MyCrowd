package com.atguigu.crowd.test;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.DetailReturnVO;
import com.atguigu.crowd.entity.vo.PortalProjectVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.mapper.ProjectPOMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private MemberPOMapper memberPOMapper;

	@Autowired
	private ProjectPOMapper projectPOMapper;
	
	private Logger logger = LoggerFactory.getLogger(MybatisTest.class);
	
/*	@Autowired
	private MemberPO memberPO;*/

	/*private Logger logger = LoggerFactory.getLogger(MybatisTest.class);

	@Test
	public void Test() throws SQLException {
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		logger.debug(connection.toString());
		logger.debug(connection.getClass().toString());
	}
*/
	@Test
	public void TestMember() {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String source = "123123";
		String encoder = passwordEncoder.encode(source);
		MemberPO memberPO = new MemberPO(null, "jack", encoder, "杰克", "jack@163.com", 1, 1, "杰克", "123123", 2);
		memberPOMapper.insert(memberPO);
	}
	
	@Test
	public void testPortalTypeList(){
		List<PortalTypeVO> portalTypeVOList = projectPOMapper.selectPortalTypeVOList();
		for (PortalTypeVO portalTypeVO : portalTypeVOList) {
			Integer id = portalTypeVO.getId();
			String name = portalTypeVO.getName();
			String remark = portalTypeVO.getRemark();
			logger.info("---"+id+"---"+name+"---"+remark+"---");
			List<PortalProjectVO> portalProjectVOList = portalTypeVO.getPortalProjectVOList();
			for (PortalProjectVO portalProjectVO : portalProjectVOList) {
				if(portalProjectVO == null){
					
					continue;
				}
				logger.info(portalProjectVO.toString());
			}
		}
	}
	
	@Test
	public void testDetailProject(){
		DetailProjectVO selectDetailProjectVO = projectPOMapper.selectDetailProjectVO(62);
		logger.info(selectDetailProjectVO.getProjectId()+"");
		logger.info(selectDetailProjectVO.getHeaderPicturePath()+"");
		logger.info(selectDetailProjectVO.getProjectDesc()+"");
		logger.info(selectDetailProjectVO.getProjectName()+"");
		logger.info(selectDetailProjectVO.getDeployDate()+"");
		logger.info(selectDetailProjectVO.getFollowerCount()+"");
		logger.info(selectDetailProjectVO.getLastDay()+"");
		logger.info(selectDetailProjectVO.getMoney()+"");
		logger.info(selectDetailProjectVO.getPercentage()+"");
		logger.info(selectDetailProjectVO.getStatus()+"");
		logger.info(selectDetailProjectVO.getSupporterCount()+"");
		logger.info(selectDetailProjectVO.getSupportMoney()+"");
		List<String> detailPicturePathList = selectDetailProjectVO.getDetailPicturePathList();
		for (String detailPicturePath : detailPicturePathList) {
			if(detailPicturePath == null){
				continue;
			}
			logger.info(detailPicturePath);
		}
		List<DetailReturnVO> detailReturnVOList = selectDetailProjectVO.getDetailReturnVOList();
		for (DetailReturnVO detailReturnVO : detailReturnVOList) {
			if(detailReturnVO == null){
				continue;
			}
			logger.info(detailReturnVO.toString());
		}
	}
}
