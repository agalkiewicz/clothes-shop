package com.example.zzjp.clothesShop.util;

import com.example.zzjp.clothesShop.dto.ItemDto;
import com.example.zzjp.clothesShop.model.Delivery;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.model.Role;
import com.example.zzjp.clothesShop.model.User;
import com.example.zzjp.clothesShop.dto.UserDto;

import java.math.BigDecimal;

public class ObjectMock {

    public static final String ITEM_DTO_NAME = "JEANS002XL";
    public static final BigDecimal ITEM_DTO_PRICE = new BigDecimal("129.99");
    public static final long ITEM_DTO_CATEGORY_ID = PropertiesValues.CATEGORY_ID_1;
    public static final String USERNAME = "franczyk88";
    public static final String PASSWORD = "bardzotrudnehaslo";
    public static final long ID = 1L;
    public static final Role ROLE = Role.USER;
    public static final String DELIVERY_NAME = "INPOST";
    public static final BigDecimal DELIVERY_PRICE = new BigDecimal("12.99");

    public static ItemDto mockItemDto() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(ITEM_DTO_NAME);
        itemDto.setPrice(ITEM_DTO_PRICE);
        itemDto.setCategoryId(ITEM_DTO_CATEGORY_ID);

        return itemDto;
    }

    public static UserDto mockUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername(USERNAME);
        userDto.setPassword(PASSWORD);

        return userDto;
    }

    public static User mockUser() {
        User user = new User();
        user.setId(ID);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.addRole(ROLE);

        return user;
    }

    public static Delivery mockDelivery() {
        Delivery delivery = new Delivery();
        delivery.setId(ID);
        delivery.setName(DELIVERY_NAME);
        delivery.setPrice(DELIVERY_PRICE);

        return delivery;
    }
}
