package pageObjects;

import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasePage {

    protected WebDriver driver;
    protected Logger logger;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.logger = LogManager.getLogger(this.getClass());

        logger.info("Initialized page: " + this.getClass().getSimpleName());
    }
}