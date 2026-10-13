package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Runs every Mobile (Android) scenario.
 * Not part of testng.xml / CI: it needs a running Appium server and an emulator or device.
 * Scenarios run one by one (no parallel data provider) because there is a single device.
 */
@CucumberOptions(
        features = "src/test/resources/features/mobile",
        glue = {"steps.mobile", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/mobile.html",
                "json:target/cucumber-reports/mobile.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "rerun:target/rerun/mobile.txt"
        }
)
public class MobileRunner extends AbstractTestNGCucumberTests {
}
