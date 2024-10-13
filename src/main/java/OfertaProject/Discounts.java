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
import java.util.LinkedHashSet;
import java.util.Set;


public class Discounts {

    String FileName = "OfertaPDFDeActivacion.xlsx";

    public void ExtractDiscounts(String text) throws IOException {

        //Create new Excel File and new Sheet
        File FinalFile = new File(FileName);
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(FinalFile)) {
            Sheet sheet = workbook.createSheet("Descuentos");

            //open the DTOS File and search into it.
            try (FileInputStream file = new FileInputStream("C:\\Oferta Extractor\\data\\DTOS.xlsx");
                 Workbook workbook1 = new XSSFWorkbook(file)) {
                Sheet sheet1 = workbook1.getSheetAt(0);

                //Extract specific data
                int rowNum = 0;
                Row row2;
                for (Row row : sheet1) {
                    for (Cell cell : row) {
                        if (text.contains(cell.toString())) {
                            Set<String> DTOS = new LinkedHashSet<>();
                            DTOS.add(cell.toString());
                            for (String Descuento : DTOS) {
                                Row row1 = sheet.createRow(rowNum++);
                                row1.createCell(0).setCellValue(Descuento);
                                for(Cell NextCell : row) {
                                    String Catalog = NextCell.getStringCellValue();
                                    row1.createCell(1).setCellValue(Catalog);
                                }
                            }
                        }
                    }
                }
                if(text.contains("DVOPD")){
                    row2 = sheet.createRow(rowNum);
                    row2.createCell(0).setCellValue("DOVPD");
                    row2.createCell(1).setCellValue("Descuentos Empresas");

                }
                //save the data in the new file.
                workbook.write(fileOut);
            }
        }
    }
}