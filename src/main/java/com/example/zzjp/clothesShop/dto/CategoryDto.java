package com.example.zzjp.clothesShop.dto;

import org.hibernate.validator.constraints.NotBlank;

public class CategoryDto {

    @NotBlank
    private String name;

    public CategoryDto(String name) {
        this.name = name;
    }

    public CategoryDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

