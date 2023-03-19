package com.hbjt.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbjt.reggie.domain.Dish;
import com.hbjt.reggie.dto.DishDto;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表
    void saveWithFlavor(DishDto dishDto);
}
