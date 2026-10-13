package hooks;

import core.MobileDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;

/**
 * Lifecycle for mobile scenarios: start the app before, screenshot on failure and quit after.
 */
public class MobileHooks {

    @Before("@mobile")
    public void startApp() {
        MobileDriverManager.startDriver();
    }

    @After("@mobile")
    public void takeScreenshotOnFailureAndQuit(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] screenshot = MobileDriverManager.getDriver().getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot on failure");
            }
        } catch (WebDriverException e) {
            scenario.log("Screenshot could not be taken: " + e.getMessage());
        } finally {
            MobileDriverManager.quitDriver();
        }
    }
}
