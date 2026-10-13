package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.DataProvider;

/**
 * Parent of all runners. Overriding scenarios() with parallel = true lets TestNG
 * run scenarios concurrently; the thread count is set in testng.xml
 * (data-provider-thread-count).
 */
public abstract class BaseRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
