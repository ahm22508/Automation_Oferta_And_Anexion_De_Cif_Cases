package OfertaProject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Trenes {
    public void   ExtractTrenes(String filePath) throws NullPointerException {

        StringBuilder text = new StringBuilder();

        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(filePath));
             Workbook workbook = new XSSFWorkbook()) {

            int Num = pdfDoc.getNumberOfPages();
            for (int i = 1; i < Num; i++) {
                String  PDFText = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i));
                if(PDFText.contains("Referencia")){
                    break;
                }
                text.append(PDFText);
            }
            Sheet sheet = workbook.createSheet("Trenes");

            Pattern pattern = Pattern.compile("DVSMO|DVMOV|DV90X|DCFWP|DVOOM|DPIDC|DVGCU|DVFNA|DVSMV|DVINT|DVSMR|DVMMN|DVMMI|DVMED|DVCAR|DVTFX|DVZWX|DVPCG|DVFZX|DVRSA|DVSML|DVSMM|DVSBC|DVSBS|DVSPR|DVSAV|DVSPM|DVIBA|DVIP2|DVIP5|DVTDA|DVTIC|DVPN1|DVPN2|DVPN5|DVPNX|DVBBP|DVBEM|DVBBL|DVBBW|DVBER|DVBDI|DVBMS|DVPOA|DVPOM|DVP11|DVP12|DVSOA|DVSOM|DVHOT|DVPCF|DVVAG|DVFME|DVTAS|DVFES|DVMTM|DVMTA|DVSME|DVLIM|DVM2M|DVDSG|DVRMG|DVRBF|DVALF|DVARA|DVARM|DVXSV|DVXSO|DVXSI|DVXMM|DVXLO|DVFFN|DVFGC|DVFIN|DVFMV|DVFOM|DVRRE|DVSVO|DVSIN|DINZ1|DINZ2|DINZ3|DINZ4|DINZ5|DMBCM|DCT4G|DCO4G|DCT2G|DCT5G|DCT1G|DC2GB|DTIPA|DTIPM|DICR1|DICRR|DSIPC|DSIP1|DSIP2|DSIP5|DSIP6|DSIP7|DSIP8|DSPTF|DSGCU|DLY02|DCONA|DCONL|DPIZ1|DPIZ2|DPIZ3|DPIZ4|DPIZ5|DPRID|DCTSM|DRML1|DRML2|DCTP1|DCTP2|DCTFM|DTMNS|DCTFE|DPITN|DCREB|DCREE|DCRMB|DCRME|DFAXI|DFAXC|DFAXN|DCTCB|DDCRW|DXBRO|DVXBR|DCDMF|DCMMF|DB90X|DTUSA|DSCOV|DCDI5|DCDI4|DCDI3|DCDI2|DCDI1|DBPIN|DBVGE|DBUTE|DBFUN|DBREF|DCSMP|DCSCR|DINP5|DINP4|DINP3|DINP2|DINP1|DINT5|DINT4|DINT3|DINT2|DINT1|DGSH5|DGSH4|DGSH3|DGSH2|DGSH1|DGST5|DGST4|DGST3|DGST2|DGST1|DTRUC|DDECB|DDCRM|DDZRM|DDTRM|DRZMU|DESIM|DAETF|DMETF|DGEST|DIMGS|DITGS|DTRVO|DTRUT|DTRRC|DSMP1|DSMP2|DSMP3|DSMP4|DSMP5|DTROR|DTSM3");
            Matcher matcher = pattern.matcher(text);
            Pattern pattern1 = Pattern.compile("(\\d+(\\.\\d+)?)(?=%)");
            Matcher matcher1 = pattern1.matcher(text);

            int x = 0;
            Row row;
            Set<String> FinalValue = new HashSet<>();
            while (matcher.find()) {
                String Code = matcher.group();
                if (!FinalValue.contains(Code)) {
                    FinalValue.add(Code);

                    if (matcher1.find(matcher.end())) {
                        row = sheet.createRow(x++);
                        row.createCell(0).setCellValue(Code);
                        row.createCell(1).setCellValue(matcher1.group());
                    }
                }
            }
                if(text.toString().contains("DRZRW")){
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DRZRW");
                    row.createCell(1).setCellValue("100");
                    FinalValue.add("DRZRW");
                    x++;
                }

            int L = 0;

            String [] MultiCIF = {"DVMOV","DVOOM","DVFNA","DVGCU","DVSMV","DVSMO"};
            String[] MPMVE = {"DVMOV", "DVOOM", "DVFNA", "DVGCU", "DVSMV", "DVSMO", "DVFMV", "DVFOM", "DVFFN", "DVFGC", "DRZRW"};

            if(text.toString().contains("MultiCIF")) {
                for (String Tren : MultiCIF) {
                    if (!MultiCIF[L].contains(FinalValue.iterator().next())) {
                        row = sheet.createRow(x++);
                        row.createCell(0).setCellValue(Tren);
                        row.createCell(1).setCellValue("100");
                        L++;
                    }
                }
                if (text.toString().contains("SMS internacionales") && !FinalValue.contains("DVSMR")) {
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DVSMR");
                    row.createCell(1).setCellValue("100");
                    x++;
                }
                if (text.toString().contains("CIINT") && !FinalValue.contains("DVINT")) {
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DVINT");
                    row.createCell(1).setCellValue("100");
                    x++;
                }
                if (text.toString().contains("CI90X") && !FinalValue.contains("DV90X")) {
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DV90X");
                    row.createCell(1).setCellValue("100");
                    x++;
                }
                if (text.toString().contains("CIROZ") && !FinalValue.contains("DVRRE")) {
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DVRRE");
                    row.createCell(1).setCellValue("100");
                    x++;
                }
                if (text.toString().contains("CIRRZ") && !FinalValue.contains("DVRSA")) {
                    row = sheet.createRow(x);
                    row.createCell(0).setCellValue("DVRSA");
                    row.createCell(1).setCellValue("100");
                }

                else if (text.toString().contains("MPMVE") || text.toString().contains("MultiCIF")) {
                    for (String Tren : MPMVE) {
                        if (!MPMVE[L].contains(FinalValue.iterator().next())) {
                            row = sheet.createRow(x++);
                            row.createCell(0).setCellValue(Tren);
                            row.createCell(1).setCellValue("100");
                            L++;
                        }
                    }
                    if (text.toString().contains("SMS internacionales") && !FinalValue.contains("DVSMR")) {
                        row = sheet.createRow(x);
                        row.createCell(0).setCellValue("DVSMR");
                        row.createCell(1).setCellValue("100");
                        x++;
                    }
                    if (text.toString().contains("CIINT") || text.toString().contains("CPINT") && !FinalValue.contains("DVINT")) {
                        row = sheet.createRow(x);
                        row.createCell(0).setCellValue("DVINT");
                        row.createCell(1).setCellValue("100");
                        x++;
                    }
                    if (text.toString().contains("CI90X") || text.toString().contains("CP90X") && !FinalValue.contains("DV90X")) {
                        row = sheet.createRow(x);
                        row.createCell(0).setCellValue("DV90X");
                        row.createCell(1).setCellValue("100");
                        x++;
                    }
                    if (text.toString().contains("CIROZ") && !FinalValue.contains("DVRRE")) {
                        row = sheet.createRow(x);
                        row.createCell(0).setCellValue("DVRRE");
                        row.createCell(1).setCellValue("100");
                        x++;
                    }
                    if (text.toString().contains("CIRRZ") && !FinalValue.contains("DVRSA")) {
                        row = sheet.createRow(x);
                        row.createCell(0).setCellValue("DVRSA");
                        row.createCell(1).setCellValue("100");

                    }
                }
            }
            File FinalFile = new File("Trenes.xlsx");
            try (FileOutputStream outputStream = new FileOutputStream(FinalFile)) {
            workbook.write(outputStream);
        }
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)){
                desktop.open(FinalFile);
            }
        }

    }
        catch(IOException e){
                e.getCause();
        }
    }
}


