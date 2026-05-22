package stepDefinitions;

import factory.BaseClass;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebElement;
import pageObjects.WellnessPage;
import utilities.ExcelUtils;
import utilities.ScreenshotUtil;

import java.util.List;

public class WellnessSteps {

    WellnessPage cwv;
    boolean isButtonEnabled;
    int currentRow;

    @Given("the user navigates to the Corporate Wellness page")
    public void navigateWellness() {

        Hooks.getTest().info("Navigating to Wellness Page");

        cwv = new WellnessPage(BaseClass.getDriver());

        cwv.setCorporates();
        cwv.setClickWellness();
        cwv.verifynavigation();
    }

    @When("the user reads wellness data from excel row {int} and fills the form")
    public void readWellnessData(int rowNum) {

        currentRow = rowNum;

        String sheet = "Wellness";

        String name = ExcelUtils.getCellData(sheet, rowNum, 0);
        String org = ExcelUtils.getCellData(sheet, rowNum, 1);
        String phone = ExcelUtils.getCellData(sheet, rowNum, 2);
        String email = ExcelUtils.getCellData(sheet, rowNum, 3);
        String orgSize = ExcelUtils.getCellData(sheet, rowNum, 4);
        String interested = ExcelUtils.getCellData(sheet, rowNum, 5);

        Hooks.getTest().info("Row " + rowNum + " → " + name);

        cwv.setName(name);
        cwv.setOrgname(org);
        cwv.setContnum(phone);
        cwv.setEmail(email);

        cwv.setScroll();
        cwv.setOrgele(orgSize);      // ✅ uses selectByValue
        cwv.setIntrestele(interested);

        ScreenshotUtil.captureAndSave(BaseClass.getDriver(), "Row_" + rowNum);
    }

    // ✅ ✅ FIX: ONLY CHECK BUTTON (NO VALIDATION LOGIC)
    @When("the user checks the Schedule button status")
    public void clickScheduleButton() {

        isButtonEnabled = cwv.setSchedulebu();

        Hooks.getTest().info("Schedule Button Enabled: " + isButtonEnabled);

        if (isButtonEnabled) {

            Hooks.getTest().pass("✅ Button Enabled");

        } else {

            Hooks.getTest().warning("⚠️ Button Disabled");
        }
    }

    // ✅ ✅ FINAL RESULT BASED ONLY ON BUTTON
    @Then("the system should record the result in Excel")
    public void writeResultExcel() {

        String sheet = "Wellness";
        String status;

        if (isButtonEnabled) {

            status = "PASS";
            Hooks.getTest().pass("✅ PASS → Button ENABLED");

        } else {

            status = "FAIL";
            Hooks.getTest().fail("❌ FAIL → Button DISABLED");
            Hooks.getTest().warning("⚠️ Invalid Details entered");
        }

        ExcelUtils.setCellData(sheet, currentRow, 6, status);

        Hooks.getTest().info("✅ Excel Updated → Row " + currentRow + " = " + status);
    }
}