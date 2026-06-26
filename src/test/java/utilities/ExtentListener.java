package utilities;

import java.awt.Desktop;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class ExtentListener implements ITestListener {

	private final ExtentReports extent = ExtentManager.getInstance();

	@Override
	public void onStart(ITestContext context) {

		System.out.println("===== Test Execution Started =====");

		extent.setSystemInfo("Suite", context.getSuite().getName());
		extent.setSystemInfo("Test", context.getName());
		extent.setSystemInfo("Groups", Arrays.toString(context.getIncludedGroups()));
	}

	@Override
	public void onTestStart(ITestResult result) {

		ExtentTest test = extent.createTest(result.getMethod().getMethodName());

		if (result.getMethod().getGroups().length > 0)
			test.assignCategory(result.getMethod().getGroups());

		ExtentTestManager.setTest(test);

		ExtentTestManager.getTest().log(Status.INFO, "Started : " + DateUtil.getTimeStamp());
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ExtentTestManager.getTest().log(Status.PASS, "Passed : " + DateUtil.getTimeStamp());
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ExtentTestManager.getTest().log(Status.FAIL, "Failed : " + DateUtil.getTimeStamp());

		ExtentTestManager.getTest().fail(result.getThrowable());

		try {

			WebDriver driver = getDriver(result);

			if (driver != null) {

				String screenshot = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());

				ExtentTestManager.getTest().fail("Failure Screenshot",
						MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		ExtentTestManager.getTest().log(Status.SKIP, "Skipped : " + DateUtil.getTimeStamp());
	}

	@Override
	public void onFinish(ITestContext context) {

		System.out.println("===== Test Execution Finished =====");

		extent.flush();

		try {

			File report = new File(ExtentManager.reportPath);

			if (report.exists())
				Desktop.getDesktop().browse(report.toURI());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// ================ Driver ===================

	private WebDriver getDriver(ITestResult result) {

		Object currentObject = result.getInstance();

		Class<?> clazz = currentObject.getClass();

		while (clazz != null) {

			try {

				Field field = clazz.getDeclaredField("driver");

				field.setAccessible(true);

				return (WebDriver) field.get(currentObject);

			}

			catch (Exception e) {

				clazz = clazz.getSuperclass();
			}
		}

		return null;
	}
}