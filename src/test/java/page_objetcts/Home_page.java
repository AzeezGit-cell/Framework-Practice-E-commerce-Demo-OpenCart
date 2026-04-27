package page_objetcts;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home_page extends Base_Page {

    public Home_page(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[normalize-space()='My Account']")
    WebElement btnMyAccount;

    @FindBy(xpath = "//a[normalize-space()='Register']")
    WebElement btnRegister;

    @FindBy(xpath = "//a[normalize-space()='Login']")
    WebElement btnLogin;

    public void clickMyAccount() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(btnMyAccount)).click();
    }

    public void clickRegister() {
        btnRegister.click();
    }

    public void clickLogin() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(btnLogin)).click();
    }
}