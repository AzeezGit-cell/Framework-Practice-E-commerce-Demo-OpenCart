package utilities;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    Workbook wb;
    Sheet sheet;

    public ExcelUtility(String path, String sheetName) throws Exception {
        FileInputStream fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        sheet = wb.getSheet(sheetName);
    }

    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    public int getCellCount(int rowNum) {
        return sheet.getRow(rowNum).getLastCellNum();
    }

    public String getCellData(int rowNum, int colNum) {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));
    }

    public void close() throws Exception {
        wb.close();
    }
}  