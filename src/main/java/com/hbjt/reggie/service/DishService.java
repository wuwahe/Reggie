package com.hbjt.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbjt.reggie.domain.Dish;
import com.hbjt.reggie.dto.DishDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表
    @Transactional
    void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和对应的口味信息
    DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应口味信息
    @Transactional
    void updateWithFlavor(DishDto dishDto);

    void updateStatus(Integer status, List<Long> ids);
}
