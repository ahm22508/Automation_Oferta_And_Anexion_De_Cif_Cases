package AuxiliaryTools;

import FileOperation.FileAccess;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;


public class preLoadWorkbook {

    public static void preloading() throws Exception {
        for (int i = 0; i <= 1; i++) {
            File PreloadFile = new File(Objects.requireNonNull(FileAccess.accessToPropertiesFile("PreloadingFile")));
            FileInputStream PreloadingStream = new FileInputStream(PreloadFile);
            Workbook PreloadingWorkbook = new XSSFWorkbook(PreloadingStream);
            PreloadingWorkbook.getSheet("Preloading");
            PreloadingWorkbook.close();
        }
    }
}