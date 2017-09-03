package com.example.zzjp.clothesShop.functional;

import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.initializer.PropertiesValues;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class CategoryDELETERemoveEndpointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostConstruct
    public void initializeDB() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(itemRepository, categoryRepository);
        databaseInitializer.initializeDB();
    }

    @BeforeClass
    public static void setup() {
        Setup.setup("/api/v1/categories");
    }

    @Test
    public void shouldRemoveCategoryWithoutItems() {
        given()
                .port(port)
                .pathParam("id", PropertiesValues.CATEGORY_ID_3)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldReturn500WhenCategoryHasItems() {
        given()
                .port(port)
                .pathParam("id", PropertiesValues.CATEGORY_ID_1)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(500);
    }
}
