package core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Creates the Appium AndroidDriver. Same ThreadLocal approach as DriverManager (web),
 * so the structure is ready for running on several devices in parallel later.
 *
 * Requires a running Appium server (appium) and a device/emulator visible in "adb devices".
 */
public final class MobileDriverManager {

    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    private MobileDriverManager() {
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("AndroidDriver is not started. Is the scenario tagged with @mobile?");
        }
        return driver;
    }

    public static void startDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid(ConfigReader.get("mobile.udid"))
                .setApp(Path.of(ConfigReader.get("mobile.app.path")).toAbsolutePath().toString())
                .setAppPackage(ConfigReader.get("mobile.app.package"))
                .setAppWaitActivity("*")
                // noReset=false clears app data before every scenario -> each test starts from a clean state
                .setNoReset(false)
                .setFullReset(false)
                .setAutoGrantPermissions(true)
                // Generous timeouts: emulators can be slow right after booting
                .setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(120))
                .setAdbExecTimeout(Duration.ofSeconds(60))
                .setNewCommandTimeout(Duration.ofSeconds(120));

        try {
            URI appiumUrl = new URI(ConfigReader.get("mobile.appium.url"));
            DRIVER.set(new AndroidDriver(appiumUrl.toURL(), options));
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalStateException("Invalid mobile.appium.url", e);
        }
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
