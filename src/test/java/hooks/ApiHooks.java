package hooks;

import api.BookingApi;
import context.ScenarioContext;
import core.ConfigReader;
import io.cucumber.java.After;

/**
 * Lifecycle for API scenarios.
 * Test data cleanup: every booking created during the scenario is deleted afterwards,
 * so the test leaves the shared environment as it found it.
 */
public class ApiHooks {

    private final ScenarioContext context;
    private final BookingApi bookingApi = new BookingApi();

    public ApiHooks(ScenarioContext context) {
        this.context = context;
    }

    @After("@api")
    public void deleteCreatedBookings() {
        if (context.getCreatedBookingIds().isEmpty()) {
            return;
        }
        String token = bookingApi
                .createToken(ConfigReader.get("api.username"), ConfigReader.get("api.password"))
                .jsonPath().getString("token");
        // Cleanup is best effort: a booking may already be deleted by the scenario itself
        context.getCreatedBookingIds().forEach(id -> bookingApi.deleteBooking(id, token));
    }
}
