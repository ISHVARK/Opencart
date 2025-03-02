package com.opencart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	WebDriver driver;
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="input-email")
	WebElement email;
	
	@FindBy(id="input-password")
	WebElement password;
	
	@FindBy(xpath="//button[normalize-space()='Login']")
	WebElement loginBtn;
	
	@FindBy(xpath="//div[contains(.,'Warning')]/div/div")
	WebElement Warningmsg;
	
	public MyAccountPage getLogin(String username,String pwd) {
		email.sendKeys(username);
		password.sendKeys(pwd);
		loginBtn.click();
		return new MyAccountPage(driver);
	}
	
	public String getWarning() {
		 try {
		        return Warningmsg.isDisplayed() ? Warningmsg.getText() : "Warning not found";
		    } catch (Exception e) {
		        return "Warning message not displayed.";
		    }
	}

}
