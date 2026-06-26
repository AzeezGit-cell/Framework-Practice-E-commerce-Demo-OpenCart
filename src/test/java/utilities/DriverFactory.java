package utilities;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

    public static WebDriver initializeDriver(String browser, String os) throws Exception {

        String executionEnv = ConfigReader.getProperty("execution_env");
        String gridType = ConfigReader.getProperty("grid_type");
        String hubUrl = ConfigReader.getProperty("hub_url");

        if (executionEnv.equalsIgnoreCase("local")) {

            return createLocalDriver(browser);

        } else if (executionEnv.equalsIgnoreCase("remote")) {

            return createRemoteDriver(browser, os, gridType, hubUrl);

        } else {

            throw new RuntimeException("Invalid execution environment : " + executionEnv);
        }
    }

    //================ Local Driver =================//

    private static WebDriver createLocalDriver(String browser) {

        switch (browser.toLowerCase()) {

        case "chrome":
            return new ChromeDriver();

        case "edge":
            return new EdgeDriver();

        case "firefox":
            return new FirefoxDriver();

        default:
            throw new RuntimeException("Invalid Browser : " + browser);
        }
    }

    //================ Remote Driver =================//

    private static WebDriver createRemoteDriver(String browser,
                                                String os,
                                                String gridType,
                                                String hubUrl) throws Exception {

        switch (browser.toLowerCase()) {

        case "chrome":

            ChromeOptions chrome = new ChromeOptions();

            if (gridType.equalsIgnoreCase("non_docker")) {
                chrome.setPlatformName(os);
            }

            return new RemoteWebDriver(new URL(hubUrl), chrome);

        case "edge":

            EdgeOptions edge = new EdgeOptions();

            if (gridType.equalsIgnoreCase("non_docker")) {
                edge.setPlatformName(os);
            }

            return new RemoteWebDriver(new URL(hubUrl), edge);

        case "firefox":

            FirefoxOptions firefox = new FirefoxOptions();

            if (gridType.equalsIgnoreCase("non_docker")) {
                firefox.setPlatformName(os);
            }

            return new RemoteWebDriver(new URL(hubUrl), firefox);

        default:

            throw new RuntimeException("Invalid Browser : " + browser);
        }
    }
}