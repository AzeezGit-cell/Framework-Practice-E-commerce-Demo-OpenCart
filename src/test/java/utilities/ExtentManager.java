package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;
    public static String reportPath;

    public static ExtentReports getInstance() {

        if (extent == null) {

            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String reportName = "Test-Report-" + timeStamp + ".html";

            reportPath = System.getProperty("user.dir") + "/reports/" + reportName;

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("OpenCart Automation Report");
            spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Application", "OpenCart");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }

        return extent;
    }
}