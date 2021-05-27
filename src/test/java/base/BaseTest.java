package base;

import data.Admin;
import data.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

import static io.restassured.RestAssured.basic;
import static tests.TestMethods.createToken;
import static tests.TestMethods.healthCheck;

public class BaseTest {

    protected static String id;
    protected static String token;
    protected static User user;
    protected static Admin admin;

    @BeforeAll
    public static void setUp() throws IOException {
        user = User.getUser();
        admin = Admin.get("src/test/resources/data/admin.json");
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        RestAssured.basePath = "/booking";
        RestAssured.authentication = basic(admin.getUsername(), admin.getPassword());

        healthCheck();
        token = createToken(admin.getUsername(), admin.getPassword());
    }
}