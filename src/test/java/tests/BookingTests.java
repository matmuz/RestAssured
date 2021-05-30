package tests;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static booking.Booking.*;
import static booking.Requests.*;
import static booking.Utils.getId;
import static booking.Utils.getJson;

/**
 * A collection of tests that cover all the API methods in simple scenarios
 * <p>
 * The keyword "should" determines what a particular test wants to achieve
 */

public final class BookingTests extends BaseTest {

    private static final String ADDITIONAL_NEED = "monster";
    private String id;
    private Response response;
    private JsonPath json;

    @Test
    public void shouldReturnListOfBookings() {

        response = getBookings();
        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        json = getJson(response);
        Assertions.assertThat(json.getList(BOOKING_ID)
                                      .size())
                .isPositive();
    }

    @Test
    public void shouldCreateNewBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);

        json = getJson(response);
        Assertions.assertThat(json.getString(BOOKING_FIRSTNAME))
                .isEqualTo(user.getFirstName());
    }

    @Test
    public void shouldGetCreatedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        response = getBooking(id);
        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);
        json = getJson(response);
        Assertions.assertThat(json.getString(FIRSTNAME))
                .isEqualTo(user.getFirstName());
    }

    @Test
    public void shouldDeleteCreatedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        response = deleteBooking(token, id);
        Assertions.assertThat(response.statusCode())
                .isEqualTo(201);
    }

    @Test
    public void shouldNotFindDeletedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        deleteBooking(token, id);
        response = getBooking(id);
        Assertions.assertThat(response.statusCode())
                .isEqualTo(404);
    }

    @Test
    public void shouldPartiallyUpdateBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        response = updateBooking(token, id, user.getFirstName(), user.getLastName());
        Assertions.assertThat(response.statusCode())
                .isEqualTo(200);
    }

    @Test
    public void shouldGetUpdatedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        updateBooking(token, id, user.getFirstName(), user.getLastName());
        response = getBooking(id);
        json = getJson(response);
        Assertions.assertThat(json.getString(FIRSTNAME))
                .isEqualTo(user.getFirstName());
    }
}