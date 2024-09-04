package OfertaProject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
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

    public void ExtractPostSelling(String filePath) {

        StringBuilder text = new StringBuilder();
        File FinalFile = new File("OfertaPDFDeActivacion.xlsx");

        try (FileInputStream fileInputStream = new FileInputStream(FinalFile)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);

        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath))) {

            int Num = pdfDoc.getNumberOfPages();
            for (int i = 1; i < Num; i++) {
                String PDFText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
                if (PDFText.contains("Referencia")) {
                    break;
                }
                text.append(PDFText);
            }

            Sheet sheet = workbook.createSheet("PosventaYBROXXX");
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
                if (matcher1.find(matcher.end())) {
                    row = sheet.getRow(1);
                    row.createCell(1).setCellValue(matcher1.group());
                }
            }

            while (matcher2.find()) {
                row = sheet.createRow(i++);
                row.createCell(0).setCellValue(matcher2.group());
            }

            try(FileOutputStream fileOutputStream = new FileOutputStream(FinalFile))
            {
            workbook.write(fileOutputStream);
            }


        }
    } catch(IOException e){
        e.getCause();
    }


    }
}

