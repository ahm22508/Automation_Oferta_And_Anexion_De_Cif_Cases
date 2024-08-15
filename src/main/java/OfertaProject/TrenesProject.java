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
        //Create StringBuilder to append efficiently the text
        StringBuilder text = new StringBuilder();

        //Connect to the pdf File and create a new Excel WorkBook
        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath));
             Workbook workbook = new XSSFWorkbook()) {

            //iterate through all PDF to get extracted the data from all the pages

            String Lines= "";
            int Num = pdfDoc.getNumberOfPages();
            for (int i = 1; i < Num; i++) {
            Lines  = String.valueOf(text.append(PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i))));

            }
            //Create a new sheet into the Excel file to populate it the extracted data.
            Sheet sheet = workbook.createSheet("Trenes");

            String [] EachLine = Lines.split("\\r?\\n");


            Row row;
            Pattern pattern = Pattern.compile("(D+(?!TFWP|ESIM|DRZRW)[A-Z]{4})(?= -)");
            Matcher matcher = pattern.matcher(text);
            Pattern pattern1 = Pattern.compile("(\\d+(\\.\\d+)?)(?=%(?!\\stráfico Zona))");
            Matcher matcher1 = pattern1.matcher(text);


            int i = 0;
            LinkedHashSet<String> Values = new LinkedHashSet<>();
            while(matcher.find()) {
                Values.add(matcher.group());
            }
            for (String value : Values) {
                row = sheet.createRow(i++);
                row.createCell(0).setCellValue(value);
            }

            for (String Line : EachLine) {
                        if (Line.contains("DRZRW")) {
                            row = sheet.createRow(i++);
                            row.createCell(0).setCellValue("DRZRW");
                            row.createCell(1).setCellValue("100");
                        } else if (Line.contains("Autorización 1 eSIM")) {
                            row = sheet.createRow(i++);
                            row.createCell(0).setCellValue("DESIM");
                        } else if (Line.contains("Autorización 2 eSIM")) {
                            row = sheet.createRow(i++);
                            row.createCell(0).setCellValue(" ");
                            row.createCell(1).setCellValue(" ");
                            i--;

                        }
                    }


            int x = 0;
            while (matcher1.find()){
                row = sheet.getRow(x++);
                row.createCell(1).setCellValue(matcher1.group());
            }

            try (FileOutputStream outputStream = new FileOutputStream("Trenes.xlsx")) {
                    workbook.write(outputStream);
                }
            }
            //handle any type of error during code process.
        catch(IOException e){
                e.getCause();
            }
        }
    }






