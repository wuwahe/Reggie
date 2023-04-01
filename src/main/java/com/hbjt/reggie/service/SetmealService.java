package com.hbjt.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbjt.reggie.domain.Setmeal;
import com.hbjt.reggie.dto.SetmealDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    @Transactional
    void updateWithDish(SetmealDto setmealDto);

    /*
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * */
    void saveWithDish(SetmealDto setmealDto);

    /*
    * 删除套餐，同时需要删除套餐和菜品的关联数据
    * */
    @Transactional
     void removeWithDish(List<Long> ids);

    void updateStatus(Integer status, List<Long> ids);

    SetmealDto querySetmeal(Long timestamp);
}
