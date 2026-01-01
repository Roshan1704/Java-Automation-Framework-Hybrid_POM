package com.automation.utils;

import com.automation.constants.FrameworkConstants;
import com.automation.exceptions.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel Reader Utility - Reads test data from Excel files
 */
public class ExcelReader {

    private static final Logger log = LoggerFactory.getLogger(ExcelReader.class);

    private final Workbook workbook;
    private final String filePath;

    public ExcelReader(String fileName) {
        this.filePath = FrameworkConstants.TEST_DATA_PATH + fileName;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            this.workbook = new XSSFWorkbook(fis);
            log.info("Excel file loaded: {}", filePath);
        } catch (IOException e) {
            throw new FrameworkException("Failed to load Excel file: " + filePath, e);
        }
    }

    public List<Map<String, String>> getTestData(String sheetName) {
        List<Map<String, String>> testData = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            throw new FrameworkException("Sheet not found: " + sheetName);
        }

        Row headerRow = sheet.getRow(0);
        int columnCount = headerRow.getLastCellNum();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row currentRow = sheet.getRow(i);
            if (currentRow == null) continue;

            Map<String, String> rowData = new HashMap<>();
            for (int j = 0; j < columnCount; j++) {
                String header = getCellValue(headerRow.getCell(j));
                String value = getCellValue(currentRow.getCell(j));
                rowData.put(header, value);
            }
            testData.add(rowData);
        }

        log.info("Loaded {} rows from sheet: {}", testData.size(), sheetName);
        return testData;
    }

    public Object[][] getTestDataAsArray(String sheetName) {
        List<Map<String, String>> data = getTestData(sheetName);
        Object[][] result = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }

        return result;
    }

    public String getCellData(String sheetName, int rowNum, int colNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return "";

        Row row = sheet.getRow(rowNum);
        if (row == null) return "";

        Cell cell = row.getCell(colNum);
        return getCellValue(cell);
    }

    public String getCellData(String sheetName, String columnName, int rowNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return "";

        Row headerRow = sheet.getRow(0);
        int colNum = -1;

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            if (getCellValue(headerRow.getCell(i)).equals(columnName)) {
                colNum = i;
                break;
            }
        }

        if (colNum == -1) return "";

        Row row = sheet.getRow(rowNum);
        if (row == null) return "";

        return getCellValue(row.getCell(colNum));
    }

    public int getRowCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet != null ? sheet.getLastRowNum() : 0;
    }

    public int getColumnCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return 0;

        Row headerRow = sheet.getRow(0);
        return headerRow != null ? headerRow.getLastCellNum() : 0;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                }
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public void close() {
        try {
            workbook.close();
            log.info("Excel workbook closed");
        } catch (IOException e) {
            log.error("Error closing workbook: {}", e.getMessage());
        }
    }
}
