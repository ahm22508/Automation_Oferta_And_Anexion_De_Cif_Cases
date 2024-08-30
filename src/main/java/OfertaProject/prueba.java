package OfertaProject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class prueba {
        public static void main(String[] args) {
            String filePath = "D:\\CV\\OfertaCM.xlsm";
            String outputFilePath = "output.xlsx";

            try (FileInputStream file = new FileInputStream(filePath);
                 Workbook workbook = new XSSFWorkbook(file);
                 Workbook outputWorkbook = new XSSFWorkbook()) {

                Sheet sheet = workbook.getSheet("Tren");
                if (sheet == null) {
                    System.out.println("Sheet 'Tren' not found.");
                    return;
                }
                Pattern pattern = Pattern.compile("DVSMO|DVMOV|DRZRW|DV90X|DCFWP|DVOOM|DPIDC|DVGCU|DVFNA|DVSMV|DVINT|DVSMR|DVMMN|DVMMI|DVMED|DVCAR|DVTFX|DVZWX|DVPCG|DVFZX|DVRSA|DVSML|DVSMM|DVSBC|DVSBS|DVSPR|DVSAV|DVSPM|DVIBA|DVIP2|DVIP5|DVTDA|DVTIC|DVPN1|DVPN2|DVPN5|DVPNX|DVBBP|DVBEM|DVBBL|DVBBW|DVBER|DVBDI|DVBMS|DVPOA|DVPOM|DVP11|DVP12|DVSOA|DVSOM|DVHOT|DVPCF|DVVAG|DVFME|DVTAS|DVFES|DVMTM|DVMTA|DVSME|DVLIM|DVM2M|DVDSG|DVRMG|DVRBF|DVALF|DVARA|DVARM|DVXSV|DVXSO|DVXSI|DVXMM|DVXLO|DVFFN|DVFGC|DVFIN|DVFMV|DVFOM|DVRRE|DVSVO|DVSIN|DINZ1|DINZ2|DINZ3|DINZ4|DINZ5|DMBCM|DCT4G|DCO4G|DCT2G|DCT5G|DCT1G|DC2GB|DTIPA|DTIPM|DICR1|DICRR|DSIPC|DSIP1|DSIP2|DSIP5|DSIP6|DSIP7|DSIP8|DSPTF|DSGCU|DLY02|DCONA|DCONL|DPIZ1|DPIZ2|DPIZ3|DPIZ4|DPIZ5|DPRID|DCTSM|DRML1|DRML2|DCTP1|DCTP2|DCTFM|DTMNS|DCTFE|DPITN|DCREB|DCREE|DCRMB|DCRME|DFAXI|DFAXC|DFAXN|DCTCB|DDCRW|DXBRO|DVXBR|DCDMF|DCMMF|DB90X|DTUSA|DSCOV|DCDI5|DCDI4|DCDI3|DCDI2|DCDI1|DBPIN|DBVGE|DBUTE|DBFUN|DBREF|DCSMP|DCSCR|DINP5|DINP4|DINP3|DINP2|DINP1|DINT5|DINT4|DINT3|DINT2|DINT1|DGSH5|DGSH4|DGSH3|DGSH2|DGSH1|DGST5|DGST4|DGST3|DGST2|DGST1|DTRUC|DDECB|DDCRM|DDZRM|DDTRM|DRZMU|DESIM|DAETF|DMETF|DGEST|DIMGS|DITGS|DTRVO|DTRUT|DTRRC|DSMP1|DSMP2|DSMP3|DSMP4|DSMP5|DTROR|DTSM3");
                Sheet sheet1 = outputWorkbook.createSheet("Filtered Codes");
                int outputRowNum = 0;
                Row row1;
                for (Row row : sheet) {
                    for (Cell cell : row) {
                    Matcher matcher  = pattern.matcher(cell.toString());
                      if (matcher.find()) {
                          Cell NextCell = row.getCell(cell.getColumnIndex() + 1);
                          if (NextCell != null) {
                              double Percentage = NextCell.getNumericCellValue() * 100;
                              if (Percentage > 0) {
                                  row1 = sheet1.createRow(outputRowNum++);
                                  row1.createCell(0).setCellValue(matcher.group());
                                  row1.createCell(1).setCellValue(Percentage);
                              }
                          }
                      }
                    }
                }


                try (FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
                    outputWorkbook.write(outputStream);
                }

                System.out.println("Filtered codes have been written to " + outputFilePath);

            } catch (IOException e) {
                e.getCause();
            }
        }
    }


