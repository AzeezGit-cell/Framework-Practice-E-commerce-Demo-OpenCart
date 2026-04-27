package page_objetcts;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class My_Account_Page extends Base_Page {

	public My_Account_Page(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[text()='My Account']")
	WebElement msg;

	@FindBy(xpath = "//div[@class = 'list-group']//a[text()='Logout']")
	WebElement btnLogout;

	public boolean isMyAccountHeadingDisplayed() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(msg));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clickLogout() {
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(btnLogout))
				.click();
	}

}