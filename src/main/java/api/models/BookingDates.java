package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingDates(
        @JsonProperty("checkin") String checkIn,
        @JsonProperty("checkout") String checkOut
) {
}
