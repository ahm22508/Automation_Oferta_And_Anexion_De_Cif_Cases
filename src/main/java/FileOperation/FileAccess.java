package FileOperation;

import Main.Main;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
            FileReader DTOFile = new FileReader(accessToPropertiesFile().get(0));
            CSVReader = CSVFormat.DEFAULT.parse(DTOFile);
        }
        catch (IOException EX){
            EX.getCause();
        }
        return CSVReader;
    }

    public static ArrayList<String> accessToPropertiesFile() throws Exception{
        ArrayList<String> ourProperties = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent() , "app.properties"));

        String Property;
        for (String line : lines) {
            if (!line.isEmpty()) {
                Property = line.substring(line.indexOf("=") +1 );
                ourProperties.add(Property);
            }
        }
        return ourProperties;
    }

}