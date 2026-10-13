package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request / response body of a booking.
 * Java uses camelCase, the API uses lowercase keys - @JsonProperty maps between them.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Booking(
        @JsonProperty("firstname") String firstName,
        @JsonProperty("lastname") String lastName,
        @JsonProperty("totalprice") int totalPrice,
        @JsonProperty("depositpaid") boolean depositPaid,
        @JsonProperty("bookingdates") BookingDates bookingDates,
        @JsonProperty("additionalneeds") String additionalNeeds
) {
}
