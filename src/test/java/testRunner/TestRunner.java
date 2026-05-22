package testRunner;

import io.cucumber.testng.*;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"stepDefinitions", "hooks"},
        plugin = {"pretty","html:reports/cucumber.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}