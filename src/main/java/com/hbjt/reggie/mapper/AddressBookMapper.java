package com.hbjt.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hbjt.reggie.domain.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
