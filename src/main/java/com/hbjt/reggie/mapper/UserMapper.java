package com.hbjt.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbjt.reggie.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
