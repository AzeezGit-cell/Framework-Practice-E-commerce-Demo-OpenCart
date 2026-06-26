package test_cases;

import org.testng.Assert;

import org.testng.annotations.Test;

import page_objetcts.Home_page;
import page_objetcts.Login_Page;
import page_objetcts.My_Account_Page;
import test_base.Base_class;
import utilities.ConfigReader;

public class TC002_LoginPage extends Base_class {

	@Test(groups = { "smoke", "regression", "master" })
	public void LoginIntoAccount() {

		logger.info("TC002 started");

		try {
			Home_page hp = new Home_page(driver);

			logger.info(driver.getCurrentUrl());
			logger.info(driver.getTitle());
			hp.clickMyAccount();
			logger.info("Clicked My account");
			hp.clickLogin();
			logger.info("Clicked Login");

			Login_Page lp = new Login_Page(driver);
			logger.info(driver.getCurrentUrl());
			logger.info(driver.getTitle());
			lp.sendEmail(ConfigReader.getProperty("email"));
			logger.info("Email added");
			lp.sendpassword(ConfigReader.getProperty("password"));
			logger.info("password added");
			lp.clickLogin();

			logger.info("Clicked on login");

			My_Account_Page mp = new My_Account_Page(driver);
			logger.info(driver.getCurrentUrl());
			logger.info(driver.getTitle());

			Assert.assertTrue(mp.isMyAccountHeadingDisplayed());

		} catch (Exception e) {

			e.printStackTrace();

			Assert.fail(e.getMessage());

		}
		logger.info("TC002 completed");
	}
}
