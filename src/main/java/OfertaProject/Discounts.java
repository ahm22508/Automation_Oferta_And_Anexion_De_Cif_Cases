package OfertaProject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Discounts {

    public void ExtractDiscounts(String filePath){

            Set<String> DTSInSheet = new LinkedHashSet<>();
            Set<String> DTSInPDF = new LinkedHashSet<>();
            Set<String> OrderedDTS = new LinkedHashSet<>();

            try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath))) {
                int numberOfPages = pdfDoc.getNumberOfPages();


                try (FileInputStream file = new FileInputStream("C:\\PdfProject\\DTOS.xlsx");
                     Workbook workbook = new XSSFWorkbook(file)) {

                    Sheet sheet = workbook.getSheetAt(0);
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            DTSInSheet.add(cell.getStringCellValue());
                        }
                    }

                    StringBuilder text = new StringBuilder();
                    for (int i = 1; i <= numberOfPages; i++) {
                        String pageText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
                        text.append(pageText);
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
                    File FinalFile = new File("Discounts.xlsx");

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
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.OPEN)) {
                            desktop.open(FinalFile);
                        }
                    }

                }

            }
            catch (IOException e) {
                e.getCause();
            }

        }
    }
