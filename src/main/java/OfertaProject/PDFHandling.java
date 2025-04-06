package OfertaProject;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Scanner;

public class PDFHandling {

    public static void main(String[] args) throws Exception {

        preLoadWorkbook.preloading();

        Scanner selector = new Scanner(System.in);
        System.out.println("press 1 to extract offer from PDF\npress 2 to extract offer from Excel\npress 3 to extract offer from PDF and Excel at same time");

        while (true) {
            int select = selector.nextInt();
            Comparison compare = new Comparison();
            ExtractingData extract = new ExtractingData();
            FileCreationForPDF createPDF = new FileCreationForPDF();
            if (select == 1) {

                System.out.println("Enter your Pdf file path:");
                Scanner pdfScan = new Scanner(System.in);
                String filePath = pdfScan.nextLine().replace("\"", "");
                if (FileAnalysis.isFile(filePath)) {
                    //File Reading
                    String text = extract.ReadPdf(filePath);
                    Sheet ofertaSheet;

                    //File Creation and Offer extraction.
                    createPDF.createFile();

                    Discounts dtos = new Discounts();
                    ofertaSheet =  createPDF.createSheet("Descuentos");
                    dtos.ExtractDiscounts(text, ofertaSheet, compare);

                    Minutes minutos = new Minutes();
                    ofertaSheet =  createPDF.createSheet("Minutos");
                    minutos.ExtractMinutes(text, ofertaSheet, compare);

                    new PostSelling().ExtractPostSelling(text, createPDF.getSheet("PosventaYBROWXXXX"), "PosventaYBROWXXXX", createPDF.getWorkbook(), compare);
                    new Trenes().ExtractTrenes(text, createPDF.getSheet("Trenes"), "Trenes", createPDF.getWorkbook(), compare);

                    System.out.println("Offer is extracted correctly");

                    // File saving and showing in the screen.
                    createPDF.SaveFile();
                    createPDF.BringFile();
                    createPDF.CloseFile();
                    createPDF.closeStreamingOfNewFile();
                    extract.closePDFReader();

                } else {
                    System.out.println("incorrect Entry. Try again");
                }

            } else if (select == 2) {
                System.out.println("Enter your Excel file path:");
                Scanner excelScan = new Scanner(System.in);
                String filePath = excelScan.nextLine().replace("\"", "");
                if (FileAnalysis.isFile(filePath)) {
                    FileCreationForExcel createExcel = new FileCreationForExcel();
                    //File Starting
                    new FileAccess().setFile(filePath);
                    Workbook PlantillaWorkBook = FileAccess.getWorkBook();
                    Sheet OfertaSheet;
                    //File Creation and Oferta Extraction
                    createExcel.createFile();

                    CMPlantilla_Descuentos dtos = new CMPlantilla_Descuentos();
                    if (dtos.isDescuentoSheet(PlantillaWorkBook)) {
                         OfertaSheet = createExcel.createSheet("PlantillaCM_Descuentos");
                        dtos.ExtractDescuentosFromCMP(PlantillaWorkBook, compare, OfertaSheet);
                    }
                    CMPlantilla_Indice indice = new CMPlantilla_Indice();
                    if(indice.isSheetIndice(PlantillaWorkBook)){
                         OfertaSheet = createExcel.createSheet("PlantillaCM_Indice");
                        indice.ExtractInfoFromCMP(OfertaSheet , PlantillaWorkBook);
                    }
                    CMPlantilla_Posventa posventa = new CMPlantilla_Posventa();
                    if(posventa.isSheetPosventa(PlantillaWorkBook)){
                         OfertaSheet = createExcel.createSheet("PlantillaCM_Posventa");
                        new CMPlantilla_Posventa().ExtractPosventaFromCMP(PlantillaWorkBook, OfertaSheet, compare);
                    }
                    CMPlantilla_MinutosInfinityBusiness MinutosIB = new CMPlantilla_MinutosInfinityBusiness();
                    if(MinutosIB.isMinutosBISheet(PlantillaWorkBook)) {
                         OfertaSheet = createExcel.createSheet("PlantillaCM_Minutos");
                        MinutosIB.ExtractMinutosFromCMP(OfertaSheet, compare, PlantillaWorkBook);
                    }
                    CMPlantilla_MinutosDescuentosYTarifas Minutos = new CMPlantilla_MinutosDescuentosYTarifas();
                    if(Minutos.isMinutosSheet(PlantillaWorkBook)){
                        if(createExcel.getWorkbook().getSheet("PlantillaCM_Minutos") == null) {
                            OfertaSheet = createExcel.createSheet("PlantillaCM_Minutos");
                        }
                        else {
                            OfertaSheet = createExcel.getSheet("PlantillaCM_Minutos");
                        }
                        Minutos.ExtractMinutosFromCMP(PlantillaWorkBook ,OfertaSheet, Minutos.analyzeSheet(OfertaSheet) , Minutos.getRowNum(), compare);
                    }

                    new CMPlantilla_TrenesInfinityBusiness().ExtractTrenesBIFromCMP(PlantillaWorkBook, createExcel.getSheet("PlantillaCM-Trenes"), "PlantillaCM-Trenes", createExcel.getWorkbook(), compare);
                    new CMPlantilla_Trenes().ExtractTrenesFromCMP(PlantillaWorkBook, createExcel.getSheet("PlantillaCM-Trenes"), "PlantillaCM-Trenes", createExcel.getWorkbook(), compare);


                    System.out.println("Offer is extracted correctly");

                    // File Saving and Closing
                    createExcel.SaveFile();
                    createExcel.BringFile();
                    createExcel.CloseFile();
                    createExcel.closeStreamingOfNewFile();
                    FileAccess.CloseWorkBook();
                    FileAccess.CloseStreaming();

                } else {
                    System.out.println("incorrect Entry. Try again");
                }
            } else if (select == 3) {
                Scanner pdfScan = new Scanner(System.in);
                Scanner excelScan = new Scanner(System.in);
                System.out.println("Enter your pdf file path and Excel sheet file path");

                System.out.println("Your Excel File Path: ");
                String excelFilePath = excelScan.nextLine().replace("\"", "");
                System.out.println("Your PDF File Path: ");
                String pdfFilePath = pdfScan.nextLine().replace("\"", "");
                if (FileAnalysis.isFile(excelFilePath) && FileAnalysis.isFile(pdfFilePath)) {
                    FileCreationForPdfAndExcel createFileForTwoOffers = new FileCreationForPdfAndExcel();
                    //File Excel Reading
                    new FileAccess().setFile(excelFilePath);
                    Workbook PlantillaWorkBook = FileAccess.getWorkBook();
                    //File Creation.
                    createFileForTwoOffers.createFile();

                    //Extract Offer From Excel
                    CMPlantilla_Descuentos dtos = new CMPlantilla_Descuentos();
                    if (dtos.isDescuentoSheet(PlantillaWorkBook)) {
                     Sheet OfertaSheet = createFileForTwoOffers.createSheet("Descuentos");
                        dtos.ExtractDescuentosFromCMP(PlantillaWorkBook, compare, OfertaSheet);
                    }
                    CMPlantilla_Posventa posventa = new CMPlantilla_Posventa();
                    if(posventa.isSheetPosventa(PlantillaWorkBook)){
                        Sheet OfertaSheet = createFileForTwoOffers.createSheet("Posventa");
                        posventa.ExtractPosventaFromCMP(PlantillaWorkBook, OfertaSheet, compare);
                    }
                    CMPlantilla_Indice indice = new CMPlantilla_Indice();
                    if(indice.isSheetIndice(PlantillaWorkBook)){
                        Sheet OfertaSheet = createFileForTwoOffers.createSheet("Indice");
                        indice.ExtractInfoFromCMP(OfertaSheet , PlantillaWorkBook);
                    }
                    CMPlantilla_MinutosInfinityBusiness MinutosIB = new CMPlantilla_MinutosInfinityBusiness();
                    if(MinutosIB.isMinutosBISheet(PlantillaWorkBook)) {
                        Sheet OfertaSheet = createFileForTwoOffers.createSheet("Minutos");
                        MinutosIB.ExtractMinutosFromCMP(OfertaSheet, compare, PlantillaWorkBook);
                    }
                    CMPlantilla_MinutosDescuentosYTarifas Minutos = new CMPlantilla_MinutosDescuentosYTarifas();
                    if(Minutos.isMinutosSheet(PlantillaWorkBook)){
                        Sheet OfertaSheet;
                        if(createFileForTwoOffers.getWorkbook().getSheet("Minutos") == null) {
                         OfertaSheet = createFileForTwoOffers.createSheet("Minutos");
                        }
                        else {
                        OfertaSheet = FileCreationForPdfAndExcel.getSheet("Minutos");
                        }
                        Minutos.ExtractMinutosFromCMP(PlantillaWorkBook ,OfertaSheet, Minutos.analyzeSheet(OfertaSheet) , Minutos.getRowNum(), compare);
                    }

                    new CMPlantilla_TrenesInfinityBusiness().ExtractTrenesBIFromCMP(PlantillaWorkBook, FileCreationForPdfAndExcel.getSheet("Trenes"), "Trenes", createFileForTwoOffers.getWorkbook(), compare);
                    new CMPlantilla_Trenes().ExtractTrenesFromCMP(PlantillaWorkBook, FileCreationForPdfAndExcel.getSheet("Trenes"), "Trenes", createFileForTwoOffers.getWorkbook(), compare);

                    //File PDF Reading
                    String text = new ExtractingData().ReadPdf(pdfFilePath);

                    //Extract Offer From PDF.
                    Discounts dtosForPDF = new Discounts();
                    if(createFileForTwoOffers.getWorkbook().getSheet("Descuentos") == null) {
                       Sheet OfertaSheet = createFileForTwoOffers.createSheet("Descuentos");
                        dtosForPDF.ExtractDiscounts(text, OfertaSheet, compare);

                    }
                    else {
                      Sheet OfertaSheet = FileCreationForPdfAndExcel.getSheet("Descuentos");
                        dtosForPDF.ExtractDiscounts(text, OfertaSheet, compare);
                    }

                    Minutes minutos  = new Minutes();
                    if(createFileForTwoOffers.getWorkbook().getSheet("Minutos") == null) {
                       Sheet OfertaSheet = createFileForTwoOffers.createSheet("Minutos");
                        minutos.ExtractMinutes(text, OfertaSheet, compare);

                    }
                    else {
                    Sheet OfertaSheet = FileCreationForPdfAndExcel.getSheet("Minutos");
                        minutos.ExtractMinutes(text, OfertaSheet, compare);
                    }

                    new PostSelling().ExtractPostSelling(text, FileCreationForPdfAndExcel.getSheet("Posventa"), "Posventa", createFileForTwoOffers.getWorkbook(), compare);
                    new Trenes().ExtractTrenes(text, FileCreationForPdfAndExcel.getSheet("Trenes"), "Trenes", createFileForTwoOffers.getWorkbook(), compare);

                    System.out.println("Offer is extracted correctly");

                    createFileForTwoOffers.SaveFile();
                    createFileForTwoOffers.BringFile();
                    createFileForTwoOffers.CloseFile();
                    createFileForTwoOffers.closeStreamingOfNewFile();
                    FileAccess.CloseWorkBook();
                    FileAccess.CloseStreaming();
                } else {
                    System.out.println("incorrect Entry. Try Again");
                }

            } else {
                System.out.println("Incorrect selection... try again");
            }
        }
    }
}