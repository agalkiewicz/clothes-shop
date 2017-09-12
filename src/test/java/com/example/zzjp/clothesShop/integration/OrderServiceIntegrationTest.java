package com.example.zzjp.clothesShop.integration;

import com.example.zzjp.clothesShop.ClothesShopApplication;
import com.example.zzjp.clothesShop.exceptions.NoItemException;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.model.Order;
import com.example.zzjp.clothesShop.repository.*;
import com.example.zzjp.clothesShop.service.OrderService;
import com.example.zzjp.clothesShop.util.PropertiesValues;
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
public class OrderServiceIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

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
    public void shouldAddItem() throws NoItemException {
        Item itemBefore = itemRepository.findOne(PropertiesValues.ITEM_ID_1);
        int amountBefore = itemBefore.getAmount();

        Order order = orderService.addItem(PropertiesValues.ORDER_ID_1, PropertiesValues.ITEM_ID_1);

        Item itemAfter = itemRepository.findOne(PropertiesValues.ITEM_ID_1);
        int amountAfter = itemAfter.getAmount();

        assertThat(order.getItems().size())
                .isEqualTo(PropertiesValues.NUMBER_OF_ORDER_ITEMS + 1);
        assertThat(amountBefore)
                .isNotEqualTo(amountAfter);
    }

    @Test(expected = NoItemException.class)
    public void shouldThrowNoItemExceptionWhenAmountEquals0() throws NoItemException {
        Order order = orderService.addItem(PropertiesValues.ORDER_ID_1, PropertiesValues.ITEM_ID_3);
    }

    @Test
    public void shouldRemoveItem() {
        Item itemBefore = itemRepository.findOne(PropertiesValues.ITEM_ID_1);
        int amountBefore = itemBefore.getAmount();

        Order result = orderService.removeItem(PropertiesValues.ORDER_ID_1, PropertiesValues.ITEM_ID_1);

        Item itemAfter = itemRepository.findOne(PropertiesValues.ITEM_ID_1);
        int amountAfter = itemAfter.getAmount();

        assertThat(amountBefore)
                .isNotEqualTo(amountAfter);
    }
}
