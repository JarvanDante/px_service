package com.example.px_service.mapper;

import com.example.px_service.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    @Select("select count(1) > 0 from user where username = #{username} limit 1")
    boolean existsByUsername(@Param("username") String username);

    @Select("select * from user where username = #{username} limit 1")
    User findByUsername(@Param("username") String username);

    @Select("select * from user order by id desc limit #{limit} offset #{offset}")
    List<User> listUsers(@Param("offset") int offset, @Param("limit") int limit);

    @Insert("""
            insert into user (
                site_id, grade_id, level_id, agent_id, channel_id,
                username, password, mobile
            ) values (
                #{siteId}, #{gradeId}, #{levelId}, #{agentId}, #{channelId},
                #{username}, #{password}, #{mobile}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);

    @Delete("delete from user where id = #{id}")
    void deleteById(Integer id);

    @Update("update user set password = #{password}, mobile = #{mobile} where id = #{id}")
    int update(User user);
}
