package com.example.zzjp.clothesShop.integration;

import com.example.zzjp.clothesShop.initializer.Constants;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Rollback
public class ItemRepositoryIntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void initializeDB() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(itemRepository,categoryRepository);
        databaseInitializer.initializeDB();
    }

    @Test
    public void shouldReturnAllItemsByCategory() {
        List<Item> result = itemRepository.findByCategoryId(Constants.CATEGORY_ID_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getId())
                .isEqualTo(Constants.ITEM_ID_1);
        assertThat(result.get(1).getId())
                .isEqualTo(Constants.ITEM_ID_2);
        assertThat(result.get(0).getName())
                .isEqualTo(Constants.ITEM_NAME_1);
        assertThat(result.get(0).getPrice())
                .isEqualTo(Constants.PRICE_1);
    }

    @Test
    public void shouldReturnAllItemsByPriceGreaterThan() {
        List<Item> result = itemRepository.findByPriceGreaterThan(new BigDecimal("80.00"));

        assertThat(result.size())
                .isEqualTo(1);
        assertThat(result.get(0).getPrice())
                .isEqualTo(Constants.PRICE_1);
    }

    @Test
    public void shouldReturnAllItemsByPrice() {
        List<Item> result = itemRepository.findByPrice(Constants.PRICE_2);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(Constants.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(Constants.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByPricesBetween() {
        List<Item> result = itemRepository.findByPriceBetween(new BigDecimal("60.00"), new BigDecimal("80.00"));

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(Constants.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(Constants.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByPriceLessThan() {
        List<Item> result = itemRepository.findByPriceLessThan(Constants.PRICE_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(Constants.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(Constants.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByColor() {
        List<Item> result = itemRepository.findByColor(Constants.COLOR_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getColor())
                .isEqualTo(Constants.COLOR_1);
        assertThat(result.get(1).getColor())
                .isEqualTo(Constants.COLOR_1);
    }

    @Test
    public void shouldReturnAllItemsByAmount() {
        List<Item> result = itemRepository.findByAmount(Constants.AMOUNT_2);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getAmount())
                .isEqualTo(Constants.AMOUNT_2);
        assertThat(result.get(1).getAmount())
                .isEqualTo(Constants.AMOUNT_2);
    }

    @Test
    public void shouldReturnAllItemsByAmountGreaterThan() {
        List<Item> result = itemRepository.findByAmountGreaterThan(Constants.AMOUNT_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getAmount())
                .isEqualTo(Constants.AMOUNT_2);
        assertThat(result.get(1).getAmount())
                .isEqualTo(Constants.AMOUNT_2);
    }

    @Test
    public void shouldReturnAllItemsByAmountLessThan() {
        List<Item> result = itemRepository.findByAmountLessThan(Constants.AMOUNT_2);

        assertThat(result.size())
                .isEqualTo(1);
        assertThat(result.get(0).getAmount())
                .isEqualTo(Constants.AMOUNT_1);
    }

    @Test
    public void shouldReturnItemByName() {
        Item result = itemRepository.findByName(Constants.ITEM_NAME_1);

        assertThat(result.getName())
                .isEqualTo(Constants.ITEM_NAME_1);
    }
}
