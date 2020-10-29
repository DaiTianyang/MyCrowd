package com.atguigu.crowd.mapper;

import com.atguigu.crowd.entity.po.AddressPo;
import com.atguigu.crowd.entity.po.AddressPoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AddressPoMapper {
    int countByExample(AddressPoExample example);

    int deleteByExample(AddressPoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AddressPo record);

    int insertSelective(AddressPo record);

    List<AddressPo> selectByExample(AddressPoExample example);

    AddressPo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AddressPo record, @Param("example") AddressPoExample example);

    int updateByExample(@Param("record") AddressPo record, @Param("example") AddressPoExample example);

    int updateByPrimaryKeySelective(AddressPo record);

    int updateByPrimaryKey(AddressPo record);
}