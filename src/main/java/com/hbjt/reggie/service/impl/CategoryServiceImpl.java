package com.hbjt.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbjt.reggie.common.CustomException;
import com.hbjt.reggie.domain.Category;
import com.hbjt.reggie.domain.Dish;
import com.hbjt.reggie.domain.Setmeal;
import com.hbjt.reggie.mapper.CategoryMapper;
import com.hbjt.reggie.service.CategoryService;
import com.hbjt.reggie.service.DishService;
import com.hbjt.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    /*
    * 根据id删除分类，删除前进行判断
    * */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联菜品，如果已关联，抛出一个异常
        if (count > 0){
            //已经关联菜品，抛出异常
            throw new CustomException("当前分类关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐，如果已关联，抛出一个异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count();
        if (count1 > 0){
            //已经关联了套餐，抛出异常
            throw new CustomException("当前套餐关联了菜品，不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }


}
