package com.example.zzjp.clothesShop.dto;

import javax.validation.constraints.NotNull;

public class AddDiscountDto {

    @NotNull
    private Long discountId;

    public AddDiscountDto(Long discountId) {
        this.discountId = discountId;
    }

    public AddDiscountDto() {
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }
}
