package com.example.zzjp.clothesShop.unit;

import com.example.zzjp.clothesShop.util.PropertiesValues;
import com.example.zzjp.clothesShop.model.Category;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.model.ItemDto;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import com.example.zzjp.clothesShop.service.CategoryService;
import com.example.zzjp.clothesShop.service.ItemService;
import com.example.zzjp.clothesShop.util.ObjectMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceUnitTest {

    @Mock
    ItemRepository itemRepository;

    ItemService itemService;

    @Mock
    CategoryService categoryService;

    @Before
    public void setUp() {
        itemService = new ItemService(itemRepository, categoryService);
    }

    private static Category category1;
    private static Category category2;
    private static Item item1;
    private static Item item2;
    private static Item item3;
    private static List<Item> items;

    @BeforeClass
    public static void initialize() {
        category1 = new Category();
        category1.setName(PropertiesValues.CATEGORY_NAME_1);
        category1.setId(PropertiesValues.CATEGORY_ID_1);

        category2 = new Category();
        category2.setName(PropertiesValues.CATEGORY_NAME_2);
        category2.setId(PropertiesValues.CATEGORY_ID_2);

        item1 = new Item();
        item1.setId(PropertiesValues.ITEM_ID_1);
        item1.setCategory(category1);
        item1.setName(PropertiesValues.ITEM_NAME_1);
        item1.setAmount(PropertiesValues.AMOUNT_1);
        item1.setPrice(PropertiesValues.PRICE_1);
        item1.setSize(PropertiesValues.SIZE_1);
        item1.setColor(PropertiesValues.COLOR_1);

        item2 = new Item();
        item2.setId(PropertiesValues.ITEM_ID_2);
        item2.setCategory(category1);
        item2.setName(PropertiesValues.ITEM_NAME_2);
        item2.setAmount(PropertiesValues.AMOUNT_2);
        item2.setPrice(PropertiesValues.PRICE_2);
        item2.setSize(PropertiesValues.SIZE_1);
        item2.setColor(PropertiesValues.COLOR_1);

        item3 = new Item();
        item3.setId(PropertiesValues.ITEM_ID_3);
        item3.setCategory(category2);
        item3.setName(PropertiesValues.ITEM_NAME_3);
        item3.setAmount(PropertiesValues.AMOUNT_2);
        item3.setPrice(PropertiesValues.PRICE_2);
        item3.setSize(PropertiesValues.SIZE_2);
        item3.setColor(PropertiesValues.COLOR_2);

        items = Arrays.asList(item1, item2, item3);
    }

    @Test
    public void shouldReturnAllItems() {
        when(itemRepository.findAll())
                .thenReturn(items);

        List<Item> result = itemService.getAll();

        verify(itemRepository)
                .findAll();

        assertThat(result)
                .isNotEmpty()
                .isEqualTo(items);
        assertThat(result.containsAll(items))
                .isTrue();
        assertThat(result.size())
                .isEqualTo(3);
    }

    @Test
    public void shouldReturnItemById() {
        when(itemRepository.findOne(PropertiesValues.ITEM_ID_1))
                .thenReturn(item1);

        Item result = itemService.getById(PropertiesValues.ITEM_ID_1);

        verify(itemRepository)
                .findOne(PropertiesValues.ITEM_ID_1);

        assertThat(result)
                .isEqualTo(item1);
    }

    @Test
    public void shouldReturnItemByName() {
        when(itemRepository.findByName(PropertiesValues.ITEM_NAME_1))
                .thenReturn(item1);

        Item result = itemService.getByName(PropertiesValues.ITEM_NAME_1);

        verify(itemRepository)
                .findByName(PropertiesValues.ITEM_NAME_1);

        assertThat(result)
                .isEqualTo(item1);
    }

    @Test
    public void shouldReturnItemsByCategoryId() {
        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepository.findByCategoryId(PropertiesValues.CATEGORY_ID_1))
                .thenReturn(items);

        List<Item> result = itemService.getByCategoryId(PropertiesValues.CATEGORY_ID_1);

        verify(itemRepository)
                .findByCategoryId(PropertiesValues.CATEGORY_ID_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByPrice() {
        List<Item> items = Arrays.asList(item2, item3);

        when(itemRepository.findByPrice(PropertiesValues.PRICE_2))
                .thenReturn(items);

        List<Item> result = itemService.getByPrice(PropertiesValues.PRICE_2);

        verify(itemRepository)
                .findByPrice(PropertiesValues.PRICE_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByPriceGreaterThan() {
        List<Item> items = Arrays.asList(item1);

        when(itemRepository.findByPriceGreaterThan(PropertiesValues.PRICE_2))
                .thenReturn(items);

        List<Item> result = itemService.getByPriceGreaterThan(PropertiesValues.PRICE_2);

        verify(itemRepository)
                .findByPriceGreaterThan(PropertiesValues.PRICE_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByPriceLessThan() {
        List<Item> items = Arrays.asList(item2, item3);

        when(itemRepository.findByPriceLessThan(PropertiesValues.PRICE_1))
                .thenReturn(items);

        List<Item> result = itemService.getByPriceLessThan(PropertiesValues.PRICE_1);

        verify(itemRepository)
                .findByPriceLessThan(PropertiesValues.PRICE_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByPriceBetween() {
        BigDecimal price1 = new BigDecimal("99.99");
        List<Item> items = Arrays.asList(item1);

        when(itemRepository.findByPriceBetween(price1, PropertiesValues.PRICE_2))
                .thenReturn(items);

        List<Item> result = itemService.getByPriceBetween(price1, PropertiesValues.PRICE_2);

        verify(itemRepository)
                .findByPriceBetween(price1, PropertiesValues.PRICE_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByColor() {
        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepository.findByColor(PropertiesValues.COLOR_1))
                .thenReturn(items);

        List<Item> result = itemService.getByColor(PropertiesValues.COLOR_1);

        verify(itemRepository)
                .findByColor(PropertiesValues.COLOR_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsBySize() {
        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepository.findBySize(PropertiesValues.SIZE_1))
                .thenReturn(items);

        List<Item> result = itemService.getBySize(PropertiesValues.SIZE_1);

        verify(itemRepository)
                .findBySize(PropertiesValues.SIZE_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByAmount() {
        List<Item> items = Arrays.asList(item2, item3);

        when(itemRepository.findByAmount(PropertiesValues.AMOUNT_2))
                .thenReturn(items);

        List<Item> result = itemService.getByAmount(PropertiesValues.AMOUNT_2);

        verify(itemRepository)
                .findByAmount(PropertiesValues.AMOUNT_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByAmountGreaterThan() {
        List<Item> items = Arrays.asList(item2, item3);

        when(itemRepository.findByAmountGreaterThan(PropertiesValues.AMOUNT_1))
                .thenReturn(items);

        List<Item> result = itemService.getByAmountGreaterThan(PropertiesValues.AMOUNT_1);

        verify(itemRepository)
                .findByAmountGreaterThan(PropertiesValues.AMOUNT_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByAmountLessThan() {
        List<Item> items = Arrays.asList(item1);

        when(itemRepository.findByAmountLessThan(PropertiesValues.AMOUNT_2))
                .thenReturn(items);

        List<Item> result = itemService.getByAmountLessThan(PropertiesValues.AMOUNT_2);

        verify(itemRepository)
                .findByAmountLessThan(PropertiesValues.AMOUNT_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldAddItem() {
        ItemDto itemDto = ObjectMock.mockItemDto();

        Category category = new Category(PropertiesValues.CATEGORY_NAME_1);
        category.setId(itemDto.getCategoryId());

        Item item = new Item(itemDto);
        item.setCategory(category);

        doReturn(category)
                .when(categoryService)
                .getById(itemDto.getCategoryId());
        doReturn(item)
                .when(itemRepository)
                .save(item);

        Item result = itemService.add(itemDto);

        verify(categoryService)
                .getById(itemDto.getCategoryId());
        verify(itemRepository)
                .save(item);

        assertThat(result)
                .isNotNull();
        assertThat(result.getName())
                .isEqualTo(ObjectMock.ITEM_DTO_NAME);
        assertThat(result.getPrice())
                .isEqualTo(ObjectMock.ITEM_DTO_PRICE);
        assertThat(result.getAmount())
                .isEqualTo(ObjectMock.ITEM_DTO_AMOUNT);
        assertThat(result.getCategory().getId())
                .isEqualTo(ObjectMock.ITEM_DTO_CATEGORY_ID);
    }

    @Test
    public void shouldUpdateItem() {
        ItemDto itemDto = ObjectMock.mockItemDto();

        Category category = new Category(PropertiesValues.CATEGORY_NAME_1);
        category.setId(itemDto.getCategoryId());

        Item item = new Item(itemDto);
        item.setCategory(category);

        Long id = 1L;
        item.setId(id);

        doReturn(item)
                .when(itemRepository)
                .findOne(id);
        doReturn(category)
                .when(categoryService)
                .getById(itemDto.getCategoryId());
        doReturn(item)
                .when(itemRepository)
                .save(item);

        Item result = itemService.update(id, itemDto);

        verify(itemRepository)
                .findOne(id);
        verify(categoryService)
                .getById(itemDto.getCategoryId());
        verify(itemRepository)
                .save(item);

        assertThat(result)
                .isNotNull();
        assertThat(result.getId())
                .isEqualTo(id);
        assertThat(result.getName())
                .isEqualTo(ObjectMock.ITEM_DTO_NAME);
        assertThat(result.getPrice())
                .isEqualTo(ObjectMock.ITEM_DTO_PRICE);
        assertThat(result.getAmount())
                .isEqualTo(ObjectMock.ITEM_DTO_AMOUNT);
        assertThat(result.getCategory().getId())
                .isEqualTo(ObjectMock.ITEM_DTO_CATEGORY_ID);

    }

    @Test
    public void shouldRemoveItem() {
        Long id = 1L;

        itemService.remove(id);

        verify(itemRepository)
                .delete(id);
    }
}
