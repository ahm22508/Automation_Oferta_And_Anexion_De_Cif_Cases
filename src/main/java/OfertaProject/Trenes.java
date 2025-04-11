package OfertaProject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Trenes {
    private  int x = RowNumCounting.getRowNumForTrenes();
    private Row row;
    private final Set<String> FinalValue = new HashSet<>();

    public void ExtractTrenes(String text, Sheet OfertaSheet, Comparison compare) {

        //Extract specific data
        Pattern pattern = Pattern.compile("DVSMO|DVMOV|DV90X|DCFWP|DVOOM|DPIDC|DVGCU|DVFNA|DVSMV|DVINT|DVSMR|DVMMN|DVMMI|DVMED|DVCAR|DVTFX|DVZWX|DVPCG|DVFZX|DVRSA|DVSML|DVSMM|DVSBC|DVSBS|DVSPR|DVSAV|DVSPM|DVIBA|DVIP2|DVIP5|DVTDA|DVTIC|DVPN1|DVPN2|DVPN5|DVPNX|DVBBP|DVBEM|DVBBL|DVBBW|DVBER|DVBDI|DVBMS|DVPOA|DVPOM|DVP11|DVP12|DVSOA|DVSOM|DVHOT|DVPCF|DVVAG|DVFME|DVTAS|DVFES|DVMTM|DVMTA|DVSME|DVLIM|DVM2M|DVDSG|DVRMG|DVRBF|DVALF|DVARA|DVARM|DVXSV|DVXSO|DVXSI|DVXMM|DVXLO|DVFFN|DVFGC|DVFIN|DVFMV|DVFOM|DVRRE|DVSVO|DVSIN|DINZ1|DINZ2|DINZ3|DINZ4|DINZ5|DMBCM|DCT4G|DCO4G|DCT2G|DCT5G|DCT1G|DC2GB|DTIPA|DTIPM|DICR1|DICRR|DSIPC|DSIP1|DSIP2|DSIP5|DSIP6|DSIP7|DSIP8|DSPTF|DSGCU|DLY02|DCONA|DCONL|DPIZ1|DPIZ2|DPIZ3|DPIZ4|DPIZ5|DPRID|DCTSM|DRML1|DRML2|DCTP1|DCTP2|DCTFM|DTMNS|DCTFE|DPITN|DCREB|DCREE|DCRMB|DCRME|DFAXI|DFAXC|DFAXN|DCTCB|DDCRW|DXBRO|DVXBR|DCDMF|DCMMF|DB90X|DTUSA|DSCOV|DCDI5|DCDI4|DCDI3|DCDI2|DCDI1|DBPIN|DBVGE|DBUTE|DBFUN|DBREF|DCSMP|DCSCR|DINP5|DINP4|DINP3|DINP2|DINP1|DINT5|DINT4|DINT3|DINT2|DINT1|DGSH5|DGSH4|DGSH3|DGSH2|DGSH1|DGST5|DGST4|DGST3|DGST2|DGST1|DTRUC|DDECB|DDCRM|DDZRM|DDTRM|DRZMU|DESIM|DAETF|DMETF|DGEST|DIMGS|DITGS|DTRVO|DTRUT|DTRRC|DSMP1|DSMP2|DSMP3|DSMP4|DSMP5|DTROR|DTSM3");
        Matcher matcher = pattern.matcher(text);
        Pattern pattern1 = Pattern.compile("(\\d+(\\.\\d+)?)(?=%)");
        Matcher matcher1 = pattern1.matcher(text);



        String[] ArrayOfPrimarios = {"DPRID", "DVFME", "DVFES", "DVFGC", "DVFIN", "DVFFN", "DVFOM", "DVFMV"};
        double FirstValue = 0;
        while (matcher.find()) {
            String Code = matcher.group();
            if (!compare.getTrenesComparator().contains(Code)) {
                if (!FinalValue.contains(Code)) {
                    FinalValue.add(Code);
                    if (matcher1.find(matcher.end())) {
                        FirstValue = Double.parseDouble(matcher1.group());
                        if (matcher1.start() - matcher.end() <= 30) {
                            String Num = matcher1.group();
                            if (!Num.equals("0")) {
                                row = OfertaSheet.createRow(x++);
                                row.createCell(0).setCellValue(Code);
                                row.createCell(1).setCellValue(matcher1.group());
                                row.createCell(2).setCellValue(" ");
                                if (Code.equals("DVXSO") || Code.equals("DVXSV")) {
                                    row.createCell(2).setCellValue("Tren de Red Box");
                                }
                                if (text.contains("LVAPC") || text.contains("LVSH")) {
                                    for (String trPr : ArrayOfPrimarios) {
                                        if (trPr.equals(Code)) {
                                            row.createCell(2).setCellValue("Tren de Primaria");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (FinalValue.contains(Code)) {
                if (matcher1.find(matcher.end())) {
                    String NumMatcher = matcher1.group();
                    double SecondValue = Double.parseDouble(NumMatcher);
                    if (SecondValue > FirstValue) {
                        for (Row rowTren : OfertaSheet) {
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
            if (!compare.getTrenesComparator().contains("DRZRW")) {
                row = OfertaSheet.createRow(x);
                row.createCell(0).setCellValue("DRZRW");
                row.createCell(1).setCellValue("100");
                row.createCell(2).setCellValue("");
                FinalValue.add("DRZRW");
                x++;
            }
        }
    }

    public void extractTrenesMultiCIFYMPMVE(String text, Comparison compare, Sheet OfertaSheet) {
        String[] CommonTrenes = {"DVMOV", "DVOOM", "DVFNA", "DVGCU", "DVSMV", "DVSMO", "DRZRW"};
        String[] MPMVE = {"DVFGC", "DVFFN", "DVFOM", "DVFMV"};
        HashSet<String> TrenesMultiCifEnElPDF = new HashSet<>();

        if (text.contains("MPMVE") || text.contains("MultiCIF")) {
            for (String Tren : CommonTrenes) {
                if (!FinalValue.contains(Tren)) {
                    if (!compare.getTrenesComparator().contains(Tren)) {
                        row = OfertaSheet.createRow(x++);
                        row.createCell(0).setCellValue(Tren);
                        row.createCell(1).setCellValue("100");
                        if (text.contains("MPMVE")) {
                            row.createCell(2).setCellValue("Tren de MPMVE");
                        }
                        if (text.contains("MultiCIF")) {
                            row.createCell(2).setCellValue("Tren de MultiCif");
                        }
                    }
                }
                if (FinalValue.contains(Tren)) {
                    TrenesMultiCifEnElPDF.add(Tren);
                }
            }
            for (String TrenMPMVE : MPMVE) {
                if (text.contains("MPMVE")) {
                    if (!compare.getTrenesComparator().contains(TrenMPMVE)) {
                        if (!FinalValue.contains(TrenMPMVE)) {
                            row = OfertaSheet.createRow(x++);
                            row.createCell(0).setCellValue(TrenMPMVE);
                            row.createCell(1).setCellValue("100");
                            row.createCell(2).setCellValue("Tren de MPMVE");
                        }
                    }
                    if (FinalValue.contains(TrenMPMVE)) {
                        TrenesMultiCifEnElPDF.add(TrenMPMVE);
                    }
                }
            }
            if (text.contains("SMS internacionales") && !FinalValue.contains("DVSMR") && !compare.getTrenesComparator().contains("DVSMR")) {
                row = OfertaSheet.createRow(x);
                row.createCell(0).setCellValue("DVSMR");
                row.createCell(1).setCellValue("100");
                if (text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren de MPMVE");
                } else if (text.contains("MultiCIF")) {
                    row.createCell(2).setCellValue("Tren de MultiCif");
                } else if (text.contains("MultiCIF") && text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren del MultiCIF y MPMVE");
                }
                x++;
            }
            if (text.contains("SMS internacionales") && FinalValue.contains("DVSMR")) {
                TrenesMultiCifEnElPDF.add("DVSMR");
            }
            if ((text.contains("CPINT") || text.contains("CIPNT") || text.contains("CIINT")) && !FinalValue.contains("DVINT") && !compare.getTrenesComparator().contains("DVINT")) {
                row = OfertaSheet.createRow(x);
                row.createCell(0).setCellValue("DVINT");
                row.createCell(1).setCellValue("100");
                if (text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren de MPMVE");
                } else if (text.contains("MultiCIF")) {
                    row.createCell(2).setCellValue("Tren de MultiCif");
                } else if (text.contains("MultiCIF") && text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren del MultiCIF y MPMVE");
                }
                x++;
            }
            if ((text.contains("CIINT") || text.contains("CIPNT") || text.contains("CPINT")) && FinalValue.contains("DVINT")) {
                TrenesMultiCifEnElPDF.add("DVINT");
            }
            if ((text.contains("CI90X") || text.contains("CP90X")) && !FinalValue.contains("DV90X") && !compare.getTrenesComparator().contains("DV90X")) {
                row = OfertaSheet.createRow(x);
                row.createCell(0).setCellValue("DV90X");
                row.createCell(1).setCellValue("100");
                if (text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren de MPMVE");
                } else if (text.contains("MultiCIF")) {
                    row.createCell(2).setCellValue("Tren de MultiCif");
                } else if (text.contains("MultiCIF") && text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren del MultiCIF y MPMVE");
                }
                x++;
            }
            if ((text.contains("CI90X") || text.contains("CP90X")) && FinalValue.contains("DV90X")) {
                TrenesMultiCifEnElPDF.add("DV90X");
            }
            if ((text.contains("CIINT") || text.contains("CPINT") || text.contains("CIPNT")) && !FinalValue.contains("DVFIN") && !compare.getTrenesComparator().contains("DVFIN") && text.contains("MPMVE")) {
                row = OfertaSheet.createRow(x);
                row.createCell(0).setCellValue("DVFIN");
                row.createCell(1).setCellValue("100");
                if (text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren de MPMVE");
                } else if (text.contains("MultiCIF")) {
                    row.createCell(2).setCellValue("Tren de MultiCif");
                } else if (text.contains("MultiCIF") && text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren del MultiCIF y MPMVE");
                }
                x++;
            }
            if ((text.contains("CPINT") || text.contains("CIINT") || text.contains("CIPNT")) && FinalValue.contains("DVFIN") && text.contains("MPMVE")) {
                TrenesMultiCifEnElPDF.add("DVFIN");
            }
            if ((text.contains("CI90X") || text.contains("CP90X")) && !FinalValue.contains("DVFES") && !compare.getTrenesComparator().contains("DVFES") && text.contains("MPMVE")) {
                row = OfertaSheet.createRow(x);
                row.createCell(0).setCellValue("DVFES");
                row.createCell(1).setCellValue("100");
                if (text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren de MPMVE");
                } else if (text.contains("MultiCIF")) {
                    row.createCell(2).setCellValue("Tren de MultiCif");
                } else if (text.contains("MultiCIF") && text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren del MultiCIF y MPMVE");
                }
                x++;
            }
            if ((text.contains("CI90X") || text.contains("CP90X")) && FinalValue.contains("DVFES") && text.contains("MPMVE")) {
                TrenesMultiCifEnElPDF.add("DVFES");
            }
            if (text.contains("CIROZ") && !FinalValue.contains("DVRRE") && !compare.getTrenesComparator().contains("DVRRE")) {
                row = OfertaSheet.createRow(x);
                row.createCell(0).setCellValue("DVRRE");
                row.createCell(1).setCellValue("100");
                if (text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren de MPMVE");
                } else if (text.contains("MultiCIF")) {
                    row.createCell(2).setCellValue("Tren de MultiCif");
                } else if (text.contains("MultiCIF") && text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren del MultiCIF y MPMVE");
                }
                x++;
            }
            if (text.contains("CIROZ") && FinalValue.contains("DVRRE")) {
                TrenesMultiCifEnElPDF.add("DVRRE");
            }

            if (text.contains("CIRRZ") && !FinalValue.contains("DVRSA") && !compare.getTrenesComparator().contains("DVRSA")) {
                row = OfertaSheet.createRow(x);
                row.createCell(0).setCellValue("DVRSA");
                row.createCell(1).setCellValue("100");
                if (text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren de MPMVE");
                } else if (text.contains("MultiCIF")) {
                    row.createCell(2).setCellValue("Tren de MultiCif");
                } else if (text.contains("MultiCIF") && text.contains("MPMVE")) {
                    row.createCell(2).setCellValue("Tren del MultiCIF y MPMVE");
                }
            }

            if (text.contains("CIRRZ") && FinalValue.contains("DVRSA")) {
                TrenesMultiCifEnElPDF.add("DVRSA");
            }
        }

        for (String tren : TrenesMultiCifEnElPDF) {
            for (Row TrenesRow : OfertaSheet) {
                for (Cell CellTren : TrenesRow) {
                    if (CellTren.toString().equals(tren)) {
                        Cell NextCell = TrenesRow.getCell(CellTren.getColumnIndex() + 1);
                        NextCell.setCellValue("100");
                        if (!CellTren.toString().equals("DRZRW")) {
                            if (text.contains("MultiCIF")) {
                                TrenesRow.getCell(2).setCellValue("Tren del MultiCIF-Nuevo JO");
                            }
                            if (text.contains("MPMVE")) {
                                TrenesRow.getCell(2).setCellValue("Tren del MPMVE-Nuevo JO");

                            }
                        }
                    }
                }
            }
        }
    }

}