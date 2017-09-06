package com.example.zzjp.clothesShop.dto;

import com.example.zzjp.clothesShop.model.OrderStatus;

public class UpdateOrderStatusDto {

    private OrderStatus orderStatus;

    public UpdateOrderStatusDto(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UpdateOrderStatusDto() {
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
