package OfertaProject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Minutes {

    public void ExtractMinutes(String filePath) {

        //Create StringBuilder to append efficiently the text
        StringBuilder text = new StringBuilder();
        //Connect to the pdf File and create a new Excel WorkBook
        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath));
             Workbook workbook = new XSSFWorkbook()) {
            String PDFText;
            //iterate through all PDF to get extracted the data from all the pages
            int Num = pdfDoc.getNumberOfPages();
            for(int i =1; i< Num; i++) {
                 PDFText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
                if (PDFText.contains("Referencia")) {
                    break;
                }
                      text.append(PDFText);
            }

            //Create a new sheet into the Excel file to populate it the extracted data.
            Sheet sheet = workbook.createSheet("Minutos");

            //create patterns to extract specific data
            Pattern pattern = Pattern.compile("\\d+\\.\\d{2,}");
            Matcher matcher = pattern.matcher(text);
            Pattern pattern1 = Pattern.compile("MPMVE|MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|TDICA|TDICB|TDICC|TDICD|TDICE|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB");
            Matcher matcher1 = pattern1.matcher(text);

            // create Row and cell and iterate through them to populate into them the extracted data from PDF file.
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

            //Create a stream to connect with sheet and write into it the extracted data then saving it.
            File FinalFile = new File("Minutos.xlsx");
            try (FileOutputStream outputStream = new FileOutputStream(FinalFile)) {
                workbook.write(outputStream);
            }
            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)){
                    desktop.open(FinalFile);
                }
            }
            //handle any type of error during code process.
        } catch (IOException e) {
            e.getCause();
        }

    }
}
//our scope: 1. Extract all Minutes code 2. Extract all minutes value 3. Extract all Trenes/descuentos Code 4. Extract all trenes/Descuentos Value 5. Extract Poses and Posca with its value