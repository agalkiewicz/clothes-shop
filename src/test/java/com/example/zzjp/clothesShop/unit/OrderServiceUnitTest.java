package com.example.zzjp.clothesShop.unit;

import com.example.zzjp.clothesShop.dto.ItemDto;
import com.example.zzjp.clothesShop.exceptions.NoItemException;
import com.example.zzjp.clothesShop.model.*;
import com.example.zzjp.clothesShop.repository.DeliveryRepository;
import com.example.zzjp.clothesShop.repository.DiscountRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import com.example.zzjp.clothesShop.repository.OrderRepository;
import com.example.zzjp.clothesShop.service.OrderService;
import com.example.zzjp.clothesShop.util.ObjectMock;
import com.example.zzjp.clothesShop.util.PropertiesValues;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    DiscountRepository discountRepository;

    @Mock
    DeliveryRepository deliveryRepository;

    OrderService orderService;

    @Before
    public void setUp() {
        orderService = new OrderService(
                orderRepository,
                itemRepository,
                discountRepository,
                deliveryRepository
        );
    }

    private static User user;
    private static Order order;
    private static Item item1;
    private static Item item2;
    private static Item item3;

    @BeforeClass
    public static void createObjects() {
        user = new User();
        order = new Order(user);
        order.setId(PropertiesValues.ORDER_ID_1);
        ItemDto itemDto = ObjectMock.mockItemDto();
        item1 = new Item(itemDto);
        item1.setId(PropertiesValues.ITEM_ID_1);
        item2 = new Item(itemDto);
        item2.setId(PropertiesValues.ITEM_ID_2);
        item3 = new Item(itemDto);
        item3.setId(PropertiesValues.ITEM_ID_3);
        order.addItem(item1);
        order.addItem(item2);
    }

    @Test
    public void shouldReturnProperOrderValueForItemsOnly() {
        BigDecimal expected = new BigDecimal("0.00");
        expected = expected.add(order.getItems().get(0).getPrice());
        expected = expected.add(order.getItems().get(1).getPrice());

        order.setValue(expected);

        when(orderRepository.findOne(order.getId()))
                .thenReturn(order);
        when(orderRepository.save(order))
                .thenReturn(order);

        Order result = orderService.countValue(order.getId());

        verify(orderRepository)
                .findOne(order.getId());
        verify(orderRepository)
                .save(order);

        assertThat(result)
                .isEqualTo(order);
    }

    @Test
    public void shouldReturnProperOrderValueForItemsAndDeliveryOnly() {
        Delivery delivery = ObjectMock.mockDelivery();
        order.setDelivery(delivery);

        BigDecimal expected = new BigDecimal("0.00");
        expected = expected.add(order.getItems().get(0).getPrice());
        expected = expected.add(order.getItems().get(1).getPrice());
        expected = expected.add(order.getDelivery().getPrice());

        order.setValue(expected);

        when(orderRepository.findOne(order.getId()))
                .thenReturn(order);
        when(orderRepository.save(order))
                .thenReturn(order);

        Order result = orderService.countValue(order.getId());

        verify(orderRepository)
                .findOne(order.getId());
        verify(orderRepository)
                .save(order);

        assertThat(result)
                .isEqualTo(order);
    }

    @Test
    public void shouldReturnProperOrderValueForItemsAndSaleDiscount() {
        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.SALE);
        discount.setId(1L);
        discount.setPercentage(new BigDecimal("0.4"));
        discount.setItem(item1);

        order.setDiscount(discount);

        BigDecimal expected = new BigDecimal("0.00");
        expected = expected.add(order.getItems().get(0).getPrice());
        expected = expected.add(order.getItems().get(1).getPrice());
        BigDecimal discountValue = item1.getPrice().multiply(discount.getPercentage());
        expected = expected.subtract(discountValue);

        order.setValue(expected);

        when(orderRepository.findOne(order.getId()))
                .thenReturn(order);
        when(orderRepository.save(order))
                .thenReturn(order);

        Order result = orderService.countValue(order.getId());

        verify(orderRepository)
                .findOne(order.getId());
        verify(orderRepository)
                .save(order);

        assertThat(result)
                .isEqualTo(order);
    }

    @Test
    public void shouldReturnProperOrderValueForItemsAndMoneyDiscount() {
        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.MONEY);
        discount.setId(1L);
        discount.setPercentage(new BigDecimal("0.4"));
        discount.setSumOfMoney(new BigDecimal("100"));

        order.setDiscount(discount);

        BigDecimal expected = new BigDecimal("0.00");
        expected = expected.add(order.getItems().get(0).getPrice());
        expected = expected.add(order.getItems().get(1).getPrice());
        BigDecimal discountValue = expected.multiply(discount.getPercentage());
        expected = expected.subtract(discountValue);

        order.setValue(expected);

        when(orderRepository.findOne(order.getId()))
                .thenReturn(order);
        when(orderRepository.save(order))
                .thenReturn(order);

        Order result = orderService.countValue(order.getId());

        verify(orderRepository)
                .findOne(order.getId());
        verify(orderRepository)
                .save(order);

        assertThat(result)
                .isEqualTo(order);
    }

    @Test
    public void shouldNotChangeOrderValueWhenMoneyDiscountFor500() {
        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.MONEY);
        discount.setId(1L);
        discount.setPercentage(new BigDecimal("0.4"));
        discount.setSumOfMoney(new BigDecimal("500"));

        order.setDiscount(discount);

        BigDecimal expected = new BigDecimal("0.00");
        expected = expected.add(order.getItems().get(0).getPrice());
        expected = expected.add(order.getItems().get(1).getPrice());

        order.setValue(expected);

        when(orderRepository.findOne(order.getId()))
                .thenReturn(order);
        when(orderRepository.save(order))
                .thenReturn(order);

        Order result = orderService.countValue(order.getId());

        verify(orderRepository)
                .findOne(order.getId());
        verify(orderRepository)
                .save(order);

        assertThat(result)
                .isEqualTo(order);
    }

    @Test
    public void shouldReturnProperOrderValueForItemsAndDeliveryDiscount() {
        Delivery delivery = ObjectMock.mockDelivery();
        order.setDelivery(delivery);

        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.DELIVERY);
        order.setDiscount(discount);

        BigDecimal expected = new BigDecimal("0.00");
        expected = expected.add(order.getItems().get(0).getPrice());
        expected = expected.add(order.getItems().get(1).getPrice());

        order.setValue(expected);

        when(orderRepository.findOne(order.getId()))
                .thenReturn(order);
        when(orderRepository.save(order))
                .thenReturn(order);

        Order result = orderService.countValue(order.getId());

        verify(orderRepository)
                .findOne(order.getId());
        verify(orderRepository)
                .save(order);

        assertThat(result)
                .isEqualTo(order);
    }

    @Test(expected = NoItemException.class)
    public void shouldThrowNoItemExceptionWhenNoItem() throws NoItemException {
        item3.setAmount(0);

        when(orderRepository.findOne(order.getId()))
                .thenReturn(order);
        when(itemRepository.findOne(item3.getId()))
                .thenReturn(item3);

        orderService.addItem(order.getId(), item3.getId());
    }

    @Test
    public void shouldDecreaseItemStateAmountWhenAddItem() throws NoItemException {
        item3.setAmount(1);

        when(orderRepository.findOne(order.getId()))
                .thenReturn(order);
        when(itemRepository.findOne(item3.getId()))
                .thenReturn(item3);

        order.addItem(item3);

        when(orderRepository.save(order))
                .thenReturn(order);

        Order result = orderService.addItem(order.getId(), item3.getId());

        verify(orderRepository)
                .findOne(order.getId());
        verify(orderRepository)
                .findOne(order.getId());

        assertThat(item3.getAmount())
                .isEqualTo(0);
        assertThat(result)
                .isEqualTo(order);
    }
}
