package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public final class TestMethods {

    private TestMethods() {
    }

    public static void healthCheck() {
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

    public static String createToken(String username, String password) {

        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("password", password);

        Response response = given().contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post(baseURI + "/auth")
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        return json.getString("token");
    }

    public static JSONObject prepareNewBooking(String firstName, String lastName) {

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2021-01-23");
        bookingDates.put("checkout", "2021-02-01");

        JSONObject booking = new JSONObject();
        booking.put("firstname", firstName);
        booking.put("lastname", lastName);
        booking.put("totalprice", "115");
        booking.put("depositpaid", "15");
        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", "monster");

        return booking;
    }

    public static JSONObject prepareBookingUpdate(String firstName, String lastName) {

        JSONObject update = new JSONObject();
        update.put("firstname", firstName);
        update.put("lastname", lastName);

        return update;
    }
}
