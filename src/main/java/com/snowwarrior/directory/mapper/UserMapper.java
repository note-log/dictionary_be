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
            "from user",
            "order by id desc",
            "limit #{size}",
            "<if test='offset != null'>",
            "   offset #{offset}",
            "</if>",
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
    User[] getUsers(Long offset, int size);

    @Select("select count(*) from user where is_deleted=false")
    Long getTotalCount();

    @Select("select count(*) from user")
    Long getRealTotalCount();

    @Select({"<script>",
            "<bind name='pattern' value='name+\"%\"'/>",
            "select count(*) from user where is_deleted=false and name like #{pattern}",
            "</script>"})
    Long getTotalCountByName(String name);

    @Select("select count(*) from user where is_audit=false")
    Long getAuditTotalCount();

    @Select("select id from user where username=#{username}")
    Optional<Long> getUserIdByUsername(String username);

    @Select("select * from user where id=#{id}")
    @ResultMap(value = "userMap")
    Optional<User> getUserById(Long id);

    @Select({"<script>",
            "<bind name='pattern' value='name+\"%\"'/>",
            "select *",
            "from user",
            "<where>",
            "   is_deleted=false",
            "    <if test='name != null'>",
            "       and name like #{pattern}",
            "    </if>",
            "</where>",
            "order by id desc",
            "limit #{size}",
            "<if test='offset != null'>",
            "   offset #{offset}",
            "</if>",
            "</script>"
    })
    @ResultMap(value = "userMap")
    User[] getUserByName(Long offset, int size, String name);

    @Select({"<script>",
            "select *",
            "from user",
            "where is_deleted=false",
            "order by id desc",
            "limit #{size}",
            "<if test='offset != null'>",
            "offset #{offset}",
            "</if>",
            "</script>"
    })
    @ResultMap(value = "userMap")
    User[] getUserList(Long offset, int size);

    @Select({"<script>",
            "select *",
            "from user where",
            "is_audit=false",
            "order by id desc",
            "limit #{size}",
            "<if test='offset != null'>",
            "   offset #{offset}",
            "</if>",
            "</script>"
    })
    @ResultMap(value = "userMap")
    User[] getAuditUsers(Long offset, int size);

    @Select("select count(*) from user where is_banned=true")
    Long getBannedCount();

    @Select("select * from user where username=#{username}")
    @ResultMap(value = "userMap")
    Optional<User> getUserByUsername(String username);

    @Select({"<script>",
            "select *",
            "from user",
            "where is_banned=true",
            "and is_deleted=false",
            "order by id desc",
            "limit #{size}",
            "<if test='offset != null'>",
            "   offset #{offset}",
            "</if>",
            "</script>"
    })
    @ResultMap(value = "userMap")
    User[] getBannedUser(Long offset, int size);

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
            "where username=#{username}",
            "</script>"})
    void updateUserByUsername(User user);

    @Update("update user set login_time=login_time+1,last_login_timestamp=unix_timestamp(now()) where username=#{username}")
    void onLoginUpdate(String username);

    @Update("update user set password=#{newPassword} where id=#{id}")
    void updatePassword(String newPassword, Long id);

    @Update("update user set is_banned=true where username=#{username}")
    void ban(String username);

    @Update("update user set is_banned=false where username=#{username}")
    void unban(String username);

    @Update("update user set is_deleted=true where username=#{username}")
    void deleteUserByUsername(String username);

    @Delete("delete from user where id=#{id}")
    void deleteUserById(Long id);

    @Update("update user set is_audit=true where username=#{username}")
    void approve(String username);
}
