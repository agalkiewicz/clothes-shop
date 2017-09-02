package com.example.zzjp.clothesShop.integration;

import com.example.zzjp.clothesShop.ClothesShopApplication;
import com.example.zzjp.clothesShop.initializer.Constants;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.model.ItemDto;
import com.example.zzjp.clothesShop.model.Size;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import com.example.zzjp.clothesShop.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesShopApplication.class)
@Transactional
@Rollback
public class ItemServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

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
        List<Item> result = itemService.getByCategoryId(Constants.CATEGORY_ID_1);

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
        List<Item> result = itemService.getByPriceGreaterThan(new BigDecimal("80.00"));

        assertThat(result.size())
                .isEqualTo(1);
        assertThat(result.get(0).getPrice())
                .isEqualTo(Constants.PRICE_1);
    }

    @Test
    public void shouldReturnAllItemsByPrice() {
        List<Item> result = itemService.getByPrice(Constants.PRICE_2);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(Constants.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(Constants.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByPricesBetween() {
        List<Item> result = itemService.getByPriceBetween(new BigDecimal("60.00"), new BigDecimal("80.00"));

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(Constants.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(Constants.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByPriceLessThan() {
        List<Item> result = itemService.getByPriceLessThan(Constants.PRICE_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(Constants.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(Constants.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByColor() {
        List<Item> result = itemService.getByColor(Constants.COLOR_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getColor())
                .isEqualTo(Constants.COLOR_1);
        assertThat(result.get(1).getColor())
                .isEqualTo(Constants.COLOR_1);
    }

    @Test
    public void shouldReturnAllItemsByAmount() {
        List<Item> result = itemService.getByAmount(Constants.AMOUNT_2);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getAmount())
                .isEqualTo(Constants.AMOUNT_2);
        assertThat(result.get(1).getAmount())
                .isEqualTo(Constants.AMOUNT_2);
    }

    @Test
    public void shouldReturnAllItemsByAmountGreaterThan() {
        List<Item> result = itemService.getByAmountGreaterThan(Constants.AMOUNT_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getAmount())
                .isEqualTo(Constants.AMOUNT_2);
        assertThat(result.get(1).getAmount())
                .isEqualTo(Constants.AMOUNT_2);
    }

    @Test
    public void shouldReturnAllItemsByAmountLessThan() {
        List<Item> result = itemService.getByAmountLessThan(Constants.AMOUNT_2);

        assertThat(result.size())
                .isEqualTo(1);
        assertThat(result.get(0).getAmount())
                .isEqualTo(Constants.AMOUNT_1);
    }

    @Test
    public void shouldReturnAllItems() {
        List<Item> result = itemService.getAll();

        assertThat(result.size())
                .isEqualTo(3);
    }

    @Test
    public void shouldReturnItemById() {
        Item result = itemService.getById(Constants.ITEM_ID_1);

        assertThat(result.getId())
                .isEqualTo(Constants.ITEM_ID_1);
    }

    @Test
    public void shouldReturnItemByName() {
        Item result = itemService.getByName(Constants.ITEM_NAME_1);

        assertThat(result.getName())
                .isEqualTo(Constants.ITEM_NAME_1);
    }

    @Test
    public void shouldAddItem() {
        ItemDto itemDto = new ItemDto();
        String name = "SHORTS003L";
        itemDto.setName(name);
        itemDto.setAmount(2);
        itemDto.setCategoryId(Constants.CATEGORY_ID_1);
        itemDto.setColor("white");
        itemDto.setSize(Size.L);
        itemDto.setPrice(new BigDecimal("49.99"));

        Item result = itemService.add(itemDto);

        List<Item> items = itemRepository.findAll();
        Item item = itemRepository.findByName(name);

        assertThat(items.size())
                .isEqualTo(4);
        assertThat(item)
                .isNotNull();
    }

    @Test
    public void shouldUpdateItem() {
        String name = "SHORTS003L";
        int amount = 2;
        String color = "blue";
        Size size = Size.XL;
        BigDecimal price = new BigDecimal("49.99");

        ItemDto itemDto = new ItemDto();
        itemDto.setName(name);
        itemDto.setAmount(amount);
        itemDto.setCategoryId(Constants.CATEGORY_ID_2);
        itemDto.setColor(color);
        itemDto.setSize(size);
        itemDto.setPrice(price);

        Item result = itemService.update(Constants.ITEM_ID_1, itemDto);

        assertThat(result.getName())
                .isEqualTo(name);
        assertThat(result.getName())
                .isNotEqualTo(Constants.ITEM_NAME_1);

        assertThat(result.getAmount())
                .isNotEqualTo(Constants.AMOUNT_1);
        assertThat(result.getAmount())
                .isEqualTo(amount);

        assertThat(result.getColor())
                .isNotEqualTo(Constants.COLOR_1);
        assertThat(result.getColor())
                .isEqualTo(color);

        assertThat(result.getSize())
                .isNotEqualTo(Constants.SIZE_1);
        assertThat(result.getSize())
                .isEqualTo(size);

        assertThat(result.getPrice())
                .isNotEqualTo(Constants.PRICE_1);
        assertThat(result.getPrice())
                .isEqualTo(price);

        assertThat(result.getCategory().getId())
                .isNotEqualTo(Constants.CATEGORY_ID_1);
        assertThat(result.getCategory().getId())
                .isEqualTo(Constants.CATEGORY_ID_2);
    }

    @Test
    public void shouldRemoveItem() {
        itemService.remove(Constants.ITEM_ID_1);

        List<Item> items = itemRepository.findAll();
        Item item = itemRepository.findOne(Constants.ITEM_ID_1);

        assertThat(items.size())
                .isEqualTo(2);
        assertThat(item)
                .isNull();
    }

    @Test
    public void shouldReturnAllItemsBySize() {
        List<Item> result = itemService.getBySize(Constants.SIZE_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getSize())
                .isEqualTo(Constants.SIZE_1);
        assertThat(result.get(1).getSize())
                .isEqualTo(Constants.SIZE_1);
    }
}
