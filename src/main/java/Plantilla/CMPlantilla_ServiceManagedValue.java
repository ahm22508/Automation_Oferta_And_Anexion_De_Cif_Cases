package Plantilla;

import DataHandling.Comparison;
import FileOperation.FileAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_ServiceManagedValue {
    private int i = 0;

    public boolean isSheetServiceValueManaged(Workbook PlantillaWorkBook) {
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        for (i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && (SheetName.contains("DTOS") || SheetName.contains("Tarifas") || SheetName.contains("Complementarios") || SheetName.contains("Complem"))) {
                return true;
            }
        }
        return false;
    }

    public void ExtractServiceManagedValueFromCMP(Workbook PlantillaWorkBook , Sheet OfertaSheet, Comparison compare, FileAccess access){

        Sheet ServiceManagedValueSheet = access.getSheet(PlantillaWorkBook.getSheetName(i));
        //Extract the specific data
        Pattern pattern = Pattern.compile("BSFUN|BSPIN|BSREF|BSUTE|BSVGE|CIDI1|CIDI2|CIDI3|CIDI4|CIDI5|CSATT|CSMP1|CSMP2|CSMP3|CSMP4|CSMP5|CSMPA|CSMPB|CSMPL|CSPFR|CSVCR|CSVEX|CSVMP|ETFRA|GESTP|GSTH1|GSTH2|GSTH3| GSTH4|GSTH5|GSTHC|GSTT1|GSTT2|GSTT3|GSTT4|GSTT5|GSTTC|GTSHI|GTSPR|GTSTO|IGSTM|IGSTT|INPP1|INPP2|INPP3|INPP4|INPP5|INPPC|INPT1|INPT2|INPT3|INPT4|INPT5|INPTC");
        int rowNum = 0;
        Row row1;

        for (Row row : ServiceManagedValueSheet) {
            for (Cell cell : row) {
                Matcher matcherServiceManagedValue = pattern.matcher(cell.toString());
                if (matcherServiceManagedValue.find()) {
                    for (Cell ProvisionCell : row) {
                        if (ProvisionCell.toString().contains("SI")) {
                            row1 = OfertaSheet.createRow(rowNum++);
                            row1.createCell(0).setCellValue(matcherServiceManagedValue.group());
                            row1.createCell(1).setCellValue("Se aplica a nivel de cuenta si la oferta lleva Alta de lineas. si no hay alta, aplica solo su correspondiente Tren si existe");
                            compare.addToServiceManagedValueComparator(matcherServiceManagedValue.group());
                        }
                    }
                }
            }
        }
    }
}
