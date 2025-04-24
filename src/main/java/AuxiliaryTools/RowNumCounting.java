package AuxiliaryTools;

import FileOperation.FileCreationForPdfAndExcel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class RowNumCounting {

    private static int RowNumForDescuentos = 0;
    private static int RowNumForMinutos = 0;
    private static int RowNumForTrenes = 0;
    private static int RowNumForPosVenta= 0;
    public static int getRowNumForDescuentos(){
        Sheet descuentoSheet = FileCreationForPdfAndExcel.getSheet("Descuentos");
        if(descuentoSheet != null) {
            for (Row descuentoRow : descuentoSheet) {
                RowNumForDescuentos = descuentoRow.getRowNum() + 1;
            }
            return RowNumForDescuentos;
        }
        else{
            return RowNumForDescuentos = 0;
        }
    }
    public static int getRowNumForMinutos(){
        Sheet minutosSheet = FileCreationForPdfAndExcel.getSheet("Minutos");
        if(minutosSheet != null) {
            for (Row minutoRow : minutosSheet) {
                RowNumForMinutos = minutoRow.getRowNum()+1;
            }
            return RowNumForMinutos;
        }
        else{
            return RowNumForMinutos = 0;
        }
    }

    public static int getRowNumForPosVenta(){
        Sheet posventaSheet = FileCreationForPdfAndExcel.getSheet("Posventa");
        if(posventaSheet != null) {
            for (Row posventaRow : posventaSheet) {
                RowNumForPosVenta = posventaRow.getRowNum();
            }
            return RowNumForPosVenta;
        }
        else {
            return RowNumForPosVenta = 0;
        }
    }
    public static int getRowNumForTrenes() {
        Sheet trenesSheet = FileCreationForPdfAndExcel.getSheet("Trenes");
        if (trenesSheet != null) {
            for (Row trenRow : trenesSheet) {
                RowNumForTrenes = trenRow.getRowNum()+1;
            }
            return RowNumForTrenes;
        } else {
            return RowNumForTrenes = 0;
        }
    }
}