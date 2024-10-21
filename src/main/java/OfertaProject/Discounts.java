package OfertaProject;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
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
                Set<String> ofertaKeywords = new HashSet<>(Arrays.asList("All types", "RED BOX", "Red empresa", "SIP Normal", "M2M", "DIVAL", "Infinity, Integrada, colectiva", "Infinity, Integrada, colectiva, DIVAL, Normal", "infinity, integrada, colectiva, M2M", "Normal, Dival", "Primaria Normal, SIP Normal", "Primaria Normal, SIP Normal, Normal", "ADSL", "M2M, Infinity, Integrada"));
                Row row2;

                HashSet<String> DTOS = new HashSet<>();
                for (Row row : sheet1) {
                    Cell DiscountCell = row.getCell(0);
                    if (DiscountCell != null) {
                        if (text.contains(DiscountCell.getStringCellValue())) {
                            DTOS.add(DiscountCell.toString());
                            Cell CatalogCell = row.getCell(1);
                            if (CatalogCell != null) {
                                if (descuentosKeywords.stream().anyMatch(keyword -> CatalogCell.toString().contains(keyword))) {
                                    Cell OfertaCell = row.getCell(2);
                                    if (OfertaCell != null) {
                                        if (ofertaKeywords.stream().anyMatch(keyword -> OfertaCell.toString().contains(keyword))) {
                                            if (!DiscountCell.toString().contains(DTOS.toString())) {
                                                Row row1 = sheet.createRow(rowNum++);
                                                row1.createCell(0).setCellValue(DiscountCell.toString());
                                                row1.createCell(1).setCellValue(CatalogCell.toString());
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
