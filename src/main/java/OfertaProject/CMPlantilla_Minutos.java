package OfertaProject;

import org.apache.poi.ss.usermodel.*;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Minutos {

    public void ExtractMinutosFromCMP(Workbook PlantillaWorkBook , Sheet OfertaSheet ,String sheetName, Workbook ofertaWorkbook) {

        //check if the sheet is found or not
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        for (int i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && SheetName.equals("Infinity Business")) {

                //create new Sheet in the new file
                if (OfertaSheet == null) {
                    OfertaSheet = ofertaWorkbook.createSheet(sheetName);
                } else {
                    OfertaSheet = ofertaWorkbook.getSheet(sheetName);
                }
                Sheet MinutosSheet = FileAccess.getSheet("Infinity Business");
                //Extract the specific data
                Pattern pattern = Pattern.compile("(?<!\\W|-\\S)\\b(MPMVE|MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|TDICA|TDICB|TDICC|TDICD|TDICE|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB)\\b");
                LinkedHashSet<String> Minutos = new LinkedHashSet<>();
                int x = 0;
                Row row1;
                for (Row row : MinutosSheet) {
                    for (Cell cell : row) {
                        Matcher matcher = pattern.matcher(cell.toString());
                        if (matcher.find()) {
                            String FinalValue = matcher.group();
                            Minutos.add(matcher.group());
                            for (Cell NextCell : row) {
                                if (NextCell.toString().contains("Cuota Final: ")) {
                                    if (Minutos.contains(FinalValue)) {
                                        row1 = OfertaSheet.createRow(x++);
                                        row1.createCell(0).setCellValue(FinalValue);
                                        String Cleaning = NextCell.getStringCellValue();
                                        String FinalNumber = Cleaning.replace("Cuota Final: ", "").replace(",", ".");
                                        row1.createCell(1).setCellValue(FinalNumber);
                                    }
                                }
                            }
                            if (cell.toString().contains("PKPID")) {
                                row1 = OfertaSheet.getRow(0);
                                row1.createCell(2).setCellValue("PKPID");
                                row1.createCell(3).setCellValue("SÍ");
                            }
                        }
                    }
                }
            }
        }
    }
}