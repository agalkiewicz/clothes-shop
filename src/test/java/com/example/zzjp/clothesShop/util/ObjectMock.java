package com.example.zzjp.clothesShop.util;

import com.example.zzjp.clothesShop.initializer.PropertiesValues;
import com.example.zzjp.clothesShop.model.ItemDto;

import java.math.BigDecimal;

public class ObjectMock {

    public static final String ITEM_DTO_NAME = "JEANS002XL";
    public static final BigDecimal ITEM_DTO_PRICE = new BigDecimal("129.99");
    public static final int ITEM_DTO_AMOUNT = 3;
    public static final long ITEM_DTO_CATEGORY_ID = PropertiesValues.CATEGORY_ID_1;

    public static ItemDto mockItemDto() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(ITEM_DTO_NAME);
        itemDto.setPrice(ITEM_DTO_PRICE);
        itemDto.setAmount(ITEM_DTO_AMOUNT);
        itemDto.setCategoryId(ITEM_DTO_CATEGORY_ID);

        return itemDto;
    }
}
