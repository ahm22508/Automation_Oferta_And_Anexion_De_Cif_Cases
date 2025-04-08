package OfertaProject;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

public class FileCreationForExcel {
    private final File OfertaFile = new File("C:\\Oferta Extractor\\PlantillaCM.xlsx");
    private FileOutputStream OpenFile;
    private final Workbook OpenSheet = new XSSFWorkbook();

    public void createFile() throws Exception{
        OpenFile = new FileOutputStream(OfertaFile);
    }
    public void closeStreamingOfNewFile() throws Exception{
        if(OpenFile != null){
            OpenFile.close();
        }
    }

    public Workbook getWorkbook(){
            return OpenSheet;
    }

    public Sheet getSheet(String sheetName){
        return OpenSheet.getSheet(sheetName);
    }
    public Sheet createSheet(String sheetName){
        return OpenSheet.createSheet(sheetName);
    }
    public void SaveFile()throws Exception{
        OpenSheet.write(OpenFile);
    }
    public void CloseFile() throws Exception{
        OpenSheet.close();
    }

    public void BringFile() throws Exception{
            Desktop desk = Desktop.getDesktop();
               desk.open(OfertaFile);
           }
        }