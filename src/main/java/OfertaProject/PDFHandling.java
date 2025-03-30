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
                //File Reading
                String text = new ExtractingData().ReadPdf(filePath);

                //File Creation and Offer extraction.
                FileCreationForPDF.createFile();
                new Discounts().ExtractDiscounts(text);
                new Minutes().ExtractMinutes(text);
                new PostSelling().ExtractPostSelling(text);
                new Trenes().ExtractTrenes(text);

                System.out.println("Offer is extracted correctly");

                // File saving and showing in the screen.
                FileCreationForPDF.SaveFile();
                FileCreationForPDF.BringFile();
                FileCreationForPDF.CloseFile();
                FileCreationForPDF.closeStreamingOfNewFile();
                ExtractingData.closePDFReader();
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
                new FileAccess().setFile(filePath);
                Workbook PlantillaWorkBook = FileAccess.getWorkBook();

                //File Creation and Oferta Extraction
                FileCreationForExcel.createFile();
                new CMPlantilla_Descuentos().ExtractDescuentosFromCMP(PlantillaWorkBook, FileCreationForExcel.getSheet("PlantillaCM_Descuentos"), "PlantillaCM_Descuentos" ,FileCreationForExcel.getWorkbook());
                new CMPlantilla_Posventa().ExtractPosventaFromCMP(PlantillaWorkBook, FileCreationForExcel.getSheet("PlantillaCM_Posventa"), "PlantillaCM_Posventa" ,FileCreationForExcel.getWorkbook());
                new CMPlantilla_Indice().ExtractInfoFromCMP(PlantillaWorkBook, FileCreationForExcel.getSheet("PlantillaCM-Indice") , "PlantillaCM-Indice" , FileCreationForExcel.getWorkbook());

                new CMPlantilla_MinutosInfinityBusiness().ExtractMinutosFromCMP(PlantillaWorkBook, FileCreationForExcel.getSheet("PlantillaCM-Minutos") , "PlantillaCM-Minutos" , FileCreationForExcel.getWorkbook());
                new CMPlantilla_MinutosDescuentosYTarifas().ExtractMinutosFromCMP(PlantillaWorkBook, FileCreationForExcel.getSheet("PlantillaCM-Minutos") , "PlantillaCM-Minutos",FileCreationForExcel.getWorkbook());

                new CMPlantilla_TrenesInfinityBusiness().ExtractTrenesBIFromCMP(PlantillaWorkBook, FileCreationForExcel.getSheet("PlantillaCM-Trenes") , "PlantillaCM-Trenes" , FileCreationForExcel.getWorkbook());
                new CMPlantilla_Trenes().ExtractTrenesFromCMP(PlantillaWorkBook, FileCreationForExcel.getWorkbook());

                System.out.println("Offer is extracted correctly");

                // File Saving and Closing
                FileCreationForExcel.SaveFile();
                FileCreationForExcel.BringFile();
                FileCreationForExcel.CloseFile();
                FileCreationForExcel.closeStreamingOfNewFile();
                FileAccess.CloseWorkBook();
                FileAccess.CloseStreaming();
            }
            else {
                System.out.println("incorrect Entry. Try again");
            }
        }

        else if (select == 3){
           Scanner pdfScan = new Scanner(System.in);
           Scanner excelScan = new Scanner(System.in);
            System.out.println("Enter your pdf file path and Excel sheet file path");

            System.out.println("Your Excel File Path: ");
            String excelFilePath  = excelScan.nextLine().replace("\"" , "");
            System.out.println("Your PDF File Path: ");
            String pdfFilePath = pdfScan.nextLine().replace("\"" , "");
           if(FileAnalysis.isFile(excelFilePath) && FileAnalysis.isFile(pdfFilePath)){
               new FileAccess().setFile(excelFilePath);
               Workbook PlantillaWorkBook = FileAccess.getWorkBook();

               //File Creation and Oferta Extraction
               FileCreationForExcel.createFile();
               new CMPlantilla_Descuentos().ExtractDescuentosFromCMP(PlantillaWorkBook, FileCreationForPdfAndExcel.getSheet("Descuentos"), "Descuentos" ,FileCreationForPdfAndExcel.getWorkbook());
               new CMPlantilla_Posventa().ExtractPosventaFromCMP(PlantillaWorkBook, FileCreationForPdfAndExcel.getSheet("Posventa"), "Posventa" ,FileCreationForPdfAndExcel.getWorkbook());
               new CMPlantilla_Indice().ExtractInfoFromCMP(PlantillaWorkBook, FileCreationForPdfAndExcel.getSheet("PlantillaCM-Indice") , "PlantillaCM-Indice" , FileCreationForPdfAndExcel.getWorkbook());

               new CMPlantilla_MinutosInfinityBusiness().ExtractMinutosFromCMP(PlantillaWorkBook, FileCreationForPdfAndExcel.getSheet("Minutos") , "Minutos" , FileCreationForPdfAndExcel.getWorkbook());
               new CMPlantilla_MinutosDescuentosYTarifas().ExtractMinutosFromCMP(PlantillaWorkBook , FileCreationForExcel.getSheet("Minutos") , "Minutos",FileCreationForPdfAndExcel.getWorkbook());

               new CMPlantilla_TrenesInfinityBusiness().ExtractTrenesBIFromCMP(PlantillaWorkBook, FileCreationForPdfAndExcel.getSheet("Trenes") , "Trenes" , FileCreationForPdfAndExcel.getWorkbook());
               new CMPlantilla_Trenes().ExtractTrenesFromCMP(PlantillaWorkBook, FileCreationForPdfAndExcel.getWorkbook());
               FileAccess.CloseWorkBook();
               FileAccess.CloseStreaming();
           }

           else {
               System.out.println("incorrect Entry. Try Again");
           }

        }
        else {
            System.out.println("Incorrect selection.. program will exit. try again");
            Thread.sleep(2000);
            System.exit(0);
        }
    }
}