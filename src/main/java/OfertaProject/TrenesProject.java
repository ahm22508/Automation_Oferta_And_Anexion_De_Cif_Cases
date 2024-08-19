package OfertaProject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class TrenesProject {
    public static void main(String[] args) throws NullPointerException {
        //ask the end to send  the file
        Scanner scanFile = new Scanner(System.in);
        System.out.println("Send the file to us...");
        String filePath = scanFile.nextLine();
        StringBuilder text = new StringBuilder();

        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath));
             Workbook workbook = new XSSFWorkbook()) {

            int Num = pdfDoc.getNumberOfPages();
            for (int i = 1; i < Num; i++) {
                String  PDFText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
                if(PDFText.contains("Referencia")){
                    break;
                }
                text.append(PDFText);
            }
            Sheet sheet = workbook.createSheet("Trenes");

            Pattern pattern = Pattern.compile("(DV90X|DVM2M|DC2GB|DB90X|\\bD\\w*\\d\\b|D+(?!TFWP|DRZRW)[A-Z]{4})(?= -)");
            Matcher matcher = pattern.matcher(text);
            Pattern pattern1 = Pattern.compile("(\\d+(\\.\\d+)?)(?=%)");
            Matcher matcher1 = pattern1.matcher(text);

            int x = 0;
            Row row;
            Set<String> FinalValue = new HashSet<>();
            while (matcher.find()) {
                String Code = matcher.group();
                if (!FinalValue.contains(Code)) {
                    FinalValue.add(Code);

                    if (matcher1.find(matcher.end())) {
                        row = sheet.createRow(x++);
                        row.createCell(0).setCellValue(Code);
                        row.createCell(1).setCellValue(matcher1.group());
                    }
                }
            }
                if(text.toString().contains("DRZRW")){
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DRZRW");
                    row.createCell(1).setCellValue("100");
                    FinalValue.add("DRZRW");
                    x++;
                }
                String [] MPMVE = {"DVMOV","DVOOM","DVFNA","DVGCU","DVSMV","DVSMO","DVFMV","DVFOM","DVFFN","DVFGC","DRZRW"};
                int L = 0;
                if(text.toString().contains("MPMVE")){
                    for (String Tren : MPMVE) {
                        if (!MPMVE[L].contains(FinalValue.iterator().next())) {
                            row = sheet.createRow(x++);
                            row.createCell(0).setCellValue(Tren);
                            row.createCell(1).setCellValue("100");
                            L++;
                        }
                    }
                    if(text.toString().contains("SMS internacionales") && !FinalValue.contains("DVSMR")){
                         row = sheet.createRow(x);
                         row.createCell(0).setCellValue("DVSMR");
                         row.createCell(1).setCellValue("100");
                         x++;
                        }
                    if(text.toString().contains("CIINT") || text.toString().contains("CPINT")  && !FinalValue.contains("DVINT")){
                        row = sheet.createRow(x);
                        row.createCell(0).setCellValue("DVINT");
                        row.createCell(1).setCellValue("100");
                        x++;
                    }
                    if(text.toString().contains("CI90X") || text.toString().contains("CP90X")  && !FinalValue.contains("DV90X")){
                        row = sheet.createRow(x);
                        row.createCell(0).setCellValue("DV90X");
                        row.createCell(1).setCellValue("100");
                        x++;
                    }
                    if(text.toString().contains("CIROZ") && !FinalValue.contains("DVRRE")){
                        row = sheet.createRow(x);
                        row.createCell(0).setCellValue("DVRRE");
                        row.createCell(1).setCellValue("100");
                        x++;
                    }
                    if(text.toString().contains("CIRRZ") && !FinalValue.contains("DVRSA")){
                        row = sheet.createRow(x);
                        row.createCell(0).setCellValue("DVRSA");
                        row.createCell(1).setCellValue("100");
                    }
                }
            String [] MultiCIF = {"DVMOV","DVOOM","DVFNA","DVGCU","DVSMV","DVSMO"};
            if(text.toString().contains("MultiCIF")){
                for (String Tren : MPMVE) {
                    if (!MultiCIF[L].contains(FinalValue.iterator().next())) {
                        row = sheet.createRow(x++);
                        row.createCell(0).setCellValue(Tren);
                        row.createCell(1).setCellValue("100");
                        L++;
                    }
                }
                if(text.toString().contains("SMS internacionales") && !FinalValue.contains("DVSMR")){
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DVSMR");
                    row.createCell(1).setCellValue("100");
                    x++;
                }
                if(text.toString().contains("CIINT") && !FinalValue.contains("DVINT")){
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DVINT");
                    row.createCell(1).setCellValue("100");
                    x++;
                }
                if(text.toString().contains("CI90X") && !FinalValue.contains("DV90X")){
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DV90X");
                    row.createCell(1).setCellValue("100");
                    x++;
                }
                if(text.toString().contains("CIROZ") && !FinalValue.contains("DVRRE")){
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DVRRE");
                    row.createCell(1).setCellValue("100");
                    x++;
                }
                if(text.toString().contains("CIRRZ") && !FinalValue.contains("DVRSA")){
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DVRSA");
                    row.createCell(1).setCellValue("100");
                }
            }
        try (FileOutputStream outputStream = new FileOutputStream("Trenes.xlsx")) {
            workbook.write(outputStream);
        }
    }
        catch(IOException e){
                e.getCause();
        }
    }
}



