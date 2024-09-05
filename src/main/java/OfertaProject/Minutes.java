package OfertaProject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Minutes extends Discounts{

    public void ExtractMinutes(String filePath) {

        StringBuilder text = new StringBuilder();
        File FinalFile = new File(FileName);
        try (FileInputStream fileInputStream = new FileInputStream(FinalFile)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath))) {
                String PDFText;

                int Num = pdfDoc.getNumberOfPages();
                for (int i = 1; i < Num; i++) {
                    PDFText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
                    if (PDFText.contains("Referencia")) {
                        break;
                    }
                    text.append(PDFText);
                }

                Sheet sheet = workbook.createSheet("Minutos");

                Pattern pattern = Pattern.compile("\\d+\\.\\d{2,}");
                Matcher matcher = pattern.matcher(text);
                Pattern pattern1 = Pattern.compile("MPMVE|MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|TDICA|TDICB|TDICC|TDICD|TDICE|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB");
                Matcher matcher1 = pattern1.matcher(text);

                Row row;
                int x = 0;
                int i = 0;
                while (matcher1.find()) {
                    row = sheet.createRow(x++);
                    row.createCell(0).setCellValue(matcher1.group());

                    if (matcher.find(matcher1.end())) {
                        row = sheet.getRow(i++);
                        row.createCell(1).setCellValue(matcher.group());
                    }
                }
                if (text.toString().contains("PKPID")) {
                    row = sheet.getRow(0);
                    row.createCell(2).setCellValue("PKPID");
                    row.createCell(3).setCellValue("SÍ");
                }

                try (FileOutputStream fileOutputStream = new FileOutputStream(FinalFile)) {
                    workbook.write(fileOutputStream);
                }

            }
            } catch (IOException e) {
                e.getCause();
            }


    }
}