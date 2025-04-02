package OfertaProject;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;


public class CMPlantilla_Descuentos {

    private int i = 0;

    public boolean isDescuentoSheet(Workbook PlantillaWorkBook) {
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        for ( i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && (SheetName.contains("DTOS") || SheetName.contains("Tarifas") || SheetName.contains("Complementarios") || SheetName.contains("Complem"))) {
                return true;
            }
        }
        return false;
    }

    public void ExtractDescuentosFromCMP(Workbook PlantillaWorkBook, Comparison compare, Sheet OfertaSheet) throws Exception {
            Sheet DescuentoSheet = FileAccess.getSheet(PlantillaWorkBook.getSheetName(i));
            CSVParser DTOReader = FileAccess.ReadCSV();

            int rowNum = 0;
            Row row1;

            for (CSVRecord Record : DTOReader) {
                for (Row row : DescuentoSheet) {
                    for (Cell cell : row) {
                        if (Record.get(0).equals(cell.toString()) && !Record.get(0).isEmpty()) {
                            for (Cell CodeCell : row) {
                                if (CodeCell.toString().contains("SI") || CodeCell.toString().contains("SÍ")) {
                                    row1 = OfertaSheet.createRow(rowNum++);
                                    row1.createCell(0).setCellValue(Record.get(0));
                                    row1.createCell(1).setCellValue(Record.get(1));
                                    row1.createCell(2).setCellValue(Record.get(2));
                                    compare.addToDescuentosComparator(Record.get(0));
                                }
                            }
                        }
                    }
                }
            }
        }
    }