package runners;

import io.cucumber.testng.CucumberOptions;

/**
 * Runs every API scenario. Narrow it down from the command line, e.g.
 * mvn test -Dcucumber.filter.tags="@smoke"
 */
@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = {"steps.api", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api.html",
                "json:target/cucumber-reports/api.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                // Failed scenarios are written here, then re-executed by FailedApiRunner
                "rerun:target/rerun/api.txt"
        }
)
public class ApiRunner extends BaseRunner {
}
