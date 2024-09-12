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

    public void ExtractInfoFromCMP(String file) throws IOException {

        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheet("Indice");

            File Finalfile = new File(FileName);
            try (FileInputStream fileInputStream1 = new FileInputStream(Finalfile);
                 Workbook workbook1 = new XSSFWorkbook(fileInputStream1)) {
                Sheet sheet1 = workbook1.createSheet("PlantillaCM-Indice");

                    Row row1;
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            if (cell.toString().contains("Comentarios CM")) {
                                Cell NextCell = row.getCell(cell.getColumnIndex() + 1);
                                if (NextCell != null) {
                                    String Comment = NextCell.getStringCellValue();
                                    row1 = sheet1.createRow(0);
                                    row1.createCell(0).setCellValue(Comment);
                                }
                            }
                        }
                    }
                    try (FileOutputStream fileOutputStream = new FileOutputStream(Finalfile)) {
                        workbook1.write(fileOutputStream);
                    } catch (IOException e) {
                        e.getCause();
                    }
                }
            }
        }
    }

