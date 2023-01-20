package booking;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A collection of methods that prepare needed Json objects to be sent to the API
 */
public final class Booking {

    public static final String FIRSTNAME_KEY = "firstname";
    private static final String LASTNAME_KEY = "lastname";
    private static final String CHECK_IN_KEY = "checkin";
    private static final String CHECK_OUT_KEY = "checkout";
    private static final String TOTAL_PRICE_KEY = "totalprice";
    private static final String DEPOSIT_PAID_KEY = "depositpaid";
    private static final String BOOKING_DATES_KEY = "bookingdates";
    private static final String ADDITIONAL_NEEDS_KEY = "additionalneeds";

    /**
     * Private constructor - do not let to create an instance
     */
    private Booking() {
    }

    /**
     * Utility method form preparing a new booking as Json
     *
     * @param firstName       user's first name for the booking
     * @param lastName        user's last name for the booking
     * @param additionalNeeds additional need for the booking
     * @return JSONObject
     */
    public static JSONObject prepareNewBooking(String firstName, String lastName, String additionalNeeds) {
        LocalDate checkInDate = java.time.LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(7);
        int totalPrice = new Random().nextInt(200);
        int depositPaid = totalPrice / 2;

        BookingModel bookingModel = new BookingModel(firstName, lastName, totalPrice, depositPaid, checkInDate,
                                                     checkOutDate,
                                                     additionalNeeds);
        Map<String, Object> bookingEntity = new HashMap<>();

        JSONObject bookingDates = new JSONObject();
        bookingDates.put(CHECK_IN_KEY, bookingModel.getCheckInDate());
        bookingDates.put(CHECK_OUT_KEY, bookingModel.getCheckOutDate());

        bookingEntity.put(FIRSTNAME_KEY, bookingModel.getFirstname());
        bookingEntity.put(LASTNAME_KEY, bookingModel.getLastname());
        bookingEntity.put(TOTAL_PRICE_KEY, bookingModel.getTotalPrice());
        bookingEntity.put(DEPOSIT_PAID_KEY, bookingModel.getDeposit());
        bookingEntity.put(BOOKING_DATES_KEY, bookingDates);
        bookingEntity.put(ADDITIONAL_NEEDS_KEY, bookingModel.getAdditionalNeeds());

        return new JSONObject(bookingEntity);
    }

    /**
     * Utility method for updating already existing booking as Json
     *
     * @param firstName user's first name for the booking update
     * @param lastName  user's last name for the booking update
     * @return JSONObject
     */
    public static JSONObject prepareBookingUpdate(String firstName, String lastName) {
        JSONObject update = new JSONObject();
        update.put(FIRSTNAME_KEY, firstName);
        update.put(LASTNAME_KEY, lastName);

        return update;
    }
}