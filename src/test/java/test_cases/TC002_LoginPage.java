package test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;

import page_objetcts.Home_page;
import page_objetcts.Login_Page;
import page_objetcts.My_Account_Page;
import test_base.Base_class;

public class TC002_LoginPage extends Base_class {

	@Test(groups = { "smoke", "regression", "master" })
	public void LoginIntoAccount() {

		logger.info("TC002 started");

		try {
			Home_page hp = new Home_page(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			Login_Page lp = new Login_Page(driver);
			lp.sendEmail(p.getProperty("email"));
			lp.sendpassword(p.getProperty("password"));
			lp.clickLogin();

			My_Account_Page mp = new My_Account_Page(driver);

			Assert.assertTrue(mp.isMyAccountHeadingDisplayed());

		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("TC002 completed");
	}
}
