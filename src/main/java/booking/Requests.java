package booking;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import logging.Logging;
import org.json.JSONObject;

import static booking.Booking.prepareBookingUpdate;
import static booking.Booking.prepareNewBooking;
import static io.restassured.RestAssured.*;

/**
 * A collection of methods that cover all the basic requests to the restful-booker API
 */
public final class Requests {

    /**
     * Json's path for particular requirements
     */
    public static final String BOOKING_ID = "bookingid";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String COOKIE = "Cookie";
    private static final String TOKEN = "token";

    private static final String PING_PATH = "/ping";
    private static final String AUTH_PATH = "/auth";

    private Requests() {
    }

    /**
     * Method for checking availability of the API
     *
     * @return Status code as an int
     */
    public static int healthCheck() {
        Response response = get(baseURI + PING_PATH)
                .then()
                .extract()
                .response();
        System.out.println("Health check finished with status code: " + response.statusCode());
        return response.statusCode();
    }

    /**
     * Method for getting a token from the API
     *
     * @param username admin username
     * @param password admin password
     * @return Token as String
     */
    public static String createToken(String username, String password) {
        JSONObject body = new JSONObject();
        body.put(USERNAME, username);
        body.put(PASSWORD, password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post(baseURI + AUTH_PATH)
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Logging.log(json.prettyPrint());
        return json.getString(TOKEN);
    }

    /**
     * Method for getting booking by id from the API
     *
     * @param id Existing booking id
     * @return Response from the API
     */
    public static Response getBooking(String id) {
        Response response = get(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();
        if (response.statusCode() != 200) {
            System.out.println("Did not find desired booking with status code: " + response.statusCode());
        } else {
            Logging.log(response.jsonPath().prettyPrint());
        }
        return response;
    }

    /**
     * Method for getting all the bookings from the API
     *
     * @return Response from the API
     */
    public static Response getBookings() {
        Response response = get(baseURI + basePath)
                .then()
                .extract()
                .response();
        Logging.log(response.jsonPath().prettyPrint());
        return response;
    }

    /**
     * Method for posting a booking to the API
     *
     * @param firstName      user's first name for the booking
     * @param lastName       user's last name for the booking
     * @param additionalNeed user's additional need for the booking
     * @return Response from the API
     */
    public static Response postBooking(String firstName, String lastName, String additionalNeed) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(prepareNewBooking(firstName, lastName, additionalNeed).toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();
        Logging.log(response.jsonPath().prettyPrint());
        return response;
    }

    /**
     * Method for deleting a booking from the API
     *
     * @param token user's valid token
     * @param id    id of a booking to delete
     * @return Response from the API
     */
    public static Response deleteBooking(String token, String id) {
        Response response = given().header(COOKIE, (TOKEN + "=") + token)
                .when()
                .delete(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();
        System.out.println("Delete request finished with status code: " + response.statusCode());
        return response;
    }

    /**
     * Method for putting a booking to the API
     *
     * @param token          user's valid token
     * @param id             id of a booking to update
     * @param firstName      user's first name to update
     * @param lastName       user's last name to update
     * @param additionalNeed user's additional need to update
     * @return Response from the API
     */
    public static Response putBooking(String token, String id, String firstName, String lastName, String additionalNeed) {
        Response response = given().header(COOKIE, (TOKEN + "=") + token)
                .contentType(ContentType.JSON)
                .body(prepareNewBooking(firstName, lastName, additionalNeed).toString())
                .when()
                .put(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();
        Logging.log(response.jsonPath().prettyPrint());
        return response;
    }

    /**
     * Method for patching a booking in the API
     *
     * @param token     user's valid token
     * @param id        id of a booking to update
     * @param firstName user's first name to update
     * @param lastName  user's last name to update
     * @return Response from the API
     */
    public static Response patchBooking(String token, String id, String firstName, String lastName) {
        Response response = given().header(COOKIE, (TOKEN + "=") + token)
                .contentType(ContentType.JSON)
                .body(prepareBookingUpdate(firstName, lastName).toString())
                .when()
                .patch(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();
        Logging.log(response.jsonPath().prettyPrint());
        return response;
    }
}