package core;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Parent of every Page Object. Holds the reusable UI actions.
 *
 * Every action waits explicitly for the element to be in the right state
 * (visible / clickable) instead of sleeping for a fixed time. The test moves on
 * as soon as the app is ready, and fails with a clear message if it never is.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("web.timeout.seconds")));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText().trim();
    }

    /**
     * Reads the text of every matching element. If the page re-renders while reading
     * (StaleElementReferenceException, common after sorting/filtering in React apps),
     * the wait simply retries instead of failing the test.
     */
    protected List<String> getTexts(By locator) {
        return wait.ignoring(StaleElementReferenceException.class).until(d -> {
            List<String> texts = d.findElements(locator).stream().map(e -> e.getText().trim()).toList();
            return texts.isEmpty() ? null : texts;
        });
    }

    /** Returns immediately (no waiting) - use for "element should NOT exist" checks. */
    protected boolean isPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected void open(String url) {
        driver.get(url);
    }
}
