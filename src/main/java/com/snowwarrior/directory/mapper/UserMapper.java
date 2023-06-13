package com.snowwarrior.directory.mapper;

import com.snowwarrior.directory.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 数据库CRUD
 *
 * @author SnowWarrior
 */
@Repository
public interface UserMapper {
    @Select({"<script>",
            "select *",
            "from user where",
            "<if test='id != null'>",
            "where id &lt; #{id}",
            "</if>",
            "order by id desc",
            "limit #{size}",
            "</script>"
    })
    @Results(id = "userMap", value = {
            @Result(column = "id", property = "id", id = true, jdbcType = JdbcType.BIGINT),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "phone", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "is_audit", property = "isAudit", jdbcType = JdbcType.BOOLEAN),
            @Result(column = "is_admin", property = "isAdmin", jdbcType = JdbcType.BOOLEAN),
            @Result(column = "is_banned", property = "isBanned", jdbcType = JdbcType.BOOLEAN),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.BOOLEAN),
            @Result(column = "login_time", property = "loginTime", jdbcType = JdbcType.INTEGER),
            @Result(column = "major", property = "major", jdbcType = JdbcType.VARCHAR),
            @Result(column = "clazz", property = "clazz", jdbcType = JdbcType.VARCHAR),
            @Result(column = "last_login_timestamp", property = "lastLoginTimestamp", jdbcType = JdbcType.BIGINT),
            @Result(column = "enrollment_year", property = "enrollmentYear", jdbcType = JdbcType.VARCHAR),
            @Result(column = "graduate_year", property = "graduateYear", jdbcType = JdbcType.VARCHAR),
            @Result(column = "company", property = "company", jdbcType = JdbcType.VARCHAR),
            @Result(column = "city", property = "city", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
    })
    List<User> getAllUsers(Long id, int size);

    @Select("select id from user where username=#{username}")
    Optional<Long> getUserIdByUsername(String username);

    @Select("select * from user where id=#{id}")
    @ResultMap(value = "userMap")
    Optional<User> getUserById(Long id);

    @Select({"<script>",
            "select *",
            "from user where",
            "<if test='id != null'>",
            "id &lt; #{id} and",
            "</if>",
            "<if test='name != null'>",
            "name=#{name}",
            "</if>",
            "order by id desc",
            "limit #{size}",
            "</script>"
    })
    @ResultMap(value = "userMap")
    User[] getUserByName(Long id, int size, String name);

    @Select({"<script>",
            "select *",
            "from user",
            "<if test='id != null'>",
            "where id &lt; #{id}",
            "</if>",
            "order by id desc",
            "limit #{size}",
            "</script>"
    })
    @ResultMap(value = "userMap")
    User[] getUserList(Long id, int size);

    @Select({"<script>",
            "select *",
            "from user where",
            "<if test='id != null'>",
            "id &lt; #{id} and",
            "</if>",
            "is_audit=false",
            "order by id desc",
            "limit #{size}",
            "</script>"
    })
    @ResultMap(value = "userMap")
    User[] getAuditUsers(Long id, int size);

    @Select("select * from user where username=#{username}")
    @ResultMap(value = "userMap")
    Optional<User> getUserByUsername(String username);

    @Select({"<script>",
            "select *",
            "from user where",
            "<if test='id != null'>",
            "id &lt; #{id} and",
            "</if>",
            "is_banned=true",
            "order by id desc",
            "limit #{size}",
            "</script>"
    })
    @ResultMap(value = "userMap")
    User[] getBannedUser(Long id, int size);

    @Insert("insert into user(" +
            "username,password,phone,email,name" +
            ")" +
            "values(" +
            "#{username},#{password},#{phone},#{email},#{name}" +
            ")")
    void addUser(User user);

    @Update({"<script>",
            "update user",
            "   <set>",
            "       <if test='phone != null'>phone=#{phone},</if>",
            "       <if test='email != null'>email=#{email},</if>",
            "       <if test='major != null'>major=#{major},</if>",
            "       <if test='clazz != null'>clazz=#{clazz},</if>",
            "       <if test='enrollmentYear != null'>enrollment_year=#{enrollmentYear},</if>",
            "       <if test='graduateYear != null'>graduate_year=#{graduateYear},</if>",
            "       <if test='company != null'>company=#{company},</if>",
            "       <if test='city != null'>city=#{city}</if>",
            "   </set>",
            "where id=#{id}",
            "</script>"})
    void updateUserById(User user);

    @Update("update user set password=#{password},phone=#{phone},email=#{email}," +
            "major=#{major},clazz=#{clazz},enrollment_year=#{enrollmentYear}," +
            "graduate_year=#{graduateYear},company=#{company},city=#{city} where username=#{username}")
    void updateUserByUsername(User user);

    @Update("update user set login_time=login_time+1,last_login_timestamp=CURRENT_TIMESTAMP where username=#{username}")
    void onLoginUpdate(String username);

    @Update("update user set is_banned=true where username=#{username}")
    void ban(String username);

    @Update("update user set is_banned=false where username=#{username}")
    void unban(String username);

    @Update("update user set is_delete=true where username=#{username}")
    void deleteUserByUsername(String username);

    @Delete("delete from user where id=#{id}")
    void deleteUserById(Long id);

    @Update("update user set is_audit=true where username=#{username}")
    void approve(String username);
}
