package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static booking.Requests.BOOKING_ID;

/**
 * A collection of utility methods that are repeated in tests
 */

public final class Utils {

    private Utils() {
    }

    /**
     * Gets response as json
     *
     * @param response response from the API
     * @return json
     */

    public static JsonPath getJson(Response response) {
        return response.jsonPath();
    }

    /**
     * Get id from json as String
     *
     * @param json json from response
     * @return String id
     */

    public static String getId(JsonPath json) {
        return json.getString(BOOKING_ID);
    }
}