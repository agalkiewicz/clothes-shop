package com.example.zzjp.clothesShop.functional.orders;

import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
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

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class OrderDELETERemoveItemEndpointTest {

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

    @PostConstruct
    public void initializeDB() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(
                itemRepository,
                categoryRepository,
                userRepository,
                passwordEncoder,
                orderRepository,
                deliveryRepository,
                discountRepository
        );

        databaseInitializer.initializeDB();
    }

    @BeforeClass
    public static void setup() {
        Setup.setup("/api/v1/orders");
    }

    @Test
    public void shouldRemoveItem() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_1, PropertiesValues.PASSSWORD_1)
                .contentType("application/json")
                .pathParam("orderId", PropertiesValues.ORDER_ID_1)
                .pathParam("itemId", PropertiesValues.ITEM_ID_1)
                .when()
                .delete("/{orderId}/items/{itemId}")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldRemoveItemWhenOwnerLoggedIn() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_2, PropertiesValues.PASSSWORD_2)
                .contentType("application/json")
                .pathParam("orderId", PropertiesValues.ORDER_ID_1)
                .pathParam("itemId", PropertiesValues.ITEM_ID_1)
                .when()
                .delete("/{orderId}/items/{itemId}")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldReturn403WhenNonOwnerNonAdminLoggedIn() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_3, PropertiesValues.PASSSWORD_3)
                .contentType("application/json")
                .pathParam("orderId", PropertiesValues.ORDER_ID_1)
                .pathParam("itemId", PropertiesValues.ITEM_ID_1)
                .when()
                .delete("/{orderId}/items/{itemId}")
                .then()
                .statusCode(403);
    }
}
