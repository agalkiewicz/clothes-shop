package com.example.zzjp.clothesShop.functional.users;

import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.dto.UserDto;
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
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class UserPOSTAddAdminEndpointTest {

    @LocalServerPort
    private int port;

    private String username = "zielona12";
    private String password = "trudne_hasło";

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
        Setup.setup("/api/v1/users/admin");
    }

    @Test
    public void shouldAddAdminAsAdmin() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_1, PropertiesValues.PASSSWORD_1)
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
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_1, PropertiesValues.PASSSWORD_1)
                .contentType("application/json")
                .body(new UserDto())
                .when()
                .post("/")
                .then()
                .statusCode(400);

    }

    @Test
    public void shouldReturn403WhenNonAdminLoggedIn() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_2, PropertiesValues.PASSSWORD_2)
                .contentType("application/json")
                .body(new UserDto(username, password))
                .when()
                .post("/")
                .then()
                .statusCode(403);

    }
}
