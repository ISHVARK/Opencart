package com.opencart.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.opencart.base.Base;
import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.pages.MyAccountPage;
import com.opencart.pages.SearchProductPage;

/**
 * Test case for searching products on OpenCart.
 * Ensures both valid and invalid product searches are handled correctly.
 */
public class SearchProduct extends Base {
    private HomePage homePage;
    private LoginPage loginPage;
    private MyAccountPage myAccountPage;
    private SearchProductPage searchProductPage;

    /**
     * Constructor for SearchProduct test case.
     * Calls Base constructor to load properties.
     */
    public SearchProduct() {
        super();
    }

    /**
     * Test setup - initializes driver, logs in, and verifies login success.
     */
    @BeforeMethod
    @Parameters("browser")  
    public void setup(@Optional("chrome") String browser) {
        try {
            driver = initializeDriverAndOpenApplicationUrl(browser);
            logger.info("Driver initialized and application launched.");

            // Initialize Page Objects
            homePage = new HomePage(driver);
            searchProductPage = new SearchProductPage(driver);

            // Navigate to Login Page
            loginPage = homePage.VisitLogin();
            logger.info("Navigated to Login Page.");

            // Perform Login
            myAccountPage = loginPage.getLogin(prop.getProperty("username"), prop.getProperty("password"));

            // Verify Login was successful before proceeding
            if (!myAccountPage.isUserNavigatedToMyAccount()) {
                logger.error("Login failed. Cannot proceed with product search.");
                Assert.fail("Login failed, cannot proceed with search.");
            }
            logger.info("Login successful. Proceeding with search tests.");
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
     * Validates product search functionality with a valid product.
     */
    @Test(priority = 0)
    public void verifyValidProductSearch() {
        try {
            logger.info("Starting valid product search test.");
            searchProductPage.searchProduct(dataProp.getProperty("productname"));

            Assert.assertTrue(
                searchProductPage.isValidProductDisplayed(),
                "Valid product was not displayed as expected."
            );
            logger.info("Valid product search test passed.");
        } catch (Exception e) {
            logger.error("Exception in verifyValidProductSearch: " + e.getMessage(), e);
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    /**
     * Validates product search functionality with an invalid product.
     */
    @Test(priority = 1)
    public void verifyInvalidProductSearch() {
        try {
            logger.info("Starting invalid product search test.");
            searchProductPage.searchProduct(dataProp.getProperty("invalidProductname"));

            Assert.assertTrue(
                searchProductPage.isNoProductMessageDisplayed(),
                "Expected 'No Product Found' message was not displayed."
            );
            logger.info("Invalid product search test passed.");
        } catch (Exception e) {
            logger.error("Exception in verifyInvalidProductSearch: " + e.getMessage(), e);
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }
}
