package OfertaProject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


@SuppressWarnings("ALL")
public class CMPlantilla_Índice {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the CM file as appear in the JO");
        String ExcelFileName = scanner.nextLine();
        String directoryToSearch = "D:\\CV";
        File PlantillaFile = CMPlantilla_Trenes.searchFile(new File(directoryToSearch), ExcelFileName);

        if (PlantillaFile == null) {
            System.out.println("No Entry");
        } else {
            File Finalfile = new File("PlantillaCM.xlsx");

            try (Workbook workbook = new XSSFWorkbook();
                 FileOutputStream fileOutputStream = new FileOutputStream(Finalfile)) {
                Sheet sheet = workbook.createSheet("PlantillaCM-Indice");
                try (FileInputStream file = new FileInputStream(PlantillaFile.getAbsoluteFile());
                     Workbook workbook1 = new XSSFWorkbook(file)) {
                    Sheet sheet1 = workbook1.getSheet("Indice");


                    Row row1;
                    for (Row row : sheet1) {
                        for (Cell cell : row) {
                            if (cell.toString().contains("Comentarios CM")) {
                                Cell NextCell = row.getCell(cell.getColumnIndex() + 1);
                                if (NextCell != null) {
                                    String Comment = NextCell.getStringCellValue();
                                    row1 = sheet.createRow(0);
                                    row1.createCell(0).setCellValue(Comment);
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.getCause();
                }
                workbook.write(fileOutputStream);
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.OPEN)) {
                        desktop.open(Finalfile);
                    }
                }
            }
        }
    }
}
