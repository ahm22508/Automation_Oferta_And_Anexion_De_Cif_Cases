package OfertaProject;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.poi.ss.usermodel.*;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_MinutosDescuentosYTarifas {

    public void ExtractMinutosFromCMP(Workbook PlantillaWorkBook, Sheet OfertaSheet ,String sheetName ,Workbook ofertaWorkbook) {
        //check if the sheet is found or not
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        HashSet<String> DuplicationMinutos = new HashSet<>();
        int rowNum = 0;

        for (int i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && (SheetName.contains("DTOS") || SheetName.contains("Tarifas") || SheetName.contains("Complementarios") || SheetName.contains("Complem"))) {


                 //create new Sheet in the new file
                if (OfertaSheet == null) {
                    OfertaSheet = ofertaWorkbook.createSheet(sheetName);
                } else {
                    OfertaSheet = ofertaWorkbook.getSheet(sheetName);
                }

                Sheet MinutosSheet = FileAccess.getSheet(PlantillaWorkBook.getSheetName(i));

                //Sheet analisis.
                for (Row MinutoRow : OfertaSheet) {
                    Cell DuplicationCell = MinutoRow.getCell(0);
                    if (DuplicationCell != null) {
                        DuplicationMinutos.add(DuplicationCell.toString());
                    }
                        rowNum = MinutoRow.getRowNum() + 1;
                    }


                //Extract the specific data
                Pattern pattern1 = Pattern.compile("(?<!-\\s)\\b(MPMVE|MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|TDICA|TDICB|TDICC|TDICD|TDICE|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB|CIPNT)\\b");
                Row row1;

                for (Row row : MinutosSheet) {
                    for (Cell cell : row) {
                        Matcher matcher1 = pattern1.matcher(cell.toString());
                        if (matcher1.find()) {
                            String FinalValue = matcher1.group();
                            if (!DuplicationMinutos.contains(FinalValue)) {
                                for (Cell NextCell : row) {
                                    if (NextCell.getCellType() == CellType.NUMERIC) {
                                        row1 = OfertaSheet.createRow(rowNum++);
                                        row1.createCell(0).setCellValue(FinalValue);
                                        row1.createCell(1).setCellValue(NextCell.getNumericCellValue());
                                        row1.createCell(2).setCellValue("Minuto del fichero Dtos y Tarifas Complementarios");
                                    } else if (NextCell.toString().matches("\\d*\\.?\\d+\\s*[-+*/%^]\\s*\\d*\\.?\\d+")) {
                                        for (Cell ConfirmCell : row) {
                                            if (ConfirmCell.toString().equalsIgnoreCase("SI")) {
                                                String CleaningNumber = NextCell.toString().replace("=", "");
                                                Expression Express = new ExpressionBuilder(CleaningNumber).build();
                                                double Num = Express.evaluate();
                                                row1 = OfertaSheet.createRow(rowNum++);
                                                row1.createCell(0).setCellValue(FinalValue);
                                                row1.createCell(1).setCellValue(Num);
                                                row1.createCell(2).setCellValue("Minuto del fichero Dtos y Tarifas Complementarios");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}