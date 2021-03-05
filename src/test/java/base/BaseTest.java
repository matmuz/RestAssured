package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.basic;

public class BaseTest {

    public static String id;
    public static String token;
    public static String username = "admin";
    public static String password = "password123";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
        RestAssured.authentication = basic(username, password);
    }
}
