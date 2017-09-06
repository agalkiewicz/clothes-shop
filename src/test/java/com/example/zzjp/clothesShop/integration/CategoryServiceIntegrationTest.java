package com.example.zzjp.clothesShop.integration;

import com.example.zzjp.clothesShop.ClothesShopApplication;
import com.example.zzjp.clothesShop.repository.*;
import com.example.zzjp.clothesShop.util.PropertiesValues;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesShopApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class CategoryServiceIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ItemStateRepository itemStateRepository;

    @PostConstruct
    public void initializeDB() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(
                itemRepository,
                categoryRepository,
                userRepository,
                passwordEncoder,
                orderRepository,
                deliveryRepository,
                discountRepository,
                itemStateRepository
        );

        databaseInitializer.initializeDB();
    }

    @Test
    public void shouldReturnCategoryByName() {
        Category result = categoryService.getByName(PropertiesValues.CATEGORY_NAME_1);

        assertThat(result.getName())
                .isEqualTo(PropertiesValues.CATEGORY_NAME_1);
    }
}
