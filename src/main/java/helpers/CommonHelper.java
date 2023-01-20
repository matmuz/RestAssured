package helpers;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static requests.RequestsConstants.BOOKING_ID;

public final class CommonHelper {

    public static JsonPath getJson(Response response) {
        return response.jsonPath();
    }

    public static String getId(JsonPath json) {
        return json.getString(BOOKING_ID);
    }
}