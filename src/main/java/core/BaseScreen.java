package core;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

/**
 * Parent of every mobile Screen Object (the mobile name for a Page Object).
 * Same rule as BasePage: every action waits explicitly, no fixed sleeps.
 */
public abstract class BaseScreen {

    protected final AndroidDriver driver;
    private final WebDriverWait wait;

    protected BaseScreen() {
        this.driver = MobileDriverManager.getDriver();
        // Mobile screens re-render often (animations, list refresh). Every action below finds the
        // element AND acts on it inside the same wait, so a stale element is simply looked up again.
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("mobile.timeout.seconds")));
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    /** Locator helper: resource-id inside the app under test, e.g. id("nameET"). */
    protected static By id(String resourceId) {
        return AppiumBy.id(packageId(resourceId));
    }

    /** Full resource-id, e.g. "com.example.app:id/nameET" - useful inside XPath expressions. */
    protected static String packageId(String resourceId) {
        return ConfigReader.get("mobile.app.package") + ":id/" + resourceId;
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void tap(By locator) {
        waitFor("tap " + locator, d -> {
            WebElement element = ExpectedConditions.elementToBeClickable(locator).apply(d);
            if (element == null) {
                return false;
            }
            element.click();
            return true;
        });
    }

    protected void type(By locator, String text) {
        waitFor("type into " + locator, d -> {
            WebElement element = ExpectedConditions.visibilityOfElementLocated(locator).apply(d);
            if (element == null) {
                return false;
            }
            element.clear();
            element.sendKeys(text);
            return true;
        });
    }

    protected String getText(By locator) {
        return waitFor("read text of " + locator, d -> {
            WebElement element = ExpectedConditions.visibilityOfElementLocated(locator).apply(d);
            return element == null ? null : element.getText().trim();
        });
    }

    protected List<String> getTexts(By locator) {
        return waitFor("read texts of " + locator, d -> {
            List<String> texts = d.findElements(locator).stream().map(e -> e.getText().trim()).toList();
            return texts.isEmpty() ? null : texts;
        });
    }

    /** Waits until at least one of the locators is visible and returns the first one that is. */
    protected By waitAnyVisible(By... locators) {
        return waitFor("any of " + List.of(locators) + " to be visible", d -> {
            for (By locator : locators) {
                if (ExpectedConditions.visibilityOfElementLocated(locator).apply(d) != null) {
                    return locator;
                }
            }
            return null;
        });
    }

    /** Runs the condition in the shared wait; on timeout, reports WHAT could not be done. */
    private <T> T waitFor(String action, Function<WebDriver, T> condition) {
        try {
            return wait.until(condition);
        } catch (TimeoutException e) {
            throw new TimeoutException("Could not " + action + " within the timeout", e);
        }
    }

    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    /**
     * Scrolls the first scrollable container until an element with the given text is visible.
     * UiScrollable runs on the device itself, so it is faster and more reliable than manual swipes.
     */
    protected void scrollToText(String text) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + text + "\"))"));
    }

    protected void hideKeyboard() {
        if (driver.isKeyboardShown()) {
            driver.hideKeyboard();
        }
    }
}
