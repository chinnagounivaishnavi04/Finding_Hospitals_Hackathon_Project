package utilities;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelUtils {

    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "src"
            + File.separator + "test" + File.separator + "resources" + File.separator + "TestData.xlsx";

    public static String getCellData(String sheetName, int rowNum, int colNum) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum - 1);
            if (row == null) return "";
            Cell cell = row.getCell(colNum);
            if (cell == null) return "";

            return cell.toString().trim();
        } catch (Exception e) {
            System.err.println("Excel Read Error: " + e.getMessage());
            return "";
        }
    }

    public static void setCellData(String sheetName, int rowNum, int colNum, String statusValue) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum - 1);
            if (row == null) {
                row = sheet.createRow(rowNum - 1);
            }
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }

            cell.setCellValue(statusValue);

            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            if (statusValue.equalsIgnoreCase("FAILED")) {
                font.setColor(IndexedColors.RED.getIndex());
            } else {
                font.setColor(IndexedColors.GREEN.getIndex());
            }
            style.setFont(font);
            cell.setCellStyle(style);

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            System.err.println("Excel Write Error: " + e.getMessage());
        }
    }
}