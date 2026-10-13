package context;

import api.models.Booking;
import api.models.BookingDates;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Builds test data. Generating unique values keeps scenarios independent:
 * the demo API is shared by many people, so we never rely on data we did not create.
 */
public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static Booking booking(String firstName, String lastName) {
        LocalDate checkIn = LocalDate.now().plusDays(7);
        return new Booking(
                firstName,
                lastName,
                150,
                true,
                new BookingDates(checkIn.toString(), checkIn.plusDays(3).toString()),
                "Breakfast"
        );
    }

    public static String uniqueSuffix() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
