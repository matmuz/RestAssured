package base;

import data.UserProvider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.basic;

public class BaseTest {

    protected static String id;
    protected static String token;
    protected static UserProvider user;

    @BeforeAll
    public static void setUp() {
        user = new UserProvider();
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
        RestAssured.authentication = basic(user.getUsername(), user.getPassword());
    }
}
