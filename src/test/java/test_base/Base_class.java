package test_base;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import utilities.ConfigReader;
import utilities.DriverFactory;

public class Base_class {

    protected WebDriver driver;
    protected Logger logger;

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() {

        // Loads config.properties automatically
        ConfigReader.getProperty("appURL");
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser","os"})
    public void setup(@Optional("chrome") String browser,
                      @Optional("linux") String os) throws Exception {

        logger = LogManager.getLogger(getClass());

        driver = DriverFactory.initializeDriver(browser, os);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        driver.get(ConfigReader.getProperty("appURL"));

        printExecutionInfo(browser, os);
    }

    private void printExecutionInfo(String browser, String os) {

        System.out.println();
        System.out.println("====================================");
        System.out.println("Execution Started");
        System.out.println("------------------------------------");
        System.out.println("Browser      : " + browser);
        System.out.println("OS           : " + os);
        System.out.println("Environment  : " + ConfigReader.getProperty("execution_env"));
        System.out.println("Grid Type    : " + ConfigReader.getProperty("grid_type"));
        System.out.println("Hub URL      : " + ConfigReader.getProperty("hub_url"));
        System.out.println("Application  : " + ConfigReader.getProperty("appURL"));
        System.out.println("====================================");
        System.out.println();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    //================ Random Data ===================

    public String randomString() {

        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {

        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {

        return randomString() + randomNumber();
    }
}