import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelRead {
    public String username;
    public String email;
    public String password;
    public double age;
    public String tokenGenerated;

    public void readExcel() throws IOException {
        File file = new File("C:\\Users\\deepanshusingh8\\Downloads\\Data.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet("Sheet1");
        XSSFRow row1 = sheet.getRow(1);
        XSSFCell cell1 = row1.getCell(0);
        XSSFCell cell2 = row1.getCell(1);
        XSSFCell cell3 = row1.getCell(2);
        XSSFCell cell4 = row1.getCell(3);
        username = cell1.getStringCellValue();
        System.out.println(username);
        email = cell2.getStringCellValue();
        System.out.println(email);
        password = cell3.getStringCellValue();
        System.out.println(password);
        age = cell4.getNumericCellValue();
    }
}
