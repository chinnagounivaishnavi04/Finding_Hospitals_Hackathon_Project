package stepDefinitions;

import factory.BaseClass;
import io.cucumber.java.en.*;
import pageObjects.HospitalsPage;

import java.util.List;

import hooks.Hooks;   // ✅ IMPORTANT (same as your DiagnosticsSteps)
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utilities.ScreenshotUtil;

public class HospitalSteps {

    private HospitalsPage dis;
    private Logger logger = LogManager.getLogger(HospitalSteps.class);

    @Given("the user should open the application and search page for {string}")
    public void the_user_should_open_the_application_and_search_page_for(String name) {

        Hooks.getTest().info("Given: Open application and search location → " + name);
        logger.info("Opening application and setting location: " + name);

        if (BaseClass.getDriver() == null) {
            try {
                BaseClass.initializeBrowser();
                BaseClass.getDriver().get(BaseClass.getProperties().getProperty("appURL"));
            } catch (Exception e) {
                logger.error("Driver initialization failed: " + e.getMessage());
                Hooks.getTest().fail("Driver initialization failed: " + e.getMessage());
            }
        }

        dis = new HospitalsPage(BaseClass.getDriver());

        Hooks.getTest().info("Entering location: " + name);
        dis.setLocation(name);

        ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Location_Entered");
    }

    @Given("the user should choose the {string}")
    public void the_user_should_choose_the(String Hname) throws InterruptedException {

        Hooks.getTest().info("Given: User searches for hospital → " + Hname);
        logger.info("Searching hospital: " + Hname);

        dis.setSearchhospital(Hname);
        Thread.sleep(4000);

        Hooks.getTest().info("Selecting hospital from suggestions");
        dis.selectHospitalFromList();

        ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Hospital_Search");
    }

    @When("the user enter a minimum rating of open and display results")
    public void the_user_enter_a_minimum_rating_of_open_and_display_results() {

        Hooks.getTest().info("When: Filter hospitals (24/7 + rating > 3.5)");
        logger.info("Filtering hospitals");

        List<String> finalHospitalList = dis.setHospitalcards();

        if (finalHospitalList.isEmpty()) {

            Hooks.getTest().fail("No hospitals matched the given criteria");
            logger.warn("No hospitals found");

        } else {

            Hooks.getTest().pass("Matching hospitals found: " + finalHospitalList.size());

            for (int i = 0; i < finalHospitalList.size(); i++) {

                String hospital = (i + 1) + ". " + finalHospitalList.get(i);

                Hooks.getTest().info("Hospital: " + hospital);
                logger.info("Hospital: " + hospital);
            }
        }

        ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Hospital_Results");

        dis.setNavigate_back();
    }
}
