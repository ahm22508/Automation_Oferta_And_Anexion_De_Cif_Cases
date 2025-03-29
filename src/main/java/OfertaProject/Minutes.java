package OfertaProject;

import org.apache.poi.ss.usermodel.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Minutes {

    public void ExtractMinutes(String text)  {

        //Create new Excel File and new Sheet
        Sheet OfertaSheet;
        if (FileCreationForExcel.getSheet("Minutos") == null) {
            OfertaSheet = FileCreationForPDF.createSheet("Minutos");
        } else {
            OfertaSheet = FileCreationForPDF.getSheet("Minutos");
        }

        //Extract specific data
        Pattern pattern = Pattern.compile("\\d+\\.\\d{1,2}");
        Matcher matcher = pattern.matcher(text);
        Pattern pattern1 = Pattern.compile("MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|PIDCG|PIDCH|TDICA|TDICB|TDICC|TDICD|TDICE|TDICH|TDICG|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB|CIPNT");
        Matcher matcher1 = pattern1.matcher(text);
        Row row;
        int x = 0;
        int i = 0;
        while (matcher1.find()) {
            row = OfertaSheet.createRow(x++);
            row.createCell(0).setCellValue(matcher1.group());
            if (matcher1.group().contains("CIPNT")) {
                row.createCell(0).setCellValue("CPINT");
            }
            if (matcher1.group().contains("MPCOB")) {
                row.createCell(0).setCellValue("MPCOU");
            }
            if (matcher1.group().contains("MPCOL")) {
                row.createCell(0).setCellValue("MPCSC");
            }
            if (matcher.find(matcher1.end())) {
                row = OfertaSheet.getRow(i++);
                row.createCell(1).setCellValue(matcher.group());
            }
        }
        if (text.contains("PKPID")) {
            row = OfertaSheet.getRow(0);
            row.createCell(2).setCellValue("PKPID");
            row.createCell(3).setCellValue("SÍ");
        }
        if (text.contains("MPMVE")) {
            row = OfertaSheet.createRow(x);
            row.createCell(0).setCellValue("MPMVE");
            row.createCell(1).setCellValue("0");
        }
    }
}