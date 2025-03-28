package OfertaProject;


import org.apache.poi.ss.usermodel.Workbook;

import java.util.Scanner;

public class PDFHandling {

    public static void main(String[] args) throws Exception{
        preLoadWorkbook.preloading();

        Scanner selector = new Scanner(System.in);
        System.out.println("press 1 to extract offer from PDF\npress 2 to extract offer from Excel\npress 3 to extract offer from PDF and Excel at same time");
        int select = selector.nextInt();

        if(select == 1){
            System.out.println("Enter your Pdf file path:");
            Scanner pdfScan = new Scanner(System.in);
            String filePath = pdfScan.nextLine().replace("\"" , "");
            if(FileAnalysis.isFile(filePath)){
                String text = new ExtractingData().ReadPdf(filePath);
                new Discounts().ExtractDiscounts(text);
                new Minutes().ExtractMinutes(text);
                new PostSelling().ExtractPostSelling(text);
                new Trenes().ExtractTrenes(text);
                FileCreation.CloseFile();
                FileCreation.closeStreamingOfNewFile();
                FileAccess.CloseWorkBook();
                FileAccess.CloseStreaming();
            }
            else {
                  System.out.println("incorrect Entry. Try again");
            }

        }

        else if(select == 2){
            System.out.println("Enter your Excel file path:");
            Scanner excelScan = new Scanner(System.in);
            String filePath = excelScan.nextLine().replace("\"" , "");
            if(FileAnalysis.isFile(filePath)){
                //File Starting
                FileCreation.createFile();
                new FileAccess().setFile(filePath);
                Workbook PlantillaWorkBook = FileAccess.getWorkBook();
                //Oferta Extraction
                new CMPlantilla_Descuentos().ExtractDescuentosFromCMP(PlantillaWorkBook);
                new CMPlantilla_Indice().ExtractInfoFromCMP(PlantillaWorkBook);
                new CMPlantilla_Minutos().ExtractMinutosFromCMP(PlantillaWorkBook);
                new CMPlantilla_TrenesBusinessInfinity().ExtractTrenesBIFromCMP(PlantillaWorkBook);
                new CMPlantilla_Trenes().ExtractTrenesFromCMP(PlantillaWorkBook);
                // File Saving and Closing
                FileCreation.SaveFile();
                FileCreation.BringFile();
                System.out.println("Offer is extracted correctly");

                FileCreation.CloseFile();
                FileCreation.closeStreamingOfNewFile();
                FileAccess.CloseWorkBook();
                FileAccess.CloseStreaming();
            }
            else {
                System.out.println("incorrect Entry. Try again");
            }
        }

        else if (select == 3){
            System.out.println("PDF And Excel");
        }
        else {
            System.out.println("Incorrect selection.. program will exit. try again");
            Thread.sleep(2000);
            System.exit(0);
        }
    }
}
