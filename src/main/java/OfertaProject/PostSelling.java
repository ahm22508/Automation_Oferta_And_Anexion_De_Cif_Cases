package OfertaProject;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PostSelling {


    public void ExtractPostSelling(String text, Sheet OfertaSheet, String sheetName, Workbook ofertaWorkbook, Comparison compare){

        //Create new Excel File and new Sheet
        if (OfertaSheet == null) {
            OfertaSheet = ofertaWorkbook.createSheet(sheetName);
        } else {
            OfertaSheet = ofertaWorkbook.getSheet(sheetName);
        }

        //Extract specific data
        Pattern pattern1 = Pattern.compile("(?<!/)(?!\\d+\\.\\d+)\\b([1-9]\\d{0,4}|0)\\b");
        Matcher matcher1 = pattern1.matcher(text);
        Pattern pattern = Pattern.compile("\\bPOS+[A-Z]{2}\\b");
        Matcher matcher = pattern.matcher(text);
        Pattern pattern2 = Pattern.compile("BRW+\\d+");
        Matcher matcher2 = pattern2.matcher(text);
        Pattern pattern3 = Pattern.compile("POC+[A-Z]{2}");
        Matcher matcher3 = pattern3.matcher(text);
        Pattern pattern4 = Pattern.compile("POS[A-Z]\\d");
        Matcher matcher4 = pattern4.matcher(text);
        Row HeaderCell = OfertaSheet.createRow(0);
        HeaderCell.createCell(0).setCellValue("Posventa Y BONO");
        HeaderCell.createCell(1).setCellValue("Value");

        Set<String> TariffTypes = new HashSet<>(Arrays.asList("XPS", "LVSH5", "LVAPC", "MVCS", "M2M", "SIP01", "MPMVA", "MPMVD", "TIDCA", "MPCOU"));
        Set<String> CodesInPdf = new HashSet<>();
        Map<String, String> Description = new HashMap<>();
        Description.put("XPS", "REDBOX");
        Description.put("LVAPC", "Primaria Antigua");
        Description.put("LVSH5", "Primaria");
        Description.put("MVCS", "Normal");
        Description.put("M2M", "M2M");
        Description.put("SIP01", "SIP");
        Description.put("MPMVA", "Integrado");
        Description.put("MPMVD", "Integrado SIP");
        Description.put("MPMVE", "Integrada Primaria Actual");
        Description.put("TIDCA", "Infinity");
        Description.put("MPCOU", "Integrado Colaboración");
        Description.put("MPIA2", "Integrada 2.0");

        Row row;
        int i = RowNumCounting.getRowNumForPosVenta()+1;
        int FirstValue = 0;
        HashSet<String> Posventas = new HashSet<>();
        while (matcher.find()) {
            if (matcher1.find(matcher.end())) {
                String Posventa = matcher.group();
                if(!compare.getPosventaComparator().contains(Posventa)){
                    if (!Posventas.contains(Posventa)) {
                        Posventas.add(Posventa);
                        row = OfertaSheet.createRow(i++);
                        row.createCell(0).setCellValue(Posventa);
                        FirstValue = Integer.parseInt(matcher1.group());
                        String ServicePostSelling = matcher.group().replace("POS", "POC");
                        row = OfertaSheet.createRow(i++);
                        row.createCell(0).setCellValue(ServicePostSelling);
                        row.createCell(1).setCellValue("Servicio Suplementario a nivel de Cuenta");
                    }

                    if (Posventas.contains(Posventa)) {
                        if (matcher1.find(matcher.end())) {
                            int Num = Integer.parseInt(matcher1.group());
                            row = OfertaSheet.getRow(i - 2);
                            row.createCell(1).setCellValue(Math.max(Num, FirstValue));
                        }
                    }
                }
            }
        }
        //This for the posventa POSP1 and the similar Post selling.
        HashSet<String> ExceptionalPosventas = new HashSet<>();
        while (matcher4.find()) {
            if (matcher1.find(matcher4.end())) {
                String Posventa = matcher4.group();
                if (!compare.getPosventaComparator().contains(Posventa)){
                    if (!ExceptionalPosventas.contains(Posventa)) {
                        ExceptionalPosventas.add(Posventa);
                        row = OfertaSheet.createRow(i++);
                        row.createCell(0).setCellValue(Posventa);
                        FirstValue = Integer.parseInt(matcher1.group());
                    }
                    if (ExceptionalPosventas.contains(Posventa)) {
                        if (matcher1.find(matcher4.end())) {
                            int Num = Integer.parseInt(matcher1.group());
                            row = OfertaSheet.getRow(i - 1);
                            row.createCell(1).setCellValue(Math.max(Num, FirstValue));
                        }
                    }
                }
            }
        }
        if (Posventas.isEmpty()) {
            while (matcher3.find()) {
                if (!compare.getPosventaComparator().contains(matcher3.group())){
                    row = OfertaSheet.createRow(i++);
                    row.createCell(0).setCellValue(matcher3.group());
                }
            }
        }
        while (matcher2.find()) {
            row = OfertaSheet.createRow(i++);
            row.createCell(0).setCellValue(matcher2.group());
        }

        if (text.contains("POV") && text.contains("SOA")) {
            row = OfertaSheet.createRow(i++);
            row.createCell(0).setCellValue("Esa Oferta lleva POVFS y SOA, entonces hay que cargarla en el Gescore");
        } else if (text.contains("SOA")) {
            row = OfertaSheet.createRow(i++);
            row.createCell(0).setCellValue("Esa Oferta lleva SOA, entonces hay que cargarla en el Gescore");
        } else if (text.contains("POF") || text.contains("POVF")) {
            row = OfertaSheet.createRow(i++);
            row.createCell(0).setCellValue("Esa Oferta lleva POVFS, entonces hay que cargarla en el Gescore");
        }
        for (String Type : TariffTypes) {
            if (text.contains(Type)) {
                row = OfertaSheet.createRow(i++);
                row.createCell(0).setCellValue("Ese PDF contiene la siguiente Tarifa " + Type);
                CodesInPdf.add(Type);
            }
        }
        if (CodesInPdf.size() > 1) {
            row = OfertaSheet.createRow(i++);
            row.createCell(0).setCellValue("Ese PDF contiene más de una tarifa como:");
        }
        for (String Code : CodesInPdf) {
            row = OfertaSheet.createRow(i++);
            row.createCell(0).setCellValue(Code);
            String Tarifa = Description.get(Code);
            if (Tarifa != null) {
                row.createCell(1).setCellValue(Tarifa);
            }
        }
    }
}