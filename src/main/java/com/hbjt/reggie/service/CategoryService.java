package com.hbjt.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hbjt.reggie.domain.Category;

import java.util.Locale;

public interface CategoryService extends IService<Category> {
    void remove(Long id);

}
