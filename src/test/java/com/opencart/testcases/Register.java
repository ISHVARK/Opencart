package com.opencart.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.opencart.base.Base;
import com.opencart.pages.HomePage;
import com.opencart.pages.MyAccountPage;
import com.opencart.pages.RegisterPage;
import com.opencart.utils.Utilities;

public class Register extends Base {

	HomePage homePage;
	RegisterPage registerPage;
	MyAccountPage myAccountPage;

	public Register() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@BeforeMethod
	public void setup() {
		driver = initializeDriverAndOpenApplicationUrl("chrome");
		homePage = new HomePage(driver);
		//registerPage = new RegisterPage(driver); optimize
		myAccountPage=new MyAccountPage(driver);
		registerPage =homePage.VisitRegister();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}


	@Test(priority = 0)
	public void verifyRegistrationWithMandatoryFields() throws InterruptedException {

		registerPage.RegisterWithMandatoryFields(dataProp.getProperty("firstname"), dataProp.getProperty("lastname"),
				Utilities.generateEmailWithTimestamp(), dataProp.getProperty("password"));
		Assert.assertTrue(myAccountPage.checkRegistrationStatus());

	}

}
