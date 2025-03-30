package OfertaProject;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Descuentos {

    public void ExtractDescuentosFromCMP(Workbook PlantillaWorkBook , Sheet OfertaSheet ,String sheetName, Workbook ofertaWorkbook) throws Exception{
        //check if the sheet is found or not
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        for (int i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && (SheetName.contains("DTOS") || SheetName.contains("Tarifas") || SheetName.contains("Complementarios") || SheetName.contains("Complem"))) {
                //create new Sheet in the new file
                if (OfertaSheet == null) {
                    OfertaSheet = ofertaWorkbook.createSheet(sheetName);
                } else {
                    OfertaSheet = ofertaWorkbook.getSheet(sheetName);
                }
                Sheet DescuentoSheet = FileAccess.getSheet(PlantillaWorkBook.getSheetName(i));
                CSVParser DTOReader = FileAccess.ReadCSV();
                //Extract the specific data
                Pattern pattern1 = Pattern.compile("(?<!-\\s)\\b(MPMVE|MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|TDICA|TDICB|TDICC|TDICD|TDICE|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB|CIPNT)\\b");
                Pattern pattern2 = Pattern.compile("POS+[A-Z]{2}");
                Pattern pattern3 = Pattern.compile("POC+[A-Z]{2}");
                int rowNum = 0;
                Row row1;
                Row row2;
                Row row3;

                for (CSVRecord Record : DTOReader) {
                    for (Row row : DescuentoSheet) {
                    for (Cell cell : row) {
                            if (Record.get(0).equals(cell.toString()) && !Record.get(0).isEmpty()){
                                for (Cell CodeCell : row) {
                                    if (CodeCell.toString().contains("SI") || CodeCell.toString().contains("SÍ")) {
                                        row1 = OfertaSheet.createRow(rowNum++);
                                        row1.createCell(0).setCellValue(Record.get(0));
                                        row1.createCell(1).setCellValue(Record.get(1));
                                        row1.createCell(2).setCellValue(Record.get(2));
                                    }
                                }
                            }
                        }
                    }
                }
                        for (Row row : DescuentoSheet) {
                            for (Cell cell : row) {
                                Matcher matcher1 = pattern1.matcher(cell.toString());
                                Matcher matcher2 = pattern2.matcher(cell.toString());
                                Matcher matcher3 = pattern3.matcher(cell.toString());
                        if (matcher1.find()) {
                            String FinalValue = matcher1.group();
                            for (Cell NextCell : row) {
                                if (NextCell.getCellType() == CellType.NUMERIC) {
                                    row2 = OfertaSheet.createRow(rowNum++);
                                    row2.createCell(0).setCellValue(FinalValue);
                                    row2.createCell(1).setCellValue(NextCell.getNumericCellValue());
                                } else if (NextCell.toString().matches("\\d*\\.?\\d+\\s*[-+*/%^]\\s*\\d*\\.?\\d+")) {
                                    for (Cell ConfirmCell : row) {
                                        if (ConfirmCell.toString().equalsIgnoreCase("SI")) {
                                            String CleaningNumber = NextCell.toString().replace("=", "");
                                            Expression Express = new ExpressionBuilder(CleaningNumber).build();
                                            double Num = Express.evaluate();
                                            row2 = OfertaSheet.createRow(rowNum++);
                                            row2.createCell(0).setCellValue(FinalValue);
                                            row2.createCell(1).setCellValue(Num);
                                        }
                                    }
                                }
                            }
                        }
                        if (matcher2.find()) {
                            for (Cell ProvisionCell : row) {
                                if (ProvisionCell.toString().contains("SI")) {
                                    row3 = OfertaSheet.createRow(rowNum++);
                                    row3.createCell(0).setCellValue(matcher2.group());
                                    row3.createCell(1).setCellValue("Posventa a nivel de Servicio y a nivel de cuenta es: " + matcher2.group().replace("POS", "POC"));
                                }
                            }
                        }
                        if (matcher3.find()) {
                            for (Cell ProvisionCell : row) {
                                if (ProvisionCell.toString().contains("SI")) {
                                    row3 = OfertaSheet.createRow(rowNum++);
                                    row3.createCell(0).setCellValue(matcher2.group());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}