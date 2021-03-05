package booking;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.*;

/**
 *     given - specification
 *     when - request
 *     then - assertion
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingTest extends BaseTest {

    @Test
    @Order(1)
    public void shouldCreateToken() {

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

        token = json.getString("token");

        Assertions.assertThat(json.getString("token"))
                .isNotEmpty();
    }

    @Test
    @Order(2)
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
    @Order(3)
    public void shouldCreateNewBooking() {

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2021-01-23");
        bookingDates.put("checkout", "2021-02-01");

        JSONObject booking = new JSONObject();
        booking.put("firstname", "firstname123");
        booking.put("lastname", "lastname123");
        booking.put("totalprice", "115");
        booking.put("depositpaid", "15");
        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", "monster");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking.toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        id = json.getString("bookingid");

        Assertions.assertThat(json.getString("booking.firstname"))
                .isEqualTo("firstname123");
    }

    @Test
    @Order(4)
    public void shouldGetCreatedBooking() {

        Response response = given()
                .when()
                .get(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        Assertions.assertThat(json.getString("firstname"))
                .isEqualTo("firstname123");
    }

    @Test
    @Order(5)
    public void shouldDeleteCreatedBooking() {

        Response response = given().header("Cookie", "token=" + token)
                .when()
                .delete(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(201);
    }

    @Test
    @Order(6)
    public void shouldNotFindDeletedBooking() {

        Response response = given().when()
                .get(baseURI + basePath + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(404);
    }

    @Test
    @Order(7)
    public void shouldCreateAnotherBooking() {

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2021-01-23");
        bookingDates.put("checkout", "2021-02-01");

        JSONObject booking = new JSONObject();
        booking.put("firstname", "firstname456");
        booking.put("lastname", "lastname456");
        booking.put("totalprice", "225");
        booking.put("depositpaid", "25");
        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", "cookie");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking.toString())
                .when()
                .post(baseURI + basePath)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        id = json.getString("bookingid");

        Assertions.assertThat(json.getString("booking.firstname"))
                .isEqualTo("firstname456");
    }

    @Test
    @Order(8)
    public void shouldPartiallyUpdateBooking() {

        JSONObject update = new JSONObject();
        update.put("firstname", "firstname123");
        update.put("lastname", "lastname123");

        Response response = given().header("Cookie", "token=" + token)
                .contentType(ContentType.JSON)
                .body(update.toString())
                .when()
                .patch(baseURI + basePath + "/" + id)
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPeek());

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);
    }
}