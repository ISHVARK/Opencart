package com.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;
	
	public HomePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement AccountMenu;
	
	@FindBy(xpath="//a[normalize-space()='Login']")
	WebElement LoginOption;
	
	@FindBy(xpath="//a[normalize-space()='Register']")
	WebElement RegisterOption;
	
	public LoginPage VisitLogin() {
		AccountMenu.click();
		LoginOption.click();
		return new LoginPage(driver);
	}
	public RegisterPage VisitRegister() {
		AccountMenu.click();
		RegisterOption.click();
		return new RegisterPage(driver);
	}

}
