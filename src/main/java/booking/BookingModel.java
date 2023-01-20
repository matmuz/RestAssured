package booking;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BookingModel {

    private final String firstname;
    private final String lastname;
    private final int totalPrice;
    private final int deposit;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final String additionalNeeds;
}
