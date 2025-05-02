package AuxiliaryTools;

import FileOperation.FileAccess;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;


public class preLoadWorkbook {

    public static void preloading() throws Exception {
        for (int i = 0; i <= 1; i++) {
            File PreloadFile = new File(FileAccess.accessToPropertiesFile().get(6));
            FileInputStream PreloadingStream = new FileInputStream(PreloadFile);
            Workbook PreloadingWorkbook = new XSSFWorkbook(PreloadingStream);
            PreloadingWorkbook.getSheet("Preloading");
            PreloadingWorkbook.close();
        }
    }
}