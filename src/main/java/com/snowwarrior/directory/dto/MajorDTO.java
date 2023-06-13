package com.snowwarrior.directory.dto;

import com.snowwarrior.directory.model.Major;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

public class MajorDTO {
    @NotBlank(message = "id不得为空")
    private Long id;
    @NotBlank(message = "专业名不得为空")
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

    public Major toMajor() {
        var major = new Major();
        BeanUtils.copyProperties(this, major);
        return major;
    }

    public static MajorDTO fromMajor(Major major) {
        var dto = new MajorDTO();
        BeanUtils.copyProperties(major, dto);
        return dto;
    }
}
