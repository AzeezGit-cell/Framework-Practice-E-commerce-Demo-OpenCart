package utilities;

import java.awt.Desktop;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.openqa.selenium.*;
import org.testng.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.MediaEntityBuilder;

import test_base.Base_class;

public class ExtentListener extends Base_class implements ITestListener {

	ExtentReports extent = ExtentManager.getInstance();

	// ================== SUITE START ==================
	@Override
	public void onStart(ITestContext context) {

		System.out.println("===== Test Execution Started =====");

		extent.setSystemInfo("Suite Name", context.getSuite().getName());
		extent.setSystemInfo("Test Name", context.getName());
		extent.setSystemInfo("Groups", Arrays.toString(context.getIncludedGroups()));
	}

	// ================== TEST START ==================
	@Override
	public void onTestStart(ITestResult result) {

		ExtentTest test = extent.createTest(result.getName());

		// Groups
		String[] groups = result.getMethod().getGroups();
		if (groups.length > 0) {
			test.assignCategory(groups);
		}

		ExtentTestManager.setTest(test);

		ExtentTestManager.getTest().log(Status.INFO, "Test Started at: " + getTimeStamp());
	}

	// ================== PASS ==================
	@Override
	public void onTestSuccess(ITestResult result) {

		ExtentTestManager.getTest().log(Status.PASS, "Test Passed at: " + getTimeStamp());
	}

	// ================== FAILURE ==================
	@Override
	public void onTestFailure(ITestResult result) {

		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed at: " + getTimeStamp());

		ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());

		try {
			WebDriver driver = getDriver(result);

			if (driver != null) {
				String path = captureScreenshot(driver, result.getName());

				// ✅ Screenshot embedded (clickable)
				ExtentTestManager.getTest().fail("Screenshot",
						MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ================== SKIPPED ==================
	@Override
	public void onTestSkipped(ITestResult result) {

		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped at: " + getTimeStamp());
	}

	// ================== SUITE END ==================
	@Override
	public void onFinish(ITestContext context) {

		System.out.println("===== Test Execution Finished =====");

		extent.flush();

		// ✅ Auto open report
		try {
			File reportFile = new File(ExtentManager.reportPath);
			Desktop.getDesktop().browse(reportFile.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ================== DRIVER FETCH ==================
	private WebDriver getDriver(ITestResult result) {

		Object testInstance = result.getInstance();
		Class<?> clazz = result.getTestClass().getRealClass();

		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField("driver");
				field.setAccessible(true);
				return (WebDriver) field.get(testInstance);
			} catch (Exception e) {
				clazz = clazz.getSuperclass();
			}
		}

		return null;
	}

	// ================== SCREENSHOT ==================

}