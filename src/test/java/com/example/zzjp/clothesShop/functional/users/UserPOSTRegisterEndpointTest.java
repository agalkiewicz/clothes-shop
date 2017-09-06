package com.example.zzjp.clothesShop.functional.users;

import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.dto.UserDto;
import com.example.zzjp.clothesShop.repository.*;
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
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class UserPOSTRegisterEndpointTest {

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
        Setup.setup("/api/v1/users");
    }

    @Test
    public void shouldRegisterUser() {
        String username = "zielona12";
        String password = "trudne_hasło";

        given()
                .port(port)
                .contentType("application/json")
                .body(new UserDto(username, password))
                .when()
                .post("/")
                .then()
                .body("id", notNullValue())
                .body("username", equalTo(username))
                .statusCode(200);
    }

    @Test
    public void shouldReturn400WhenUserIncomplete() {
        given()
                .port(port)
                .contentType("application/json")
                .body(new UserDto())
                .when()
                .post("/")
                .then()
                .statusCode(400);

    }
}
