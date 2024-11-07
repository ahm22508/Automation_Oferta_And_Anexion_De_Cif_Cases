package OfertaProject;



import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
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
                Pattern pattern3 = Pattern.compile("POC+[A-Z]{2}");
                Matcher matcher3 = pattern3.matcher(text);
                Row HeaderCell = sheet.createRow(0);
                HeaderCell.createCell(0).setCellValue("Posventa Y BONO");
                HeaderCell.createCell(1).setCellValue("Value");

            Set<String> TariffTypes = new HashSet<>(Arrays.asList("XPS", "LVAPC", "MVCS", "M2M", "SIP01", "MPMVA", "MPMVD", "TIDCA", "MPCOU"));
            Set<String> CodesInPdf =new HashSet<>();
            Map<String, String> Description= new HashMap<>();
            Description.put("XPS" , "REDBOX");
            Description.put("LVAPC" , "Primaria Antigua");
            Description.put("MVCS" , "Normal");
            Description.put("M2M", "M2M");
            Description.put("SIP01", "SIP");
            Description.put("MPMVA", "Integrado");
            Description.put("MPMVD", "Integrado SIP");
            Description.put("MPMVE" , "Integrada Primaria Actual");
            Description.put("TIDCA", "Infinity");
            Description.put("MPCOU" , "Integrado Colaboración");
            Description.put("MPIA2", "Integrada 2.0");

                Row row;
                int i = 1;
                int NumRow = 5;
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
            if(!matcher.find()) {
                while (matcher3.find()) {
                    row = sheet.createRow(i++);
                    row.createCell(0).setCellValue(matcher3.group());
                }
            }
                while (matcher2.find()) {
                    row = sheet.createRow(3);
                    row.createCell(0).setCellValue(matcher2.group());
                }

                if(text.contains("POV") && text.contains("SOA")) {
                    row = sheet.createRow(4);
                    row.createCell(0).setCellValue("Esa Oferta lleva POVFS y SOA, entonces hay que cargarla en el Gescore");
                }
                else if (text.contains("SOA")) {
                    row = sheet.createRow(4);
                    row.createCell(0).setCellValue("Esa Oferta lleva SOA, entonces hay que cargarla en el Gescore");
                } else if (text.contains("POF") || text.contains("POVF")) {
                    row = sheet.createRow(4);
                    row.createCell(0).setCellValue("Esa Oferta lleva POVFS, entonces hay que cargarla en el Gescore");
                }
                for(String Type : TariffTypes) {
                    if (text.contains(Type)) {
                        row = sheet.createRow(NumRow++);
                        row.createCell(0).setCellValue("Ese PDF contiene la siguiente Tarifa " + Type);
                        CodesInPdf.add(Type);
                    }
                }
                        if (CodesInPdf.size() > 1) {
                            row = sheet.createRow(NumRow++);
                            row.createCell(0).setCellValue("Ese PDF contiene más de una tarifa como:");
                        }
                            for(String Code : CodesInPdf){
                                row = sheet.createRow(NumRow++);
                                row.createCell(0).setCellValue(Code);
                              String Tarifa = Description.get(Code);
                              if(Tarifa != null){
                                  row.createCell(1).setCellValue(Tarifa);
                              }
                            }




            //save the data in the new file.
            try (FileOutputStream fileOutputStream = new FileOutputStream(FinalFile)) {
                    workbook.write(fileOutputStream);
                }
            }
         }
      }

