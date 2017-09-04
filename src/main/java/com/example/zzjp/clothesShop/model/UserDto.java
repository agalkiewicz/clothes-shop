package com.example.zzjp.clothesShop.model;

import org.hibernate.validator.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
