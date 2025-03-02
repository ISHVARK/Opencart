package com.opencart.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
	WebDriver driver;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "#input-firstname")
	WebElement firstname;
	
	@FindBy(css = "#input-lastname")
	WebElement lastname;
	
	@FindBy(css = "#input-email")
	WebElement email;
	
	@FindBy(css = "#input-password")
	WebElement password;

	@FindBy(xpath = "//button[normalize-space()='Continue']")
	WebElement continueBtn;
	
	@FindBy(css = "input[value='1'][name='agree']")
	WebElement privacyPolicy;
	
	@FindBy(xpath =  "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement checkregister;
	
	public boolean checkRegistrationStatus() {
		return checkregister.isDisplayed();
	}

	public void RegisterWithMandatoryFields(String fname, String lname,String eml,String pwd) {
		firstname.sendKeys(fname);
		lastname.sendKeys(lname);
		email.sendKeys(eml);
		password.sendKeys(pwd);
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", privacyPolicy);
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    privacyPolicy.click();
		continueBtn.click();
	}

}
