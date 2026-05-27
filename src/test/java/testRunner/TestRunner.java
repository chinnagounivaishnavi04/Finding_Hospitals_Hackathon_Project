package testRunner;

import io.cucumber.testng.*;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"stepDefinitions", "hooks"},
        plugin = {"pretty","html:reports/cucumber.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
}