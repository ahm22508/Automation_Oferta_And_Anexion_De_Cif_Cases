package OfertaProject;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Minutos extends CMPlantilla_Descuentos {

    public void ExtractMinutosFromCMP(String file) throws IOException {

        //open the plantilla and search about specific sheet
        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheet("Infinity Business");

            //Create New Excel File
            File FinalFile = new File(FileName);
            try (FileInputStream fileInputStream1 = new FileInputStream(FinalFile);
                 Workbook workbook1 = new XSSFWorkbook(fileInputStream1)) {

                //check if the sheet is found or not
                int SheetNums = workbook.getNumberOfSheets();
                for(int i = 0; i < SheetNums; i++){
                    String SheetName = workbook.getSheetName(i);
                    if (!workbook.isSheetHidden(i) && SheetName.contains("Infinity Business")) {

                        //create new Sheet in the new file
                        Sheet sheet1 = workbook1.createSheet("PlantillaCM-Minutos");

                        //Extract the specific data
                        Pattern pattern = Pattern.compile("(?<!-\\s)\\b(MPMVE|MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|TDICA|TDICB|TDICC|TDICD|TDICE|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB)\\b");
                        LinkedHashSet<String> Minutos = new LinkedHashSet<>();
                        int x = 0;
                        Row row1;
                        for (Row row : sheet) {
                            for (Cell cell : row) {
                                Matcher matcher = pattern.matcher(cell.toString());
                                if (matcher.find()) {
                                    String FinalValue = matcher.group();
                                    Minutos.add(matcher.group());
                                    for (Cell NextCell : row) {
                                        if (NextCell.toString().contains("Cuota Final: ")) {
                                            if (Minutos.contains(FinalValue)) {
                                                row1 = sheet1.createRow(x++);
                                                row1.createCell(0).setCellValue(FinalValue);
                                                String Cleaning = NextCell.getStringCellValue();
                                                String FinalNumber = Cleaning.replace("Cuota Final: ", "");
                                                row1.createCell(1).setCellValue(FinalNumber);
                                            }
                                        }
                                    }
                                    if (cell.toString().contains("PKPID")) {
                                        row1 = sheet.getRow(0);
                                        row1.createCell(2).setCellValue("PKPID");
                                        row1.createCell(3).setCellValue("SÍ");
                                    }
                                }
                            }
                        }
                        //save the new file with the extracted data
                        try (FileOutputStream fileOutputStream = new FileOutputStream(FinalFile)) {
                            workbook1.write(fileOutputStream);
                        }
                    }
                }
            }
        }
    }
}