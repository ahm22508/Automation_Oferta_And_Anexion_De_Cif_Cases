package FileOperation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.Objects;
import java.util.Properties;

public class FileAccess {

    private FileInputStream OpenFile;
    private Workbook OpenSheet;


    public void setFile(String FilePath) throws Exception {
        File PlantillaFile = new File(FilePath);
        OpenFile = new FileInputStream(PlantillaFile);
    }

    public Workbook getWorkBook() throws Exception{
        return OpenSheet = new XSSFWorkbook(OpenFile);
    }
    public void CloseWorkBook() throws Exception{
        if (OpenSheet != null){
            OpenSheet.close();
        }
    }
    public void CloseStreaming() throws Exception{
        if(OpenFile != null){
            OpenFile.close();
        }
    }

    public Sheet getSheet(String SheetName){
        return OpenSheet.getSheet(SheetName);
    }

    public static CSVParser ReadCSV() throws Exception{
        CSVParser CSVReader = null;
        try {
            FileReader DTOFile = new FileReader(Objects.requireNonNull(accessToPropertiesFile("DTOS.File")));
            CSVReader = CSVFormat.DEFAULT.parse(DTOFile);
        }
        catch (IOException EX){
            EX.getCause();
        }
        return CSVReader;
    }

    public static String accessToPropertiesFile(String key) throws Exception {
            FileReader fileConfig = new FileReader(System.getProperty("user.dir") +"//app.properties");
            Properties prop = new Properties();
            prop.load(fileConfig);
           return prop.getProperty(key) != null? prop.getProperty(key).trim() : null;
    }

}