package OfertaProject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMPlantilla_Minutos {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the CM file as appear in the JO");
        String ExcelFileName = scanner.nextLine();
        String directoryToSearch = "D:\\CV";
        File PlantillaFile = CMPlantilla_Trenes.searchFile(new File(directoryToSearch), ExcelFileName);

        if (PlantillaFile == null) {
            System.out.println("No Entry");
        } else {
            File Finalfile = new File("PlantillaCM.xlsx");

            try (Workbook workbook = new XSSFWorkbook();
                 FileOutputStream fileOutputStream = new FileOutputStream(Finalfile)) {
                Sheet sheet = workbook.createSheet("PlantillaCM-Minutos");
                try (FileInputStream file = new FileInputStream(PlantillaFile.getAbsoluteFile());
                     Workbook workbook1 = new XSSFWorkbook(file)) {
                    Sheet sheet1 = workbook1.getSheet("Infinity Business");
                    Pattern pattern = Pattern.compile("(?<!-\\s)\\b(MPMVE|MPMVA|MPMVB|MPIMC|MPIMD|MPYME|MPIMF|MPIA2|MPIB2|MPIC2|MPID2|MPIE2|MPIF2|PIDCA|PIDCB|PIDCC|PIDCD|PIDCE|PIDCF|TDICA|TDICB|TDICC|TDICD|TDICE|TDICF|PIDCU|TDICU|MPIDU|MPMVD|MPCOB|MPCOL|MPCOU|MPCSC|MTCOU|MTCSC|MPRCV|MPRSC|CIGCU|CIVVF|CIOMM|CIFIJ|CI90X|CIINT|CIRR1|CIRO1|CIRRZ|CIROZ|CISVF|CISOM|CISIN|CIRSO|CIVNA|CISNA|CP90X|CPGCU|CPINT|CPVNA|MPIMA|MPIMB)\\b");
                    LinkedHashSet<String> Minutos = new LinkedHashSet<>();
                    int x = 0;
                    Row row1;
                    for (Row row : sheet1) {
                        for ( Cell cell : row) {
                            Matcher matcher = pattern.matcher(cell.toString());

                            if (matcher.find()) {
                                String FinalValue = matcher.group();
                                Minutos.add(matcher.group());
                                for (Cell NextCell : row) {
                                    if (NextCell.toString().contains("Cuota Final: ")) {
                                       if(Minutos.contains(FinalValue)){
                                            row1 = sheet.createRow(x++);
                                            row1.createCell(0).setCellValue(FinalValue);
                                            String Cleaning = NextCell.getStringCellValue();
                                            String FinalNumber = Cleaning.replace("Cuota Final: ", "");
                                            row1.createCell(1).setCellValue(FinalNumber);
                                        }
                                    }
                                }
                                if (cell.toString().contains("PKPID")) {
                                    row1 = sheet.getRow(0);
                                    row1.createCell(2).setCellValue("PKPID");
                                    row1.createCell(3).setCellValue("SÍ");
                                }
                            }

                        }
                    }

                            workbook.write(fileOutputStream);
                            if (Desktop.isDesktopSupported()) {
                                Desktop desktop = Desktop.getDesktop();
                                if (desktop.isSupported(Desktop.Action.OPEN)) {
                                    desktop.open(Finalfile);
                                }
                            }
                        } catch(
                                IOException e){
                            e.getCause();
                        }

                    }
                }
            }
        }


