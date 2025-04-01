package OfertaProject;


import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;

import java.util.HashSet;



public class Discounts {


    public void ExtractDiscounts(String text, Sheet OfertaSheet, String sheetName, Workbook ofertaWorkbook, Comparison compare) throws Exception {

        //open the DTOS File and search into it.

        if (OfertaSheet == null) {
            OfertaSheet = ofertaWorkbook.createSheet(sheetName);
        } else {
            OfertaSheet = ofertaWorkbook.getSheet(sheetName);
        }


        //Extract specific data
      int rowNum = RowNumCounting.getRowNumForDescuentos();
        Row row2;
        HashSet<String> DTOS = new HashSet<>();
        CSVParser DTOReader = FileAccess.ReadCSV();

        for (CSVRecord record : DTOReader) {

            if (text.contains(record.get(0)) && !record.get(0).isEmpty()) {
                DTOS.add(record.get(0));
                        if (!record.get(0).contains(DTOS.toString()) && !record.get(0).contains(compare.getDescuentosComparator().toString())) {
                            Row row1 = OfertaSheet.createRow(rowNum++);
                            row1.createCell(0).setCellValue(record.get(0));
                            row1.createCell(1).setCellValue(record.get(1));
                            row1.createCell(2).setCellValue(record.get(2));
                        }
                    }
                }
        if (text.contains("DVOPD")) {
            row2 = OfertaSheet.createRow(rowNum);
            row2.createCell(0).setCellValue("DOVPD");
            row2.createCell(1).setCellValue("Descuentos Empresas");
            row2.createCell(2).setCellValue("All Types");
            rowNum++;

        }
        if (text.contains("DSV05")) {
            row2 = OfertaSheet.createRow(rowNum);
            row2.createCell(0).setCellValue("DSVO5");
            row2.createCell(1).setCellValue("Descuentos Especial Empresas");
            row2.createCell(2).setCellValue("All Types");
        }
    }
}