package com.snowwarrior.directory.mapper;

import com.snowwarrior.directory.model.Major;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MajorMapper {
    @Select({"<script>",
            "select *",
            "from major where",
            "<if test='id != null'>",
            "where id &lt; #{id}",
            "</if>",
            "order by id desc",
            "limit #{size}",
            "</script>"
    })
    @Results(id = "majorMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "is_deleted", property = "isDeleted", jdbcType = JdbcType.BOOLEAN),
    })
    Major[] getMajorList(Long id, int size);

    @Select("select * from major")
    @ResultMap("majorMap")
    Major[] getAllMajor();

    @Select("select * from major where id=#{id}")
    @ResultMap("majorMap")
    Optional<Major> getMajorById(Long id);

    @Insert("insert into major (name) values (#{name})")
    void addMajor(Major major);

    @Update("update major set is_deleted=true where id=#{id}")
    void deleteMajor(Long id);

    @Update("update major set name=#{name}")
    void updateMajor(Major major);
}
