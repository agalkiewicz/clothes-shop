package com.example.zzjp.clothesShop.functional.categories;

import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.CategoryDto;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import com.example.zzjp.clothesShop.repository.UserRepository;
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
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class CategoryPUTUpdateEndpointTest {

    @LocalServerPort
    private int port;

    private String name = "shoes";
    private int id = 1;

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
        Setup.setup("/api/v1/categories");
    }

    @Test
    public void shouldUpdateItem() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_1, PropertiesValues.PASSSWORD_1)
                .contentType("application/json")
                .pathParam("id", id)
                .body(new CategoryDto(name))
                .when()
                .put("/{id}")
                .then()
                .body("id", equalTo(id))
                .body("name", equalTo(name))
                .statusCode(200);
    }

    @Test
    public void shouldReturn500WhenIdNotExists() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_1, PropertiesValues.PASSSWORD_1)
                .contentType("application/json")
                .pathParam("id", 1000)
                .body(new CategoryDto(name))
                .when()
                .put("/{id}")
                .then()
                .statusCode(500);
    }

    @Test
    public void shouldReturn400WhenCategoryIncomplete() {
        given()
                .port(port)
                .auth()
                .preemptive()
                .basic(PropertiesValues.USERNAME_1, PropertiesValues.PASSSWORD_1)
                .contentType("application/json")
                .pathParam("id", 1)
                .body(new CategoryDto())
                .when()
                .put("/{id}")
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
                .pathParam("id", id)
                .body(new CategoryDto(name))
                .when()
                .put("/{id}")
                .then()
                .statusCode(403);
    }
}
