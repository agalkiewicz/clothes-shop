package com.example.zzjp.clothesShop.initializer;

import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.model.Size;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        category1.setName(Constants.CATEGORY_NAME_1);
        category1.setId(Constants.CATEGORY_ID_1);

        Category category2 = new Category();
        category2.setName(Constants.CATEGORY_NAME_2);
        category2.setId(Constants.CATEGORY_ID_2);

        categoryRepository.save(Arrays.asList(category1, category2));

        Item item1 = new Item();
        item1.setId(Constants.ITEM_ID_1);
        item1.setCategory(category1);
        item1.setName(Constants.ITEM_NAME_1);
        item1.setAmount(Constants.AMOUNT_1);
        item1.setPrice(Constants.PRICE_1);
        item1.setSize(Constants.SIZE_1);
        item1.setColor(Constants.COLOR_1);

        Item item2 = new Item();
        item2.setId(Constants.ITEM_ID_2);
        item2.setCategory(category1);
        item2.setName(Constants.ITEM_NAME_2);
        item2.setAmount(Constants.AMOUNT_2);
        item2.setPrice(Constants.PRICE_2);
        item2.setSize(Constants.SIZE_1);
        item2.setColor(Constants.COLOR_1);

        Item item3 = new Item();
        item3.setId(Constants.ITEM_ID_3);
        item3.setCategory(category2);
        item3.setName(Constants.ITEM_NAME_3);
        item3.setAmount(Constants.AMOUNT_2);
        item3.setPrice(Constants.PRICE_2);
        item3.setSize(Constants.SIZE_2);
        item3.setColor(Constants.COLOR_2);

        itemRepository.save(Arrays.asList(item1, item2, item3));
    }

}
