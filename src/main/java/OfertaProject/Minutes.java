package OfertaProject;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Minutes extends Discounts {

    public void ExtractMinutes(String text) throws  IOException{

        //Create new Excel File and new Sheet
        File FinalFile = new File(FileName);
        try (FileInputStream fileInputStream = new FileInputStream(FinalFile)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.createSheet("Minutos");

            //Extract specific data
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
                if (text.contains("PKPID")) {
                    row = sheet.getRow(0);
                    row.createCell(2).setCellValue("PKPID");
                    row.createCell(3).setCellValue("SÍ");
                }

            //save the data in the new file.
            try (FileOutputStream fileOutputStream = new FileOutputStream(FinalFile)) {
                    workbook.write(fileOutputStream);
                }
        }
    }
}
