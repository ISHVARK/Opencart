package com.opencart.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.opencart.base.Base;
import com.opencart.dataproviders.LoginDataProvider;
import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.pages.MyAccountPage;

public class Login extends Base {
	LoginPage login;
	MyAccountPage myAccount;
	HomePage hp;

	public Login() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@BeforeMethod
	public void setup() {
		driver = initializeDriverAndOpenApplicationUrl(prop.getProperty("browser"));
		hp = new HomePage(driver);
		// login = new LoginPage(driver); //code optimized
		myAccount = new MyAccountPage(driver);
		login = hp.VisitLogin();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test(dataProvider = "loginData", dataProviderClass = LoginDataProvider.class, priority = 1,dependsOnMethods = {"verifyLoginWithValidCredentials"})
	public void verifyLoginDataDriven(String username, String password) {
		login.getLogin(username, password);
		Assert.assertTrue(myAccount.isUserNavigatedToMyAccount());

	}

	@Test(priority = 0)
	public void verifyLoginWithValidCredentials() {

		login.getLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(myAccount.isUserNavigatedToMyAccount());
	}

	@Test(priority = 2)
	public void verifyLoginWithInvalidCredentials() {

		login.getLogin(dataProp.getProperty("invalidEmail"), dataProp.getProperty("invalidPassword"));
		Assert.assertTrue(login.getWarning().contains(dataProp.getProperty("InvalidUserWarning")));
	}

	@Test(priority = 3)
	public void verifyLoginWithInvalidEmailAndValidPassword() {
		login.getLogin(dataProp.getProperty("invalidEmail"), prop.getProperty("password"));
		Assert.assertTrue(login.getWarning().contains(dataProp.getProperty("InvalidUserWarning")));
	}
}
