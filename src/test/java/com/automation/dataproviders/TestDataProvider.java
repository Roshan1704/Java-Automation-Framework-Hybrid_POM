package com.automation.dataproviders;

import com.automation.utils.ExcelReader;
import com.automation.utils.JsonReader;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

/**
 * Test Data Provider - Provides test data from various sources
 */
public class TestDataProvider {

    @DataProvider(name = "excelLoginData")
    public Object[][] getExcelLoginData() {
        ExcelReader excelReader = new ExcelReader("testdata.xlsx");
        Object[][] data = excelReader.getTestDataAsArray("LoginData");
        excelReader.close();
        return data;
    }

    @DataProvider(name = "jsonUserData")
    public Object[][] getJsonUserData() {
        List<Map<String, Object>> data = JsonReader.readJsonAsList("testdata.json");
        Object[][] result = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;
    }

    @DataProvider(name = "browserData", parallel = true)
    public Object[][] getBrowserData() {
        return new Object[][] {
                { "chrome" },
                { "firefox" },
                { "edge" }
        };
    }

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return new Object[][] {
                { "selenium", 10 },
                { "automation", 5 },
                { "testing", 15 }
        };
    }
}
