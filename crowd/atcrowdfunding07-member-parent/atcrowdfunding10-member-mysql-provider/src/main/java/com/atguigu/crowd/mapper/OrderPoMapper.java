package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.po.OrderPo;
import com.atguigu.crowd.entity.po.OrderPoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderPoMapper {
    int countByExample(OrderPoExample example);

    int deleteByExample(OrderPoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderPo record);

    int insertSelective(OrderPo record);

    List<OrderPo> selectByExample(OrderPoExample example);

    OrderPo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderPo record, @Param("example") OrderPoExample example);

    int updateByExample(@Param("record") OrderPo record, @Param("example") OrderPoExample example);

    int updateByPrimaryKeySelective(OrderPo record);

    int updateByPrimaryKey(OrderPo record);
}