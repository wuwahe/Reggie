package com.hbjt.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbjt.reggie.domain.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
