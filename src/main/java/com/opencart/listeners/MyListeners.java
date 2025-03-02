package com.opencart.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.opencart.utils.Utilities;

public class MyListeners implements ITestListener {
	ExtentReports extentReport;
	ExtentTest extentTest;
	String TestName;

	public void onStart(ITestContext context) {
		extentReport = com.opencart.utils.ExtentReporter.generateExtentReport();
		if (extentReport == null) {
			System.out.println("Extent Report initialization failed!");
		} else {
			System.out.println("Execution of Project Tests Started.");
		}
	}

	public void onTestStart(ITestResult result) {
		TestName = result.getName();
		extentTest = extentReport.createTest(TestName);
		extentTest.log(Status.INFO, TestName + " Started Execution");
	}

	public void onTestSuccess(ITestResult result) {
		extentTest.log(Status.PASS, TestName + " got successfully Executed");
	}

	public void onTestFailure(ITestResult result) {
		extentTest.log(Status.FAIL, result.getName() + " failed.");
		extentTest.log(Status.INFO, result.getThrowable());

		if (result.getMethod().getRetryAnalyzer(result) == null) {
			result.getMethod().setRetryAnalyzerClass(RetryAnalyzer.class);
		}

		WebDriver driver = getDriverInstance(result);

		if (driver != null) {
			String screenshotPath = Utilities.getScreenShot(driver, result.getName());
			try {
				extentTest.addScreenCaptureFromPath(screenshotPath);
			} catch (Exception e) {
				extentTest.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
			}
		} else {
			extentTest.log(Status.WARNING, "Screenshot not captured as WebDriver instance is null.");
		}
	}

	public void onTestSkipped(ITestResult result) {
		extentTest.log(Status.SKIP, TestName + " Got Skipped");
		extentTest.log(Status.INFO, result.getThrowable());
	}

	public void onFinish(ITestContext context) {
		extentReport.flush();
		String FilePathOfExtentReport = System.getProperty("user.dir")
				+ "\\test-output\\ExtentReports\\extentReport.html";
		File extentreport = new File(FilePathOfExtentReport);
		try {
			Desktop.getDesktop().browse(extentreport.toURI());
		} catch (IOException e) {
			extentTest.log(Status.FAIL, "Failed to open extent report: " + e.getMessage());
		}
	}

	private WebDriver getDriverInstance(ITestResult result) {
		try {
			Object testClass = result.getInstance();
			return (WebDriver) testClass.getClass().getDeclaredField("driver").get(testClass);
		} catch (NoSuchFieldException e) {
			extentTest.log(Status.INFO, "Driver field not found in test class.");
		} catch (IllegalAccessException e) {
			extentTest.log(Status.INFO, "Cannot access driver field: " + e.getMessage());
		} catch (Exception e) {
			extentTest.log(Status.INFO, "Error retrieving WebDriver instance: " + e.getMessage());
		}
		return null;
	}
}
