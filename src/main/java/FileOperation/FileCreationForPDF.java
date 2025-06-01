package FileOperation;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class FileCreationForPDF {

    private final File OfertaFile = new File(Objects.requireNonNull(FileAccess.accessToPropertiesFile("NewFileForPDF")));
    private FileOutputStream OpenFile;
    private final Workbook OpenSheet = new XSSFWorkbook();

    public FileCreationForPDF() throws Exception{

    }
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

    public Sheet createSheet(String newSheetName){
        return OpenSheet.createSheet(newSheetName);
    }

    public Sheet getSheet(String SheetName){
        return OpenSheet.getSheet(SheetName);
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
