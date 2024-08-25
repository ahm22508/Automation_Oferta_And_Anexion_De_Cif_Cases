package OfertaProject;

import javax.swing.*;
import java.awt.*;


public class PDFHandling {

    static public void main(String[] args) {


        JFrame frame = new JFrame();
        frame.setTitle("PDF Offer Extractor");
        frame.setSize(600, 600);
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
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblFilePath = new JLabel("Enter your PDF Path here to edit it");
        centerPanel.add(lblFilePath, gbc);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(500, 30));
        centerPanel.add(textField, gbc);

        JButton btnExtract = new JButton("Extract Offer");
        btnExtract.setPreferredSize(new Dimension(110, 30));
        centerPanel.add(btnExtract, gbc);

        btnExtract.addActionListener(e -> {
            String filePath = textField.getText();
            if (filePath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select a file.");
                return;
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
