package com.example.zzjp.clothesShop.integration;

import com.example.zzjp.clothesShop.initializer.PropertiesValues;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Rollback
public class CategoryRepositoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostConstruct
    public void initializeDB() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(itemRepository,categoryRepository);
        databaseInitializer.initializeDB();
    }

    @Test
    public void shouldReturnCategoryByName() {
        Category result = categoryRepository.findByName(PropertiesValues.CATEGORY_NAME_1);

        assertThat(result.getName())
                .isEqualTo(PropertiesValues.CATEGORY_NAME_1);
    }
}
