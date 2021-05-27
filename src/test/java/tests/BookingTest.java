package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static tests.TestMethods.prepareBookingUpdate;
import static tests.TestMethods.prepareNewBooking;

public class BookingTest extends BaseTest {

    @Test
    public void shouldReturnListOfBookings() {

        Response response = given()
                .when()
                .get(baseURI + basePath)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        Assertions.assertThat(json.getList("bookingid")
                .size())
                .isPositive();
    }

    @Test
    public void shouldCreateNewBooking() {

        Response response = given()
                .contentType(ContentType.JSON)
                .body(prepareNewBooking(user.getFirstName(), user.getLastName()).toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        Assertions.assertThat(json.getString("booking.firstname"))
                .isEqualTo(user.getFirstName());
    }

    @Test
    public void shouldGetCreatedBooking() {

        Response response = given().contentType(ContentType.JSON)
                .body(prepareNewBooking(user.getFirstName(), user.getLastName()).toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("bookingid");

        response = given()
                .when()
                .get(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        json = response.jsonPath();

        Assertions.assertThat(json.getString("firstname"))
                .isEqualTo(user.getFirstName());
    }

    @Test
    public void shouldDeleteCreatedBooking() {

        Response response = given().contentType(ContentType.JSON)
                .body(prepareNewBooking(user.getFirstName(), user.getLastName()).toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("bookingid");

        response = given().header("Cookie", "token=" + token)
                .when()
                .delete(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(201);
    }

    @Test
    public void shouldNotFindDeletedBooking() {

        Response response = given().contentType(ContentType.JSON)
                .body(prepareNewBooking(user.getFirstName(), user.getLastName()).toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("bookingid");

        given().header("Cookie", "token=" + token)
                .when()
                .delete(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();

        response = given().when()
                .get(baseURI + basePath + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(404);
    }

    @Test
    public void shouldPartiallyUpdateBooking() {

        Response response = given().contentType(ContentType.JSON)
                .body(prepareNewBooking(user.getFirstName(), user.getLastName()).toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("bookingid");

        response = given().header("Cookie", "token=" + token)
                .contentType(ContentType.JSON)
                .body(prepareBookingUpdate(user.getFirstName(), user.getLastName()).toString())
                .when()
                .patch(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);
    }

    @Test
    public void shouldGetUpdatedBooking() {

        Response response = given().contentType(ContentType.JSON)
                .body(prepareNewBooking(user.getFirstName(), user.getLastName()).toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        id = json.getString("bookingid");

        given().header("Cookie", "token=" + token)
                .contentType(ContentType.JSON)
                .body(prepareBookingUpdate(user.getFirstName(), user.getLastName()).toString())
                .when()
                .patch(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();

        response = given().when()
                .get(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();

        json = response.jsonPath();

        Assertions.assertThat(json.getString("firstname"))
                .isEqualTo(user.getFirstName());
    }
}