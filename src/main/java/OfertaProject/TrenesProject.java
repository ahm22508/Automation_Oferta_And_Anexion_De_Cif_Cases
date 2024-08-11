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
            int Num = pdfDoc.getNumberOfPages();
            for (int i = 1; i < Num; i++) {
                text.append(PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i)));
            }
            //Create a new sheet into the Excel file to populate it the extracted data.
            Sheet sheet = workbook.createSheet("Trenes");
            Pattern pattern1 = Pattern.compile("\\d+(\\.\\d+)?+(?=%)");
            Matcher matcher1 = pattern1.matcher(text);
            int i = 0;


            Row row;
            Pattern pattern = Pattern.compile("D+(?!TFWP)[A-Z]{4}");
            Matcher matcher = pattern.matcher(text);
            int x = 0;
//            boolean Pointer = false;
            List<String> Values = new ArrayList<>();
            while (matcher.find()) {
                String FinalValue = matcher.group();
                if (!Values.contains(FinalValue)) {
//                    Pointer = true;
                    Values.add(FinalValue);
                    row = sheet.createRow(x++);
                    row.createCell(0).setCellValue(FinalValue);
                    while (matcher1.find()) {
                        row = sheet.getRow(i++);
                        row.createCell(1).setCellValue(matcher1.group());
                        break;
                    }
                }
                else {
                    row = sheet.getRow(i);
                    row.createCell(1).setCellValue(matcher1.group());
                    row.createCell(1).setCellValue("");
                        break;
                }
            }

//                    row = sheet.createRow(x++);


//                    for (String value : Values) {
//                        row.createCell(0).setCellValue(value);
//                    }


//                for (String value : Values) {
//                    row = sheet.createRow(x++);
//                    row.createCell(0).setCellValue(value);
//                }

//                while (matcher1.find()) {
//
//                        row = sheet.createRow(i++);
//                        row.createCell(1).setCellValue(matcher1.group());
//                        if(Pointer){
//                            row.getCell(1).setCellValue("");
//                        }
//
//                }
//                for (String value : Values) {
//                    row = sheet.getRow(x++);
//                    row.createCell(0).setCellValue(value);
//                }




                try (FileOutputStream outputStream = new FileOutputStream("Fichero.xlsx")) {
                    workbook.write(outputStream);
                }
            }
            //handle any type of error during code process.
        catch(IOException e){
                e.getCause();
            }
        }
    }






