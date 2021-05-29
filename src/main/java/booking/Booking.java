package booking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Random;

public final class Booking {

    public static final String BOOKING_ID = "bookingid";
    public static final String COOKIE = "Cookie";
    public static final String BOOKING_FIRSTNAME = "booking.firstname";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String CHECK_IN = "checkin";
    private static final String CHECK_OUT = "checkout";
    private static final String TOTAL_PRICE = "totalprice";
    private static final String DEPOSIT_PAID = "depositpaid";
    private static final String BOOKING_DATES = "bookingdates";
    private static final String ADDITIONAL_NEEDS = "additionalneeds";

    public static final String TOKEN = "token";
    private static final String PING_PATH = "/ping";
    private static final String AUTH_PATH = "/auth";

    private static final Random random = new Random();

    private Booking() {
    }

    public static int healthCheck() {
        Response response = RestAssured.given().when()
                .get(RestAssured.baseURI + PING_PATH)
                .then()
                .extract()
                .response();

        return response.statusCode();
    }

    public static String createToken(String username, String password) {

        JSONObject body = new JSONObject();
        body.put(USERNAME, username);
        body.put(PASSWORD, password);

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post(RestAssured.baseURI + AUTH_PATH)
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        return json.getString(TOKEN);
    }

    public static JSONObject prepareNewBooking(String firstName, String lastName, String additionalNeeds) {

        LocalDate date = java.time.LocalDate.now();
        String checkInDate = date.toString();
        String checkOutDate = date.plusDays(7)
                .toString();

        JSONObject bookingDates = new JSONObject();
        bookingDates.put(CHECK_IN, checkInDate);
        bookingDates.put(CHECK_OUT, checkOutDate);

        String totalPrice = Double.toString(random.nextInt(200));
        String depositPaid = Double.toString(Double.parseDouble(totalPrice) / 2);

        JSONObject booking = new JSONObject();
        booking.put(FIRSTNAME, firstName);
        booking.put(LASTNAME, lastName);
        booking.put(TOTAL_PRICE, totalPrice);
        booking.put(DEPOSIT_PAID, depositPaid);
        booking.put(BOOKING_DATES, bookingDates);
        booking.put(ADDITIONAL_NEEDS, additionalNeeds);

        return booking;
    }

    public static JSONObject prepareBookingUpdate(String firstName, String lastName) {

        JSONObject update = new JSONObject();
        update.put(FIRSTNAME, firstName);
        update.put(LASTNAME, lastName);

        return update;
    }
}
