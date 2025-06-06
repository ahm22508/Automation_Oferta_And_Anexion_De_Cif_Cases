package pdfOperation;

import AuxiliaryTools.RowNumCounting;
import DataHandling.Comparison;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceManagedValue {

    public void ExtractServiceManagedValue(String text, Sheet OfertaSheet, Comparison compare) {
        //Extract specific data
        Pattern pattern = Pattern.compile("BSFUN|BSPIN|BSREF|BSUTE|BSVGE|CIDI1|CIDI2|CIDI3|CIDI4|CIDI5|CSATT|CSMP1|CSMP2|CSMP3|CSMP4|CSMP5|CSMPA|CSMPB|CSMPL|CSPFR|CSVCR|CSVEX|CSVMP|ETFRA|GESTP|GSTH1|GSTH2|GSTH3| GSTH4|GSTH5|GSTHC|GSTT1|GSTT2|GSTT3|GSTT4|GSTT5|GSTTC|GTSHI|GTSPR|GTSTO|IGSTM|IGSTT|INPP1|INPP2|INPP3|INPP4|INPP5|INPPC|INPT1|INPT2|INPT3|INPT4|INPT5|INPTC");
        Matcher matcher = pattern.matcher(text);
        Row row;
        int x = RowNumCounting.getRowNumForServiceManagedValue();
        while (matcher.find()) {
            if (!compare.getServiceManagedValueComparator().contains(matcher.group())) {
                    row = OfertaSheet.createRow(x++);
                    row.createCell(0).setCellValue(matcher.group());
                    row.createCell(1).setCellValue("Se aplica a nivel de cuenta si la oferta lleva Alta de lineas. si no hay alta, aplica solo su correspondiente Tren si existe");
                }
            }
        }
    }
