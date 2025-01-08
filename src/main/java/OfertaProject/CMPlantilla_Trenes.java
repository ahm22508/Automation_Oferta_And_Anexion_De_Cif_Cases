package OfertaProject;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Trenes extends CMPlantilla_Descuentos {

    public void ExtractTrenesFromCMP(String file, int Indicator, String text) throws IOException {

        //open the plantilla and search about specific sheet
        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheet("Tren");

            Sheet InfinitySheet = workbook.getSheet("Infinity Business");

            //Create New Excel File
            File Finalfile = new File(FileName);
            try (FileInputStream fileInputStream1 = new FileInputStream(Finalfile);
                 Workbook workbook1 = new XSSFWorkbook(fileInputStream1)) {

                //create new Sheet in the new file
                Sheet sheet1 = workbook1.getSheet("PlantillaCM-Trenes");

                //check if the sheet is found or not
                int SheetNums = workbook.getNumberOfSheets();
                for (int i = 0; i < SheetNums; i++) {
                    String SheetName = workbook.getSheetName(i);
                    if (!workbook.isSheetHidden(i) && SheetName.equals("Tren")) {
                        Row HeaderRow = sheet1.getRow(0);
                        Cell HeaderCell = HeaderRow.createCell(0);
                        HeaderCell.setCellValue("All Trenes From CM-Plantilla...");

                        //Sheet analisis.
                        HashSet<String> DuplicationTrenes = new HashSet<>();
                        int RowNum = 0;
                        for (Row TrenRow : sheet1) {
                            Cell DuplicationCell = TrenRow.getCell(0);
                            if (DuplicationCell != null) {
                                DuplicationTrenes.add(DuplicationCell.toString());
                            }
                            for (Cell TrenCell : TrenRow) {
                                RowNum = TrenCell.getRow().getRowNum() + 1;
                            }

                        }


                        //Extract the specific data
                        Pattern pattern = Pattern.compile("DVSMO|DVMOV|DRZRW|DV90X|DCFWP|DVOOM|DPIDC|DVGCU|DVFNA|DVSMV|DVINT|DVSMR|DVMMN|DVMMI|DVMED|DVCAR|DVTFX|DVZWX|DVPCG|DVFZX|DVRSA|DVSML|DVSMM|DVSBC|DVSBS|DVSPR|DVSAV|DVSPM|DVIBA|DVIP2|DVIP5|DVTDA|DVTIC|DVPN1|DVPN2|DVPN5|DVPNX|DVBBP|DVBEM|DVBBL|DVBBW|DVBER|DVBDI|DVBMS|DVPOA|DVPOM|DVP11|DVP12|DVSOA|DVSOM|DVHOT|DVPCF|DVVAG|DVFME|DVTAS|DVFES|DVMTM|DVMTA|DVSME|DVLIM|DVM2M|DVDSG|DVRMG|DVRBF|DVALF|DVARA|DVARM|DVXSV|DVXSO|DVXSI|DVXMM|DVXLO|DVFFN|DVFGC|DVFIN|DVFMV|DVFOM|DVRRE|DVSVO|DVSIN|DINZ1|DINZ2|DINZ3|DINZ4|DINZ5|DMBCM|DCT4G|DCO4G|DCT2G|DCT5G|DCT1G|DC2GB|DTIPA|DTIPM|DICR1|DICRR|DSIPC|DSIP1|DSIP2|DSIP5|DSIP6|DSIP7|DSIP8|DSPTF|DSGCU|DLY02|DCONA|DCONL|DPIZ1|DPIZ2|DPIZ3|DPIZ4|DPIZ5|DPRID|DCTSM|DRML1|DRML2|DCTP1|DCTP2|DCTFM|DTMNS|DCTFE|DPITN|DCREB|DCREE|DCRMB|DCRME|DFAXI|DFAXC|DFAXN|DCTCB|DDCRW|DXBRO|DVXBR|DCDMF|DCMMF|DB90X|DTUSA|DSCOV|DCDI5|DCDI4|DCDI3|DCDI2|DCDI1|DBPIN|DBVGE|DBUTE|DBFUN|DBREF|DCSMP|DCSCR|DINP5|DINP4|DINP3|DINP2|DINP1|DINT5|DINT4|DINT3|DINT2|DINT1|DGSH5|DGSH4|DGSH3|DGSH2|DGSH1|DGST5|DGST4|DGST3|DGST2|DGST1|DTRUC|DDECB|DDCRM|DDZRM|DDTRM|DRZMU|DESIM|DAETF|DMETF|DGEST|DIMGS|DITGS|DTRVO|DTRUT|DTRRC|DSMP1|DSMP2|DSMP3|DSMP4|DSMP5|DTROR|DTSM3");
                        Pattern patternNum = Pattern.compile("(\\d+(,\\d+)?)(?=%)");
                        Row row1;
                        String TrenInfinityBusiness = "";
                        Matcher matcher;
                        Matcher matcherNum;

                        for (Row row : sheet) {
                            for (Cell cell : row) {
                                 matcher = pattern.matcher(cell.toString());
                                if (matcher.find()) {
                                    Cell NextCell = row.getCell(cell.getColumnIndex() + 1);
                                    if (NextCell != null) {
                                        if (NextCell.getCellType() == CellType.NUMERIC) {
                                            double Percentage = NextCell.getNumericCellValue() * 100;
                                            if (Percentage > 0) {
                                                if (!DuplicationTrenes.contains(matcher.group())) {
                                                    row1 = sheet1.createRow(RowNum++);
                                                    row1.createCell(0).setCellValue(matcher.group());
                                                    row1.createCell(1).setCellValue(Percentage);
                                                }
                                            }
                                        }
                                        if ((NextCell.toString().contains("/") || NextCell.toString().contains("*") || NextCell.toString().contains("+") || NextCell.toString().contains("-")) && NextCell.getCellType() == CellType.NUMERIC) {
                                            double Equation = NextCell.getNumericCellValue() * 100;
                                            double ModifyNum = Math.floor(Equation * 100) / 100;
                                            row1 = sheet1.createRow(RowNum++);
                                            row1.createCell(0).setCellValue(matcher.group());
                                            row1.createCell(1).setCellValue(Math.floor(Equation * 100) / 100);
                                            if (String.valueOf(ModifyNum).contains(".99")) {
                                                row1.createCell(1).setCellValue((Math.floor(Equation * 100) / 100) + 0.01);
                                            }
                                        }
                                        if (NextCell.toString().matches("[A-Z]\\d+")) {
                                            Pattern LetterPattern = Pattern.compile("[A-Z](?=\\d+)");
                                            Matcher LetterMatch = LetterPattern.matcher(NextCell.toString());
                                            Pattern NumPattern = Pattern.compile("(?<=[A-Z])\\d+");
                                            Matcher NumMatch = NumPattern.matcher(NextCell.toString());
                                            if (LetterMatch.find()) {
                                                ExtractingData extractingData = new ExtractingData();
                                                int CellNum = extractingData.Converter(LetterMatch.group());
                                                if (NumMatch.find()) {
                                                    int RowNumber = Integer.parseInt(NumMatch.group()) - 1;
                                                    if (sheet.getRow(RowNumber).getCell(CellNum) != null) {
                                                        double Percentage = sheet.getRow(RowNumber).getCell(CellNum).getNumericCellValue() * 100;
                                                        if (Percentage > 0) {
                                                            row1 = sheet1.createRow(RowNum++);
                                                            row1.createCell(0).setCellValue(matcher.group());
                                                            row1.createCell(1).setCellValue(Percentage);
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (NextCell.toString().contains("Infinity Business") || NextCell.toString().contains("Infinity Business Media")) {
                                            if (DuplicationTrenes.size() == 1) {
                                                if (InfinitySheet != null) {
                                                    for (Row InfinityRow : InfinitySheet) {
                                                        for (Cell InfinityCell : InfinityRow) {
                                                            if (InfinityCell.toString().contains(cell.toString()))
                                                                for (Cell TrenCell : InfinityRow) {
                                                                    if (TrenCell.toString().contains("TDV04")) {
                                                                        for (Cell FinalTrenCell : InfinityRow) {
                                                                            if (FinalTrenCell.toString().contains("Descuento")) {
                                                                                TrenInfinityBusiness = cell.getStringCellValue();
                                                                            }
                                                                        }
                                                                    }
                                                                    if (TrenCell.toString().contains("%")) {
                                                                        for (Cell percentageCell : InfinityRow) {
                                                                            if (percentageCell.toString().contains("TDV04")) {
                                                                                String PercentageCell = TrenCell.getStringCellValue();
                                                                                String RemoveTren = PercentageCell.replace(TrenInfinityBusiness, "");
                                                                                String RemoveSC = RemoveTren.replace("-", "");
                                                                                String CleanPerc = RemoveSC.replace("%", "");
                                                                                String CleanSpace = CleanPerc.replace(" ", "");
                                                                                String CleanComa = CleanSpace.replace(",", ".");
                                                                                String AddZero = "0" + CleanComa;
                                                                                double FinalNum = Double.parseDouble(AddZero);
                                                                                if (FinalNum > 0) {
                                                                                    row1 = sheet1.createRow(RowNum++);
                                                                                    row1.createCell(0).setCellValue(TrenInfinityBusiness);
                                                                                    row1.createCell(1).setCellValue(FinalNum);
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
                                    }
                                }

                                if (cell.toString().contains("Porcentaje de DTO")) {
                                    int Column = cell.getColumnIndex();
                                    for (Row PercentageRow : sheet) {
                                        Cell PercentageCell = PercentageRow.getCell(Column);
                                        if (PercentageCell != null) {
                                            if (PercentageCell.getCellType() == CellType.STRING) {
                                                 matcherNum = patternNum.matcher(PercentageCell.toString());
                                                if (matcherNum.find()) {
                                                    Cell TrenCell = PercentageRow.getCell(PercentageCell.getColumnIndex() - 1);
                                                    row1 = sheet1.createRow(RowNum++);
                                                    row1.createCell(0).setCellValue(TrenCell.getStringCellValue());
                                                    row1.createCell(1).setCellValue(matcherNum.group());
                                                }
                                            }
                                        }
                                    }
                                }
                                //First Exception... Here is added all Exception we will have.
                                if (cell.toString().contains("DSIM")) {
                                    Cell PercentageCell = row.getCell(cell.getColumnIndex() + 1);
                                    if (PercentageCell.getCellType() == CellType.NUMERIC) {
                                        double Percentage = PercentageCell.getNumericCellValue() * 100;
                                        if (Percentage > 0) {
                                            row1 = sheet1.createRow(RowNum++);
                                            row1.createCell(0).setCellValue("DESIM");
                                            row1.createCell(1).setCellValue(Percentage);
                                        }
                                    }
                                }
                            }
                        }
                        if (Indicator == 1) {
                            Set<String> FinalValue = new HashSet<>();
                             matcher = pattern.matcher(text);
                             matcherNum = patternNum.matcher(text);
                            double FirstValue = 0;
                            while (matcher.find()) {
                                String Code = matcher.group();
                                if (!FinalValue.contains(Code)) {
                                    FinalValue.add(Code);
                                    if (matcherNum.find(matcher.end())) {
                                        FirstValue = Double.parseDouble(matcherNum.group());
                                        if (matcherNum.start() - matcher.end() <= 30) {
                                            String Num = matcherNum.group();
                                            if (!Num.equals("0")) {
                                                row1 = sheet1.createRow(RowNum++);
                                                row1.createCell(0).setCellValue(Code);
                                                row1.createCell(1).setCellValue(matcherNum.group());
                                            }
                                        }
                                    }
                                }
                                if (FinalValue.contains(Code)) {
                                    if (matcherNum.find(matcher.end())) {
                                        String NumMatcher = matcherNum.group();
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
                                row1 = sheet1.createRow(RowNum);
                                row1.createCell(0).setCellValue("DRZRW");
                                row1.createCell(1).setCellValue("100");
                                FinalValue.add("DRZRW");
                                RowNum++;
                            }
                            String[] CommonTrenes = {"DVMOV", "DVOOM", "DVFNA", "DVGCU", "DVSMV", "DVSMO", "DRZRW"};
                            String[] MPMVE = {"DVFGC", "DVFFN", "DVFOM", "DVFMV"};
                            HashSet<String> TrenesMultiCifEnElPDF = new HashSet<>();

                            if (text.contains("MPMVE") || text.contains("MultiCIF")) {

                                for (String Tren : CommonTrenes) {
                                    if (!FinalValue.contains(Tren)) {
                                        row1 = sheet1.createRow(RowNum++);
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
                                            row1 = sheet1.createRow(RowNum++);
                                            row1.createCell(0).setCellValue(TrenMPMVE);
                                            row1.createCell(1).setCellValue("100");
                                        }
                                        if (FinalValue.contains(TrenMPMVE)) {
                                            TrenesMultiCifEnElPDF.add(TrenMPMVE);
                                        }
                                    }
                                }
                                if (text.contains("SMS internacionales") && !FinalValue.contains("DVSMR")) {
                                    row1 = sheet1.createRow(RowNum);
                                    row1.createCell(0).setCellValue("DVSMR");
                                    row1.createCell(1).setCellValue("100");
                                    RowNum++;
                                }
                                if (text.contains("SMS internacionales") && FinalValue.contains("DVSMR")) {
                                    TrenesMultiCifEnElPDF.add("DVSMR");
                                }
                                if ((text.contains("CIINT") || text.contains("CPINT")) && !FinalValue.contains("DVINT")) {
                                    row1 = sheet1.createRow(RowNum);
                                    row1.createCell(0).setCellValue("DVINT");
                                    row1.createCell(1).setCellValue("100");
                                    RowNum++;
                                }
                                if ((text.contains("CIINT") || text.contains("CPINT")) && FinalValue.contains("DVINT")) {
                                    TrenesMultiCifEnElPDF.add("DVINT");
                                }
                                if ((text.contains("CI90X") || text.contains("CP90X")) && !FinalValue.contains("DV90X")) {
                                    row1 = sheet1.createRow(RowNum);
                                    row1.createCell(0).setCellValue("DV90X");
                                    row1.createCell(1).setCellValue("100");
                                    RowNum++;
                                }
                                if ((text.contains("CI90X") || text.contains("CP90X")) && FinalValue.contains("DV90X")) {
                                    TrenesMultiCifEnElPDF.add("DV90X");
                                }
                                if ((text.contains("CIINT") || text.contains("CPINT")) && !FinalValue.contains("DVFIN") && text.contains("MPMVE")) {
                                    row1 = sheet1.createRow(RowNum);
                                    row1.createCell(0).setCellValue("DVFIN");
                                    row1.createCell(1).setCellValue("100");
                                    RowNum++;
                                }
                                if ((text.contains("CIINT") || text.contains("CPINT")) && FinalValue.contains("DVFIN") && text.contains("MPMVE")) {
                                    TrenesMultiCifEnElPDF.add("DVFIN");
                                }
                                if ((text.contains("CI90X") || text.contains("CP90X")) && !FinalValue.contains("DVFES") && text.contains("MPMVE")) {
                                    row1 = sheet1.createRow(RowNum);
                                    row1.createCell(0).setCellValue("DVFES");
                                    row1.createCell(1).setCellValue("100");
                                    RowNum++;
                                }
                                if ((text.contains("CI90X") || text.contains("CP90X")) && FinalValue.contains("DVFES") && text.contains("MPMVE")) {
                                    TrenesMultiCifEnElPDF.add("DVFES");
                                }
                                if (text.contains("CIROZ") && !FinalValue.contains("DVRRE")) {
                                    row1 = sheet1.createRow(RowNum);
                                    row1.createCell(0).setCellValue("DVRRE");
                                    row1.createCell(1).setCellValue("100");
                                    RowNum++;
                                }
                                if (text.contains("CIROZ") && FinalValue.contains("DVRRE")) {
                                    TrenesMultiCifEnElPDF.add("DVRRE");
                                }
                                if (text.contains("CIRRZ") && !FinalValue.contains("DVRSA")) {
                                    row1 = sheet1.createRow(RowNum);
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
                    if (workbook.isSheetHidden(i) && SheetName.equals("Tren")) {
                        int OtherRowNum = 0;
                        for (Row TrenRow : sheet1) {
                            for (Cell TrenCell : TrenRow) {
                                OtherRowNum = TrenCell.getRow().getRowNum()+1;
                            }
                        }
                        Row HeaderRow = sheet1.createRow(OtherRowNum);
                        Cell HeaderCell = HeaderRow.createCell(2);
                        HeaderCell.setCellValue("el Fichero de Tren en la plantilla del CM no Existe.");
                    }
                }

                //save the new file with the extracted data
                try (FileOutputStream fileOutputStream = new FileOutputStream(Finalfile)) {
                    workbook1.write(fileOutputStream);
                }

            }
            //Search the new File and open it
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(Finalfile);
                }
            }
        }
    }
}