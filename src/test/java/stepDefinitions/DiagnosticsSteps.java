package stepDefinitions;

import factory.BaseClass;
import io.cucumber.java.en.*;
import pageObjects.DiagnosticsPage;

import java.util.List;

import hooks.Hooks;   //
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ScreenshotUtil;

public class DiagnosticsSteps {

    DiagnosticsPage ddn;
    List<String> capturedcities;

    private Logger logger = LogManager.getLogger(DiagnosticsSteps.class);

    @Given("the user is on the Diagnostics page")
    public void the_user_is_on_the_diagnostics_page() throws InterruptedException {

        // Extent log
        Hooks.getTest().info("Given the user is on the Diagnostics page");

        logger.info("Initializing Diagnostics Page");

        ddn = new DiagnosticsPage(BaseClass.getDriver());

        ddn.setScroll();
        ddn.setClickdignostic();

        Thread.sleep(3000);

        ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Diagnostics_Page_Loaded");
    }

    @When("the user identifies all names in the Top Cities section")
    public void the_user_identifies_all_names_in_the_top_cities_section() {

        Hooks.getTest().info("When user identifies Top Cities section");

        ddn.setCities();

        ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Top_Cities_Section");
    }

    @When("stores these city names in a List")
    public void stores_these_city_names_in_a_list() {

        Hooks.getTest().info("And user stores city names into list");

        capturedcities = ddn.getTopcitynames();
    }

    @Then("the system should display the captured List of cities in the console output")
    public void the_system_should_display_the_captured_list_of_cities_in_the_console_output() {

        Hooks.getTest().info("Then system displays the captured cities");

        System.out.println("------Top Cities------");

        for (String cityName : capturedcities) {
            System.out.println(cityName);

            // Add each city to Extent
            Hooks.getTest().info("City: " + cityName);
        }

        System.out.println("Total Cities: " + capturedcities.size());

        ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Diagnostics_Final_Output");
    }

    // EXCEL VALIDATION WITH EXTENT
    @When("validate diagnostics from excel")
    public void validate_diagnostics_from_excel() {

        Hooks.getTest().info("When validating diagnostics data from Excel");

        String file = System.getProperty("user.dir") + "/src/test/resources/TestData.xlsx";

        String status = "PASS";

        try {

            DiagnosticsPage dp = new DiagnosticsPage(BaseClass.getDriver());

            dp.setScroll();
            dp.setClickdignostic();

            ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Diagnostics_Navigation");

            dp.setCities();
            List<String> cities = dp.getTopcitynames();

            ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Diagnostics_Top_Cities_List");

            if (cities.isEmpty()) {
                status = "FAIL";
                Hooks.getTest().fail("No cities found in diagnostics section");
            } else {
                Hooks.getTest().pass("Cities successfully fetched: " + cities.size());
            }

        } catch (Exception e) {

            logger.error("Diagnostics validation failed: " + e.getMessage());

            Hooks.getTest().fail("Diagnostics validation failed: " + e.getMessage());

            status = "FAIL";

            ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Diagnostics_Error");
        }

        Hooks.getTest().info("Excel updated with status: " + status);

        ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Diagnostics_Status_Updated");
    }
}