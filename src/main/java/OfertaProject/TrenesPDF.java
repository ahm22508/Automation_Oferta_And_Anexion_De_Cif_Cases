package OfertaProject;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TrenesPDF extends CMPlantilla_Descuentos {

    public void  GetTrenesFromPDF(String Text){

        File file = new File(FileName);
        try(FileInputStream PlantillaFile = new FileInputStream(file); Workbook PlantillaWorkbook = new XSSFWorkbook()) {

        }
        catch (IOException e){
            e.getCause();

        }
    }
}
