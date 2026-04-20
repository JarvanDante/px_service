package com.example.px_service.mapper;

import com.example.px_service.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select id from user where id = #{id}")
    User findById(Integer id);

}
