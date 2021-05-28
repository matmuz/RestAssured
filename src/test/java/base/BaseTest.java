package base;

import data.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static data.Admin.PASSWORD;
import static data.Admin.USERNAME;
import static io.restassured.RestAssured.basic;
import static tests.TestMethods.createToken;
import static tests.TestMethods.healthCheck;

public class BaseTest {

    protected static String id;
    protected static String token;
    protected static User user;

    @BeforeAll
    public static void setUp() {
        user = User.getUser();
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
        RestAssured.authentication = basic(USERNAME, PASSWORD);

        healthCheck();
        token = createToken(USERNAME, PASSWORD);
    }
}