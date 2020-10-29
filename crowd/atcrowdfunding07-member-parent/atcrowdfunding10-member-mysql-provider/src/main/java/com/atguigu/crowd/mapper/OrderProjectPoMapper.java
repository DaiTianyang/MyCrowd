package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.po.OrderProjectPo;
import com.atguigu.crowd.entity.po.OrderProjectPoExample;
import com.atguigu.crowd.entity.vo.OrderProjectVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderProjectPoMapper {
    int countByExample(OrderProjectPoExample example);

    int deleteByExample(OrderProjectPoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderProjectPo record);

    int insertSelective(OrderProjectPo record);

    List<OrderProjectPo> selectByExample(OrderProjectPoExample example);

    OrderProjectPo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderProjectPo record, @Param("example") OrderProjectPoExample example);

    int updateByExample(@Param("record") OrderProjectPo record, @Param("example") OrderProjectPoExample example);

    int updateByPrimaryKeySelective(OrderProjectPo record);

    int updateByPrimaryKey(OrderProjectPo record);
    
    OrderProjectVo selectOrderProjectVO(Integer returnId);
}