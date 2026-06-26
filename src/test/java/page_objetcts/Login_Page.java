package page_objetcts;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login_Page extends Base_Page {
	
	int a = 10;

	public Login_Page(WebDriver driver) {
		super(driver);
	}

	@FindBy(name  = "email")
	WebElement txtEmail;

	@FindBy(name = "password")
	WebElement txtPassword;

	@FindBy(xpath = "//button[normalize-space()='Login']")
	WebElement btnLogin;

	public void sendEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void sendpassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}

	public void clickLogin() {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(btnLogin))
				.click();
	}
}