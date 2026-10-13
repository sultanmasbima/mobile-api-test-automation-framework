package runners;

import io.cucumber.testng.CucumberOptions;

/**
 * Re-runs only the Web scenarios listed in target/rerun/web.txt (written by WebRunner).
 */
@CucumberOptions(
        features = "@target/rerun/web.txt",
        glue = {"steps.web", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/web-rerun.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class FailedWebRunner extends BaseRunner {
}
