package OfertaProject;

import org.apache.poi.ss.usermodel.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Trenes {

    public void ExtractTrenesFromCMP(Workbook PlantillaWorkBook , Workbook ofertaWorkbook) {

        Sheet InfinitySheet = PlantillaWorkBook.getSheet("Infinity Business");

        //create new Sheet in the new file
         Sheet OfertaSheet = ofertaWorkbook.getSheet("PlantillaCM-Trenes");

        int RowNum = 0;
        int iterator = 0;

        //Map To Resolve The Current Modification Exception...
        HashSet<String> DuplicationTrenes = new HashSet<>();

        Map<String, Double> TrenDeOtroFichero = new HashMap<>();
        //Extract the specific data
        Pattern pattern = Pattern.compile("DVSMO|DVMOV|DRZRW|DV90X|DCFWP|DVOOM|DPIDC|DVGCU|DVFNA|DVSMV|DVINT|DVSMR|DVMMN|DVMMI|DVMED|DVCAR|DVTFX|DVZWX|DVPCG|DVFZX|DVRSA|DVSML|DVSMM|DVSBC|DVSBS|DVSPR|DVSAV|DVSPM|DVIBA|DVIP2|DVIP5|DVTDA|DVTIC|DVPN1|DVPN2|DVPN5|DVPNX|DVBBP|DVBEM|DVBBL|DVBBW|DVBER|DVBDI|DVBMS|DVPOA|DVPOM|DVP11|DVP12|DVSOA|DVSOM|DVHOT|DVPCF|DVVAG|DVFME|DVTAS|DVFES|DVMTM|DVMTA|DVSME|DVLIM|DVM2M|DVDSG|DVRMG|DVRBF|DVALF|DVARA|DVARM|DVXSV|DVXSO|DVXSI|DVXMM|DVXLO|DVFFN|DVFGC|DVFIN|DVFMV|DVFOM|DVRRE|DVSVO|DVSIN|DINZ1|DINZ2|DINZ3|DINZ4|DINZ5|DMBCM|DCT4G|DCO4G|DCT2G|DCT5G|DCT1G|DC2GB|DTIPA|DTIPM|DICR1|DICRR|DSIPC|DSIP1|DSIP2|DSIP5|DSIP6|DSIP7|DSIP8|DSPTF|DSGCU|DLY02|DCONA|DCONL|DPIZ1|DPIZ2|DPIZ3|DPIZ4|DPIZ5|DPRID|DCTSM|DRML1|DRML2|DCTP1|DCTP2|DCTFM|DTMNS|DCTFE|DPITN|DCREB|DCREE|DCRMB|DCRME|DFAXI|DFAXC|DFAXN|DCTCB|DDCRW|DXBRO|DVXBR|DCDMF|DCMMF|DB90X|DTUSA|DSCOV|DCDI5|DCDI4|DCDI3|DCDI2|DCDI1|DBPIN|DBVGE|DBUTE|DBFUN|DBREF|DCSMP|DCSCR|DINP5|DINP4|DINP3|DINP2|DINP1|DINT5|DINT4|DINT3|DINT2|DINT1|DGSH5|DGSH4|DGSH3|DGSH2|DGSH1|DGST5|DGST4|DGST3|DGST2|DGST1|DTRUC|DDECB|DDCRM|DDZRM|DDTRM|DRZMU|DESIM|DAETF|DMETF|DGEST|DIMGS|DITGS|DTRVO|DTRUT|DTRRC|DSMP1|DSMP2|DSMP3|DSMP4|DSMP5|DTROR|DTSM3");
        Pattern patternNum = Pattern.compile("(\\d+(,\\d+)?)(?=%)");
        Row row1;
        String TrenInfinityBusiness = "";
        Matcher matcher;
        Matcher matcherNum;
        //check if the sheet is found or not
        int SheetNums = PlantillaWorkBook.getNumberOfSheets();
        for (int i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (!PlantillaWorkBook.isSheetHidden(i) && (SheetName.equals("Tren") || SheetName.contains("tren") || SheetName.contains("Tren_") || SheetName.contains("Trenes"))) {
                Sheet sheet = PlantillaWorkBook.getSheet(PlantillaWorkBook.getSheetName(i));

                if (iterator == 0) {
                    Row HeaderRow = OfertaSheet.getRow(0);
                    Cell HeaderCell = HeaderRow.createCell(0);
                    HeaderCell.setCellValue("All Trenes From CM-Plantilla...");
                }

                //Sheet analisis.
                for (Row TrenRow : OfertaSheet) {
                    Cell DuplicationCell = TrenRow.getCell(0);
                    if (DuplicationCell != null) {
                        DuplicationTrenes.add(DuplicationCell.toString());
                    }
                    for (Cell TrenCell : TrenRow) {
                        RowNum = TrenCell.getRow().getRowNum() + 1;
                    }
                }

                iterator++;
                if (iterator == 2) {
                    Row row = OfertaSheet.getRow(0);
                    Cell cell = row.createCell(2);
                    cell.setCellValue("Existe más que un fichero en la plantilla del CM, puedes encontrar muchos duplicados, pregunta al ejecutivo que Fichero aplicamos");
                }

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
                                            row1 = OfertaSheet.createRow(RowNum++);
                                            row1.createCell(0).setCellValue(matcher.group());
                                            row1.createCell(1).setCellValue(Percentage);
                                        }
                                    }

                                    if (DuplicationTrenes.contains(matcher.group())) {

                                        for (Row SearchTren : OfertaSheet) {
                                            for (Cell CellTren : SearchTren) {
                                                if (CellTren.toString().equals(matcher.group())) {
                                                    double ActualValue = SearchTren.getCell(CellTren.getColumnIndex() + 1).getNumericCellValue();
                                                    if (Percentage != ActualValue) {
                                                        TrenDeOtroFichero.put(matcher.group(), Percentage);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if ((NextCell.toString().contains("/") || NextCell.toString().contains("*") || NextCell.toString().contains("+") || NextCell.toString().contains("-")) && !NextCell.toString().matches(".*[A-Za-z].*")) {
                                    double Equation = NextCell.getNumericCellValue() * 100;
                                    double ModifyNum = Math.floor(Equation * 100) / 100;
                                    row1 = OfertaSheet.createRow(RowNum++);
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
                                                    row1 = OfertaSheet.createRow(RowNum++);
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
                                                                        String PercentageCell = TrenCell.getStringCellValue().
                                                                                replace(TrenInfinityBusiness, "").
                                                                                replace("-", "").
                                                                                replace("%", "").
                                                                                replace(" ", "").
                                                                                replace(",", ".");
                                                                        String AddZero = "0" + PercentageCell;
                                                                        double FinalNum = Double.parseDouble(AddZero);

                                                                        if (FinalNum > 0) {
                                                                            row1 = OfertaSheet.createRow(RowNum++);
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
                                            row1 = OfertaSheet.createRow(RowNum++);
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
                                    row1 = OfertaSheet.createRow(RowNum++);
                                    row1.createCell(0).setCellValue("DESIM");
                                    row1.createCell(1).setCellValue(Percentage);
                                }
                            }
                        }
                        if (cell.toString().equals("DVCAR")) {
                            for (Cell PorDefectoCell : row) {
                                if (PorDefectoCell.toString().contains("por defecto") && PorDefectoCell.getColumnIndex() > cell.getColumnIndex()) {
                                    row1 = OfertaSheet.createRow(RowNum++);
                                    row1.createCell(0).setCellValue("DVCAR");
                                    row1.createCell(1).setCellValue(100);
                                }
                            }
                        }
                    }
                }
                //Map To Resolve The Current Modification Exception...
                for (Map.Entry<String, Double> map : TrenDeOtroFichero.entrySet()) {
                    row1 = OfertaSheet.createRow(RowNum++);
                    row1.createCell(0).setCellValue(map.getKey());
                    row1.createCell(1).setCellValue(map.getValue());
                    row1.createCell(2).setCellValue("Tren De Otro Fichero");
                }

            }
        }
        for (int i = 0; i < SheetNums; i++) {
            String SheetName = PlantillaWorkBook.getSheetName(i);
            if (PlantillaWorkBook.isSheetHidden(i) && SheetName.equals("Tren")) {
                int OtherRowNum = 0;
                for (Row TrenRow : OfertaSheet) {
                    for (Cell TrenCell : TrenRow) {
                        OtherRowNum = TrenCell.getRow().getRowNum() + 1;
                    }
                }
                Row HeaderRow = OfertaSheet.createRow(OtherRowNum);
                Cell HeaderCell = HeaderRow.createCell(2);
                HeaderCell.setCellValue("el Fichero de Tren en la plantilla del CM no Existe.");
            }
        }
    }
}