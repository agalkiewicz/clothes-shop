package com.example.zzjp.clothesShop.unit;

import com.example.zzjp.clothesShop.initializer.Constants;
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
        category1.setName(Constants.CATEGORY_NAME_1);
        category1.setId(Constants.CATEGORY_ID_1);

        category2 = new Category();
        category2.setName(Constants.CATEGORY_NAME_2);
        category2.setId(Constants.CATEGORY_ID_2);

        item1 = new Item();
        item1.setId(Constants.ITEM_ID_1);
        item1.setCategory(category1);
        item1.setName(Constants.ITEM_NAME_1);
        item1.setAmount(Constants.AMOUNT_1);
        item1.setPrice(Constants.PRICE_1);
        item1.setSize(Constants.SIZE_1);
        item1.setColor(Constants.COLOR_1);

        item2 = new Item();
        item2.setId(Constants.ITEM_ID_2);
        item2.setCategory(category1);
        item2.setName(Constants.ITEM_NAME_2);
        item2.setAmount(Constants.AMOUNT_2);
        item2.setPrice(Constants.PRICE_2);
        item2.setSize(Constants.SIZE_1);
        item2.setColor(Constants.COLOR_1);

        item3 = new Item();
        item3.setId(Constants.ITEM_ID_3);
        item3.setCategory(category2);
        item3.setName(Constants.ITEM_NAME_3);
        item3.setAmount(Constants.AMOUNT_2);
        item3.setPrice(Constants.PRICE_2);
        item3.setSize(Constants.SIZE_2);
        item3.setColor(Constants.COLOR_2);

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
        when(itemRepository.findOne(Constants.ITEM_ID_1))
                .thenReturn(item1);

        Item result = itemService.getById(Constants.ITEM_ID_1);

        verify(itemRepository)
                .findOne(Constants.ITEM_ID_1);

        assertThat(result)
                .isEqualTo(item1);
    }

    @Test
    public void shouldReturnItemByName() {
        when(itemRepository.findByName(Constants.ITEM_NAME_1))
                .thenReturn(item1);

        Item result = itemService.getByName(Constants.ITEM_NAME_1);

        verify(itemRepository)
                .findByName(Constants.ITEM_NAME_1);

        assertThat(result)
                .isEqualTo(item1);
    }

    @Test
    public void shouldReturnItemsByCategoryId() {
        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepository.findByCategoryId(Constants.CATEGORY_ID_1))
                .thenReturn(items);

        List<Item> result = itemService.getByCategoryId(Constants.CATEGORY_ID_1);

        verify(itemRepository)
                .findByCategoryId(Constants.CATEGORY_ID_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByPrice() {
        List<Item> items = Arrays.asList(item2, item3);

        when(itemRepository.findByPrice(Constants.PRICE_2))
                .thenReturn(items);

        List<Item> result = itemService.getByPrice(Constants.PRICE_2);

        verify(itemRepository)
                .findByPrice(Constants.PRICE_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByPriceGreaterThan() {
        List<Item> items = Arrays.asList(item1);

        when(itemRepository.findByPriceGreaterThan(Constants.PRICE_2))
                .thenReturn(items);

        List<Item> result = itemService.getByPriceGreaterThan(Constants.PRICE_2);

        verify(itemRepository)
                .findByPriceGreaterThan(Constants.PRICE_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByPriceLessThan() {
        List<Item> items = Arrays.asList(item2, item3);

        when(itemRepository.findByPriceLessThan(Constants.PRICE_1))
                .thenReturn(items);

        List<Item> result = itemService.getByPriceLessThan(Constants.PRICE_1);

        verify(itemRepository)
                .findByPriceLessThan(Constants.PRICE_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByPriceBetween() {
        BigDecimal price1 = new BigDecimal("99.99");
        List<Item> items = Arrays.asList(item1);

        when(itemRepository.findByPriceBetween(price1, Constants.PRICE_2))
                .thenReturn(items);

        List<Item> result = itemService.getByPriceBetween(price1, Constants.PRICE_2);

        verify(itemRepository)
                .findByPriceBetween(price1, Constants.PRICE_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByColor() {
        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepository.findByColor(Constants.COLOR_1))
                .thenReturn(items);

        List<Item> result = itemService.getByColor(Constants.COLOR_1);

        verify(itemRepository)
                .findByColor(Constants.COLOR_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsBySize() {
        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepository.findBySize(Constants.SIZE_1))
                .thenReturn(items);

        List<Item> result = itemService.getBySize(Constants.SIZE_1);

        verify(itemRepository)
                .findBySize(Constants.SIZE_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByAmount() {
        List<Item> items = Arrays.asList(item2, item3);

        when(itemRepository.findByAmount(Constants.AMOUNT_2))
                .thenReturn(items);

        List<Item> result = itemService.getByAmount(Constants.AMOUNT_2);

        verify(itemRepository)
                .findByAmount(Constants.AMOUNT_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByAmountGreaterThan() {
        List<Item> items = Arrays.asList(item2, item3);

        when(itemRepository.findByAmountGreaterThan(Constants.AMOUNT_1))
                .thenReturn(items);

        List<Item> result = itemService.getByAmountGreaterThan(Constants.AMOUNT_1);

        verify(itemRepository)
                .findByAmountGreaterThan(Constants.AMOUNT_1);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldReturnItemsByAmountLessThan() {
        List<Item> items = Arrays.asList(item1);

        when(itemRepository.findByAmountLessThan(Constants.AMOUNT_2))
                .thenReturn(items);

        List<Item> result = itemService.getByAmountLessThan(Constants.AMOUNT_2);

        verify(itemRepository)
                .findByAmountLessThan(Constants.AMOUNT_2);

        assertThat(result)
                .isEqualTo(items);
    }

    @Test
    public void shouldAddItem() {
        ItemDto itemDto = ObjectMock.mockItemDto();

        Category category = new Category(Constants.CATEGORY_NAME_1);
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

        Category category = new Category(Constants.CATEGORY_NAME_1);
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
