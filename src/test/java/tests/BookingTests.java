package tests;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static booking.BookingKeys.FIRSTNAME_KEY;
import static helpers.CommonHelper.getId;
import static helpers.CommonHelper.getJson;
import static requests.Requests.*;
import static requests.RequestsConstants.BOOKING_ID;
import static utils.CustomAssertions.assertThat;

public final class BookingTests extends BaseTest {

    private String id;
    private Response response;
    private JsonPath json;

    private static final String ADDITIONAL_NEED = "monster";
    private static final String UPDATED_FIRSTNAME = "Diukey";

    @Test
    public void shouldGetListOfBookings() {

        response = getBookings();
        assertThat(response.statusCode()).isEqualTo(200);
        json = getJson(response);
        assertThat(json.getList(BOOKING_ID).size()).isPositive();
    }

    @Test
    public void shouldGetPostedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        response = getBooking(id);
        assertThat(response.statusCode()).isEqualTo(200);
        json = getJson(response);
        assertThat(json.getString(FIRSTNAME_KEY)).isEqualTo(user.getFirstName());
    }

    @Test
    public void shouldGetPutBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        putBooking(token, id, UPDATED_FIRSTNAME, user.getLastName(), ADDITIONAL_NEED);
        response = getBooking(id);
        assertThat(response.statusCode()).isEqualTo(200);
        json = getJson(response);
        id = getId(json);
        assertThat(json.getString(FIRSTNAME_KEY)).isEqualTo(UPDATED_FIRSTNAME);
    }

    @Test
    public void shouldGetPatchedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        patchBooking(token, id, UPDATED_FIRSTNAME, user.getLastName());
        response = getBooking(id);
        assertThat(response.statusCode()).isEqualTo(200);
        json = getJson(response);
        assertThat(json.getString(FIRSTNAME_KEY)).isEqualTo(UPDATED_FIRSTNAME);
    }

    @Test
    public void shouldNotGetDeletedBooking() {

        response = postBooking(user.getFirstName(), user.getLastName(), ADDITIONAL_NEED);
        json = getJson(response);
        id = getId(json);

        deleteBooking(token, id);
        response = getBooking(id);
        assertThat(response.statusCode()).isEqualTo(404);
    }
}