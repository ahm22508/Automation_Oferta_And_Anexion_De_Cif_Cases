package OfertaProject;



import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostSelling extends Discounts {

    public void ExtractPostSelling(String text) throws IOException {

        //Create new Excel File and new Sheet
        File FinalFile = new File(FileName);
        try (FileInputStream fileInputStream = new FileInputStream(FinalFile)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.createSheet("PosventaYBROXXX");

               //Extract specific data
                Pattern pattern1 = Pattern.compile("(?<!/)(?!\\d+\\.\\d+)\\b([1-9]\\d{0,4}|0)\\b");
                Matcher matcher1 = pattern1.matcher(text);
                Pattern pattern = Pattern.compile("POS+[A-Z]{2}");
                Matcher matcher = pattern.matcher(text);
                Pattern pattern2 = Pattern.compile("BRW+\\d+");
                Matcher matcher2 = pattern2.matcher(text);
                Row HeaderCell = sheet.createRow(0);
                HeaderCell.createCell(0).setCellValue("Posventa Y BONO");
                HeaderCell.createCell(1).setCellValue("Value");
                Row row;
                int i = 1;
                while (matcher.find()) {
                    row = sheet.createRow(i++);
                    row.createCell(0).setCellValue(matcher.group());
                    String ServicePostSelling = matcher.group();
                    String AccountPostSelling = ServicePostSelling.replace("POS" , "POC");
                    row = sheet.createRow(2);
                    row.createCell(0).setCellValue(AccountPostSelling);
                    row.createCell(1).setCellValue("Servicio Suplementario a nivel de linea/servicio");
                    if (matcher1.find(matcher.end())) {
                        row = sheet.getRow(1);
                        row.createCell(1).setCellValue(matcher1.group());
                    }
                }
                while (matcher2.find()) {
                    row = sheet.createRow(3);
                    row.createCell(0).setCellValue(matcher2.group());
                }

            //save the data in the new file.
            try (FileOutputStream fileOutputStream = new FileOutputStream(FinalFile)) {
                    workbook.write(fileOutputStream);
                }
            }
         }
      }

