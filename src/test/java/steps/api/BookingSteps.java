package steps.api;

import api.BookingApi;
import api.models.Booking;
import api.models.BookingDates;
import context.ScenarioContext;
import context.TestDataFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookingSteps {

    private final ScenarioContext context;
    private final BookingApi bookingApi = new BookingApi();

    public BookingSteps(ScenarioContext context) {
        this.context = context;
    }

    // ---------- Given (preconditions: prepared through the API, not asserted as the test target) ----------

    @Given("a booking exists for guest {string} {string}")
    public void aBookingExistsForGuest(String firstName, String lastName) {
        createBookingAndRemember(TestDataFactory.booking(firstName, lastName));
        assertEquals(context.getLastResponse().statusCode(), 200, "Precondition failed: booking was not created");
    }

    @Given("a booking exists for guest {string} with a unique last name")
    public void aBookingExistsForGuestWithAUniqueLastName(String firstName) {
        aBookingExistsForGuest(firstName, "Tester-" + TestDataFactory.uniqueSuffix());
    }

    // ---------- When (actions under test) ----------

    @When("I create a booking with the following details:")
    public void iCreateABookingWithTheFollowingDetails(Map<String, String> details) {
        Booking booking = new Booking(
                details.get("firstName"),
                details.get("lastName"),
                Integer.parseInt(details.get("totalPrice")),
                Boolean.parseBoolean(details.get("depositPaid")),
                new BookingDates(details.get("checkIn"), details.get("checkOut")),
                details.get("additionalNeeds")
        );
        createBookingAndRemember(booking);
    }

    @When("I retrieve the booking by its id")
    public void iRetrieveTheBookingByItsId() {
        context.setLastResponse(bookingApi.getBooking(context.getBookingId()));
    }

    @When("I retrieve the booking with id {int}")
    public void iRetrieveTheBookingWithId(int bookingId) {
        context.setLastResponse(bookingApi.getBooking(bookingId));
    }

    @When("I search bookings by the guest name")
    public void iSearchBookingsByTheGuestName() {
        Booking booking = context.getSubmittedBooking();
        context.setLastResponse(bookingApi.getBookingIdsByName(booking.firstName(), booking.lastName()));
    }

    @When("I update the booking total price to {int} and additional needs to {string}")
    public void iUpdateTheBooking(int totalPrice, String additionalNeeds) {
        Booking current = context.getSubmittedBooking();
        Booking updated = new Booking(current.firstName(), current.lastName(), totalPrice,
                current.depositPaid(), current.bookingDates(), additionalNeeds);
        context.setLastResponse(bookingApi.updateBooking(context.getBookingId(), updated, context.getToken()));
    }

    @When("I update the booking total price to {int} using token {string}")
    public void iUpdateTheBookingUsingToken(int totalPrice, String token) {
        Booking current = context.getSubmittedBooking();
        Booking updated = new Booking(current.firstName(), current.lastName(), totalPrice,
                current.depositPaid(), current.bookingDates(), current.additionalNeeds());
        context.setLastResponse(bookingApi.updateBooking(context.getBookingId(), updated, token));
    }

    @When("I partially update the booking first name to {string}")
    public void iPartiallyUpdateTheBookingFirstName(String firstName) {
        context.setLastResponse(bookingApi.partialUpdateBooking(
                context.getBookingId(), Map.of("firstname", firstName), context.getToken()));
    }

    @When("I delete the booking")
    public void iDeleteTheBooking() {
        context.setLastResponse(bookingApi.deleteBooking(context.getBookingId(), context.getToken()));
    }

    // ---------- Then (verifications) ----------

    @Then("the created booking should contain the submitted details")
    public void theCreatedBookingShouldContainTheSubmittedDetails() {
        // Deserialize the "booking" object of the response and compare it with what was sent
        Booking actual = context.getLastResponse().jsonPath().getObject("booking", Booking.class);
        assertEquals(actual, context.getSubmittedBooking());
    }

    @Then("the booking first name should be {string}")
    public void theBookingFirstNameShouldBe(String expected) {
        assertEquals(context.getLastResponse().jsonPath().getString("firstname"), expected);
    }

    @Then("the booking total price should be {int}")
    public void theBookingTotalPriceShouldBe(int expected) {
        assertEquals(context.getLastResponse().jsonPath().getInt("totalprice"), expected);
    }

    @Then("the booking additional needs should be {string}")
    public void theBookingAdditionalNeedsShouldBe(String expected) {
        assertEquals(context.getLastResponse().jsonPath().getString("additionalneeds"), expected);
    }

    @Then("the search result should contain the booking id")
    public void theSearchResultShouldContainTheBookingId() {
        List<Integer> ids = context.getLastResponse().jsonPath().getList("bookingid", Integer.class);
        assertTrue(ids.contains(context.getBookingId()),
                "Booking id " + context.getBookingId() + " not found in search result " + ids);
    }

    @Then("retrieving the booking should return status code {int}")
    public void retrievingTheBookingShouldReturnStatusCode(int expectedStatusCode) {
        assertEquals(bookingApi.getBooking(context.getBookingId()).statusCode(), expectedStatusCode);
    }

    private void createBookingAndRemember(Booking booking) {
        Response response = bookingApi.createBooking(booking);
        context.setLastResponse(response);
        context.setSubmittedBooking(booking);
        if (response.statusCode() == 200) {
            context.setBookingId(response.jsonPath().getInt("bookingid"));
        }
    }
}
