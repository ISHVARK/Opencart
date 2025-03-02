package com.opencart.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.opencart.base.Base;
import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.pages.MyAccountPage;
import com.opencart.pages.SearchProductPage;

public class SearchProduct extends Base {

	HomePage homePage;
	LoginPage loginPage;
	MyAccountPage myAccountPage;
	SearchProductPage searchProductPage;

	public SearchProduct() throws Exception {
		super();
	}

	@BeforeMethod
	public void setup() {
		driver = initializeDriverAndOpenApplicationUrl("chrome");

		// Initialize Page Objects
		homePage = new HomePage(driver);
		//myAccountPage = new MyAccountPage(driver);
		searchProductPage = new SearchProductPage(driver);

		// Navigate to Login Page
		loginPage =homePage.VisitLogin();

		// Perform Login
		myAccountPage = loginPage.getLogin(prop.getProperty("username"), prop.getProperty("password"));

		// Verify Login was successful before proceeding
		Assert.assertTrue(myAccountPage.isUserNavigatedToMyAccount(), "Login Failed, cannot proceed with search.");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(priority = 0,dependsOnMethods = {"verifyLoginWithValidCredentials"})
	public void verifyValidProductSearch() throws InterruptedException {

		searchProductPage.searchProduct(dataProp.getProperty("productname"));
		Assert.assertTrue(searchProductPage.isValidProductDisplayed());
	}

	@Test(priority = 1,dependsOnMethods = {"verifyValidProductSearch"})
	public void verifyInvalidProductSearch() throws InterruptedException {
		
		searchProductPage.searchProduct(dataProp.getProperty("invalidProductname"));
		searchProductPage.isNoProductMessageDisplayed();
	}

}
