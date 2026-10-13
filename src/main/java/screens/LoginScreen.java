package screens;

import core.BaseScreen;
import org.openqa.selenium.By;

/**
 * Screen Object: Login screen (opened from the side menu or when checking out).
 */
public class LoginScreen extends BaseScreen {

    private final By usernameField = id("nameET");
    private final By passwordField = id("passwordET");
    private final By loginButton = id("loginBtn");
    private final By usernameError = id("nameErrorTV");
    private final By passwordError = id("passwordErrorTV");

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        hideKeyboard();
        tap(loginButton);
    }

    /**
     * The app shows username problems under the username field and every other
     * problem (empty password, locked account) under the password field.
     */
    public String getErrorMessage() {
        return getText(waitAnyVisible(usernameError, passwordError));
    }
}
