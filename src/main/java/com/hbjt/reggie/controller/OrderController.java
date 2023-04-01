package com.hbjt.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbjt.reggie.common.R;
import com.hbjt.reggie.domain.Orders;
import com.hbjt.reggie.domain.User;
import com.hbjt.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*
    * 用户下单
    * */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /*
    * 订单详情分页查询
    * */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,Long number){
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(number != null,Orders::getId,number);
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /*
    * 修改配送状态
    * */
    @PutMapping
    public R<String> updateStatus(@RequestBody Orders orders){
       LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.like(orders.getId() != null,Orders::getId,orders.getId());
       orderService.update(orders,queryWrapper);
        return R.success("修改成功");
    }

    /*用户订单查询*/
    @GetMapping("/userPage")
    public R<Page> userPage(HttpSession session, int page, int pageSize){
        Page<Orders> pageInfo = new Page<>();
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        Long userId = (Long) session.getAttribute("user");
        queryWrapper.like(userId != null,Orders::getUserId,userId);
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
}
