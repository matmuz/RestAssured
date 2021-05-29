package tests;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static booking.Booking.*;

public class BookingTests extends BaseTest {

    private static String id;
    private static final String ADDITIONAL_NEED = "monster";

    @Test
    public void shouldReturnListOfBookings() {

        Response response = Requests.getBookings();

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        Assertions.assertThat(json.getList(BOOKING_ID)
                                      .size())
                .isPositive();
    }

    @Test
    public void shouldCreateNewBooking() {

        Response response = Requests.postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        JsonPath json = response.jsonPath();

        Assertions.assertThat(json.getString(BOOKING_FIRSTNAME))
                .isEqualTo(user.getFirstName());
    }

    @Test
    public void shouldGetCreatedBooking() {

        Response response = Requests.postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);

        JsonPath json = response.jsonPath();

        id = json.getString(BOOKING_ID);

        response = Requests.getBooking(id);

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        json = response.jsonPath();

        Assertions.assertThat(json.getString(FIRSTNAME))
                .isEqualTo(user.getFirstName());
    }

    @Test
    public void shouldDeleteCreatedBooking() {

        Response response = Requests.postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);

        JsonPath json = response.jsonPath();

        id = json.getString(BOOKING_ID);

        response = Requests.deleteBooking(token, id);

        Assertions.assertThat(response.statusCode())
                .isEqualTo(201);
    }

    @Test
    public void shouldNotFindDeletedBooking() {

        Response response = Requests.postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);

        JsonPath json = response.jsonPath();

        id = json.getString(BOOKING_ID);

        Requests.deleteBooking(token, id);

        response = Requests.getBooking(id);

        Assertions.assertThat(response.statusCode())
                .isEqualTo(404);
    }

    @Test
    public void shouldPartiallyUpdateBooking() {

        Response response = Requests.postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);

        JsonPath json = response.jsonPath();

        id = json.getString(BOOKING_ID);

        response = Requests.updateBooking(token, id, user.getFirstName(), user.getLastName());

        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);
    }

    @Test
    public void shouldGetUpdatedBooking() {

        Response response = Requests.postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);

        JsonPath json = response.jsonPath();

        id = json.getString(BOOKING_ID);

        Requests.updateBooking(token, id, user.getFirstName(), user.getLastName());

        response = Requests.getBooking(id);

        json = response.jsonPath();

        Assertions.assertThat(json.getString(FIRSTNAME))
                .isEqualTo(user.getFirstName());
    }
}