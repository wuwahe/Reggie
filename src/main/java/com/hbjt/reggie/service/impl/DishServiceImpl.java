package com.hbjt.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbjt.reggie.common.CustomException;
import com.hbjt.reggie.domain.Dish;
import com.hbjt.reggie.domain.DishFlavor;
import com.hbjt.reggie.domain.Setmeal;
import com.hbjt.reggie.domain.SetmealDish;
import com.hbjt.reggie.dto.DishDto;
import com.hbjt.reggie.mapper.DishMapper;
import com.hbjt.reggie.service.DishFlavorService;
import com.hbjt.reggie.service.DishService;
import com.hbjt.reggie.service.SetmealDishService;
import com.hbjt.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private RedisTemplate redisTemplate;

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long id = dishDto.getId(); //菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(dishDto.getFlavors());

        //清理某个分类下面的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
    }

    /*
    * 根据id查询菜品信息和对应的口味信息
    * */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息，从Dish表查询
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);
        //清理当前菜品对应口味数据--dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);
        //添加当前提交过来的口味数据--dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

        //清理某个分类下面的菜品缓存数据
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
    }

    @Override
    public void updateStatus(Integer status, List<Long> ids) {
        if (status == 0) {
            LambdaQueryWrapper<SetmealDish> slq = new LambdaQueryWrapper<>();
            slq.in(SetmealDish::getDishId, ids);
            List<SetmealDish> list = setmealDishService.list(slq);

            List<Setmeal> setmealList = list.stream().map(item -> {
                String setmealId = String.valueOf(item.getSetmealId());
                Setmeal setmeal = setmealService.getById(setmealId);
                return setmeal;
            }).collect(Collectors.toList());

            for (Setmeal setmeal : setmealList) {
                if (setmeal != null && setmeal.getStatus() == 1) {
                    throw new CustomException("该菜品关联的套餐正在热卖，不能停售");
                }
            }
        }

        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.in(Dish::getId, ids);

        Dish dish = new Dish();
        dish.setStatus(status);

        dishMapper.update(dish, lqw);
    }
}
