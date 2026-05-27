package hooks;

import com.aventstack.extentreports.*;
import factory.BaseClass;
import io.cucumber.java.*;
import org.openqa.selenium.*;
import utilities.ExtentReportManager;

import java.util.Base64;
import java.util.Properties;

public class Hooks {

    private static Properties p;

    // Thread-safe ExtentTest
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ExtentReports extent = ExtentReportManager.getReportInstance();

    public static ExtentTest getTest() {
        return test.get();
    }

    //  BEFORE SCENARIO
    @Before
    public void setup(Scenario scenario) {

        try {
            BaseClass.initializeBrowser();
            p = BaseClass.getProperties();

            test.set(extent.createTest(scenario.getName()));

            BaseClass.getDriver().get(p.getProperty("appURL"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  AFTER EACH STEP (SCREENSHOT + REPORT)
    @AfterStep
    public void addScreenshot(Scenario scenario) {

        WebDriver driver = BaseClass.getDriver();

        if (driver == null) return;

        try {

            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

            String base64 = Base64.getEncoder().encodeToString(screenshot);

            //  Always attach to Cucumber
            scenario.attach(screenshot, "image/png", "Step Screenshot");

            ExtentTest extentTest = test.get();
            if (extentTest == null) return;

            if (scenario.isFailed()) {

                extentTest.fail("Step Failed",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());

            } else {

                extentTest.pass("Step Passed",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
            }

        } catch (Exception e) {
            System.out.println("Screenshot error: " + e.getMessage());
        }
    }

    // AFTER SCENARIO
    @After
    public void tearDown(Scenario scenario) {

        try {
            WebDriver driver = BaseClass.getDriver();

            if (driver != null) {

                driver.quit();              //  close browser
                BaseClass.removeDriver();   //  VERY IMPORTANT for parallel
            }

        } catch (Exception e) {
            System.out.println("Driver already closed");
        }
    }

    //  AFTER ALL SCENARIOS
    @AfterAll
    public static void flushReport() {

        if (extent != null) {
            extent.flush();  //  ENSURE report is written
        }
    }
}
