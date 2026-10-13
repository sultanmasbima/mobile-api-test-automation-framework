package api;

import api.models.Booking;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Service Object for the Restful Booker API (https://restful-booker.herokuapp.com/apidoc).
 * It is the API version of a Page Object: one method per endpoint, no assertions inside.
 * Each method returns the raw Response so the step decides what to verify.
 */
public class BookingApi extends ApiClient {

    public Response createToken(String username, String password) {
        return given().spec(baseSpec())
                .body(Map.of("username", username, "password", password))
                .post("/auth");
    }

    public Response createBooking(Booking booking) {
        return given().spec(baseSpec())
                .body(booking)
                .post("/booking");
    }

    public Response getBooking(int bookingId) {
        return given().spec(baseSpec())
                .get("/booking/{id}", bookingId);
    }

    public Response getBookingIdsByName(String firstName, String lastName) {
        return given().spec(baseSpec())
                .queryParam("firstname", firstName)
                .queryParam("lastname", lastName)
                .get("/booking");
    }

    public Response updateBooking(int bookingId, Booking booking, String token) {
        return given().spec(baseSpec())
                .cookie("token", token)
                .body(booking)
                .put("/booking/{id}", bookingId);
    }

    public Response partialUpdateBooking(int bookingId, Map<String, Object> fields, String token) {
        return given().spec(baseSpec())
                .cookie("token", token)
                .body(fields)
                .patch("/booking/{id}", bookingId);
    }

    public Response deleteBooking(int bookingId, String token) {
        return given().spec(baseSpec())
                .cookie("token", token)
                .delete("/booking/{id}", bookingId);
    }
}
