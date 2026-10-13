package screens;

import core.BaseScreen;
import org.openqa.selenium.By;

/**
 * Screen Object: Checkout flow (shipping address -> payment -> review -> complete).
 * The steps share the same bottom button (paymentBtn) whose label changes per step.
 */
public class CheckoutScreen extends BaseScreen {

    // Shipping address
    private final By fullNameField = id("fullNameET");
    private final By addressLine1Field = id("address1ET");
    private final By cityField = id("cityET");
    private final By zipCodeField = id("zipET");
    private final By countryField = id("countryET");
    private final By fullNameError = id("fullNameErrorTV");

    // Payment
    private final By cardHolderField = id("nameET");
    private final By cardNumberField = id("cardNumberET");
    private final By expirationDateField = id("expirationDateET");
    private final By securityCodeField = id("securityCodeET");

    // Shared bottom button: "To Payment" / "Review Order" / "Place Order"
    private final By nextButton = id("paymentBtn");

    // Complete
    private final By completeTitle = id("completeTV");

    public void submitShippingAddress(String fullName, String address, String city, String zipCode, String country) {
        type(fullNameField, fullName);
        type(addressLine1Field, address);
        type(cityField, city);
        type(zipCodeField, zipCode);
        type(countryField, country);
        hideKeyboard();
        tap(nextButton);
    }

    public void submitEmptyShippingAddress() {
        tap(nextButton);
    }

    public String getFullNameError() {
        return getText(fullNameError);
    }

    public void submitPayment(String cardHolder, String cardNumber, String expirationDate, String securityCode) {
        type(cardHolderField, cardHolder);
        type(cardNumberField, cardNumber);
        type(expirationDateField, expirationDate);
        type(securityCodeField, securityCode);
        hideKeyboard();
        tap(nextButton);
    }

    public void placeOrder() {
        tap(nextButton);
    }

    public String getCompleteTitle() {
        return getText(completeTitle);
    }
}
