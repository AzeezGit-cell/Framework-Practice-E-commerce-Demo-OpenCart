package page_objetcts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Account_Registration_page extends Base_Page {

	public Account_Registration_page(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement btnFirstName;

	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement btnLastName;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement btnEmail;

//	@FindBy(xpath = "//input[@id='input-telephone']")
//	WebElement btntelephone;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement btnpassword;

//	@FindBy(xpath = "//input[@id='input-confirm']")
//	WebElement btnconfirmPassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement checkpolicy;

	@FindBy(xpath = "(//button[@type='submit'])[2]")
	WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;

	public void setFirstName(String fname) {
		btnFirstName.sendKeys(fname);
	}

	public void setLastName(String lname) {
		btnLastName.sendKeys(lname);
	}

	public void setEmail(String email) {
		btnEmail.sendKeys(email);
	}

//	public void setTelePhone(String telephone) {
//		btntelephone.sendKeys(telephone);
//	}

	public void setPassword(String pwrd) {
		btnpassword.sendKeys(pwrd);
	}

//	public void confirmPassword(String CnfrmPwrd) {
//		btnconfirmPassword.sendKeys(CnfrmPwrd);
//	}

	public void checkpolicy() {

		js.executeScript("arguments[0].scrollIntoView(true);", checkpolicy);
		js.executeScript("arguments[0].click();", checkpolicy);
	}

	public void conitnue() {
		js.executeScript("arguments[0].scrollIntoView(true);", btnContinue);
		js.executeScript("arguments[0].click();", btnContinue);
	}

	public String getConfirmationMsg() {
		try {
			return (msgConfirmation.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
	}
}
