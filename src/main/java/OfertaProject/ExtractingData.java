package OfertaProject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import java.io.IOException;

public class ExtractingData {
    public String ReadPdf(String filePath) {
        StringBuilder text = new StringBuilder();
        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath))) {
            int numberOfPages = pdfDoc.getNumberOfPages();
            for (int i = 1; i <= numberOfPages; i++) {
                String pageText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
                if (pageText.contains("Referencia") || pageText.contains("Productos a dar de Baja")) {
                    break;
                }
                text.append(pageText);
            }
        } catch (IOException e) {
            e.getCause();
        }
        return text.toString();
    }
}