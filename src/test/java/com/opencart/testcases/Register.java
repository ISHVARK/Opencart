package com.opencart.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.opencart.base.Base;
import com.opencart.pages.HomePage;
import com.opencart.pages.MyAccountPage;
import com.opencart.pages.RegisterPage;
import com.opencart.utils.Utilities;

/**
 * Test class for user registration functionality.
 * Ensures registration with mandatory fields is working as expected.
 */
public class Register extends Base {
    private HomePage homePage;
    private RegisterPage registerPage;
    private MyAccountPage myAccountPage;

    /**
     * Constructor to initialize properties from Base class.
     */
    public Register() {
        super();
    }

    /**
     * Test setup - initializes driver, navigates to register page.
     */
    @BeforeMethod
    @Parameters("browser")  
    public void setup(@Optional("chrome") String browser) {
        try {
            driver = initializeDriverAndOpenApplicationUrl(browser);
            logger.info("Driver initialized and application launched.");
            
            homePage = new HomePage(driver);
            myAccountPage = new MyAccountPage(driver);
            registerPage = homePage.VisitRegister();
            logger.info("Navigated to Register Page.");
        } catch (Exception e) {
            logger.error("Exception in setup: " + e.getMessage(), e);
            Assert.fail("Setup failed due to an exception: " + e.getMessage());
        }
    }

    /**
     * Test cleanup - closes browser.
     */
    @AfterMethod
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
                logger.info("Browser closed successfully.");
            }
        } catch (Exception e) {
            logger.error("Exception during tearDown: " + e.getMessage(), e);
        }
    }

    /**
     * Test case to verify registration with mandatory fields.
     */
    @Test(priority = 0)
    public void verifyRegistrationWithMandatoryFields() {
        try {
            logger.info("Starting registration test with mandatory fields.");
            
            registerPage.RegisterWithMandatoryFields(
                dataProp.getProperty("firstname"),
                dataProp.getProperty("lastname"),
                Utilities.generateEmailWithTimestamp(),
                dataProp.getProperty("password")
            );
            
            Assert.assertTrue(myAccountPage.checkRegistrationStatus(), "Registration failed.");
            logger.info("Registration test passed.");
        } catch (Exception e) {
            logger.error("Exception in verifyRegistrationWithMandatoryFields: " + e.getMessage(), e);
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }
}
