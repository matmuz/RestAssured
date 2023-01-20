package booking;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static booking.BookingKeys.*;

public final class BookingUtils {

    public static JSONObject getNewBooking(String firstName, String lastName, String additionalNeeds) {
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

    public static JSONObject getBookingUpdate(String firstName, String lastName) {
        JSONObject update = new JSONObject();
        update.put(FIRSTNAME_KEY, firstName);
        update.put(LASTNAME_KEY, lastName);

        return update;
    }
}