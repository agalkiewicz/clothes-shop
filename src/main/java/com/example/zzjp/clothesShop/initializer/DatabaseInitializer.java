package com.example.zzjp.clothesShop.initializer;

import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseInitializer {

    private ItemRepository itemRepository;

    private CategoryRepository categoryRepository;

    public DatabaseInitializer(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    public void initializeDB() {
        Category category1 = new Category();
        category1.setName(com.example.zzjp.clothesShop.initializer.PropertiesValues.CATEGORY_NAME_1);
        category1.setId(com.example.zzjp.clothesShop.initializer.PropertiesValues.CATEGORY_ID_1);

        Category category2 = new Category();
        category2.setName(com.example.zzjp.clothesShop.initializer.PropertiesValues.CATEGORY_NAME_2);
        category2.setId(com.example.zzjp.clothesShop.initializer.PropertiesValues.CATEGORY_ID_2);

        Category category3 = new Category();
        category3.setName(com.example.zzjp.clothesShop.initializer.PropertiesValues.CATEGORY_NAME_3);
        category3.setId(com.example.zzjp.clothesShop.initializer.PropertiesValues.CATEGORY_ID_3);

        categoryRepository.save(Arrays.asList(category1, category2, category3));

        Item item1 = new Item();
        item1.setId(com.example.zzjp.clothesShop.initializer.PropertiesValues.ITEM_ID_1);
        item1.setCategory(category1);
        item1.setName(com.example.zzjp.clothesShop.initializer.PropertiesValues.ITEM_NAME_1);
        item1.setAmount(com.example.zzjp.clothesShop.initializer.PropertiesValues.AMOUNT_1);
        item1.setPrice(com.example.zzjp.clothesShop.initializer.PropertiesValues.PRICE_1);
        item1.setSize(com.example.zzjp.clothesShop.initializer.PropertiesValues.SIZE_1);
        item1.setColor(com.example.zzjp.clothesShop.initializer.PropertiesValues.COLOR_1);

        Item item2 = new Item();
        item2.setId(com.example.zzjp.clothesShop.initializer.PropertiesValues.ITEM_ID_2);
        item2.setCategory(category1);
        item2.setName(com.example.zzjp.clothesShop.initializer.PropertiesValues.ITEM_NAME_2);
        item2.setAmount(com.example.zzjp.clothesShop.initializer.PropertiesValues.AMOUNT_2);
        item2.setPrice(com.example.zzjp.clothesShop.initializer.PropertiesValues.PRICE_2);
        item2.setSize(com.example.zzjp.clothesShop.initializer.PropertiesValues.SIZE_1);
        item2.setColor(com.example.zzjp.clothesShop.initializer.PropertiesValues.COLOR_1);

        Item item3 = new Item();
        item3.setId(com.example.zzjp.clothesShop.initializer.PropertiesValues.ITEM_ID_3);
        item3.setCategory(category2);
        item3.setName(com.example.zzjp.clothesShop.initializer.PropertiesValues.ITEM_NAME_3);
        item3.setAmount(com.example.zzjp.clothesShop.initializer.PropertiesValues.AMOUNT_2);
        item3.setPrice(com.example.zzjp.clothesShop.initializer.PropertiesValues.PRICE_2);
        item3.setSize(com.example.zzjp.clothesShop.initializer.PropertiesValues.SIZE_2);
        item3.setColor(com.example.zzjp.clothesShop.initializer.PropertiesValues.COLOR_2);

        itemRepository.save(Arrays.asList(item1, item2, item3));
    }

}
