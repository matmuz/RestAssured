package booking;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Random;

/**
 * A collection of methods that prepare needed Json objects to be sent to the API
 */
public final class Booking {

    public static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String CHECK_IN = "checkin";
    private static final String CHECK_OUT = "checkout";
    private static final String TOTAL_PRICE = "totalprice";
    private static final String DEPOSIT_PAID = "depositpaid";
    private static final String BOOKING_DATES = "bookingdates";
    private static final String ADDITIONAL_NEEDS = "additionalneeds";

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
        LocalDate date = java.time.LocalDate.now();
        String checkInDate = date.toString();
        String checkOutDate = date.plusDays(7).toString();

        JSONObject bookingDates = new JSONObject();
        bookingDates.put(CHECK_IN, checkInDate);
        bookingDates.put(CHECK_OUT, checkOutDate);

        Random random = new Random();
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

    /**
     * Utility method for updating already existing booking as Json
     *
     * @param firstName user's first name for the booking update
     * @param lastName  user's last name for the booking update
     * @return JSONObject
     */
    public static JSONObject prepareBookingUpdate(String firstName, String lastName) {
        JSONObject update = new JSONObject();
        update.put(FIRSTNAME, firstName);
        update.put(LASTNAME, lastName);

        return update;
    }
}