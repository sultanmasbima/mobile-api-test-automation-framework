package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Re-runs only the Mobile scenarios listed in target/rerun/mobile.txt (written by MobileRunner).
 */
@CucumberOptions(
        features = "@target/rerun/mobile.txt",
        glue = {"steps.mobile", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/mobile-rerun.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class FailedMobileRunner extends AbstractTestNGCucumberTests {
}
