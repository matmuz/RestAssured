package base;

import data.User;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import static booking.Requests.createToken;
import static booking.Requests.healthCheck;
import static data.Admin.PASSWORD;
import static data.Admin.USERNAME;
import static io.restassured.RestAssured.basic;

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
    @BeforeTest
    @Parameters({"baseURI", "basePath"})
    public void setUp(String baseURI, String basePath) {
        user = User.getUser();
        RestAssured.baseURI = baseURI;
        RestAssured.basePath = basePath;
        RestAssured.authentication = basic(USERNAME, PASSWORD);

        if (healthCheck() != 201) {
            throw new RuntimeException("restful-booker is not available at this moment. The tests will not run");
        }

        token = createToken(USERNAME, PASSWORD);
    }
}