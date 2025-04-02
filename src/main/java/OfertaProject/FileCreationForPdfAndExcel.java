package OfertaProject;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

public class FileCreationForPdfAndExcel {

    private static final File OfertaFile = new File("OfertaPDFDeActivacion&PlantillaCM.xlsx");
    private static FileOutputStream OpenFile;
    private static final Workbook OpenSheet = new XSSFWorkbook();

    public static void createFile() throws Exception{
        OpenFile = new FileOutputStream(OfertaFile);
    }
    public static void closeStreamingOfNewFile() throws Exception{
        if(OpenFile != null){
            OpenFile.close();
        }
    }

    public static Workbook getWorkbook(){
        return OpenSheet;
    }

    public static Sheet getSheet(String sheetName){
        return OpenSheet.getSheet(sheetName);
    }
    public static Sheet createSheet(String sheetName){
        return OpenSheet.getSheet(sheetName);
    }


    public static void SaveFile()throws Exception{
        OpenSheet.write(OpenFile);
    }
    public static void CloseFile() throws Exception{
        OpenSheet.close();
    }

    public static void BringFile() throws Exception{
        Desktop desk = Desktop.getDesktop();
        desk.open(OfertaFile);
    }
}
