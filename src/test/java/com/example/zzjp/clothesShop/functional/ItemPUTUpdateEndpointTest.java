package com.example.zzjp.clothesShop.functional;

import com.example.zzjp.clothesShop.functional.Setup;
import com.example.zzjp.clothesShop.initializer.DatabaseInitializer;
import com.example.zzjp.clothesShop.model.CategoryDto;
import com.example.zzjp.clothesShop.model.Item;
import com.example.zzjp.clothesShop.model.ItemDto;
import com.example.zzjp.clothesShop.repository.CategoryRepository;
import com.example.zzjp.clothesShop.repository.ItemRepository;
import com.example.zzjp.clothesShop.util.ObjectMock;
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
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@Rollback
public class ItemPUTUpdateEndpointTest {

    @LocalServerPort
    private int port;

    private int id = 1;

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
        Setup.setup("/api/v1/items");
    }

    @Test
    public void shouldUpdateItem() {
        Item item = given()
                .port(port)
                .contentType("application/json")
                .pathParam("id", id)
                .body(ObjectMock.mockItemDto())
                .when()
                .put("/{id}")
                .as(Item.class);

        assertTrue(item.getId() != null);
        assertTrue(item.getPrice().equals(ObjectMock.ITEM_DTO_PRICE));
        assertTrue(item.getAmount() == ObjectMock.ITEM_DTO_AMOUNT);
        assertTrue(item.getName().equals(ObjectMock.ITEM_DTO_NAME));
        assertTrue(item.getCategory().getId().equals(ObjectMock.ITEM_DTO_CATEGORY_ID));
    }

    @Test
    public void shouldReturn500WhenIdNotExists() {
        given()
                .port(port)
                .contentType("application/json")
                .pathParam("id", 1000)
                .body(ObjectMock.mockItemDto())
                .when()
                .put("/{id}")
                .then()
                .statusCode(500);
    }

    @Test
    public void shouldReturn400WhenItemIncomplete() {
        given()
                .port(port)
                .contentType("application/json")
                .pathParam("id", id)
                .body(new ItemDto())
                .when()
                .put("/{id}")
                .then()
                .statusCode(400);
    }
}
