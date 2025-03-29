package OfertaProject;

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

    private File PlantillaFile;
    private static FileInputStream OpenFile;
    private static Workbook OpenSheet;


            public void setFile(String FilePath) throws Exception {
                PlantillaFile = new File(FilePath);
                 OpenFile = new FileInputStream(PlantillaFile);
            }
            public File getFile(){
                return PlantillaFile;
            }

        public static Workbook getWorkBook() throws Exception{
                  return OpenSheet = new XSSFWorkbook(OpenFile);
        }
        public static void CloseWorkBook() throws Exception{
                if (OpenSheet != null){
                    OpenSheet.close();
                }
        }
        public static void CloseStreaming() throws Exception{
                if(OpenFile != null){
                    OpenFile.close();
                }
        }

        public static Sheet getSheet(String SheetName){
                return OpenSheet.getSheet(SheetName);
            }

            public static CSVParser ReadCSV() throws Exception{
              CSVParser CSVReader = null;
                try {
                    FileReader DTOFile = new FileReader(accessToDTOFile());
                    CSVReader = CSVFormat.DEFAULT.parse(DTOFile);
                }
                catch (IOException EX){
                    EX.getCause();
                }
                return CSVReader;
            }

            public static String accessToDTOFile() throws Exception{
                Properties proper = new Properties();
                FileInputStream file = new FileInputStream("C:\\Oferta Extractor\\data\\file.properties");
                proper.load(file);
                return proper.getProperty("url");
            }
    }