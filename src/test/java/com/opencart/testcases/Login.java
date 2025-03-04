package com.opencart.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.opencart.base.Base;
import com.opencart.dataproviders.LoginDataProvider;
import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.pages.MyAccountPage;

public class Login extends Base {
    private LoginPage login;
    private MyAccountPage myAccount;
    private HomePage homePage;

    /**
     * Setup method executed before each test case.
     * Initializes WebDriver and navigates to the Login Page.
     */
    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {  // Default browser as Chrome
        try {
            driver = initializeDriverAndOpenApplicationUrl(browser);
            homePage = new HomePage(driver);
            login = homePage.VisitLogin(); // Navigates to login page

            logger.info("Setup completed: Browser '{}' launched, navigated to Login Page.", browser);
        } catch (Exception e) {
            logger.error("Setup failed: " + e.getMessage(), e);
            throw new RuntimeException("Test setup failed!", e);
        }
    }

    /**
     * Teardown method executed after each test.
     * Closes the browser session and cleans up resources.
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully.");
        }
    }

    /**
     * Test: Verify login using data-driven approach.
     * Fetches multiple credentials from LoginDataProvider.
     */
    @Test(dataProvider = "loginData", dataProviderClass = LoginDataProvider.class, priority = 1)
    public void verifyLoginWithDataProvider(String username, String password) {
        try {
            login.getLogin(username, password);
            myAccount = new MyAccountPage(driver); // Ensure correct page instance

            Assert.assertTrue(myAccount.isUserNavigatedToMyAccount(),
                              "Login failed for user: " + username);
            logger.info("Login successful for user: {}", username);
        } catch (Exception e) {
            logger.error("Login test failed for user: " + username + ". Error: " + e.getMessage(), e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    /**
     * Test: Verify login with valid credentials from config.properties.
     */
    @Test(priority = 0)
    public void verifyLoginWithValidCredentials() {
        try {
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            login.getLogin(username, password);
            myAccount = new MyAccountPage(driver); // Ensure correct page instance

            Assert.assertTrue(myAccount.isUserNavigatedToMyAccount(),
                              "Valid login failed for username: " + username);
            logger.info("Login successful for default test user: {}", username);
        } catch (Exception e) {
            logger.error("Login with valid credentials failed: " + e.getMessage(), e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    /**
     * Test: Verify login with invalid credentials.
     */
    @Test(priority = 2)
    public void verifyLoginWithInvalidCredentials() {
        try {
            login.getLogin(dataProp.getProperty("invalidEmail"), dataProp.getProperty("invalidPassword"));

            String warningMessage = login.getWarning();
            Assert.assertTrue(warningMessage.contains(dataProp.getProperty("InvalidUserWarning")),
                              "Expected warning not displayed for invalid credentials.");

            logger.warn("Attempted login with invalid credentials.");
        } catch (Exception e) {
            logger.error("Invalid login test failed: " + e.getMessage(), e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    /**
     * Test: Verify login with an invalid email and a valid password.
     */
    @Test(priority = 3)
    public void verifyLoginWithInvalidEmailAndValidPassword() {
        try {
            login.getLogin(dataProp.getProperty("invalidEmail"), prop.getProperty("password"));

            String warningMessage = login.getWarning();
            Assert.assertTrue(warningMessage.contains(dataProp.getProperty("InvalidUserWarning")),
                              "Expected warning not displayed for invalid email.");

            logger.warn("Attempted login with invalid email but valid password.");
        } catch (Exception e) {
            logger.error("Test failed for invalid email and valid password: " + e.getMessage(), e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
