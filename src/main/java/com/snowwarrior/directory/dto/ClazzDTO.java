package com.snowwarrior.directory.dto;

import com.snowwarrior.directory.model.Clazz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;

public class ClazzDTO {
    @NotNull(message = "id不得为空")
    private Long id;
    @NotBlank(message = "班级名不得为空")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clazz toClazz() {
        var clazz = new Clazz();
        BeanUtils.copyProperties(this, clazz);
        return clazz;
    }

    public static ClazzDTO fromClazz(Clazz clazz) {
        var dto = new ClazzDTO();
        BeanUtils.copyProperties(clazz, dto);
        return dto;
    }
}
