package com.example.zzjp.clothesShop.integration;

import com.example.zzjp.clothesShop.ClothesShopApplication;
import com.example.zzjp.clothesShop.repository.UserRepository;
import com.example.zzjp.clothesShop.util.PropertiesValues;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.dto.ItemDto;
import com.example.zzjp.clothesShop.model.Size;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import com.example.zzjp.clothesShop.service.ItemService;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothesShopApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class ItemServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

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
        List<Item> result = itemService.getByCategoryId(PropertiesValues.CATEGORY_ID_1);

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
        List<Item> result = itemService.getByPriceGreaterThan(new BigDecimal("80.00"));

        assertThat(result.size())
                .isEqualTo(1);
        assertThat(result.get(0).getPrice())
                .isEqualTo(PropertiesValues.PRICE_1);
    }

    @Test
    public void shouldReturnAllItemsByPrice() {
        List<Item> result = itemService.getByPrice(PropertiesValues.PRICE_2);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByPricesBetween() {
        List<Item> result = itemService.getByPriceBetween(new BigDecimal("60.00"), new BigDecimal("80.00"));

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByPriceLessThan() {
        List<Item> result = itemService.getByPriceLessThan(PropertiesValues.PRICE_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
        assertThat(result.get(1).getPrice())
                .isEqualTo(PropertiesValues.PRICE_2);
    }

    @Test
    public void shouldReturnAllItemsByColor() {
        List<Item> result = itemService.getByColor(PropertiesValues.COLOR_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getColor())
                .isEqualTo(PropertiesValues.COLOR_1);
        assertThat(result.get(1).getColor())
                .isEqualTo(PropertiesValues.COLOR_1);
    }

    @Test
    public void shouldReturnAllItems() {
        List<Item> result = itemService.getAll();

        assertThat(result.size())
                .isEqualTo(3);
    }

    @Test
    public void shouldReturnItemById() {
        Item result = itemService.getById(PropertiesValues.ITEM_ID_1);

        assertThat(result.getId())
                .isEqualTo(PropertiesValues.ITEM_ID_1);
    }

    @Test
    public void shouldReturnItemByName() {
        Item result = itemService.getByName(PropertiesValues.ITEM_NAME_1);

        assertThat(result.getName())
                .isEqualTo(PropertiesValues.ITEM_NAME_1);
    }

    @Test
    public void shouldAddItem() {
        ItemDto itemDto = new ItemDto();
        String name = "SHORTS003L";
        itemDto.setName(name);
        itemDto.setCategoryId(PropertiesValues.CATEGORY_ID_1);
        itemDto.setColor("white");
        itemDto.setSize(Size.L);
        itemDto.setPrice(new BigDecimal("49.99"));

        List<Item> itemsBefore = itemRepository.findAll();
        Item result = itemService.add(itemDto);
        List<Item> itemsAfter = itemRepository.findAll();

        assertThat(itemsBefore.size())
                .isNotEqualTo(itemsAfter.size());
        assertThat(result)
                .isNotNull();
    }

    @Test
    public void shouldUpdateItem() {
        String name = "SHORTS003L";
        String color = "blue";
        Size size = Size.XL;
        BigDecimal price = new BigDecimal("49.99");

        ItemDto itemDto = new ItemDto();
        itemDto.setName(name);
        itemDto.setCategoryId(PropertiesValues.CATEGORY_ID_2);
        itemDto.setColor(color);
        itemDto.setSize(size);
        itemDto.setPrice(price);

        Item result = itemService.update(PropertiesValues.ITEM_ID_1, itemDto);

        assertThat(result.getName())
                .isEqualTo(name);
        assertThat(result.getName())
                .isNotEqualTo(PropertiesValues.ITEM_NAME_1);

        assertThat(result.getColor())
                .isNotEqualTo(PropertiesValues.COLOR_1);
        assertThat(result.getColor())
                .isEqualTo(color);

        assertThat(result.getSize())
                .isNotEqualTo(PropertiesValues.SIZE_1);
        assertThat(result.getSize())
                .isEqualTo(size);

        assertThat(result.getPrice())
                .isNotEqualTo(PropertiesValues.PRICE_1);
        assertThat(result.getPrice())
                .isEqualTo(price);

        assertThat(result.getCategory().getId())
                .isNotEqualTo(PropertiesValues.CATEGORY_ID_1);
        assertThat(result.getCategory().getId())
                .isEqualTo(PropertiesValues.CATEGORY_ID_2);
    }

    @Test
    public void shouldRemoveItem() {
        itemService.remove(PropertiesValues.ITEM_ID_1);

        List<Item> items = itemRepository.findAll();
        Item item = itemRepository.findOne(PropertiesValues.ITEM_ID_1);

        assertThat(items.size())
                .isEqualTo(2);
        assertThat(item)
                .isNull();
    }

    @Test
    public void shouldReturnAllItemsBySize() {
        List<Item> result = itemService.getBySize(PropertiesValues.SIZE_1);

        assertThat(result.size())
                .isEqualTo(2);
        assertThat(result.get(0).getSize())
                .isEqualTo(PropertiesValues.SIZE_1);
        assertThat(result.get(1).getSize())
                .isEqualTo(PropertiesValues.SIZE_1);
    }
}
