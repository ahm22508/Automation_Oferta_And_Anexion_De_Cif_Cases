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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Discounts {

String FileName = "OfertaPDFDeActivacion.xlsx";
    public void ExtractDiscounts(String text){

            Set<String> DTSInSheet = new LinkedHashSet<>();
            Set<String> DTSInPDF = new LinkedHashSet<>();
            Set<String> OrderedDTS = new LinkedHashSet<>();

                try (FileInputStream file = new FileInputStream("C:\\Users\\DELL\\OneDrive\\Escritorio\\Oferta Extractor\\data\\DTOS.xlsx");
                     Workbook workbook = new XSSFWorkbook(file)) {

                    Sheet sheet = workbook.getSheetAt(0);
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            DTSInSheet.add(cell.getStringCellValue());
                        }
                    }

                    Pattern pattern = Pattern.compile("\\bD\\w*\\b");
                    Matcher matcher = pattern.matcher(text);
                    while (matcher.find()) {
                        DTSInPDF.add(matcher.group());
                    }
                    for (String Discount : DTSInPDF) {
                        if (DTSInSheet.contains(Discount)) {
                            OrderedDTS.add(Discount);
                        }
                    }
                  File FinalFile= new File(FileName);

                    try (Workbook workbook1 = new XSSFWorkbook();
                         FileOutputStream fileOut = new FileOutputStream(FinalFile)) {
                        Sheet sheet1 = workbook1.createSheet("Descuentos");
                        int rowNum = 0;

                        for (String value : OrderedDTS) {
                            Row row1 = sheet1.createRow(rowNum++);
                            Cell cell1 = row1.createCell(0);
                            cell1.setCellValue(value);
                        }
                        workbook1.write(fileOut);
                    }

                }

            catch (IOException e) {
                e.getCause();
            }
        }
    }
