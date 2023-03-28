package com.hbjt.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbjt.reggie.domain.Orders;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}
