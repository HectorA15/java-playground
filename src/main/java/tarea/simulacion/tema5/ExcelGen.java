package tarea.simulacion.tema5;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;


public class ExcelGen {

    public static void main(String[] args) {

        // Create a workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a sheet
            Sheet sheet = workbook.createSheet("Example");

            // Create headers in row 0
            String[] headers = {"ID", "Name", "Age"};
            Row row = sheet.createRow(0);

            // Add headers to row 0
            for (int i = 0; i < headers.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Create data in row 1
            String[] data = {"1", "Hector", "20"};
            Row dataRow = sheet.createRow(1);

            // Add data to row 1
            for (int i = 0; i < data.length; i++) {
                Cell cell = dataRow.createCell(i);
                cell.setCellValue(data[i]);
            }

            // Save the workbook
            try (FileOutputStream fileOut = new FileOutputStream(
                    "C:\\Users\\crusc\\IdeaProjects\\java-playground\\src\\main\\java\\tarea\\simulacion\\tema5\\example.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel file saved successfully.");
            } catch (Exception e) {
                System.out.println("error at saving: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error creating workbook: " + e.getMessage());
        }


    }
}
