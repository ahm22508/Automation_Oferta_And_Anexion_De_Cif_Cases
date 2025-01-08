package OfertaProject;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_TrenesBusinessInfinity extends CMPlantilla_Descuentos {

    public void ExtractTrenesBIFromCMP(String file, int Indicator, String text) throws IOException {
        //open the plantilla and search about specific sheet
        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            //Create New Excel File
            File FinalFile = new File(FileName);
            try (FileInputStream fileInputStream1 = new FileInputStream(FinalFile);
                 Workbook workbook1 = new XSSFWorkbook(fileInputStream1)) {

                //create new Sheet in the new file

                int Pointer=0;
                int rowNum=0;
                int pointer1 = 0;
                Sheet sheet1 = workbook1.createSheet("PlantillaCM-Trenes");
                Row row1;
                int SheetNums = workbook.getNumberOfSheets();
                for (int i = 0; i < SheetNums; i++) {
                    String SheetName = workbook.getSheetName(i);
                    if (!workbook.isSheetHidden(i) && (SheetName.contains("Infinity") || SheetName.contains("infinity") || SheetName.contains("Business") || SheetName.contains("business"))) {
                        pointer1++;
                        Sheet sheet = workbook.getSheet(workbook.getSheetName(i));
                        Row HeaderRow = sheet1.createRow(0);
                        Cell HeaderCell = HeaderRow.createCell(0);
                        HeaderCell.setCellValue("All Trenes From CM-Plantilla...");


                        //Extract the specific data
                        Pattern pattern = Pattern.compile("(?<!-\\s)\\b(DVSMO|DVMOV|DRZRW|DV90X|DCFWP|DVOOM|DPIDC|DVGCU|DVFNA|DVSMV|DVINT|DVSMR|DVMMN|DVMMI|DVMED|DVCAR|DVTFX|DVZWX|DVPCG|DVFZX|DVRSA|DVSML|DVSMM|DVSBC|DVSBS|DVSPR|DVSAV|DVSPM|DVIBA|DVIP2|DVIP5|DVTDA|DVTIC|DVPN1|DVPN2|DVPN5|DVPNX|DVBBP|DVBEM|DVBBL|DVBBW|DVBER|DVBDI|DVBMS|DVPOA|DVPOM|DVP11|DVP12|DVSOA|DVSOM|DVHOT|DVPCF|DVVAG|DVFME|DVTAS|DVFES|DVMTM|DVMTA|DVSME|DVLIM|DVM2M|DVDSG|DVRMG|DVRBF|DVALF|DVARA|DVARM|DVXSV|DVXSO|DVXSI|DVXMM|DVXLO|DVFFN|DVFGC|DVFIN|DVFMV|DVFOM|DVRRE|DVSVO|DVSIN|DINZ1|DINZ2|DINZ3|DINZ4|DINZ5|DMBCM|DCT4G|DCO4G|DCT2G|DCT5G|DCT1G|DC2GB|DTIPA|DTIPM|DICR1|DICRR|DSIPC|DSIP1|DSIP2|DSIP5|DSIP6|DSIP7|DSIP8|DSPTF|DSGCU|DLY02|DCONA|DCONL|DPIZ1|DPIZ2|DPIZ3|DPIZ4|DPIZ5|DPRID|DCTSM|DRML1|DRML2|DCTP1|DCTP2|DCTFM|DTMNS|DCTFE|DPITN|DCREB|DCREE|DCRMB|DCRME|DFAXI|DFAXC|DFAXN|DCTCB|DDCRW|DXBRO|DVXBR|DCDMF|DCMMF|DB90X|DTUSA|DSCOV|DCDI5|DCDI4|DCDI3|DCDI2|DCDI1|DBPIN|DBVGE|DBUTE|DBFUN|DBREF|DCSMP|DCSCR|DINP5|DINP4|DINP3|DINP2|DINP1|DINT5|DINT4|DINT3|DINT2|DINT1|DGSH5|DGSH4|DGSH3|DGSH2|DGSH1|DGST5|DGST4|DGST3|DGST2|DGST1|DTRUC|DDECB|DDCRM|DDZRM|DDTRM|DRZMU|DESIM|DAETF|DMETF|DGEST|DIMGS|DITGS|DTRVO|DTRUT|DTRRC|DSMP1|DSMP2|DSMP3|DSMP4|DSMP5|DTROR|DTSM3)\\b");

                        if (pointer1 > 1) {
                            rowNum = Pointer;
                        } else {
                            rowNum = 1;
                        }
                        String ModTren = "";
                        for (Row row : sheet) {
                            for (Cell cell : row) {
                                Matcher matcher = pattern.matcher(cell.toString());
                                if (matcher.find()) {
                                    for (Cell TrenCell : row) {
                                        if (TrenCell.toString().contains("TDV04")) {
                                            for (Cell FinalTrenCell : row) {
                                                if (FinalTrenCell.toString().contains("Descuento")) {
                                                    ModTren = matcher.group();
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
                                                    String CleanSpace = CleanPerc.replace(" ", "");
                                                    String CleanComa = CleanSpace.replace(",", ".");
                                                    String AddZero = "0" + CleanComa;
                                                    double FinalNum = Double.parseDouble(AddZero);
                                                    if (FinalNum > 0) {
                                                        row1 = sheet1.createRow(rowNum++);
                                                        row1.createCell(0).setCellValue(matcher.group());
                                                        row1.createCell(1).setCellValue(FinalNum);
                                                        row1.createCell(2).setCellValue("Trenes del Fichero " + workbook.getSheetName(i));
                                                        Pointer = rowNum;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                        if (Indicator == 1) {
                            for (int i1 = 0; i1 < SheetNums; i1++) {
                                String SheetName = workbook.getSheetName(i1);
                                if (workbook.isSheetHidden(i1) && (SheetName.equals("Tren") || SheetName.equals("Trenes"))) {
                                    Pattern pattern = Pattern.compile("(?<!-\\s)\\b(DVSMO|DVMOV|DRZRW|DV90X|DCFWP|DVOOM|DPIDC|DVGCU|DVFNA|DVSMV|DVINT|DVSMR|DVMMN|DVMMI|DVMED|DVCAR|DVTFX|DVZWX|DVPCG|DVFZX|DVRSA|DVSML|DVSMM|DVSBC|DVSBS|DVSPR|DVSAV|DVSPM|DVIBA|DVIP2|DVIP5|DVTDA|DVTIC|DVPN1|DVPN2|DVPN5|DVPNX|DVBBP|DVBEM|DVBBL|DVBBW|DVBER|DVBDI|DVBMS|DVPOA|DVPOM|DVP11|DVP12|DVSOA|DVSOM|DVHOT|DVPCF|DVVAG|DVFME|DVTAS|DVFES|DVMTM|DVMTA|DVSME|DVLIM|DVM2M|DVDSG|DVRMG|DVRBF|DVALF|DVARA|DVARM|DVXSV|DVXSO|DVXSI|DVXMM|DVXLO|DVFFN|DVFGC|DVFIN|DVFMV|DVFOM|DVRRE|DVSVO|DVSIN|DINZ1|DINZ2|DINZ3|DINZ4|DINZ5|DMBCM|DCT4G|DCO4G|DCT2G|DCT5G|DCT1G|DC2GB|DTIPA|DTIPM|DICR1|DICRR|DSIPC|DSIP1|DSIP2|DSIP5|DSIP6|DSIP7|DSIP8|DSPTF|DSGCU|DLY02|DCONA|DCONL|DPIZ1|DPIZ2|DPIZ3|DPIZ4|DPIZ5|DPRID|DCTSM|DRML1|DRML2|DCTP1|DCTP2|DCTFM|DTMNS|DCTFE|DPITN|DCREB|DCREE|DCRMB|DCRME|DFAXI|DFAXC|DFAXN|DCTCB|DDCRW|DXBRO|DVXBR|DCDMF|DCMMF|DB90X|DTUSA|DSCOV|DCDI5|DCDI4|DCDI3|DCDI2|DCDI1|DBPIN|DBVGE|DBUTE|DBFUN|DBREF|DCSMP|DCSCR|DINP5|DINP4|DINP3|DINP2|DINP1|DINT5|DINT4|DINT3|DINT2|DINT1|DGSH5|DGSH4|DGSH3|DGSH2|DGSH1|DGST5|DGST4|DGST3|DGST2|DGST1|DTRUC|DDECB|DDCRM|DDZRM|DDTRM|DRZMU|DESIM|DAETF|DMETF|DGEST|DIMGS|DITGS|DTRVO|DTRUT|DTRRC|DSMP1|DSMP2|DSMP3|DSMP4|DSMP5|DTROR|DTSM3)\\b");
                                    Matcher matcher = pattern.matcher(text);
                                    Pattern pattern1 = Pattern.compile("(\\d+(\\.\\d+)?)(?=%)");
                                    Matcher matcher1 = pattern1.matcher(text);

                                    Set<String> FinalValue = new HashSet<>();
                                    double FirstValue = 0;
                                    while (matcher.find()) {
                                        String Code = matcher.group();
                                        if (!FinalValue.contains(Code)) {
                                            FinalValue.add(Code);
                                            if (matcher1.find(matcher.end())) {
                                                FirstValue = Double.parseDouble(matcher1.group());
                                                if (matcher1.start() - matcher.end() <= 30) {
                                                    String Num = matcher1.group();
                                                    if (!Num.equals("0")) {
                                                        row1 = sheet1.createRow(rowNum++);
                                                        row1.createCell(0).setCellValue(Code);
                                                        row1.createCell(1).setCellValue(matcher1.group());
                                                    }
                                                }
                                            }
                                        }
                                        if (FinalValue.contains(Code)) {
                                            if (matcher1.find(matcher.end())) {
                                                String NumMatcher = matcher1.group();
                                                double SecondValue = Double.parseDouble(NumMatcher);
                                                if (SecondValue > FirstValue) {
                                                    for (Row rowTren : sheet1) {
                                                        for (Cell CellTren : rowTren) {
                                                            if (CellTren.toString().equals(Code)) {
                                                                Cell ChangePercentage = rowTren.getCell(CellTren.getColumnIndex() + 1);
                                                                ChangePercentage.setCellValue(SecondValue);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (text.contains("DRZRW")) {
                                        row1 = sheet1.createRow(rowNum);
                                        row1.createCell(0).setCellValue("DRZRW");
                                        row1.createCell(1).setCellValue("100");
                                        FinalValue.add("DRZRW");
                                        rowNum++;
                                    }
                                    String[] CommonTrenes = {"DVMOV", "DVOOM", "DVFNA", "DVGCU", "DVSMV", "DVSMO", "DRZRW"};
                                    String[] MPMVE = {"DVFGC", "DVFFN", "DVFOM", "DVFMV"};
                                    HashSet<String> TrenesMultiCifEnElPDF = new HashSet<>();

                                    if (text.contains("MPMVE") || text.contains("MultiCIF")) {

                                        for (String Tren : CommonTrenes) {
                                            if (!FinalValue.contains(Tren)) {
                                                row1 = sheet1.createRow(rowNum++);
                                                row1.createCell(0).setCellValue(Tren);
                                                row1.createCell(1).setCellValue("100");
                                            }
                                            if (FinalValue.contains(Tren)) {
                                                TrenesMultiCifEnElPDF.add(Tren);
                                            }
                                        }
                                        for (String TrenMPMVE : MPMVE) {
                                            if (text.contains("MPMVE")) {
                                                if (!FinalValue.contains(TrenMPMVE)) {
                                                    row1 = sheet1.createRow(rowNum++);
                                                    row1.createCell(0).setCellValue(TrenMPMVE);
                                                    row1.createCell(1).setCellValue("100");
                                                }
                                                if (FinalValue.contains(TrenMPMVE)) {
                                                    TrenesMultiCifEnElPDF.add(TrenMPMVE);
                                                }
                                            }
                                        }
                                        if (text.contains("SMS internacionales") && !FinalValue.contains("DVSMR")) {
                                            row1 = sheet1.createRow(rowNum);
                                            row1.createCell(0).setCellValue("DVSMR");
                                            row1.createCell(1).setCellValue("100");
                                            rowNum++;
                                        }
                                        if (text.contains("SMS internacionales") && FinalValue.contains("DVSMR")) {
                                            TrenesMultiCifEnElPDF.add("DVSMR");
                                        }
                                        if ((text.contains("CIINT") || text.contains("CPINT")) && !FinalValue.contains("DVINT")) {
                                            row1 = sheet1.createRow(rowNum);
                                            row1.createCell(0).setCellValue("DVINT");
                                            row1.createCell(1).setCellValue("100");
                                            rowNum++;
                                        }
                                        if ((text.contains("CIINT") || text.contains("CPINT")) && FinalValue.contains("DVINT")) {
                                            TrenesMultiCifEnElPDF.add("DVINT");
                                        }
                                        if ((text.contains("CI90X") || text.contains("CP90X")) && !FinalValue.contains("DV90X")) {
                                            row1 = sheet1.createRow(rowNum);
                                            row1.createCell(0).setCellValue("DV90X");
                                            row1.createCell(1).setCellValue("100");
                                            rowNum++;
                                        }
                                        if ((text.contains("CI90X") || text.contains("CP90X")) && FinalValue.contains("DV90X")) {
                                            TrenesMultiCifEnElPDF.add("DV90X");
                                        }
                                        if ((text.contains("CIINT") || text.contains("CPINT")) && !FinalValue.contains("DVFIN") && text.contains("MPMVE")) {
                                            row1 = sheet1.createRow(rowNum);
                                            row1.createCell(0).setCellValue("DVFIN");
                                            row1.createCell(1).setCellValue("100");
                                            rowNum++;
                                        }
                                        if ((text.contains("CIINT") || text.contains("CPINT")) && FinalValue.contains("DVFIN") && text.contains("MPMVE")) {
                                            TrenesMultiCifEnElPDF.add("DVFIN");
                                        }
                                        if ((text.contains("CI90X") || text.contains("CP90X")) && !FinalValue.contains("DVFES") && text.contains("MPMVE")) {
                                            row1 = sheet1.createRow(rowNum);
                                            row1.createCell(0).setCellValue("DVFES");
                                            row1.createCell(1).setCellValue("100");
                                            rowNum++;
                                        }
                                        if ((text.contains("CI90X") || text.contains("CP90X")) && FinalValue.contains("DVFES") && text.contains("MPMVE")) {
                                            TrenesMultiCifEnElPDF.add("DVFES");
                                        }
                                        if (text.contains("CIROZ") && !FinalValue.contains("DVRRE")) {
                                            row1 = sheet1.createRow(rowNum);
                                            row1.createCell(0).setCellValue("DVRRE");
                                            row1.createCell(1).setCellValue("100");
                                            rowNum++;
                                        }
                                        if (text.contains("CIROZ") && FinalValue.contains("DVRRE")) {
                                            TrenesMultiCifEnElPDF.add("DVRRE");
                                        }
                                        if (text.contains("CIRRZ") && !FinalValue.contains("DVRSA")) {
                                            row1 = sheet1.createRow(rowNum);
                                            row1.createCell(0).setCellValue("DVRSA");
                                            row1.createCell(1).setCellValue("100");

                                        }
                                        if (text.contains("CIRRZ") && FinalValue.contains("DVRSA")) {
                                            TrenesMultiCifEnElPDF.add("DVRSA");
                                        }
                                    }
                                    for (String tren : TrenesMultiCifEnElPDF) {
                                        for (Row TrenesRow : sheet1) {
                                            for (Cell CellTren : TrenesRow) {
                                                if (CellTren.toString().equals(tren)) {
                                                    Cell NextCell = TrenesRow.getCell(CellTren.getColumnIndex() + 1);
                                                    NextCell.setCellValue("100");

                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }



                for (int i = 0; i < SheetNums; i++) {
                    String SheetName = workbook.getSheetName(i);
                    if (workbook.isSheetHidden(i) && SheetName.equals("Infinity Business")) {
                        Row HeaderRow = sheet1.createRow(0);
                        Cell HeaderCell = HeaderRow.createCell(2);
                        HeaderCell.setCellValue("el Fichero de Infinity Business en la plantilla del CM no Existe.");
                    }
                    if (workbook.getSheet("Infinity Business") == null) {
                        Row HeaderRow = sheet1.createRow(0);
                        Cell HeaderCell = HeaderRow.createCell(2);
                        HeaderCell.setCellValue("el Fichero de Infinity Business en la plantilla del CM no Existe.");
                    }
                }


                //save the new file with the extracted data
                try (FileOutputStream fileOutputStream = new FileOutputStream(FinalFile)) {
                    workbook1.write(fileOutputStream);
                }
            }
        }
    }
}