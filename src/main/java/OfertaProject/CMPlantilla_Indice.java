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



public class CMPlantilla_Indice extends CMPlantilla_Descuentos {

    public void ExtractInfoFromCMP(String ExcelFileName) throws IOException {

        String directoryToSearch = "C:\\Users\\DELL\\OneDrive\\Escritorio\\Oferta Extractor\\data";
        File PlantillaFile = SearchFile.searchFile(new File(directoryToSearch), ExcelFileName);


        File Finalfile = new File(FileName);

        try (FileInputStream fileInputStream = new FileInputStream(Finalfile);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.createSheet("PlantillaCM-Indice");
            if (PlantillaFile != null) {
                try (FileInputStream file = new FileInputStream(PlantillaFile);
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