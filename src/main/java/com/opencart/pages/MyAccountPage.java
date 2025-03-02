package com.opencart.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage {
	WebDriver driver;
	public MyAccountPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//h1[normalize-space()='My Account']")
	WebElement MyAccount;
	
	@FindBy(xpath =  "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement checkregister;
	
	public boolean checkRegistrationStatus() {
		return checkregister.isDisplayed();
	}
	
	public boolean isUserNavigatedToMyAccount() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    try {
	        WebElement myAccountHeading =  wait.until(ExpectedConditions.visibilityOf(MyAccount)); 
	        return myAccountHeading.isDisplayed();
	    } catch (Exception e) {
	        System.out.println("My Account page not found: " + e.getMessage());
	        return false;
	    }
	}
	
}
