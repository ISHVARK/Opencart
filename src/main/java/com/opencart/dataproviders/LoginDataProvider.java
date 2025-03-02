package com.opencart.dataproviders;

import java.io.IOException;

import com.opencart.utils.ExcelUtils;

public class LoginDataProvider {

	@org.testng.annotations.DataProvider(name = "loginData")
	public Object[][] ProvideData() throws IOException {
		String xlfile = System.getProperty("user.dir") + "/src/test/resources/LoginData.xlsx";
		int row = ExcelUtils.getRowCount(xlfile, "Sheet1");
		int col = ExcelUtils.getCellCount(xlfile, "Sheet1", 1);

		Object[][] obj = new Object[row][col];
		for (int i = 1; i <= row; i++) {
			for (int j = 0; j < col; j++) {
				try {
					obj[i - 1][j] = ExcelUtils.getCellData(xlfile, "Sheet1", i, j);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
}
