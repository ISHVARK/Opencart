package com.opencart.base;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import com.opencart.utils.Utilities;


public class Base {
	public WebDriver driver;
	public static JavascriptExecutor js;
	public static Properties prop;
	public static Properties dataProp;
	ChromeOptions options;

	public Base() throws Exception {
		File f = new File(System.getProperty("user.dir") + "/src/main/java/com/opencart/config/config.properties");
		FileInputStream file = new FileInputStream(f);
		prop = new Properties();
		prop.load(file);
		
		
		File datafile = new File(System.getProperty("user.dir") + "/src/main/java/com/opencart/testdata/testData.properties");
		FileInputStream dataFile = new FileInputStream(datafile);
		dataProp = new Properties();
		dataProp.load(dataFile);
		options=new ChromeOptions();
		options.addArguments("--headless=new");
	}

	protected WebDriver initializeDriverAndOpenApplicationUrl(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Utilities.implicitWaitTimeout));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Utilities.pageLoadTimeout));
		driver.manage().window().maximize();
		js = (JavascriptExecutor) driver;
		driver.get(prop.getProperty("baseUrl"));

		return driver;
	}

}
