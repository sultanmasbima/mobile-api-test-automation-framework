package pages;

import core.BasePage;
import core.ConfigReader;
import org.openqa.selenium.By;

/**
 * Page Object: Login page of https://www.saucedemo.com
 * Locators prefer the stable data-test attributes over text or index based XPath.
 */
public class LoginPage extends BasePage {

    private final By usernameField = By.cssSelector("[data-test='username']");
    private final By passwordField = By.cssSelector("[data-test='password']");
    private final By loginButton = By.cssSelector("[data-test='login-button']");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage openPage() {
        open(ConfigReader.get("web.base.url"));
        waitVisible(loginButton);
        return this;
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
