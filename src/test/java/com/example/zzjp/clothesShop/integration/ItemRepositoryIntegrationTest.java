package com.example.zzjp.clothesShop.integration;

import com.example.zzjp.clothesShop.repository.UserRepository;
import com.example.zzjp.clothesShop.util.PropertiesValues;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Rollback
public class ItemRepositoryIntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initializeDB() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(itemRepository, categoryRepository, userRepository, passwordEncoder);
        databaseInitializer.initializeDB();
    }

    @Test
    public void shouldReturnAllItemsByCategory() {
        List<Item> result = itemRepository.findByCategoryId(PropertiesValues.CATEGORY_ID_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getId())
                .isEqualTo(PropertiesValues.ITEM_ID_1);
        assertThat(result.get(1).getId())
                .isEqualTo(PropertiesValues.ITEM_ID_2);
        assertThat(result.get(0).getName())
                .isEqualTo(PropertiesValues.ITEM_NAME_1);
        assertThat(result.get(0).getPrice())
                .isEqualTo(PropertiesValues.PRICE_1);
    }

    @Test
    public void shouldReturnAllItemsByPriceGreaterThan() {
        List<Item> result = itemRepository.findByPriceGreaterThan(new BigDecimal("80.00"));

        assertThat(result.size())
                .isEqualTo(1);
        assertThat(result.get(0).getPrice())
                .isEqualTo(PropertiesValues.PRICE_1);
    }

    @Test
    public void shouldReturnAllItemsByPrice() {
        List<Item> result = itemRepository.findByPrice(PropertiesValues.PRICE_2);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByPricesBetween() {
        List<Item> result = itemRepository.findByPriceBetween(new BigDecimal("60.00"), new BigDecimal("80.00"));

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByPriceLessThan() {
        List<Item> result = itemRepository.findByPriceLessThan(PropertiesValues.PRICE_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByColor() {
        List<Item> result = itemRepository.findByColor(PropertiesValues.COLOR_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getColor())
                .isEqualTo(PropertiesValues.COLOR_1);
        assertThat(result.get(1).getColor())
                .isEqualTo(PropertiesValues.COLOR_1);
    }

    @Test
    public void shouldReturnAllItemsByAmount() {
        List<Item> result = itemRepository.findByAmount(PropertiesValues.AMOUNT_2);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getAmount())
                .isEqualTo(PropertiesValues.AMOUNT_2);
        assertThat(result.get(1).getAmount())
                .isEqualTo(PropertiesValues.AMOUNT_2);
    }

    @Test
    public void shouldReturnAllItemsByAmountGreaterThan() {
        List<Item> result = itemRepository.findByAmountGreaterThan(PropertiesValues.AMOUNT_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getAmount())
                .isEqualTo(PropertiesValues.AMOUNT_2);
        assertThat(result.get(1).getAmount())
                .isEqualTo(PropertiesValues.AMOUNT_2);
    }

    @Test
    public void shouldReturnAllItemsByAmountLessThan() {
        List<Item> result = itemRepository.findByAmountLessThan(PropertiesValues.AMOUNT_2);

        assertThat(result.size())
                .isEqualTo(1);
        assertThat(result.get(0).getAmount())
                .isEqualTo(PropertiesValues.AMOUNT_1);
    }

    @Test
    public void shouldReturnItemByName() {
        Item result = itemRepository.findByName(PropertiesValues.ITEM_NAME_1);

        assertThat(result.getName())
                .isEqualTo(PropertiesValues.ITEM_NAME_1);
    }
}
