package booking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingTest {

    // given - specification
    // when - request
    // then - assertion

    static String id;
    static String token;
    static String username = "admin";
    static String password = "password123";

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = "http://localhost:3001";
        RestAssured.port = 3001;
        RestAssured.basePath = "/booking";
        RestAssured.authentication = basic(username, password);
    }

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
                .post(baseURI + basePath)
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
                .isEqualTo("Diukey101");
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
                .post(baseURI + basePath)
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
    @Order(8)
    public void shouldPartiallyUpdateBooking() {

        JSONObject update = new JSONObject();
        update.put("firstname", "mat");
        update.put("lastname", "muz");

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