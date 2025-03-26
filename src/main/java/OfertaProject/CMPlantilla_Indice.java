package OfertaProject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class CMPlantilla_Indice extends CMPlantilla_Descuentos {

    public void ExtractInfoFromCMP(Workbook PlantillaWorkBook) {

        //check if the sheet is found or not
                int SheetNums = PlantillaWorkBook.getNumberOfSheets();
                for(int i = 0; i < SheetNums; i++){
                    String SheetName = PlantillaWorkBook.getSheetName(i);
                    if (!PlantillaWorkBook.isSheetHidden(i) && SheetName.equals("Indice")) {
                        //create new Sheet in the new file
                        Sheet OfertaSheet;
                        if(FileCreation.getSheet("PlantillaCM-Indice") == null) {
                            OfertaSheet = FileCreation.createSheet("PlantillaCM-Indice");
                        }
                        else {
                            OfertaSheet = FileCreation.getSheet("PlantillaCM-Indice");
                        }
                        //Extract the specific data
                        Row row1;
                        for (Row row : FileAccess.getSheet("Indice")) {
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
            }
        }