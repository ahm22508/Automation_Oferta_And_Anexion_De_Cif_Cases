package OfertaProject;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_TrenesBusinessInfinity {

    public static void main(String[] args) throws NullPointerException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the CM file as appear in the JO");
        String ExcelFileName = scanner.nextLine();
        String directoryToSearch = "C:\\Users\\DELL\\OneDrive\\Escritorio\\Oferta Extractor\\data";
        File PlantillaFile = SearchFile.searchFile(new File(directoryToSearch), ExcelFileName);

        if (PlantillaFile == null) {
            System.out.println("No Entry");
        } else {
            File FinalFile = new File("PlantillaCM.xlsx");

            try (FileInputStream fileInputStream = new FileInputStream(FinalFile);
                 Workbook workbook = new XSSFWorkbook(fileInputStream)) {
                Sheet sheet = workbook.createSheet("PlantillaCM-Trenes(BI)");
                try (FileInputStream file = new FileInputStream(PlantillaFile.getAbsoluteFile());
                     Workbook workbook1 = new XSSFWorkbook(file)) {
                    Sheet sheet1 = workbook1.getSheet("Infinity Business");
                    Pattern pattern = Pattern.compile("(?<!-\\s)\\b(DVSMO|DVMOV|DRZRW|DV90X|DCFWP|DVOOM|DPIDC|DVGCU|DVFNA|DVSMV|DVINT|DVSMR|DVMMN|DVMMI|DVMED|DVCAR|DVTFX|DVZWX|DVPCG|DVFZX|DVRSA|DVSML|DVSMM|DVSBC|DVSBS|DVSPR|DVSAV|DVSPM|DVIBA|DVIP2|DVIP5|DVTDA|DVTIC|DVPN1|DVPN2|DVPN5|DVPNX|DVBBP|DVBEM|DVBBL|DVBBW|DVBER|DVBDI|DVBMS|DVPOA|DVPOM|DVP11|DVP12|DVSOA|DVSOM|DVHOT|DVPCF|DVVAG|DVFME|DVTAS|DVFES|DVMTM|DVMTA|DVSME|DVLIM|DVM2M|DVDSG|DVRMG|DVRBF|DVALF|DVARA|DVARM|DVXSV|DVXSO|DVXSI|DVXMM|DVXLO|DVFFN|DVFGC|DVFIN|DVFMV|DVFOM|DVRRE|DVSVO|DVSIN|DINZ1|DINZ2|DINZ3|DINZ4|DINZ5|DMBCM|DCT4G|DCO4G|DCT2G|DCT5G|DCT1G|DC2GB|DTIPA|DTIPM|DICR1|DICRR|DSIPC|DSIP1|DSIP2|DSIP5|DSIP6|DSIP7|DSIP8|DSPTF|DSGCU|DLY02|DCONA|DCONL|DPIZ1|DPIZ2|DPIZ3|DPIZ4|DPIZ5|DPRID|DCTSM|DRML1|DRML2|DCTP1|DCTP2|DCTFM|DTMNS|DCTFE|DPITN|DCREB|DCREE|DCRMB|DCRME|DFAXI|DFAXC|DFAXN|DCTCB|DDCRW|DXBRO|DVXBR|DCDMF|DCMMF|DB90X|DTUSA|DSCOV|DCDI5|DCDI4|DCDI3|DCDI2|DCDI1|DBPIN|DBVGE|DBUTE|DBFUN|DBREF|DCSMP|DCSCR|DINP5|DINP4|DINP3|DINP2|DINP1|DINT5|DINT4|DINT3|DINT2|DINT1|DGSH5|DGSH4|DGSH3|DGSH2|DGSH1|DGST5|DGST4|DGST3|DGST2|DGST1|DTRUC|DDECB|DDCRM|DDZRM|DDTRM|DRZMU|DESIM|DAETF|DMETF|DGEST|DIMGS|DITGS|DTRVO|DTRUT|DTRRC|DSMP1|DSMP2|DSMP3|DSMP4|DSMP5|DTROR|DTSM3)\\b");
                    int rowNum = 0;
                    int i = 0;
                    Row row1;
                    String ModTren = "";
                    for (Row row : sheet1) {
                        for (Cell cell : row) {
                            Matcher matcher = pattern.matcher(cell.toString());
                            if (matcher.find()) {
                                for (Cell TrenCell : row) {
                                    if (TrenCell.toString().contains("TDV04")) {
                                        for (Cell FinalTrenCell : row) {
                                            if (FinalTrenCell.toString().contains("Descuento")) {
                                                ModTren = matcher.group();
                                                row1 = sheet.createRow(rowNum++);
                                                row1.createCell(0).setCellValue(matcher.group());
                                            }
                                        }
                                    }
                                           if (TrenCell.toString().contains("%")) {
                                            for (Cell percentageCell : row) {
                                                if (percentageCell.toString().contains("TDV04")) {
                                                    String PercentageCell = TrenCell.getStringCellValue();
                                                    String RemoveTren = PercentageCell.replace(ModTren, "");
                                                    String RemoveSC = RemoveTren.replace("-", "");
                                                    String CleanPerc = RemoveSC.replace("%", "");
                                                    try {
                                                        row1 = sheet.getRow(i++);
                                                        row1.createCell(1).setCellValue(CleanPerc);
                                                    }
                                                    catch (NullPointerException n){
                                                        row1 = sheet.createRow(i++);
                                                        row1.createCell(1).setCellValue(CleanPerc);
                                                        n.getMessage();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                try (FileOutputStream fileOutputStream = new FileOutputStream(FinalFile)) {
                    workbook.write(fileOutputStream);
                }
                } catch (IOException e) {
                    e.getCause();
                }

            }
        }
    }
