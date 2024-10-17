package OfertaProject;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
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
                Set<String> descuentosKeywords = new HashSet<>(Arrays.asList("Descuentos", "Fibra", "Internos", "Catalogo", "Descuento"));
                Set<String> ofertaKeywords = new HashSet<>(Arrays.asList("All types", "Primaria Normal", "Red Box", "Red Empresa", "SIP Normal", "M2M", "Dival", "DIVAL", "infinity", "Integrada", "Colectiva", "Normal"));
                Row row2;
                for (Row row : sheet1) {
                    for (Cell cell : row) {
                        if (text.contains(cell.toString())) {
                            Set<String> DTOS = new LinkedHashSet<>();
                            DTOS.add(cell.toString());
                            for (String Descuento : DTOS) {
                                for (Cell NextCell : row) {
                                    if (descuentosKeywords.stream().anyMatch(keyword -> NextCell.toString().contains(keyword))) {
                                        for (Cell OfertaCell : row) {
                                            if (ofertaKeywords.stream().anyMatch(keyword -> OfertaCell.toString().contains(keyword))) {
                                                Row row1 = sheet.createRow(rowNum++);
                                                row1.createCell(0).setCellValue(Descuento);
                                                String Catalog = NextCell.getStringCellValue();
                                                row1.createCell(1).setCellValue(Catalog);
                                                row1.createCell(2).setCellValue(OfertaCell.toString());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                    if (text.contains("DVOPD")) {
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
