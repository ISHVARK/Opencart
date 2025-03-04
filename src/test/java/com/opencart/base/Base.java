package com.opencart.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


import com.opencart.utils.Utilities;

/**
 * Base class for initializing WebDriver and managing test configurations.
 * Handles browser setup, property file loading, and global settings.
 */
public class Base {
    protected WebDriver driver;
    protected static JavascriptExecutor js;
    protected static Properties prop;
    protected static Properties dataProp;
    protected static final Logger logger = LogManager.getLogger(Base.class);
    private static ChromeOptions chromeOptions;
    private static EdgeOptions edgeOptions ;

    // Static block for one-time setup (loads properties & browser options)
    static {
        prop = new Properties();
        dataProp = new Properties();
        chromeOptions = new ChromeOptions();
       // chromeOptions.addArguments("--start-maximized"); 
        
        edgeOptions = new EdgeOptions();
      //  edgeOptions.addArguments("--start-maximized"); 
        edgeOptions.addArguments("--remote-allow-origins=*");
        // Assign a unique user data directory to avoid conflicts
//       edgeOptions.addArguments("--user-data-dir=" + Paths.get(System.getProperty("java.io.tmpdir"), "EdgeUserData").toString());

        try {
            loadProperties(prop, "/src/main/java/com/opencart/config/config.properties");
            loadProperties(dataProp, "/src/main/java/com/opencart/testdata/testData.properties");
        } catch (IOException e) {
            logger.error("Error loading properties files: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load properties files.", e);
        }
    }

    /**
     * Loads a properties file from the given path.
     *
     * @param properties Properties object to load data into.
     * @param filePath   Path to the properties file.
     * @throws IOException If an error occurs while reading the file.
     */
    private static void loadProperties(Properties properties, String filePath) throws IOException {
        File file = new File(System.getProperty("user.dir") + filePath);
        try (FileInputStream fileInput = new FileInputStream(file)) {
            properties.load(fileInput);
            logger.info("Loaded properties from: {}", filePath);
        }
    }

    /**
     * Initializes WebDriver based on the provided browser type and opens the application URL.
     *
     * @param browser Browser type (chrome, edge) received from TestNG parameters.
     * @return Initialized WebDriver instance.
     */
    @Parameters("browser")
    public WebDriver initializeDriverAndOpenApplicationUrl(@Optional String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver(chromeOptions);
                logger.info("Chrome browser launched.");
                break;
            case "edge":
                driver = new EdgeDriver();
                logger.info("Edge browser launched.");
                break;
            case "firefox":
                driver = new FirefoxDriver();
                logger.info("FireFox browser launched.");
                break;
            default:
                logger.error("Unsupported browser specified: {}", browser);
                throw new IllegalArgumentException("Invalid browser: " + browser);
        }

        // Configure browser settings
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Utilities.implicitWaitTimeout));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Utilities.pageLoadTimeout));
        driver.manage().window().maximize();
        // Initialize JavaScript Executor
        js = (JavascriptExecutor) driver;

        // Open base URL
        String baseUrl = prop.getProperty("baseUrl");
        if (baseUrl != null && !baseUrl.isEmpty()) {
            driver.get(baseUrl);
            logger.info("Navigated to: " + baseUrl);
            System.out.println("Base URL: " + baseUrl);
            logger.info("Base URL from properties: " + baseUrl);

        } else {
            logger.error("Base URL not found in properties file.");
            throw new RuntimeException("Base URL is missing in config.properties.");
        }

        return driver;
    }
}