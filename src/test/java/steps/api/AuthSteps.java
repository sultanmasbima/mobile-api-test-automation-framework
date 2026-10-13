package steps.api;

import api.BookingApi;
import context.ScenarioContext;
import core.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.testng.Assert.assertNotNull;

public class AuthSteps {

    private final ScenarioContext context;
    private final BookingApi bookingApi = new BookingApi();

    public AuthSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("I am authenticated as admin")
    public void iAmAuthenticatedAsAdmin() {
        String token = bookingApi.createToken(ConfigReader.get("api.username"), ConfigReader.get("api.password"))
                .jsonPath().getString("token");
        assertNotNull(token, "Precondition failed: could not create an auth token");
        context.setToken(token);
    }

    @When("I request a token with valid admin credentials")
    public void iRequestATokenWithValidAdminCredentials() {
        iRequestATokenWith(ConfigReader.get("api.username"), ConfigReader.get("api.password"));
    }

    @When("I request a token with username {string} and password {string}")
    public void iRequestATokenWith(String username, String password) {
        Response response = bookingApi.createToken(username, password);
        context.setLastResponse(response);
    }
}
