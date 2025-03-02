package com.opencart.pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchProductPage {
	WebDriver driver;
	WebDriverWait wait;

	// Constructor to initialize WebDriver and PageFactory elements
	public SearchProductPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// WebElements for Search functionality
	@FindBy(name = "search")
	private WebElement searchBox;

	@FindBy(xpath = "//i[@class='fa-solid fa-magnifying-glass']")
	private WebElement searchButton;

	@FindBy(xpath = "//a[normalize-space()='iMac']")
	private WebElement validProduct;

	@FindBy(xpath = "//p[contains(text(),'There is no product that matches the search criter')]")
	private WebElement noProductMessage;

	// Method to search for a product
	public void searchProduct(String productName) {
		wait.until(ExpectedConditions.visibilityOf(searchBox)).sendKeys(productName);
		wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
	}

	// Method to verify if a valid product is displayed
	public boolean isValidProductDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(validProduct)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// Method to verify if no product message is displayed
	public boolean isNoProductMessageDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(noProductMessage)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}
