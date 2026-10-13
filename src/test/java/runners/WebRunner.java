package runners;

import io.cucumber.testng.CucumberOptions;

/**
 * Runs every Web UI scenario.
 */
@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = {"steps.web", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/web.html",
                "json:target/cucumber-reports/web.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "rerun:target/rerun/web.txt"
        }
)
public class WebRunner extends BaseRunner {
}
