package steps.web;

import core.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.InventoryPage;
import pages.LoginPage;

import static org.testng.Assert.assertEquals;

public class LoginSteps {

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        new LoginPage().openPage();
    }

    @Given("I am logged in as {string}")
    public void iAmLoggedInAs(String username) {
        iAmOnTheLoginPage();
        iLogInAs(username);
        iShouldSeeThePage("Products");
    }

    /** Uses the shared demo password from config, so feature files stay free of credentials. */
    @When("I log in as {string}")
    public void iLogInAs(String username) {
        new LoginPage().login(username, ConfigReader.get("web.password"));
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWithUsernameAndPassword(String username, String password) {
        new LoginPage().login(username, password);
    }

    @Then("I should see the {string} page")
    public void iShouldSeeThePage(String title) {
        assertEquals(new InventoryPage().getPageTitle(), title);
    }

    @Then("I should see the login error {string}")
    public void iShouldSeeTheLoginError(String expectedError) {
        assertEquals(new LoginPage().getErrorMessage(), expectedError);
    }
}
