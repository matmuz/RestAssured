package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static booking.Booking.*;
import static io.restassured.RestAssured.*;

public final class Requests {

    private Requests() {

    }

    public static Response getBooking(String id) {

        return given()
                .when()
                .get(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();
    }

    public static Response getBookings() {

        return given()
                .when()
                .get(baseURI + basePath)
                .then()
                .extract()
                .response();
    }

    public static Response postBooking(String firstName, String lastName, String additionalNeed) {

        return given()
                .contentType(ContentType.JSON)
                .body(prepareNewBooking(firstName, lastName, additionalNeed).toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();
    }

    public static Response deleteBooking(String token, String id) {

        return given().header(COOKIE, (TOKEN + "=") + token)
                .when()
                .delete(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();
    }

    public static Response updateBooking(String token, String id, String firstName, String lastName) {

        return given().header(COOKIE, (TOKEN + "=") + token)
                .contentType(ContentType.JSON)
                .body(prepareBookingUpdate(firstName, lastName).toString())
                .when()
                .patch(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();
    }
}
