package com.snowwarrior.directory.mapper;

import com.snowwarrior.directory.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 数据库CRUD
 * @author SnowWarrior
 */
@Repository
public interface UserMapper {
    @Select("select * from user")
    List<User> getAllUsers();

    @Select("select * from user where id=#{id}")
    Optional<User> getUserById(Long id);

    @Select("select id from user where username=#{username}")
    Optional<Long> getUserIdByUsername(String username);

    @Insert("insert into user(" +
            "username,password,phone,email" +
            ")" +
            "values(" +
            "#{username},#{password},#{phone},#{email}" +
            ")")
    void addUser(User user);

    @Update("update user set username=#{username},password=#{password},phone=#{phone},email=#{email}" +
            "where id=#{id}")
    void updateUserById(User user);

    @Delete("delete from user where id=#{id}")
    void deleteUserById(Long id);

    @Select("select * from user where username=#{username}")
    Optional<User> getUserByUsername(String username);

}
