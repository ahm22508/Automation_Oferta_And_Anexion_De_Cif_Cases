package OfertaProject;


import org.apache.poi.ss.usermodel.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Posventa {

    public void ExtractPosventaFromCMP(Workbook PlantillaWorkBook , Sheet OfertaSheet , String sheetName, Workbook ofertaWorkbook){
        //check if the sheet is found or not
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        for (int i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && (SheetName.contains("DTOS") || SheetName.contains("Tarifas") || SheetName.contains("Complementarios") || SheetName.contains("Complem"))) {
                //create new Sheet in the new file
                if (OfertaSheet == null) {
                    OfertaSheet = ofertaWorkbook.createSheet(sheetName);
                } else {
                    OfertaSheet = ofertaWorkbook.getSheet(sheetName);
                }
                Sheet PosventaSheet = FileAccess.getSheet(PlantillaWorkBook.getSheetName(i));
                //Extract the specific data
                Pattern PatternPosventaLine = Pattern.compile("POS+[A-Z]{2}");
                Pattern PatternPosventaAccount = Pattern.compile("POC+[A-Z]{2}");
                int rowNum = 0;
                Row row1;

                for (Row row : PosventaSheet) {
                    for (Cell cell : row) {
                        Matcher matcherPosventaLine = PatternPosventaLine.matcher(cell.toString());
                        Matcher matcherPosventaAccount = PatternPosventaAccount.matcher(cell.toString());
                        if (matcherPosventaLine.find()) {
                            for (Cell ProvisionCell : row) {
                                if (ProvisionCell.toString().contains("SI")) {
                                    row1 = OfertaSheet.createRow(rowNum++);
                                    row1.createCell(0).setCellValue(matcherPosventaLine.group());
                                    row1.createCell(1).setCellValue("Posventa a nivel de Servicio y a nivel de cuenta es: " + matcherPosventaLine.group().replace("POS", "POC"));
                                }
                            }
                        }
                        if (matcherPosventaAccount.find()) {
                            for (Cell ProvisionCell : row) {
                                if (ProvisionCell.toString().contains("SI")) {
                                    row1 = OfertaSheet.createRow(rowNum++);
                                    row1.createCell(0).setCellValue(matcherPosventaLine.group());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}