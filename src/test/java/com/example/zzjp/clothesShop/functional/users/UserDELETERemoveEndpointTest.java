package com.example.zzjp.clothesShop.functional.users;

import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.repository.UserRepository;
import com.example.zzjp.clothesShop.util.PropertiesValues;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
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
public class UserDELETERemoveEndpointTest {

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

    @PostConstruct
    public void initializeDB() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(itemRepository, categoryRepository, userRepository, passwordEncoder);
        databaseInitializer.initializeDB();
    }

    @BeforeClass
    public static void setup() {
        Setup.setup("/api/v1/users");
    }

    @Test
    public void asAdminShouldRemoveUser() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_1, PropertiesValues.PASSSWORD_1)
                .pathParam("id", PropertiesValues.USER_ID_2)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldReturn401WhenUserNotAuthenticated() {
        given()
                .port(port)
                .pathParam("id", PropertiesValues.USER_ID_2)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(401);
    }

    @Test
    public void asNonAdminShouldRemoveHimself() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_2, PropertiesValues.PASSSWORD_2)
                .pathParam("id", PropertiesValues.USER_ID_2)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void asNonAdminShouldNotRemoveOtherUser() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_2, PropertiesValues.PASSSWORD_2)
                .pathParam("id", PropertiesValues.USER_ID_3)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(403);
    }
}
