package Plantilla;

import FileOperation.FileAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class CMPlantilla_Indice {
    private int i = 0;

    public boolean isSheetIndice(Workbook PlantillaWorkBook) {
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        for ( i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && SheetName.equals("Indice")) {
                return true;
            }
        }
        return false;
    }

    public void ExtractInfoFromCMP(Sheet OfertaSheet, Workbook ofertaWorkbook, FileAccess access) {
        Sheet IndiceSheet = access.getSheet(ofertaWorkbook.getSheetName(i));
        //Extract the specific data
                        Row row1;
                        for (Row row : IndiceSheet) {
                            for (Cell cell : row) {
                                if (cell.toString().contains("Comentarios CM") || cell.toString().contains("Comentarios")) {
                                    Cell NextCell = row.getCell(cell.getColumnIndex() + 1);
                                    if (NextCell != null) {
                                        String Comment = NextCell.getStringCellValue();
                                        row1 = OfertaSheet.createRow(0);
                                        row1.createCell(0).setCellValue(Comment);
                                    }
                                }
                            }
                        }
                    }
                }