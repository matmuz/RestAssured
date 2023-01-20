package requests;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import static booking.BookingUtils.getBookingUpdate;
import static booking.BookingUtils.getNewBooking;
import static io.restassured.RestAssured.*;
import static requests.RequestsConstants.*;

/**
 * A collection of methods that cover all the basic requests to the restful-booker API
 */
@Slf4j
public final class Requests {

    @Step("Health check")
    public static int healthCheck() {
        Response response = get(baseURI + PING_PATH).then().extract().response();
        log.info("Health check finished with status code: " + response.statusCode());
        return response.statusCode();
    }

    /**
     * Method for getting a token from the API
     *
     * @param username admin username
     * @param password admin password
     * @return Token as String
     */
    @Step("Create token")
    public static String createToken(String username, String password) {
        JSONObject body = new JSONObject();
        body.put(USERNAME, username);
        body.put(PASSWORD, password);

        Response response = given().contentType(ContentType.JSON)
                                   .body(body.toString())
                                   .when()
                                   .post(baseURI + AUTH_PATH)
                                   .then()
                                   .extract()
                                   .response();

        JsonPath json = response.jsonPath();
        log.debug(json.prettyPrint());
        return json.getString(TOKEN);
    }

    /**
     * Method for getting booking by id from the API
     *
     * @param id Existing booking id
     * @return Response from the API
     */
    @Step("Get booking")
    public static Response getBooking(String id) {
        Response response = get(baseURI + basePath + "/" + id).then().extract().response();
        if (response.statusCode() != 200) {
            log.warn("Did not find desired booking with status code: " + response.statusCode());
        } else {
            log.debug(response.jsonPath().prettyPrint());
        }
        return response;
    }

    /**
     * Method for getting all the bookings from the API
     *
     * @return Response from the API
     */
    @Step("Get bookings")
    public static Response getBookings() {
        Response response = get(baseURI + basePath).then().extract().response();
        log.debug(response.jsonPath().prettyPrint());
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
    @Step("Post booking")
    public static Response postBooking(String firstName, String lastName, String additionalNeed) {
        Response response = given().contentType(ContentType.JSON)
                                   .body(getNewBooking(firstName, lastName, additionalNeed).toString())
                                   .when()
                                   .post(baseURI + basePath)
                                   .then()
                                   .extract()
                                   .response();
        log.debug(response.jsonPath().prettyPrint());
        return response;
    }

    /**
     * Method for deleting a booking from the API
     *
     * @param token user's valid token
     * @param id    id of a booking to delete
     * @return Response from the API
     */
    @Step("Delete booking")
    public static Response deleteBooking(String token, String id) {
        Response response = given().header(COOKIE, (TOKEN + "=") + token)
                                   .when()
                                   .delete(baseURI + basePath + "/" + id)
                                   .then()
                                   .extract()
                                   .response();
        log.info("Delete request finished with status code: " + response.statusCode());
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
    @Step("Put booking")
    public static Response putBooking(String token, String id, String firstName, String lastName,
                                      String additionalNeed) {
        Response response = given().header(COOKIE, (TOKEN + "=") + token)
                                   .contentType(ContentType.JSON)
                                   .body(getNewBooking(firstName, lastName, additionalNeed).toString())
                                   .when()
                                   .put(baseURI + basePath + "/" + id)
                                   .then()
                                   .extract()
                                   .response();
        log.debug(response.jsonPath().prettyPrint());
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
    @Step("Patch booking")
    public static Response patchBooking(String token, String id, String firstName, String lastName) {
        Response response = given().header(COOKIE, (TOKEN + "=") + token)
                                   .contentType(ContentType.JSON)
                                   .body(getBookingUpdate(firstName, lastName).toString())
                                   .when()
                                   .patch(baseURI + basePath + "/" + id)
                                   .then()
                                   .extract()
                                   .response();
        log.debug(response.jsonPath().prettyPrint());
        return response;
    }
}