package OfertaProject;


import java.util.Scanner;

public class PDFHandling {
   static public void main(String[] args) {
     Scanner scanner = new Scanner(System.in);
       System.out.println("Send the file to us...");
      String ScanFile =  scanner.nextLine();
    new Discounts().ExtractDiscounts(ScanFile);
    new Minutes().ExtractMinutes(ScanFile);
    new PostSelling().ExtractPostSelling(ScanFile);
    new Trenes().ExtractTrenes(ScanFile);
    }
}
