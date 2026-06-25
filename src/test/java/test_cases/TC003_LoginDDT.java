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

		logger.info("Running with: " + email + " | Expected: " + exp);

		try {
			Home_page hp = new Home_page(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			Login_Page lp = new Login_Page(driver);
			lp.sendEmail(email.trim());
			lp.sendpassword(pwd.trim());
			lp.clickLogin();

			My_Account_Page mp = new My_Account_Page(driver);

			boolean status = mp.isMyAccountHeadingDisplayed();

			System.out.println("Expected: " + exp + " | Login Success: " + status);

			if (exp.equalsIgnoreCase("Valid")) {
				Assert.assertTrue(status,
						"FAIL: Expected successful login but My Account page not shown for: " + email);
				mp.clickLogout(); // cleanup

			} else if (exp.equalsIgnoreCase("Invalid")) {
				Assert.assertFalse(status, "FAIL: Expected login to fail but My Account page was shown for: " + email);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception: " + e.getMessage());
		}

		logger.info("TC003 Execution Finished for: " + email);
	}
}