package OfertaProject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;



public class CMPlantilla_Indice {
    public void ExtractInfoFromCMP(String ExcelFileName) throws IOException {

        String directoryToSearch = "D:\\CV";
        File PlantillaFile = SearchFile.searchFile(new File(directoryToSearch), ExcelFileName);

        if (PlantillaFile == null) {
            System.out.println("No Entry");
        } else {
            File Finalfile = new File("PlantillaCM.xlsx");

            try (FileInputStream fileInputStream = new FileInputStream(Finalfile);
                 Workbook workbook = new XSSFWorkbook(fileInputStream)) {

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


                    try (FileOutputStream fileOutputStream = new FileOutputStream(Finalfile)) {
                        workbook.write(fileOutputStream);
                    }

                } catch (IOException e) {
                    e.getCause();
                }
            }
        }
    }
}