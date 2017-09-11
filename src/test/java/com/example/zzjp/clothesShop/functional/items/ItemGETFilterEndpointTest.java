package com.example.zzjp.clothesShop.functional.items;

import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.repository.*;
import com.example.zzjp.clothesShop.util.PropertiesValues;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import static junit.framework.TestCase.assertTrue;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class ItemGETFilterEndpointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

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

    @BeforeClass
    public static void setup() {
        Setup.setup("/api/v1/items/filter");
    }

    @Test
    public void shouldReturnAllItemsWhenNoFilterSet() {
        Item[] items = given()
                .port(port)
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == PropertiesValues.NUMBER_OF_ITEMS);
    }

    @Test
    public void shouldReturnItemsByCategory() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 2);
        assertTrue(items[0].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[1].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
    }

    @Test
    public void shouldReturnItemsByColor() {
        Item[] items = given()
                .port(port)
                .param("color", PropertiesValues.COLOR_1)
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 2);
        assertTrue(items[0].getColor().equals(PropertiesValues.COLOR_1));
        assertTrue(items[1].getColor().equals(PropertiesValues.COLOR_1));
    }

    @Test
    public void shouldReturnItemsBySize() {
        Item[] items = given()
                .port(port)
                .param("size", PropertiesValues.SIZE_1)
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 2);
        assertTrue(items[0].getSize() == PropertiesValues.SIZE_1);
        assertTrue(items[1].getSize() == PropertiesValues.SIZE_1);
    }

    @Test
    public void shouldReturnItemsByPrice() {
        Item[] items = given()
                .port(port)
                .param("minPrice", new BigDecimal("60.00"))
                .param("maxPrice", new BigDecimal("100.00"))
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 2);
        assertTrue(items[0].getPrice().equals(PropertiesValues.PRICE_2));
        assertTrue(items[1].getPrice().equals(PropertiesValues.PRICE_2));
    }

    @Test
    public void shouldReturnNothingWhenCategoryExcludesColor() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("color", PropertiesValues.COLOR_2)
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 0);
    }

    @Test
    public void shouldReturnItemsByCategoryAndColor() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("color", PropertiesValues.COLOR_1)
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 2);
        assertTrue(items[0].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[1].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[0].getColor().equals(PropertiesValues.COLOR_1));
        assertTrue(items[1].getColor().equals(PropertiesValues.COLOR_1));
    }

    @Test
    public void shouldReturnNothingWhenCategoryExcludesSize() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("size", PropertiesValues.SIZE_2)
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 0);
    }

    @Test
    public void shouldReturnItemsByCategoryAndSize() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("size", PropertiesValues.SIZE_1)
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 2);
        assertTrue(items[0].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[1].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[0].getSize().equals(PropertiesValues.SIZE_1));
        assertTrue(items[1].getSize().equals(PropertiesValues.SIZE_1));
    }

    @Test
    public void shouldReturnNothingWhenCategoryExcludesPrice() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("minPrice", new BigDecimal("140.00"))
                .param("maxPrice", new BigDecimal("150.00"))
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 0);
    }

    @Test
    public void shouldReturnItemsByCategoryAndPrice() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("minPrice", new BigDecimal("110.00"))
                .param("maxPrice", new BigDecimal("130.00"))
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 1);
        assertTrue(items[0].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[0].getPrice().equals(PropertiesValues.PRICE_1));
    }

    @Test
    public void shouldReturnItemsByCategoryAndColorAndSize() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("color", PropertiesValues.COLOR_1)
                .param("size", PropertiesValues.SIZE_1)
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 2);
        assertTrue(items[0].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[0].getColor().equals(PropertiesValues.COLOR_1));
        assertTrue(items[0].getSize().equals(PropertiesValues.SIZE_1));
        assertTrue(items[1].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[1].getColor().equals(PropertiesValues.COLOR_1));
        assertTrue(items[1].getSize().equals(PropertiesValues.SIZE_1));
    }

    @Test
    public void shouldReturnItemsByCategoryAndColorAndPrice() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("color", PropertiesValues.COLOR_1)
                .param("minPrice", (PropertiesValues.PRICE_1).subtract(new BigDecimal(10.00)))
                .param("maxPrice", (PropertiesValues.PRICE_1).add(new BigDecimal(10.00)))
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 1);
        assertTrue(items[0].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[0].getColor().equals(PropertiesValues.COLOR_1));
        assertTrue(items[0].getPrice().equals(PropertiesValues.PRICE_1));
    }

    @Test
    public void shouldReturnItemsByCategoryAndSizeAndPrice() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("size", PropertiesValues.SIZE_1)
                .param("minPrice", (PropertiesValues.PRICE_1).subtract(new BigDecimal(10.00)))
                .param("maxPrice", (PropertiesValues.PRICE_1).add(new BigDecimal(10.00)))
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 1);
        assertTrue(items[0].getCategory().getId() == PropertiesValues.CATEGORY_ID_1);
        assertTrue(items[0].getSize().equals(PropertiesValues.SIZE_1));
        assertTrue(items[0].getPrice().equals(PropertiesValues.PRICE_1));
    }

    @Test
    public void shouldReturnItemsByColorAndSizeAndPrice() {
        Item[] items = given()
                .port(port)
                .param("color", PropertiesValues.COLOR_1)
                .param("size", PropertiesValues.SIZE_1)
                .param("minPrice", (PropertiesValues.PRICE_1).subtract(new BigDecimal(10.00)))
                .param("maxPrice", (PropertiesValues.PRICE_1).add(new BigDecimal(10.00)))
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 1);
        assertTrue(items[0].getColor().equals(PropertiesValues.COLOR_1));
        assertTrue(items[0].getSize().equals(PropertiesValues.SIZE_1));
        assertTrue(items[0].getPrice().equals(PropertiesValues.PRICE_1));
    }

    @Test
    public void shouldReturnItemsByCategoryAndColorAndSizeAndPrice() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_1)
                .param("color", PropertiesValues.COLOR_1)
                .param("size", PropertiesValues.SIZE_1)
                .param("minPrice", (PropertiesValues.PRICE_1).subtract(new BigDecimal(10.00)))
                .param("maxPrice", (PropertiesValues.PRICE_1).add(new BigDecimal(10.00)))
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 1);
        assertTrue(items[0].getCategory().getId().equals(PropertiesValues.CATEGORY_ID_1));
        assertTrue(items[0].getColor().equals(PropertiesValues.COLOR_1));
        assertTrue(items[0].getSize().equals(PropertiesValues.SIZE_1));
        assertTrue(items[0].getPrice().equals(PropertiesValues.PRICE_1));
    }

    @Test
    public void shouldReturnNothingWhenConditionsExcludeThemselves() {
        Item[] items = given()
                .port(port)
                .param("categoryId", PropertiesValues.CATEGORY_ID_2)
                .param("color", PropertiesValues.COLOR_1)
                .param("size", PropertiesValues.SIZE_1)
                .param("minPrice", (PropertiesValues.PRICE_1).subtract(new BigDecimal(10.00)))
                .param("maxPrice", (PropertiesValues.PRICE_1).add(new BigDecimal(10.00)))
                .when()
                .get()
                .as(Item[].class);

        assertTrue(items.length == 0);
    }
}
