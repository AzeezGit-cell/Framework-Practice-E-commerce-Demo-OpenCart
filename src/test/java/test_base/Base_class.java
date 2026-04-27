package test_base;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

public class Base_class {

    protected WebDriver driver;
    protected Logger logger;
    protected Properties p;

    @BeforeClass(alwaysRun = true)
    public void loadConfig() throws IOException {
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser", "os" })
    public void setup(String br, @Optional("linux") String os)  // ✅ @Optional prevents skip
            throws MalformedURLException {

        logger = LogManager.getLogger(this.getClass());

        if (br == null) br = "chrome";
        if (os == null) os = "linux";

        String execEnv = p.getProperty("execution_env");
        String gridType = p.getProperty("grid_type");
        String hubUrl   = p.getProperty("hub_url");

        System.out.println("=============================");
        System.out.println("Browser  : " + br);
        System.out.println("OS       : " + os);
        System.out.println("Exec Env : " + execEnv);
        System.out.println("Grid Type: " + gridType);
        System.out.println("Hub URL  : " + hubUrl);
        System.out.println("App URL  : " + p.getProperty("appURL"));
        System.out.println("=============================");

        if (execEnv.equalsIgnoreCase("remote")) {

            if (gridType.equalsIgnoreCase("docker")) {

                // ✅ Docker mode
                // No DesiredCapabilities — use Options directly
                // No platformName — Docker is Linux internally
                // Use hubUrl from config — NOT localhost
                switch (br.toLowerCase()) {
                    case "chrome":
                        ChromeOptions dockerChrome = new ChromeOptions();
                        driver = new RemoteWebDriver(
                            new URL(hubUrl), 
                            dockerChrome
                        );
                        break;

                    case "edge":
                        EdgeOptions dockerEdge = new EdgeOptions();
                        driver = new RemoteWebDriver(
                            new URL(hubUrl), 
                            dockerEdge
                        );
                        break;

                    case "firefox":
                        FirefoxOptions dockerFirefox = new FirefoxOptions();
                        driver = new RemoteWebDriver(
                            new URL(hubUrl), // ✅ 192.168.1.81:4444
                            dockerFirefox
                        );
                        break;

                    default:
                        throw new RuntimeException("No matching browser: " + br);
                }

            } else if (gridType.equalsIgnoreCase("non_docker")) {

                // ✅ Non Docker mode
                // Use Options with platformName for real machines
                String platformName;
                switch (os.toLowerCase()) {
                    case "windows": platformName = "Windows"; break;
                    case "mac":     platformName = "MAC";     break;
                    case "linux":   platformName = "Linux";   break;
                    default: throw new RuntimeException("No matching OS: " + os);
                }

                switch (br.toLowerCase()) {
                    case "chrome":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.setPlatformName(platformName);
                        driver = new RemoteWebDriver(
                            new URL(hubUrl),
                            chromeOptions
                        );
                        break;

                    case "edge":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        edgeOptions.setPlatformName(platformName);
                        driver = new RemoteWebDriver(
                            new URL(hubUrl),
                            edgeOptions
                        );
                        break;

                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.setPlatformName(platformName);
                        driver = new RemoteWebDriver(
                            new URL(hubUrl),
                            firefoxOptions
                        );
                        break;

                    default:
                        throw new RuntimeException("No matching browser: " + br);
                }

            } else {
                throw new RuntimeException("Invalid grid_type: " + gridType);
            }

        } else if (execEnv.equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new RuntimeException("No matching browser: " + br);
            }

        } else {
            throw new RuntimeException("Invalid execution_env: " + execEnv);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get(p.getProperty("appURL"));
        System.out.println("App launched successfully");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {
        return randomString() + randomNumber();
    }

    protected String getTimeStamp() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    protected String captureScreenshot(WebDriver driver, String testName) {
        String timeStamp = getTimeStamp();
        String path = System.getProperty("user.dir") + "/screenshots/"
                    + testName + "_" + timeStamp + ".png";
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}