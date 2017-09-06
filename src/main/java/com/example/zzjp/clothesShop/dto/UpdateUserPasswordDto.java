package com.example.zzjp.clothesShop.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UpdateUserPasswordDto {

    @NotBlank
    private String password;

    public UpdateUserPasswordDto(String password) {
        this.password = password;
    }

    public UpdateUserPasswordDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
