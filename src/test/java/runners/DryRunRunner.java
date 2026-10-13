package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Quality gate: checks that every Gherkin step has a matching step definition,
 * without opening a browser or calling any API. Runs in seconds.
 */
@CucumberOptions(
        dryRun = true,
        features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        plugin = {"summary"}
)
public class DryRunRunner extends AbstractTestNGCucumberTests {
}
