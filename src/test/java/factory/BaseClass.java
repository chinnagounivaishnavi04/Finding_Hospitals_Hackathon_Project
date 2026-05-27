package factory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {

    //  THREAD-SAFE DRIVER
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static Properties p;
    private static Logger logger = LogManager.getLogger(BaseClass.class);

    //  GET DRIVER
    public static WebDriver getDriver() {
        return driver.get();
    }

    //  SET DRIVER
    public static void setDriver(WebDriver d) {
        driver.set(d);
    }

    //  REMOVE DRIVER (VERY IMPORTANT)
    public static void removeDriver() {
        driver.remove();
    }

    //  INITIALIZE BROWSER
    public static WebDriver initializeBrowser() throws IOException {

        p = getProperties();

        String executionEnv = p.getProperty("execution_env");

        //  dynamic browser support
        String browser = System.getProperty("browser") != null
                ? System.getProperty("browser")
                : p.getProperty("browser");

        browser = browser.toLowerCase();
        String os = p.getProperty("os").toLowerCase();

        WebDriver d = null;

        try {
            if (executionEnv.equalsIgnoreCase("remote")) {

                DesiredCapabilities capabilities = new DesiredCapabilities();

                switch (os) {
                    case "windows" -> capabilities.setPlatform(Platform.WINDOWS);
                    case "mac" -> capabilities.setPlatform(Platform.MAC);
                    case "linux" -> capabilities.setPlatform(Platform.LINUX);
                }

                switch (browser) {

                    case "chrome" -> {
                        ChromeOptions chOptions = new ChromeOptions();
                        chOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                        chOptions.addArguments("--disable-notifications");
                        capabilities.merge(chOptions);
                    }

                    case "edge" -> capabilities.setBrowserName("MicrosoftEdge");
                    case "firefox" -> capabilities.setBrowserName("firefox");
                }

                d = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

            } else {

                switch (browser) {

                    case "chrome" -> {
                        ChromeOptions chOptions = new ChromeOptions();
                        chOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                        chOptions.addArguments("--disable-notifications");
                        d = new ChromeDriver(chOptions);
                    }

                    case "edge" -> {
                        EdgeOptions edgeOptions = new EdgeOptions();
                        edgeOptions.addArguments("--disable-notifications");
                        d = new EdgeDriver(edgeOptions);
                    }

                    case "firefox" -> d = new FirefoxDriver();
                }
            }

        } catch (Exception e) {
            logger.error("Browser initialization failed: " + e.getMessage());
            throw e;
        }

        //  SET THREAD DRIVER
        setDriver(d);

        if (getDriver() != null) {

            getDriver().manage().deleteAllCookies();
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0)); //  disable implicit
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            getDriver().manage().window().maximize();
        }

        return getDriver();
    }

    //  PROPERTIES
    public static Properties getProperties() throws IOException {

        if (p == null) {

            FileReader file = new FileReader(
                    System.getProperty("user.dir")
                            + File.separator + "src"
                            + File.separator + "test"
                            + File.separator + "resources"
                            + File.separator + "config.properties"
            );

            p = new Properties();
            p.load(file);
        }

        return p;
    }

    //  LOGGER
    public static Logger getLogger() {
        return logger;
    }
}
