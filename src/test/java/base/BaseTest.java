package base;

import data.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static data.Admin.PASSWORD;
import static data.Admin.USERNAME;
import static io.restassured.RestAssured.basic;
import static booking.Requests.createToken;
import static booking.Requests.healthCheck;

/**
 * Base test class responsible for test preparation
 */

public class BaseTest {

    protected static String token;
    protected static User user;

    /**
     * setUp method that is run before all test using @BeforeAll annotation
     * <p>
     * prepares for tests:
     * - gets test user
     * - sets base path and basic endpoint
     * - sets authentication method
     * - check availability of the API
     * - creates token
     */

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