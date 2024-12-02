package OfertaProject;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Descuentos {

    String FileName = "PlantillaCM.xlsx";

    public void ExtractDescuentosFromCMP(String file) throws IOException {
        //open the plantilla and search about specific sheet
        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheet("Dtos y Tarifas Complementarios");
            if(sheet == null) {
            sheet = workbook.getSheet("Dtos Complementarios");
            }
            try (FileInputStream DTOSFile = new FileInputStream("C:\\Oferta Extractor\\data\\DTOS.xlsx");
                 Workbook DTOSWorkbook = new XSSFWorkbook(DTOSFile)) {
                Sheet DTOSSheet = DTOSWorkbook.getSheetAt(0);


                //Create New Excel File
                File Finalfile = new File(FileName);
                try (FileOutputStream fileOutputStream = new FileOutputStream(Finalfile);
                     Workbook workbook1 = new XSSFWorkbook()) {

                    //check if the sheet is found or not
                    int SheetNums = workbook.getNumberOfSheets();
                    for (int i = 0; i < SheetNums; i++) {
                        String SheetName = workbook.getSheetName(i);
                        if (!workbook.isSheetHidden(i) && (SheetName.equals("Dtos y Tarifas Complementarios") || SheetName.equals("Dtos Complementarios"))) {

                            //create new Sheet in the new file
                            Sheet sheet1 = workbook1.createSheet("PlantillaCM-Descuentos y Otros");

                            //Extract the specific data
                            Pattern pattern1 = Pattern.compile("(?<!-\\s)\\b(MPMVE|MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|TDICA|TDICB|TDICC|TDICD|TDICE|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB|CIPNT)\\b");
                            Pattern pattern2 = Pattern.compile("POS+[A-Z]{2}");
                            Pattern pattern3 = Pattern.compile("POC+[A-Z]{2}");
                            Pattern NumPattern = Pattern.compile("^\\d+(,\\d+)?(?=\\s*\\w+|\\W+)");
                            int rowNum = 0;
                            Row row1;
                            Row row2;
                            Row row3;
                            for (Row row : sheet) {
                                for (Cell cell : row) {
                                    Matcher matcher1 = pattern1.matcher(cell.toString());
                                    Matcher matcher2 = pattern2.matcher(cell.toString());
                                    Matcher matcher3 = pattern3.matcher(cell.toString());

                                    for (Row DTOSRow : DTOSSheet) {
                                        Cell DTOSCell = DTOSRow.getCell(0);
                                        if (DTOSCell != null) {
                                            if (cell.toString().contains(DTOSCell.toString())) {
                                                String Codes = cell.toString();
                                                for (Cell CodeCell : row) {
                                                    if (CodeCell.toString().equals("SI")) {
                                                        row1 = sheet1.createRow(rowNum++);
                                                        row1.createCell(0).setCellValue(Codes);
                                                        Cell CatalogCell = DTOSRow.getCell(DTOSCell.getColumnIndex() + 1);
                                                        if (CatalogCell != null) {
                                                            row1.createCell(1).setCellValue(CatalogCell.toString());
                                                        }
                                                        Cell TariffType = DTOSRow.getCell(DTOSCell.getColumnIndex() + 2);
                                                        if (TariffType != null) {
                                                            row1.createCell(2).setCellValue(TariffType.toString());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (matcher1.find()) {
                                        String FinalValue = matcher1.group();
                                        for (Cell NextCell : row) {
                                            Matcher MatchOnlyNums = NumPattern.matcher(NextCell.toString());
                                            if (NextCell.getCellType() == CellType.NUMERIC) {
                                                row2 = sheet1.createRow(rowNum++);
                                                row2.createCell(0).setCellValue(FinalValue);
                                                row2.createCell(1).setCellValue(NextCell.getNumericCellValue());
                                            }

                                            else if (MatchOnlyNums.find()) {
                                                for (Cell ConfirmCell : row) {
                                                    if (ConfirmCell.toString().contains("SI")) {
                                                        row2 = sheet1.createRow(rowNum++);
                                                        row2.createCell(0).setCellValue(FinalValue);
                                                        row2.createCell(1).setCellValue(MatchOnlyNums.group());
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (matcher2.find()) {
                                        for (Cell ProvisionCell : row) {
                                            if (ProvisionCell.toString().contains("SI")) {
                                                row3 = sheet1.createRow(rowNum++);
                                                row3.createCell(0).setCellValue(matcher2.group());
                                            }
                                        }
                                    }
                                    if (matcher3.find()) {
                                        for (Cell ProvisionCell : row) {
                                            if (ProvisionCell.toString().contains("SI")) {
                                                row3 = sheet1.createRow(rowNum++);
                                                row3.createCell(0).setCellValue(matcher2.group());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //save the new file with the extracted data
                    workbook1.write(fileOutputStream);

                }
            }
        }
    }
}
