package OfertaProject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExtractingData {
    public String ReadPdf(String filePath) {
        StringBuilder text = new StringBuilder();
        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath))) {
            int numberOfPages = pdfDoc.getNumberOfPages();
            for (int i = 1; i <= numberOfPages; i++) {
                String pageText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
                if (pageText.contains("Referencia") || pageText.contains("Productos a dar de Baja") || pageText.contains("EUROS VODAFONE")) {
                    break;
                }
                text.append(pageText);
            }
        } catch (IOException e) {
            e.getCause();
        }
        return text.toString();
    }

    public int Converter(String Letter){
        Map <String , Integer>ConvertLetter = new HashMap<>();
        ConvertLetter.put("A" , 0);
        ConvertLetter.put("B" , 1);
        ConvertLetter.put("C" , 2);
        ConvertLetter.put("D" , 3);
        ConvertLetter.put("E" , 4);
        ConvertLetter.put("F", 5);
        ConvertLetter.put("G" , 6);
        ConvertLetter.put("H", 7);
        ConvertLetter.put("I", 8);
        ConvertLetter.put("J", 9);
        ConvertLetter.put("K", 10);
        return ConvertLetter.get(Letter);
    }

}