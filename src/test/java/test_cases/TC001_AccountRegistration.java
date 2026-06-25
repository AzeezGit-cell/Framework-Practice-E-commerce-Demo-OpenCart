package test_cases;

import org.testng.Assert;

import org.testng.annotations.Test;

import page_objetcts.Account_Registration_page;
import page_objetcts.Home_page;
import test_base.Base_class;

public class TC001_AccountRegistration extends Base_class {

	@Test(groups = { "regression", "master" })
	public void accountRegistration() {

		try {
			logger.info("Testcase_1 started");
			Home_page homepage = new Home_page(driver);

			homepage.clickMyAccount();
			logger.info("clicked on myAccount");
			homepage.clickRegister();
			logger.info("clicked on register");

			Account_Registration_page ac = new Account_Registration_page(driver);

			logger.info("providing details");

			logger.info("firstname");
			ac.setFirstName(randomString().toUpperCase());
			logger.info("lastname");
			ac.setLastName(randomString().toUpperCase());
			logger.info("Email");
			ac.setEmail(randomString() + "@gmail.com");
			// ac.setTelePhone(randomNumber());

			String password = randomAlphaNumeric();
			logger.info("password");
			ac.setPassword(password);
			// ac.confirmPassword(password);

			logger.info("checkpolicy");

			ac.checkpolicy();

			logger.info("continue");

			ac.conitnue();

			logger.info("validating expected message");
			String confmsg = ac.getConfirmationMsg();
			if (confmsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.error("Test failed");
				logger.debug("Debugg logs");
				Assert.assertTrue(false);
			}
			logger.info("message successfull");
		} catch (Exception e) {

			Assert.fail();
		}
		logger.info("Execution finished");

	}

}
