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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PosVentaProject {

    public static void main(String[] args) {
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
            int Num = pdfDoc.getNumberOfPages();
            for (int i = 1; i < Num; i++) {
                text.append(PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i)));
            }

            //Create a new sheet into the Excel file to populate it the extracted data.
            Sheet sheet = workbook.createSheet("PosventaYBROXXX");

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
            }
            while (matcher2.find()) {
                row = sheet.createRow(i++);
                row.createCell(0).setCellValue(matcher2.group());
            }

            Pattern pattern1 = Pattern.compile( "(?<!/)(?!\\d+\\.\\d+)\\b([1-9]\\d{0,4}|0)\\b");
            Matcher matcher1 = pattern1.matcher(text);
            List<String> Numbers = new ArrayList<>();

            while (matcher1.find()) {
                Numbers.add(matcher1.group());
            }
            String largestNumber = Numbers.getFirst();
            for (String number : Numbers) {
                if (new java.math.BigInteger(number).compareTo(new java.math.BigInteger(largestNumber)) > 0) {
                    largestNumber = number;
                    row = sheet.getRow(1);
                    row.createCell(1).setCellValue(largestNumber);
                }
        }

        try (FileOutputStream outputStream = new FileOutputStream("PosVentaYBRWXX.xlsx")) {
            workbook.write(outputStream);
        }
        //handle any type of error during code process.
    } catch(IOException e){
        e.getCause();
    }


    }
}

