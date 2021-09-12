package tests;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static booking.Booking.FIRSTNAME;
import static booking.Requests.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Utils.getId;
import static utils.Utils.getJson;

/**
 * A collection of tests that cover all the API methods in simple scenarios
 * <p>
 * The keyword "should" determines what a particular test wants to achieve
 */
public final class BookingTests extends BaseTest {

    private static final String ADDITIONAL_NEED = "monster";
    private static final String UPDATED_FIRSTNAME = "Diukey";
    private String id;
    private Response response;
    private JsonPath json;

    @Test
    public void shouldGetListOfBookings() {

        response = getBookings();
        assertThat(response.statusCode())
                .isEqualTo(200);
        json = getJson(response);
        assertThat(json.getList(BOOKING_ID)
                .size())
                .isPositive();
    }

    @Test
    public void shouldGetPostedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        response = getBooking(id);
        assertThat(response.statusCode())
                .isEqualTo(200);
        json = getJson(response);
        assertThat(json.getString(FIRSTNAME))
                .isEqualTo(user.getFirstName());
    }

    @Test
    public void shouldGetPutBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        putBooking(token, id, UPDATED_FIRSTNAME, user.getLastName(), ADDITIONAL_NEED);
        response = getBooking(id);
        assertThat(response.statusCode())
                .isEqualTo(200);
        json = getJson(response);
        id = getId(json);
        assertThat(json.getString(FIRSTNAME))
                .isEqualTo(UPDATED_FIRSTNAME);
    }

    @Test
    public void shouldGetPatchedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        patchBooking(token, id, UPDATED_FIRSTNAME, user.getLastName());
        response = getBooking(id);
        assertThat(response.statusCode())
                .isEqualTo(200);
        json = getJson(response);
        assertThat(json.getString(FIRSTNAME))
                .isEqualTo(UPDATED_FIRSTNAME);
    }

    @Test
    public void shouldNotGetDeletedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        deleteBooking(token, id);
        response = getBooking(id);
        assertThat(response.statusCode())
                .isEqualTo(404);
    }
}