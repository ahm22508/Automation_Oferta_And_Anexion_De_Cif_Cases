package Plantilla;


import DataHandling.Comparison;
import FileOperation.FileAccess;
import org.apache.poi.ss.usermodel.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Posventa {
    private int i = 0;

    public boolean isSheetPosventa(Workbook PlantillaWorkBook) {
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        for (i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && (SheetName.contains("DTOS") || SheetName.contains("Tarifas") || SheetName.contains("Complementarios") || SheetName.contains("Complem"))) {
                return true;
            }
        }
        return false;
    }

    public void ExtractPosventaFromCMP(Workbook PlantillaWorkBook , Sheet OfertaSheet, Comparison compare, FileAccess access){

                Sheet PosventaSheet = access.getSheet(PlantillaWorkBook.getSheetName(i));
                //Extract the specific data
                Pattern PatternPosventaLine = Pattern.compile("POS+[A-Z]{2}");
                Pattern PatternPosventaAccount = Pattern.compile("POC+[A-Z]{2}");
                Pattern patternBrow = Pattern.compile("BRW+\\d+");
               int rowNum = 0;
                Row row1;

                for (Row row : PosventaSheet) {
                    for (Cell cell : row) {
                        Matcher matcherPosventaLine = PatternPosventaLine.matcher(cell.toString());
                        Matcher matcherPosventaAccount = PatternPosventaAccount.matcher(cell.toString());
                        Matcher matcherBonoBrow = patternBrow.matcher(cell.toString());
                        if (matcherPosventaLine.find()) {
                            for (Cell ProvisionCell : row) {
                                if (ProvisionCell.toString().contains("SI")) {
                                    row1 = OfertaSheet.createRow(rowNum++);
                                    row1.createCell(0).setCellValue(matcherPosventaLine.group());
                                    row1.createCell(1).setCellValue("Posventa a nivel de Servicio y a nivel de cuenta es: " + matcherPosventaLine.group().replace("POS", "POC"));
                                    compare.addToPosventaComparator(matcherPosventaLine.group());
                                }
                            }
                        }
                        if (matcherPosventaAccount.find()) {
                            for (Cell ProvisionCell : row) {
                                if (ProvisionCell.toString().contains("SI")) {
                                    row1 = OfertaSheet.createRow(rowNum++);
                                    row1.createCell(0).setCellValue(matcherPosventaLine.group());
                                    row1.createCell(1).setCellValue("Posventa a nivel de Servicio y a nivel de linea es: " + matcherPosventaAccount.group().replace("POC", "POS"));
                                    compare.addToPosventaComparator(matcherPosventaAccount.group());
                                }
                            }
                        }
                        if(matcherBonoBrow.find()){
                            for (Cell ProvisionCell : row) {
                                if (ProvisionCell.toString().contains("SI")) {
                                    row1 = OfertaSheet.createRow(rowNum++);
                                    row1.createCell(0).setCellValue(matcherBonoBrow.group());
                                    row1.createCell(1).setCellValue("Se aplica a nivel de cuenta");
                                    row1.createCell(2).setCellValue("Si hay varios pregunta al ejecutivo que Bono aplicamos.");
                                }
                            }
                        }
                    }
                }
            }
        }