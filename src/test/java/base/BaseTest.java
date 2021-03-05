package base;

import data.JsonPicker;
import data.UserProvider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

import static io.restassured.RestAssured.basic;

public class BaseTest {

    protected static String id;
    protected static String token;
    protected static UserProvider user;
    protected static JsonPicker admin;

    @BeforeAll
    public static void setUp() throws IOException {
        user = new UserProvider();
        admin = JsonPicker.get("src/test/resources/data/admin.json");
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
        RestAssured.authentication = basic(admin.getUsername(), admin.getPassword());
    }
}