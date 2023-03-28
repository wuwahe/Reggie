package com.hbjt.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbjt.reggie.domain.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
