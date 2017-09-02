package com.example.zzjp.clothesShop.integration;

import com.example.zzjp.clothesShop.ClothesShopApplication;
import com.example.zzjp.clothesShop.ClothesShopApplicationTests;
import com.example.zzjp.clothesShop.initializer.Constants;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import com.example.zzjp.clothesShop.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesShopApplication.class)
@Transactional
@Rollback
public class CategoryServiceIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @PostConstruct
    public void initializeDB() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(itemRepository,categoryRepository);
        databaseInitializer.initializeDB();
    }

    @Test
    public void shouldReturnCategoryByName() {
        Category result = categoryService.getByName(Constants.CATEGORY_NAME_1);

        assertThat(result.getName())
                .isEqualTo(Constants.CATEGORY_NAME_1);
    }
}
