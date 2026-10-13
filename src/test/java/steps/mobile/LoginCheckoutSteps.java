package steps.mobile;

import core.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import screens.CartScreen;
import screens.CatalogScreen;
import screens.CheckoutScreen;
import screens.LoginScreen;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class LoginCheckoutSteps {

    @Given("I open the login screen from the menu")
    public void iOpenTheLoginScreenFromTheMenu() {
        new CatalogScreen().openMenuItem("Log In");
    }

    @When("I log in on mobile with username {string} and password {string}")
    public void iLogInOnMobileWith(String username, String password) {
        new LoginScreen().login(username, password);
    }

    /** Uses the public demo account from config, so feature files stay free of credentials. */
    @When("I log in on mobile as the default user")
    public void iLogInOnMobileAsTheDefaultUser() {
        new LoginScreen().login(ConfigReader.get("mobile.username"), ConfigReader.get("mobile.password"));
    }

    @Then("I should see the mobile login error {string}")
    public void iShouldSeeTheMobileLoginError(String expectedError) {
        assertEquals(new LoginScreen().getErrorMessage(), expectedError);
    }

    @When("I proceed to checkout from the mobile cart")
    public void iProceedToCheckoutFromTheMobileCart() {
        new CartScreen().proceedToCheckout();
    }

    @When("I submit the shipping address:")
    public void iSubmitTheShippingAddress(Map<String, String> address) {
        new CheckoutScreen().submitShippingAddress(
                address.get("fullName"),
                address.get("address"),
                address.get("city"),
                address.get("zipCode"),
                address.get("country"));
    }

    @When("I submit an empty shipping address")
    public void iSubmitAnEmptyShippingAddress() {
        new CheckoutScreen().submitEmptyShippingAddress();
    }

    @When("I submit the payment details:")
    public void iSubmitThePaymentDetails(Map<String, String> payment) {
        new CheckoutScreen().submitPayment(
                payment.get("cardHolder"),
                payment.get("cardNumber"),
                payment.get("expirationDate"),
                payment.get("securityCode"));
    }

    @When("I place the order")
    public void iPlaceTheOrder() {
        new CheckoutScreen().placeOrder();
    }

    @Then("I should see the {string} confirmation")
    public void iShouldSeeTheConfirmation(String expectedTitle) {
        assertEquals(new CheckoutScreen().getCompleteTitle(), expectedTitle);
    }

    @Then("I should see the shipping error {string}")
    public void iShouldSeeTheShippingError(String expectedError) {
        assertEquals(new CheckoutScreen().getFullNameError(), expectedError);
    }
}
