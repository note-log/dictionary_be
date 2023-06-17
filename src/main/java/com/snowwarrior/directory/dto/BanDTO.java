package com.snowwarrior.directory.dto;

import jakarta.validation.constraints.NotBlank;

public class BanDTO {
    @NotBlank(message = "学号不得为空")
    public String username;
}
