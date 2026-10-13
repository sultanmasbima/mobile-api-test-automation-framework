package context;

import api.models.Booking;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Data shared between step classes during ONE scenario.
 *
 * Cucumber PicoContainer creates a fresh instance for every scenario and injects it
 * into each step class constructor. No static state means no data leaking between
 * scenarios, and parallel execution stays safe.
 */
public class ScenarioContext {

    private Response lastResponse;
    private Booking submittedBooking;
    private Integer bookingId;
    private String token;
    private final List<Integer> createdBookingIds = new ArrayList<>();

    public Response getLastResponse() {
        if (lastResponse == null) {
            throw new IllegalStateException("No API request has been sent in this scenario yet");
        }
        return lastResponse;
    }

    public void setLastResponse(Response lastResponse) {
        this.lastResponse = lastResponse;
    }

    public Booking getSubmittedBooking() {
        return submittedBooking;
    }

    public void setSubmittedBooking(Booking submittedBooking) {
        this.submittedBooking = submittedBooking;
    }

    public int getBookingId() {
        if (bookingId == null) {
            throw new IllegalStateException("No booking has been created in this scenario yet");
        }
        return bookingId;
    }

    /** Remembers the id as the "current" booking and registers it for cleanup after the scenario. */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
        this.createdBookingIds.add(bookingId);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Integer> getCreatedBookingIds() {
        return createdBookingIds;
    }
}
