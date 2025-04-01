package OfertaProject;

import org.apache.poi.ss.usermodel.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Minutes {

    public void ExtractMinutes(String text, Sheet OfertaSheet, String sheetName, Workbook ofertaWorkbook, Comparison compare) {
        //Create new Excel File and new Sheet
        if (OfertaSheet == null) {
            OfertaSheet = ofertaWorkbook.createSheet(sheetName);
        } else {
            OfertaSheet = ofertaWorkbook.getSheet(sheetName);
        }

        //Extract specific data
        Pattern pattern = Pattern.compile("\\d+\\.\\d{1,2}");
        Matcher matcher = pattern.matcher(text);
        Pattern pattern1 = Pattern.compile("MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|PIDCG|PIDCH|TDICA|TDICB|TDICC|TDICD|TDICE|TDICH|TDICG|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB|CIPNT");
        Matcher matcher1 = pattern1.matcher(text);
        Row row;
        int x = RowNumCounting.getRowNumForMinutos();

        while (matcher1.find()) {
            if (!compare.getMinutosComparator().contains(matcher1.group())) {
                if (matcher.find(matcher1.end())) {
                    row = OfertaSheet.createRow(x++);
                    row.createCell(0).setCellValue(matcher1.group());
                    row.createCell(1).setCellValue(matcher.group());

                    if (matcher1.group().contains("CIPNT")) {
                        row.createCell(0).setCellValue("CPINT");
                    }
                    if (matcher1.group().contains("MPCOB")) {
                        row.createCell(0).setCellValue("MPCOU");
                    }
                    if (matcher1.group().contains("MPCOL")) {
                        row.createCell(0).setCellValue("MPCSC");
                    }
                }
            }
        }

        if (text.contains("PKPID")) {
            if (!"PKPID".contains(compare.getMinutosComparator().toString())) {
                row = OfertaSheet.getRow(0);
                row.createCell(2).setCellValue("PKPID");
                row.createCell(3).setCellValue("SÍ");
            }
        }
        if (text.contains("MPMVE")) {
            if (!"MPMVE".contains(compare.getMinutosComparator().toString())) {
                row = OfertaSheet.createRow(x);
                row.createCell(0).setCellValue("MPMVE");
                row.createCell(1).setCellValue("0");
            }
        }
    }
}