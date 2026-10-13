package core;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Map;

/**
 * Creates and stores one WebDriver per thread.
 *
 * ThreadLocal is what makes parallel execution safe: every thread (scenario)
 * gets its own browser, so scenarios never click in each other's window.
 * Driver binaries are resolved automatically by Selenium Manager.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not started. Is the scenario tagged with @web?");
        }
        return driver;
    }

    public static void startDriver() {
        String browser = ConfigReader.get("web.browser").toLowerCase();
        boolean headless = ConfigReader.getBoolean("web.headless");

        WebDriver driver = switch (browser) {
            case "chrome" -> new ChromeDriver(chromeOptions(headless));
            case "firefox" -> new FirefoxDriver(firefoxOptions(headless));
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

        // Implicit wait stays 0: waiting is handled explicitly in BasePage (see README "Waiting strategy")
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        // EAGER = continue once the DOM is ready, without waiting for every third-party script.
        // Element readiness is handled by the explicit waits in BasePage.
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1366,768", "--no-sandbox", "--disable-dev-shm-usage", "--incognito");
        // Disable Chrome's password manager pop-ups ("Change your password"), which block clicks on login forms
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (headless) {
            options.addArguments("-headless");
        }
        options.addArguments("--width=1366", "--height=768");
        return options;
    }
}
