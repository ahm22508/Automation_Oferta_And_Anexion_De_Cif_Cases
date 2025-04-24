package FileOperation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FileAccess {

    private  FileInputStream OpenFile;
    private  Workbook OpenSheet;


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
                    FileReader DTOFile = new FileReader(accessToPropertiesFile()[0]);
                    CSVReader = CSVFormat.DEFAULT.parse(DTOFile);
                }
                catch (IOException EX){
                    EX.getCause();
                }
                return CSVReader;
            }

            public static String[] accessToPropertiesFile() throws Exception{
                String [] ourProperties = new String[6];
                Properties proper = new Properties();
                FileInputStream file = new FileInputStream("C:\\Oferta Extractor\\data\\app.properties");
                proper.load(file);
                ourProperties[0] = proper.getProperty("DTOS.File");
                ourProperties[1] = proper.getProperty("isPdfFile");
                ourProperties[2] = proper.getProperty("isExcelFile");
                ourProperties[3] = proper.getProperty("isPdfAndExcelFile");
                ourProperties[4] = proper.getProperty("PdfFile");
                ourProperties[5] = proper.getProperty("ExcelFile");

                return ourProperties;
            }

    }