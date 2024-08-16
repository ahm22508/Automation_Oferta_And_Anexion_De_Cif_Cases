package OfertaProject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;


public class DescuentoProject {

    public static void main(String[] args) throws IOException {
        Scanner scanFile = new Scanner(System.in);
        System.out.println("Send the file to us...");
        String filePath = scanFile.nextLine();
        Set<String> foundValues = new LinkedHashSet<>();
        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath))) {
            int numberOfPages = pdfDoc.getNumberOfPages();



            try (FileInputStream file = new FileInputStream("C:\\PdfProject\\DTOS.xlsx");
                 Workbook workbook = new XSSFWorkbook(file)) {

                Set<String> excelValues = new LinkedHashSet<>();
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        excelValues.add(cell.getStringCellValue());
                    }
                }
                for (int i = 1; i <= numberOfPages; i++) {
                    String pageText= PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
                        for (String ExcelValue : excelValues) {
                            if (pageText.contains(ExcelValue)) {
                                foundValues.add(ExcelValue);
                            }
                        }
                    }
                }
            }


                        try (Workbook workbook1 = new XSSFWorkbook();
                             FileOutputStream fileOut = new FileOutputStream("Descuentos.xlsx")) {
                            Sheet sheet1 = workbook1.createSheet("Found Values");
                            int rowNum = 0;

                            for (String value : foundValues) {
                                Row row1 = sheet1.createRow(rowNum++);
                                Cell cell1 = row1.createCell(0);
                                cell1.setCellValue(value);
                            }

                            workbook1.write(fileOut);
                        }
                }

            }


