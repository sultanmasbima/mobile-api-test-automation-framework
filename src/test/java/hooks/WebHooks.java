package hooks;

import core.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

/**
 * Lifecycle for UI scenarios. The tag expression "@web" means these hooks
 * only run for web scenarios, so API scenarios never open a browser.
 */
public class WebHooks {

    @Before("@web")
    public void startBrowser() {
        DriverManager.startDriver();
    }

    @After("@web")
    public void takeScreenshotOnFailureAndQuit(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                // Attachments are picked up by both the Cucumber HTML report and Allure
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot on failure");
            }
        } catch (WebDriverException e) {
            // A broken browser must not hide the original failure reason
            scenario.log("Screenshot could not be taken: " + e.getMessage());
        } finally {
            DriverManager.quitDriver();
        }
    }
}
