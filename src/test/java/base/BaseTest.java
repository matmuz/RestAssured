package base;

import data.JsonPicker;
import data.UserProvider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

import static io.restassured.RestAssured.*;

//TODO: Utility methods should be placed elsewhere

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

        healthCheck();
        createToken();
    }

    public static void healthCheck(){
        Response response = given().when()
                .get(baseURI + "/ping")
                .then()
                .extract()
                .response();

        int statusCode = response.statusCode();

        if (statusCode != 201) {
            System.out.println("Health check failed with status " + statusCode);
            System.exit(1);
        }
    }

    public static void createToken(){

        JSONObject body = new JSONObject();
        body.put("username", admin.getUsername());
        body.put("password", admin.getPassword());

        Response response = given().contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post(baseURI + "/auth")
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        token = json.getString("token");
    }

    public static JSONObject prepareNewBooking(){

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2021-01-23");
        bookingDates.put("checkout", "2021-02-01");

        JSONObject booking = new JSONObject();
        booking.put("firstname", user.getFirstName());
        booking.put("lastname", user.getLastName());
        booking.put("totalprice", "115");
        booking.put("depositpaid", "15");
        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", "monster");

        return booking;
    }

    public static JSONObject partiallyUpdateBooking(){

        JSONObject update = new JSONObject();
        update.put("firstname", user.getFirstName());
        update.put("lastname", user.getLastName());

        return update;
    }
}