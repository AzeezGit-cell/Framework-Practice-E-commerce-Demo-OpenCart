package test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;

import page_objetcts.Home_page;
import page_objetcts.Login_Page;
import page_objetcts.My_Account_Page;
import test_base.Base_class;
import utilities.DataProviders;

public class TC003_LoginDDT extends Base_class {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = { "datadriven", "master" })
	public void verify_loginDDT(String email, String pwd, String exp) {

		logger.info("=================================================");
		logger.info("Executing Login DDT");
		logger.info("Email    : " + email);
		logger.info("Expected : " + exp);
		logger.info("=================================================");

		try {

			Home_page hp = new Home_page(driver);

			hp.clickMyAccount();
			hp.clickLogin();

			Login_Page lp = new Login_Page(driver);

			lp.sendEmail(email.trim());
			lp.sendpassword(pwd.trim());
			lp.clickLogin();

			My_Account_Page mp = new My_Account_Page(driver);

			boolean actualStatus = mp.isMyAccountHeadingDisplayed();

			logger.info("Actual Login Status : " + actualStatus);

			// ================ VALID =================

			if (exp.equalsIgnoreCase("Valid")) {

				Assert.assertTrue(actualStatus, "Expected successful login but login failed for : " + email);

				logger.info("Login Successful");

				mp.clickLogout();

				logger.info("Logout Successful");
			}

			// ================ INVALID =================

			else if (exp.equalsIgnoreCase("Invalid")) {

				Assert.assertFalse(actualStatus, "Expected login failure but login succeeded for : " + email);

				logger.info("Invalid Login Verified");
			}

		} catch (Exception e) {

			logger.error("Exception occurred while executing DDT Login", e);
			Assert.fail("Unexpected Exception : " + e.getMessage());
		}

		logger.info("DDT Execution Completed");
	}
}