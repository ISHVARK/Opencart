package com.opencart.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Utilities {

    public static final int implicitWaitTimeout = 5;
    public static final int pageLoadTimeout = 5;

    public static String generateEmailWithTimestamp() {
    	Date d = new Date();
		String datetext = d.toString().replace(" ", "_").replace(":", "_");
		return "ishvar" + datetext + "kharche@gmail.com";
    }

    public static String getScreenShot(WebDriver driver, String TestName) {
        File SrcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotDir = System.getProperty("user.dir") + "\\Screenshots\\";
        String DestFile = screenshotDir + TestName + ".png";

        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            FileHandler.copy(SrcFile, new File(DestFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DestFile;
    }
}
