package OfertaProject;

import javax.swing.*;
import java.awt.*;


public class PDFHandling {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("PDF Offer Extractor");
        frame.setSize(950, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("C:\\PdfProject\\Icon.jpg");
        frame.setIconImage(imageIcon.getImage());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTitle = new JLabel("Welcome To Our App", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblTitle);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon VFLogo = new ImageIcon("C:\\PdfProject\\vodafone.png");

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
        centerPanel.add(textField, gbc);

        JButton btnExtract = new JButton("Extract Offer From PDF");
        btnExtract.setPreferredSize(new Dimension(200, 30));
        centerPanel.add(btnExtract, gbc);

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 1;
        gbc1.gridy = GridBagConstraints.RELATIVE;
        gbc1.insets = new Insets(20, 20, 20, 20);
        gbc1.anchor = GridBagConstraints.WEST;

        JLabel lblCMPlantilla = new JLabel("<html>Enter Excel Name of PCM here to extract the offer<br>(Optional: only if the file of CM is existed)</html>");
        centerPanel.add(lblCMPlantilla, gbc1);

        JTextField textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(300, 30));
        centerPanel.add(textField1, gbc1);

        JButton btnExtract1 = new JButton("Extract Offer from PCM");
        btnExtract1.setPreferredSize(new Dimension(200, 30));
        centerPanel.add(btnExtract1, gbc1);

        btnExtract.addActionListener(e -> {
            String filePath = textField.getText();
            String excelName = textField1.getText();
            if (filePath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select a PDF file.");
                return;
            }

            if (!excelName.isEmpty()) {
                try {
                    new Discounts().ExtractDiscounts(excelName);
                    new Minutes().ExtractMinutes(excelName);
                    new PostSelling().ExtractPostSelling(excelName);
                    new Trenes().ExtractTrenes(excelName);
                    JOptionPane.showMessageDialog(frame, "Offer is extracted successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No Excel file specified. Proceeding with PDF extraction only.");
            }

            try {
                new Discounts().ExtractDiscounts(filePath);
                new Minutes().ExtractMinutes(filePath);
                new PostSelling().ExtractPostSelling(filePath);
                new Trenes().ExtractTrenes(filePath);
                JOptionPane.showMessageDialog(frame, "Offer is extracted successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage());
            }
        });

        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}