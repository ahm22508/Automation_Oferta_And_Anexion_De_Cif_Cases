package OfertaProject;

import org.apache.poi.ss.usermodel.Workbook;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.io.File;


public class PDFHandling {

    public static void main(String[] args) throws Exception{
        preLoadWorkbook.preloading();
        JFrame frame = new JFrame();
        frame.setTitle("PDF Offer Extractor");
        frame.setSize(950, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileCreation.CloseFile();
                    FileCreation.closeStreamingOfNewFile();
                    FileAccess.CloseWorkBook();
                    FileAccess.CloseStreaming();
                }
                catch(Exception EX){
                    throw new RuntimeException(EX);
                }
            }
        });
        frame.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("C:\\Oferta Extractor\\data\\Icon.jpg");
        frame.setIconImage(imageIcon.getImage());

        JPanel centerPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon VFLogo = new ImageIcon("C:\\Oferta Extractor\\data\\vodafone.png");

                Graphics2D g2d = (Graphics2D) g.create();

                float alpha = 0.3f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

                g2d.drawImage(VFLogo.getImage(), 0, 0, getWidth(), getHeight(), this);

                g2d.dispose();
            }
        };
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(20, 20, 20, 200);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblFilePath = new JLabel("Enter your PDF Path here to extract the offer");
        centerPanel.add(lblFilePath, gbc);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 30));
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(e -> textField.paste());
        popupMenu.add(pasteItem);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

       centerPanel.add(textField, gbc);

        JButton btnExtract = new JButton("Extract Offer From PDF");
        btnExtract.setPreferredSize(new Dimension(200, 30));
        centerPanel.add(btnExtract, gbc);

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 1;
        gbc1.gridy = GridBagConstraints.RELATIVE;
        gbc1.insets = new Insets(20, 20, 20, 20);
        gbc1.anchor = GridBagConstraints.WEST;
        JTextField textField1 = new JTextField();
        JLabel lblCMPlantilla = new JLabel("<html>Enter Excel Name of PCM here to extract the offer<br>(Optional: only if the file of CM is existed)</html>");
        centerPanel.add(lblCMPlantilla, gbc1);
        JPopupMenu jPopupMenu1 = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem("Paste");
        jMenuItem.addActionListener(a-> textField1.paste());
        jPopupMenu1.add(jMenuItem);
        textField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    jPopupMenu1.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                if(e.isPopupTrigger()){
                    jPopupMenu1.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        textField1.setPreferredSize(new Dimension(300, 30));
        centerPanel.add(textField1, gbc1);



        JButton btnExtract1 = new JButton("Extract Offer from PCM");
        btnExtract1.setPreferredSize(new Dimension(200, 30));
        centerPanel.add(btnExtract1, gbc1);


        btnExtract.addActionListener(e -> {
            String filePath = textField.getText();
            File Checking = new File(filePath);
            if (!Checking.exists()) {
                JOptionPane.showMessageDialog(frame, "Entry No Correct. Please enter a valid PDF File.");
                return;
            }
            try {
                long Start = System.nanoTime();
                String text = new ExtractingData().ReadPdf(filePath);
                new Discounts().ExtractDiscounts(text);
                new Minutes().ExtractMinutes(text);
                new PostSelling().ExtractPostSelling(text);
                new Trenes().ExtractTrenes(text);
                long end = System.nanoTime();
                System.out.println(end - Start);
                JOptionPane.showMessageDialog(frame, "Offer is extracted successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage());
            }
        });

        btnExtract1.addActionListener(e -> {
            String FilePath = textField1.getText();
            File Checking = new File(FilePath);

           if(!Checking.exists()) {
                   JOptionPane.showMessageDialog(frame, "Entry No Correct. Please enter a valid File.xlsm or xlsx");
                   return;
               }
                try {
                    //File Starting
                    long Start = System.nanoTime();
                    FileCreation.createFile();
                    new FileAccess().setFile(FilePath);
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
                    long end = System.nanoTime();
                    System.out.println(end - Start);

                    JOptionPane.showMessageDialog(frame, "Offer is extracted successfully.");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage());
                }
        });
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);

    }
}