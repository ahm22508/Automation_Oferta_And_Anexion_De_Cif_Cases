package OfertaProject;

import org.apache.poi.ss.usermodel.*;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_MinutosInfinityBusiness {
    private int i = 0;

    public boolean isMinutosBISheet(Workbook PlantillaWorkBook) {
        //check if the sheet is found or not
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        for ( i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && SheetName.equals("Infinity Business")) {
                return true;
            }
        }
    return false;
    }

    public void ExtractMinutosFromCMP(Sheet OfertaSheet, Comparison compare , Workbook PlantillaWorkbook, FileAccess access) {

                Sheet MinutosSheet = access.getSheet(PlantillaWorkbook.getSheetName(i));
                //Extract the specific data
                Pattern pattern = Pattern.compile("(?<!\\W|-\\S)\\b(MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|PIDCG|PIDCH|TDICA|TDICB|TDICC|TDICD|TDICE|TDICH|TDICG|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB|CIPNT)\\b");
                LinkedHashSet<String> Minutos = new LinkedHashSet<>();
                int RowNum = 0;
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
                                        row1 = OfertaSheet.createRow(RowNum++);
                                        row1.createCell(0).setCellValue(FinalValue);
                                        String Cleaning = NextCell.getStringCellValue();
                                        String FinalNumber = Cleaning.replace("Cuota Final: ", "").replace(",", ".");
                                        row1.createCell(1).setCellValue(FinalNumber);
                                        compare.addToMinutosComparator(FinalValue);
                                    }
                                }
                            }
                            if (cell.toString().contains("PKPID")) {
                                row1 = OfertaSheet.getRow(0);
                                row1.createCell(2).setCellValue("PKPID");
                                row1.createCell(3).setCellValue("SÍ");
                                compare.addToMinutosComparator("PKPID");
                            }
                        }
                    }
                }
            }
        }