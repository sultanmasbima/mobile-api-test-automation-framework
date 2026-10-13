package pages;

import core.BasePage;
import org.openqa.selenium.By;

/**
 * Page Object: Checkout flow (information -> overview -> complete).
 * The three steps are small, so they share one class.
 */
public class CheckoutPage extends BasePage {

    private final By firstNameField = By.cssSelector("[data-test='firstName']");
    private final By lastNameField = By.cssSelector("[data-test='lastName']");
    private final By postalCodeField = By.cssSelector("[data-test='postalCode']");
    private final By continueButton = By.cssSelector("[data-test='continue']");
    private final By finishButton = By.cssSelector("[data-test='finish']");
    private final By completeHeader = By.cssSelector("[data-test='complete-header']");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public void fillInformation(String firstName, String lastName, String postalCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, postalCode);
        click(continueButton);
    }

    public void finish() {
        click(finishButton);
    }

    public String getCompleteHeader() {
        return getText(completeHeader);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
