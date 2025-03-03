package OfertaProject;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

public class FileCreation {
    private static final File OfertaFile = new File("C:\\Oferta Extractor\\data\\PlantillaCM.xlsx");
    private static FileOutputStream OpenFile;
    private static final Workbook OpenSheet = new XSSFWorkbook();

    public static void createFile() throws Exception{
        OpenFile = new FileOutputStream(OfertaFile);
    }

    public static Sheet createSheet(String newSheetName){
            return OpenSheet.createSheet(newSheetName);
    }

    public static Sheet getSheet(String SheetName){
        return OpenSheet.getSheet(SheetName);
    }

    public static void SaveFile()throws Exception{
        OpenSheet.write(OpenFile);
    }
    public static void CloseFile() throws Exception{
        OpenSheet.close();
    }

    public static void BringFile() throws Exception{
        if(Desktop.isDesktopSupported()){
            Desktop desk = Desktop.getDesktop();
           if(desk.isSupported(Desktop.Action.OPEN)){
               desk.open(OfertaFile);
           }
        }
    }
}
