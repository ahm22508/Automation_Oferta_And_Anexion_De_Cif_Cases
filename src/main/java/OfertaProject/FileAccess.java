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

    private  FileInputStream OpenFile;
    private  Workbook OpenSheet;
    private File newFile;

            public void setFile(String FilePath) throws Exception {
                File PlantillaFile = new File(FilePath);
                String newFileName = "C:\\Oferta Extractor\\data\\Plantilla.xlsm";
                 newFile = new File(newFileName.replace("\"" , ""));
                if (PlantillaFile.renameTo(newFile)) {
                    OpenFile = new FileInputStream(newFile);
                }
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
            public void deleteFile(){
               if( newFile.delete()){
                   System.out.println("File Deleted...");
               }
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
                FileInputStream file = new FileInputStream("C:\\Oferta Extractor\\data\\DTOS.properties");
                proper.load(file);
                return proper.getProperty("url");
            }
    }