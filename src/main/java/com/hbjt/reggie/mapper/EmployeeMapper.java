package com.hbjt.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbjt.reggie.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
