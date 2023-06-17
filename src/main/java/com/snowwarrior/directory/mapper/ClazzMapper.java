package com.snowwarrior.directory.mapper;

import com.snowwarrior.directory.model.Clazz;
import com.snowwarrior.directory.model.Major;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClazzMapper {
    @Select({"<script>",
            "select *",
            "from clazz",
            "where is_deleted=false",
            "order by id desc",
            "limit #{size}",
            "<if test='offset != null'>",
            "offset #{offset}",
            "</if>",
            "</script>"
    })
    @Results(id = "clazzMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.BOOLEAN),
    })
    Clazz[] getClazzList(Long offset, int size);

    @Select("select count(*) from clazz")
    Long getTotalCount();

    @Select("select * from clazz")
    @ResultMap("clazzMap")
    Clazz[] getAllClazz();

    @Select("select * from clazz where id=#{id}")
    @ResultMap("clazzMap")
    Optional<Clazz> getClazzById(Long id);

    @Select("select * from clazz where name=#{name}")
    @ResultMap("clazzMap")
    Optional<Clazz> getClazzByName(String name);

    @Insert("insert into clazz (name) values (#{name})")
    void addClazz(Clazz clazz);

    @Update("update clazz set is_deleted=true where id=#{id}")
    void deleteClazz(Long id);

    @Update("update clazz set name=#{name} where id=#{id}")
    void updateClazz(Clazz clazz);

}
