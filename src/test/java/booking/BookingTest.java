package booking;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class BookingTest {

    // given - specification
    // when - request
    // then - assertion

    static String id;

    @Test
    public void shouldReturnListOfBookings() {

        Response response = given()
                .when()
                .get("http://localhost:3001/booking")
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

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2021-01-23");
        bookingDates.put("checkout", "2021-02-01");

        JSONObject booking = new JSONObject();
        booking.put("firstname", "Diukey101");
        booking.put("lastname", "PSN");
        booking.put("totalprice", "115");
        booking.put("depositpaid", "15");
        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", "monster");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking.toString())
                .when()
                .post("http://localhost:3001/booking")
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        id = json.getString("bookingid");

        Assertions.assertThat(json.getString("booking.firstname"))
                .isEqualTo("Diukey101");
    }

    @Test
    public void shouldGetCreatedBooking() {

        Response response = given()
                .when()
                .get("http://localhost:3001/booking/" + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        Assertions.assertThat(json.getString("firstname"))
                .isEqualTo("Diukey101");
    }

    @Test
    public void shouldDeleteCreatedBooking() {

        Response response = given().header("Cookie", "token=065a20e5f167859")
                .when()
                .delete("http://localhost:3001/booking/" + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(201);
    }

    @Test
    public void shouldNotFindDeletedBooking() {

        Response response = given().when()
                .get("http://localhost:3001/booking/" + id)
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(404);
    }

    @Test
    public void shouldCreateAnotherBooking() {

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2021-01-23");
        bookingDates.put("checkout", "2021-02-01");

        JSONObject booking = new JSONObject();
        booking.put("firstname", "Diukey101");
        booking.put("lastname", "PSN");
        booking.put("totalprice", "115");
        booking.put("depositpaid", "15");
        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", "monster");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking.toString())
                .when()
                .post("http://localhost:3001/booking")
                .then()
                .extract()
                .response();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        id = json.getString("bookingid");

        Assertions.assertThat(json.getString("booking.firstname"))
                .isEqualTo("Diukey101");
    }

    @Test
    public void shouldPartiallyUpdateBooking() {

        JSONObject update = new JSONObject();
        update.put("firstname", "mat");
        update.put("lastname", "muz");

        Response response = given().header("Cookie", "token=065a20e5f167859")
                .contentType(ContentType.JSON)
                .body(update.toString())
                .when()
                .patch("http://localhost:3001/booking/" + id)
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPeek());

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);
    }
}