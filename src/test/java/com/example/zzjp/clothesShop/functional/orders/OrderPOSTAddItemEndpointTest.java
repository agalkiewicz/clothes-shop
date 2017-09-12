package com.example.zzjp.clothesShop.functional.orders;

import com.example.zzjp.clothesShop.dto.AddDiscountDto;
import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.Order;
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
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class OrderPOSTAddItemEndpointTest {

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
    public void shouldAddItem() {
        given()
                .port(port).log().all()
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_1, PropertiesValues.PASSSWORD_1)
                .contentType("application/json")
                .pathParam("orderId", 1)
                .body(1)
                .when().log().all()
                .post("/{orderId}/items")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void shouldReturn404WhenAmountEquals0() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_1, PropertiesValues.PASSSWORD_1)
                .contentType("application/json")
                .pathParam("id", 1)
                .body(PropertiesValues.ITEM_ID_3)
                .when()
                .post("/{id}/items")
                .then()
                .statusCode(404);
    }

    @Test
    public void asNonLoggedInShouldNotAddItem() {
        given()
                .port(port)
                .contentType("application/json")
                .pathParam("id", 1)
                .body(PropertiesValues.ITEM_ID_1)
                .when()
                .post("/{id}/items")
                .then()
                .statusCode(401);
    }

    @Test
    public void shouldAddItemWhenOwnerLoggedIn() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_2, PropertiesValues.PASSSWORD_2)
                .contentType("application/json")
                .pathParam("orderId", PropertiesValues.ORDER_ID_1)
                .body(new AddDiscountDto(PropertiesValues.DISCOUNT_ID_1))
                .when()
                .post("/{orderId}/discounts/")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldReturn403WhenNonOwnerLoggedIn() {
        given()
                .port(port).log().all()
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_3, PropertiesValues.PASSSWORD_3)
                .contentType("application/json")
                .pathParam("orderId", 1)
                .body(1)
                .when().log().all()
                .post("/{orderId}/items")
                .then()
                .statusCode(403).log().all();
    }
}
