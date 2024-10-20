package OfertaProject;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Descuentos {

    String FileName = "PlantillaCM.xlsx";

    public void ExtractDescuentosFromCMP(String file) throws IOException {
        //open the plantilla and search about specific sheet
        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheet("Dtos y Tarifas Complementarios");

            //Create New Excel File
            File Finalfile = new File(FileName);
            try (FileOutputStream fileOutputStream = new FileOutputStream(Finalfile);
                 Workbook workbook1 = new XSSFWorkbook()) {

                //check if the sheet is found or not
                int SheetNums = workbook.getNumberOfSheets();
                for (int i = 0; i < SheetNums; i++) {
                    String SheetName = workbook.getSheetName(i);
                    if (!workbook.isSheetHidden(i) && SheetName.contains("Dtos y Tarifas Complementarios")) {

                        //create new Sheet in the new file
                        Sheet sheet1 = workbook1.createSheet("PlantillaCM-Descuentos y Otros");

                        //Extract the specific data
                        Pattern pattern = Pattern.compile("DAGB2|DCVFA|DCVFC|DFUPC|DNPTP|DPG11|DPYG4|DPYG6|DPYG8|DSNHS|DTBTP|DVCTP|DXA2L|DXAB3|DXAHZ|DXAMB|DXAVB|DXAVM|DXHIG|DXIND|DXLOW|DXRAZ|BS100|CADIF|D10MI|D13TF|D20MI|D25IN|D2N12|D30MI|D33TF|D35IN|D40MI|D50MI|D50TF|D50TP|D60SG|D60TP|D66TF|D70TP|D75TF|D80SG|D80TP|D90FN|D90TP|D93FN|DALSA|DBB10|DCAM2|DCAML|DCDIS|DCP10|DCSOA|DDESV|DEI9A|DEI9B|DEIIA|DEIIB|DEINA|DEINB|DFA30|DFA40|DFA50|DFA60|DFI50|DFN00|DFN20|DFN40|DFN45|DFN50|DFN55|DFN60|DFN65|DFN70|DFN75|DFN80|DFN85|DFRE0|DFRE3|DFV00|DFV75|DFV80|DGC00|DGC10|DGC20|DGC25|DGC30|DGC35|DGC40|DGC45|DGC50|DGC54|DGC55|DGC60|DGC65|DGC75|DGC80|DGC85|DGC90|DHOT0|DHOT1|DHOT2|DHOT3|DI100|DLYFT|DLYMD|DMI60|DMO50|DMUZC|DNA10|DNA15|DNA20|DNA40|DNC10|DNC11|DNC15|DNC25|DNC30|DNC32|DNC35|DNC45|DNC50|DNC55|DNESP|DOFVA|DOFVB|DOV14|DOV15|DPC24|DPH10|DPOSP|DRCJA|DREST|DRFC8|DRFC9|DRO05|DRO10|DRO20|DRO25|DRO50|DRO60|DROPA|DROPB|DROPC|DROPD|DROPE|DROPF|DROPG|DROPH|DROPI|DROPJ|DRR05|DRR20|DRR25|DRR30|DRR40|DRR50|DRRPA|DRRPB|DRRPC|DRRPD|DRRPE|DRRPF|DRRPG|DRRPH|DRRPI|DS10M|DS20M|DS50M|DS65M|DS90D|DS90G|DSA15|DSA25|DSA30|DSA35|DSA40|DSA45|DSA50|DSA55|DSA60|DSA70|DSA75|DSA80|DSAIR|DSATO|DSBR2|DSC80|DSCE1|DSF20|DSF25|DSGC2|DSGT2|DSGT3|DSGT6|DSGV2|DSGV3|DSGV5|DSH65|DSI00|DSI25|DSI30|DSI35|DSI40|DSI41|DSI45|DSI50|DSI55|DSI60|DSI65|DSI70|DSI80|DSIN1|DSIN4|DSLIM|DSM12|DSM13|DSM20|DSM25|DSM40|DSM65|DSMA5|DSMA7|DSMM5|DSMM7|DSMOE|DSMUL|DSMUZ|DSN10|DSN15|DSN20|DSN21|DSN25|DSN28|DSN30|DSN35|DSN38|DSN40|DSN55|DSNAI|DSNN5|DSO30|DSO35|DSO45|DSO50|DSO55|DSO70|DSOA3|DSOAA|DSOAM|DSOFM|DSOFV|DSP05|DSPLA|DSR20|DSRCC|DSRE6|DSREC|DSRES|DSROI|DSRTW|DSRV1|DSRVG|DSRVQ|DSRZ3|DSS20|DSS25|DSS40|DSS55|DSS60|DSS65|DSSEC|DSSM4|DSSM5|DSSM6|DSSMS|DSSVQ|DSSVS|DSSVX|DSTE1|DSTE2|DSTI1|DSTI2|DSTI4|DSTI5|DSV51|DSVO5|DSVO6|DTCAL|DTF06|DTF13|DTF20|DTF26|DTF30|DTF33|DTF40|DTF50|DTF60|DTG50|DTIPC|DTO60|DTO75|DTO90|DTP1F|DTP50|DTP75|DTPA1|DTPA5|DTPA9|DTPAD|DTPAT|DTPAU|DTPAY|DTPAZ|DTPIN|DTR50|DTROJ|DTZ10|DTZ20|DTZ30|DTZ40|DTZ60|DVAG2|DVAG3|DVN00|DVN40|DVN45|DVN60|DVN65|DVN70|DVN75|DVN80|DVV65|DVV85|DVV90|DWO00|DWO75|DXIPC|GTS15|GTS25|GTS50|GTSCI|IPC40|TPAP1|TPAP2|TPVV7|D00OR|D05RC|D05UC|D100G|D10RC|D10UC|D10UT|D15RC|D15UC|D20RC|D20UC|D20UT|D25OR|D25RC|D25UC|D25UT|D503M|D50CV|D50OR|D50RC|D50UC|D50UT|D75OR|D75RC|D75UC|DACR1|DACR2|DACR4|DACR6|DACR8|DAN40|DAN50|DAOOV|DASD1|DASD2|DASDO|DASON|DAUTO|DBW30|DBW50|DCAP1|DCAP2|DCAP4|DCAP5|DCAP6|DCEC3|DCK05|DCK10|DCK12|DCK14|DCK15|DCK16|DCK18|DCK20|DCK22|DCK24|DCK25|DCK26|DCK28|DCK30|DCK35|DCK40|DCK45|DCK50|DCKSD|DCLI1|DCLI5|DCLTF|DCMLD|DCNK2|DCO19|DCOOM|DCOSK|DCR20|DCR40|DCR50|DCR60|DCSK0|DCSK1|DCSK2|DCSK3|DCSK4|DCSK5|DCSK6|DCSK7|DCSK8|DDCON|DEU30|DEU50|DFI10|DFI11|DFID2|DFID3|DFID5|DFID6|DFID7|DFID8|DFID9|DFRE6|DFRE9|DGCU5|DGCZ1|DGINK|DIDL2|DIDLL|DKB10|DKB20|DKB30|DKB40|DKC10|DKC20|DKC30|DKC40|DKCPC|DKNL5|DKPP1|DKS10|DKS20|DKS30|DKS40|DKSPC|DKT10|DKT20|DKT30|DKT40|DLD25|DMK40|DMK45|DMK50|DMS13|DMT25|DMU00|DMU50|DMU75|DOR25|DOR50|DOR75|DOVPD|DPI05|DPI10|DPI20|DPI25|DPI30|DPI35|DPI40|DPI50|DPK15|DPK20|DPK25|DPK30|DPOV1|DPOV2|DPOV5|\n");
                        Pattern pattern1 = Pattern.compile("(?<!-\\s)\\b(MPMVE|MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|TDICA|TDICB|TDICC|TDICD|TDICE|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB)\\b");
                        Pattern pattern2 = Pattern.compile("POS+[A-Z]{2}");
                        Pattern pattern3 = Pattern.compile("POC+[A-Z]{2}");
                        LinkedHashSet<String> Minutos = new LinkedHashSet<>();
                        int rowNum = 0;
                        Row row1;
                        Row row2;
                        Row row3;
                        for (Row row : sheet) {
                            for (Cell cell : row) {
                                Matcher matcher = pattern.matcher(cell.toString());
                                Matcher matcher1 = pattern1.matcher(cell.toString());
                                Matcher matcher2 = pattern2.matcher(cell.toString());
                                Matcher matcher3 = pattern3.matcher(cell.toString());
                                if (matcher.find()) {
                                    String Codes = matcher.group();
                                    for (Cell CodeCell : row) {
                                        if (CodeCell.toString().equals("SI")) {
                                            row1 = sheet1.createRow(rowNum++);
                                            row1.createCell(0).setCellValue(Codes);
                                        }
                                    }
                                }
                                if (matcher1.find()) {
                                    String FinalValue = matcher1.group();
                                    Minutos.add(matcher1.group());
                                    for (Cell NextCell : row) {
                                        if (Minutos.contains(FinalValue)) {
                                            if (NextCell.getCellType() == CellType.NUMERIC) {
                                                row2 = sheet1.createRow(rowNum++);
                                                row2.createCell(0).setCellValue(FinalValue);
                                                row2.createCell(1).setCellValue(NextCell.getNumericCellValue());
                                            }
                                        }
                                    }
                                }
                                if (matcher2.find()) {
                                    for (Cell ProvisionCell : row) {
                                        if (ProvisionCell.toString().contains("SI")) {
                                            row3 = sheet1.createRow(rowNum++);
                                            row3.createCell(0).setCellValue(matcher2.group());
                                        }
                                    }
                                }
                                if (matcher3.find()) {
                                    for (Cell ProvisionCell : row) {
                                        if (ProvisionCell.toString().contains("SI")) {
                                            row3 = sheet1.createRow(rowNum++);
                                            row3.createCell(0).setCellValue(matcher2.group());
                                        }
                                    }
                                }
                            }
                        }

                        //save the new file with the extracted data
                        workbook1.write(fileOutputStream);
                    }
                }
                    }
                }
            }
        }
