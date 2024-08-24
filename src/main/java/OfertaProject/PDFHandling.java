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

        // Create the top panel for the title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Title centered in the top
        JLabel lblTitle = new JLabel("Welcome To Our App", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));  // Set font and style for title
        topPanel.add(lblTitle);
        frame.add(topPanel, BorderLayout.NORTH);  // Add the top panel to the top of the frame

        JPanel centerPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Load the image
                ImageIcon VFLogo = new ImageIcon("C:\\PdfProject\\vodafone.png");

                // Cast Graphics to Graphics2D to enable advanced features
                Graphics2D g2d = (Graphics2D) g.create();

                // Set the transparency level (0.0 = fully transparent, 1.0 = fully opaque)
                float alpha = 0.3f; // Adjust this value for desired transparency (e.g., 0.3 for pale effect)
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

                // Draw the image with transparency
                g2d.drawImage(VFLogo.getImage(), 0, 0, getWidth(), getHeight(), this);

                // Dispose the Graphics2D object
                g2d.dispose();
            }
        };
        centerPanel.setLayout(new GridBagLayout());  // Use GridBagLayout to center components

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;  // Arrange components vertically
        gbc.insets = new Insets(20, 0, 20, 0);  // Add spacing between components
        gbc.anchor = GridBagConstraints.CENTER;

        // Add JLabel for file path
        JLabel lblFilePath = new JLabel("Enter your PDF Path here to edit it");
        centerPanel.add(lblFilePath, gbc);

        // Add JTextField for the input
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(500, 30));
        centerPanel.add(textField, gbc);

        // Add JButton for extracting the offer
        JButton btnExtract = new JButton("Extract Offer");
        btnExtract.setPreferredSize(new Dimension(110, 30));
        centerPanel.add(btnExtract, gbc);

        // Button action logic
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

        // Add the center panel to the frame
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
