package com.example.zzjp.clothesShop.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UpdateUsernameDto {

    @NotBlank
    private String username;

    public UpdateUsernameDto(String username) {
        this.username = username;
    }

    public UpdateUsernameDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
