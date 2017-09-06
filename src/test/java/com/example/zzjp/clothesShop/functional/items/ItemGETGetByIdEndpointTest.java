package com.example.zzjp.clothesShop.functional.items;

import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.repository.*;
import com.example.zzjp.clothesShop.util.PropertiesValues;
import com.example.zzjp.clothesShop.model.Item;
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

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class ItemGETGetByIdEndpointTest {

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
        Setup.setup("/api/v1/items");
    }

    @Test
    public void shouldReturnItemWhenIdExists() {
        given()
                .port(port)
                .pathParam("id", 1)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldReturn404WhenIdNotExists() {
        given()
                .port(port)
                .pathParam("id", 1000)
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldReturnProperItemWhenIdExists() {
        long id = 1;
        Item item = given()
                .port(port)
                .pathParam("id", id)
                .when()
                .get("/{id}")
                .as(Item.class);

        assertTrue(item.getId().equals(PropertiesValues.ITEM_ID_1));
        assertTrue(item.getName().equals(PropertiesValues.ITEM_NAME_1));
        assertTrue(item.getPrice().equals(PropertiesValues.PRICE_1));
        assertTrue(item.getColor().equals(PropertiesValues.COLOR_1));
        assertTrue(item.getSize().equals(PropertiesValues.SIZE_1));
        assertTrue(item.getCategory().getId().equals(PropertiesValues.CATEGORY_ID_1));
    }
}
