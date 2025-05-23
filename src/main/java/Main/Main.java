package Main;

import DataHandling.Comparison;
import DataHandling.ExtractingData;
import AuxiliaryTools.preLoadWorkbook;
import FileOperation.*;
import Plantilla.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pdfOperation.Discounts;
import pdfOperation.Minutes;
import pdfOperation.PostSelling;
import pdfOperation.Trenes;



public class Main {

    public static void main(String[] args) {

        try{
            preLoadWorkbook.preloading();

            Comparison compare = new Comparison();
            ExtractingData extract = new ExtractingData();
            FileCreationForPDF createPDF = new FileCreationForPDF();
            FileAccess access = new FileAccess();

            // PDF part

            if (FileAccess.accessToPropertiesFile().get(1).equals("true")) {

                String filePath = FileAccess.accessToPropertiesFile().get(4).replace("\"", "").replace("\\", "\\\\");

                if (FileAnalysis.isFile(filePath)) {
                    //File Reading
                    String text = extract.ReadPdf(filePath);
                    Sheet ofertaSheet;

                    //File Creation and Offer extraction.
                    createPDF.createFile();

                    Discounts dtos = new Discounts();
                    ofertaSheet = createPDF.createSheet("Descuentos");
                    dtos.ExtractDiscounts(text, ofertaSheet, compare);

                    Minutes minutos = new Minutes();
                    ofertaSheet = createPDF.createSheet("Minutos");
                    minutos.ExtractMinutes(text, ofertaSheet, compare);

                    PostSelling posventa = new PostSelling();
                    ofertaSheet = createPDF.createSheet("PosventaYBROWXXXX");
                    posventa.ExtractPostSelling(text, ofertaSheet, compare);
                    posventa.extractBonoBrow(text, ofertaSheet);
                    posventa.extractInsights(text, ofertaSheet);


                    Trenes Tren = new Trenes();
                    ofertaSheet = createPDF.createSheet("Trenes");
                    Tren.ExtractTrenes(text, ofertaSheet, compare);
                    Tren.extractTrenesMultiCIFYMPMVE(text, compare, ofertaSheet);


                    // File saving and showing in the screen.
                    createPDF.SaveFile();
                    createPDF.BringFile();
                    createPDF.CloseFile();
                    createPDF.closeStreamingOfNewFile();
                    extract.closePDFReader();

                }
            }


            //Excel Part


            if (FileAccess.accessToPropertiesFile().get(2).equals("true")) {

                String filePath = FileAccess.accessToPropertiesFile().get(5).replace("\"", "").replace("\\", "\\\\");
                if (FileAnalysis.isFile(filePath)) {
                    FileCreationForExcel createExcel = new FileCreationForExcel();
                    //File Starting
                    access.setFile(filePath);
                    Workbook PlantillaWorkBook = access.getWorkBook();
                    Sheet OfertaSheet;
                    //File Creation and Oferta Extraction
                    createExcel.createFile();

                    CMPlantilla_Descuentos dtos = new CMPlantilla_Descuentos();
                    if (dtos.isDescuentoSheet(PlantillaWorkBook)) {
                        OfertaSheet = createExcel.createSheet("PlantillaCM_Descuentos");
                        dtos.ExtractDescuentosFromCMP(PlantillaWorkBook, compare, OfertaSheet, access);
                    }

                    CMPlantilla_Indice indice = new CMPlantilla_Indice();
                    if (indice.isSheetIndice(PlantillaWorkBook)) {
                        OfertaSheet = createExcel.createSheet("PlantillaCM_Indice");
                        indice.ExtractInfoFromCMP(OfertaSheet, PlantillaWorkBook, access);
                    }

                    CMPlantilla_Posventa posventa = new CMPlantilla_Posventa();
                    if (posventa.isSheetPosventa(PlantillaWorkBook)) {
                        OfertaSheet = createExcel.createSheet("PlantillaCM_Posventa");
                        new CMPlantilla_Posventa().ExtractPosventaFromCMP(PlantillaWorkBook, OfertaSheet, compare, access);
                    }

                    CMPlantilla_MinutosInfinityBusiness MinutosIB = new CMPlantilla_MinutosInfinityBusiness();
                    if (MinutosIB.isMinutosBISheet(PlantillaWorkBook)) {
                        OfertaSheet = createExcel.createSheet("PlantillaCM_Minutos");
                        MinutosIB.ExtractMinutosFromCMP(OfertaSheet, compare, PlantillaWorkBook, access);
                    }

                    CMPlantilla_MinutosDescuentosYTarifas Minutos = new CMPlantilla_MinutosDescuentosYTarifas();
                    if (Minutos.isMinutosSheet(PlantillaWorkBook)) {
                        if (createExcel.getWorkbook().getSheet("PlantillaCM_Minutos") == null) {
                            OfertaSheet = createExcel.createSheet("PlantillaCM_Minutos");
                        } else {
                            OfertaSheet = createExcel.getSheet("PlantillaCM_Minutos");
                        }
                        Minutos.ExtractMinutosFromCMP(PlantillaWorkBook, OfertaSheet, Minutos.analyzeSheet(OfertaSheet), Minutos.getRowNum(), compare, access);
                    }

                    boolean isSheet = false;
                    CMPlantilla_TrenesInfinityBusiness trenIB = new CMPlantilla_TrenesInfinityBusiness();
                    while (trenIB.isInfinityBusinessTrenesSheet(PlantillaWorkBook)) {
                        if (createExcel.getWorkbook().getSheet("PlantillaCM_Trenes") == null) {
                            OfertaSheet = createExcel.createSheet("PlantillaCM_Trenes");
                        } else {
                            OfertaSheet = createExcel.getSheet("PlantillaCM_Trenes");
                        }
                        trenIB.ExtractTrenesBIFromCMP(PlantillaWorkBook, OfertaSheet, compare);
                        isSheet = true;
                    }
                    if (!isSheet) {
                        if (createExcel.getWorkbook().getSheet("PlantillaCM_Trenes") == null) {
                            OfertaSheet = createExcel.createSheet("PlantillaCM_Trenes");
                        } else {
                            OfertaSheet = createExcel.getSheet("PlantillaCM_Trenes");
                        }
                        trenIB.logInfo(OfertaSheet);
                    }

                    CMPlantilla_Trenes trenes = new CMPlantilla_Trenes();
                    boolean isSheetTrenes = false;
                    while (trenes.isTrenSheet(PlantillaWorkBook)) {
                        OfertaSheet = createExcel.getSheet("PlantillaCM_Trenes");
                        trenes.ExtractTrenesFromCMP(PlantillaWorkBook, OfertaSheet, compare);
                        isSheetTrenes = true;
                    }
                    if (!isSheetTrenes) {
                        OfertaSheet = createExcel.getSheet("PlantillaCM_Trenes");
                        trenes.logInfo(PlantillaWorkBook, OfertaSheet);
                    }


                    // File Saving and Closing
                    createExcel.SaveFile();
                    createExcel.BringFile();
                    createExcel.CloseFile();
                    createExcel.closeStreamingOfNewFile();
                    access.CloseWorkBook();
                    access.CloseStreaming();

                }

            }

            if (FileAccess.accessToPropertiesFile().get(3).equals("true")) {

                String pdfFilePath = FileAccess.accessToPropertiesFile().get(4).replace("\"", "").replace("\\", "\\\\");
                String excelFilePath = FileAccess.accessToPropertiesFile().get(5).replace("\"", "").replace("\\", "\\\\");

                if (FileAnalysis.isFile(excelFilePath) && FileAnalysis.isFile(pdfFilePath)) {
                    FileCreationForPdfAndExcel createFileForTwoOffers = new FileCreationForPdfAndExcel();
                    //File Excel Reading
                    access.setFile(excelFilePath);
                    Workbook PlantillaWorkBook = access.getWorkBook();
                    //File Creation.
                    createFileForTwoOffers.createFile();
                    Sheet OfertaSheet;
                    //Extract Offer From Excel

                    CMPlantilla_Descuentos dtos = new CMPlantilla_Descuentos();
                    if (dtos.isDescuentoSheet(PlantillaWorkBook)) {
                        OfertaSheet = FileCreationForPdfAndExcel.createSheet("Descuentos");
                        dtos.ExtractDescuentosFromCMP(PlantillaWorkBook, compare, OfertaSheet, access);
                    }

                    CMPlantilla_Posventa posventa = new CMPlantilla_Posventa();
                    if (posventa.isSheetPosventa(PlantillaWorkBook)) {
                        OfertaSheet = FileCreationForPdfAndExcel.createSheet("Posventa");
                        posventa.ExtractPosventaFromCMP(PlantillaWorkBook, OfertaSheet, compare, access);
                    }

                    CMPlantilla_Indice indice = new CMPlantilla_Indice();
                    if (indice.isSheetIndice(PlantillaWorkBook)) {
                        OfertaSheet = FileCreationForPdfAndExcel.createSheet("Indice");
                        indice.ExtractInfoFromCMP(OfertaSheet, PlantillaWorkBook, access);
                    }

                    CMPlantilla_MinutosInfinityBusiness MinutosIB = new CMPlantilla_MinutosInfinityBusiness();
                    if (MinutosIB.isMinutosBISheet(PlantillaWorkBook)) {
                        OfertaSheet = FileCreationForPdfAndExcel.createSheet("Minutos");
                        MinutosIB.ExtractMinutosFromCMP(OfertaSheet, compare, PlantillaWorkBook, access);
                    }

                    CMPlantilla_MinutosDescuentosYTarifas Minutos = new CMPlantilla_MinutosDescuentosYTarifas();
                    if (Minutos.isMinutosSheet(PlantillaWorkBook)) {
                        if (createFileForTwoOffers.getWorkbook().getSheet("Minutos") == null) {
                            OfertaSheet = FileCreationForPdfAndExcel.createSheet("Minutos");
                        } else {
                            OfertaSheet = FileCreationForPdfAndExcel.getSheet("Minutos");
                        }
                        Minutos.ExtractMinutosFromCMP(PlantillaWorkBook, OfertaSheet, Minutos.analyzeSheet(OfertaSheet), Minutos.getRowNum(), compare, access);
                    }

                    boolean isSheet = false;
                    CMPlantilla_TrenesInfinityBusiness trenIB = new CMPlantilla_TrenesInfinityBusiness();
                    while (trenIB.isInfinityBusinessTrenesSheet(PlantillaWorkBook)) {
                        if (createFileForTwoOffers.getWorkbook().getSheet("Trenes") == null) {
                            OfertaSheet = FileCreationForPdfAndExcel.createSheet("Trenes");
                        } else {
                            OfertaSheet = FileCreationForPdfAndExcel.getSheet("Trenes");
                        }
                        trenIB.ExtractTrenesBIFromCMP(PlantillaWorkBook, OfertaSheet, compare);
                        isSheet = true;
                    }
                    if (!isSheet) {
                        if (createFileForTwoOffers.getWorkbook().getSheet("Trenes") == null) {
                            OfertaSheet = FileCreationForPdfAndExcel.createSheet("Trenes");
                        } else {
                            OfertaSheet = FileCreationForPdfAndExcel.getSheet("Trenes");
                        }
                        trenIB.logInfo(OfertaSheet);
                    }

                    boolean isSheetTrenes = false;
                    CMPlantilla_Trenes trenesCM = new CMPlantilla_Trenes();
                    while (trenesCM.isTrenSheet(PlantillaWorkBook)) {
                        OfertaSheet = FileCreationForPdfAndExcel.getSheet("Trenes");
                        trenesCM.ExtractTrenesFromCMP(PlantillaWorkBook, OfertaSheet, compare);
                        isSheetTrenes = true;
                    }
                    if (!isSheetTrenes) {
                        OfertaSheet = FileCreationForPdfAndExcel.getSheet("Trenes");
                        trenesCM.logInfo(PlantillaWorkBook, OfertaSheet);
                    }

                    //File PDF Reading
                    String text = new ExtractingData().ReadPdf(pdfFilePath);

                    //Extract Offer From PDF.
                    Discounts dtosForPDF = new Discounts();
                    if (createFileForTwoOffers.getWorkbook().getSheet("Descuentos") == null) {
                        OfertaSheet = FileCreationForPdfAndExcel.createSheet("Descuentos");
                    } else {
                        OfertaSheet = FileCreationForPdfAndExcel.getSheet("Descuentos");
                    }
                    dtosForPDF.ExtractDiscounts(text, OfertaSheet, compare);

                    Minutes minutos = new Minutes();
                    if (createFileForTwoOffers.getWorkbook().getSheet("Minutos") == null) {
                        OfertaSheet = FileCreationForPdfAndExcel.createSheet("Minutos");
                    } else {
                        OfertaSheet = FileCreationForPdfAndExcel.getSheet("Minutos");
                    }
                    minutos.ExtractMinutes(text, OfertaSheet, compare);


                    PostSelling posventaForPDF = new PostSelling();
                    if (createFileForTwoOffers.getWorkbook().getSheet("Posventa") == null) {
                        OfertaSheet = FileCreationForPdfAndExcel.createSheet("Posventa");
                    } else {
                        OfertaSheet = FileCreationForPdfAndExcel.getSheet("Posventa");
                    }
                    posventaForPDF.ExtractPostSelling(text, OfertaSheet, compare);
                    posventaForPDF.extractBonoBrow(text, OfertaSheet);
                    posventaForPDF.extractInsights(text, OfertaSheet);

                    Trenes Tren = new Trenes();

                    if (createFileForTwoOffers.getWorkbook().getSheet("Trenes") == null) {
                        OfertaSheet = FileCreationForPdfAndExcel.createSheet("Trenes");
                    } else {
                        OfertaSheet = FileCreationForPdfAndExcel.getSheet("Trenes");
                    }
                    Tren.ExtractTrenes(text, OfertaSheet, compare);
                    Tren.extractTrenesMultiCIFYMPMVE(text, compare, OfertaSheet);


                    createFileForTwoOffers.SaveFile();
                    createFileForTwoOffers.BringFile();
                    createFileForTwoOffers.CloseFile();
                    createFileForTwoOffers.closeStreamingOfNewFile();
                    access.CloseWorkBook();
                    access.CloseStreaming();
                }
            }
        }
        catch (Exception ex){
            Logger log = LoggerFactory.getLogger(Main.class);
            log.error("An Error Occurred: " , ex);
        }
    }
}