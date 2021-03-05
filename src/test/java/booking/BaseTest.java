package booking;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.basic;

public class BaseTest {

    static String id;
    static String token;
    static String username = "admin";
    static String password = "password123";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
        RestAssured.authentication = basic(username, password);
    }
}
