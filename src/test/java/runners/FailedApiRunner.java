package runners;

import io.cucumber.testng.CucumberOptions;

/**
 * Re-runs only the API scenarios listed in target/rerun/api.txt (written by ApiRunner).
 * Helps separate real defects from one-off failures such as a slow network.
 */
@CucumberOptions(
        features = "@target/rerun/api.txt",
        glue = {"steps.api", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api-rerun.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class FailedApiRunner extends BaseRunner {
}
