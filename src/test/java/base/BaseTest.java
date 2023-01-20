package base;

import user.User;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import static config.ConfigurationRetriever.getConfiguration;
import static io.restassured.RestAssured.basic;
import static requests.Requests.createToken;
import static requests.Requests.healthCheck;


@Slf4j
public abstract class BaseTest {

    protected static String token;
    protected static User user;

    @BeforeTest
    @Parameters({"baseURI", "basePath"})
    public void setUp(String baseURI, String basePath) {
        String username = getConfiguration().username();
        String password = getConfiguration().password();
        user = User.getUser();
        RestAssured.baseURI = baseURI;
        RestAssured.basePath = basePath;
        RestAssured.authentication = basic(username, password);

        if (healthCheck() != 201) {
            log.error("restful-booker service is not available at this moment. The tests will not run");
            System.exit(0);
        }

        token = createToken(username, password);
    }
}