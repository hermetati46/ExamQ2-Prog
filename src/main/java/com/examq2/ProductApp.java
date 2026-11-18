package com.examq2;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ProductApp extends JFrame {

    // GUI Form Components
    private JTextField txtCustomerName;
    private JComboBox<String> cmbProduct;
    private JTextField txtPrice;
    private JButton btnProcess;

    // Menu Components
    private JMenuItem menuItemExit;
    private JMenuItem menuItemProcess;
    private JMenuItem menuItemClear;

    // Available products
    private static final String[] PRODUCTS = {
            "Cozy Nights Monarch",
            "Restonic Barbados",
            "Sealy Phuket"
    };

    // Output log file name
    private static final String DATA_FILE = "data.txt";

     // Constructor sets up the JFrame window, menus, form layout and event listeners.
    public ProductApp() {
        setTitle("Product App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 260);
        setLocationRelativeTo(null); // Center window on screen
        setResizable(false);

        initMenuBar();
        initComponents();
        attachEventListeners();
    }

     // Initializes the JMenuBar with menu system navigation
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        menuItemExit = new JMenuItem("Exit");
        fileMenu.add(menuItemExit);

        // Tools Menu
        JMenu toolsMenu = new JMenu("Tools");
        menuItemProcess = new JMenuItem("Process");
        menuItemClear = new JMenuItem("Clear");
        toolsMenu.add(menuItemProcess);
        toolsMenu.add(menuItemClear);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);
    }

    // Initializes form labels, inputs and layout
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0 Customer Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        mainPanel.add(new JLabel("Customer Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        txtCustomerName = new JTextField(15);
        mainPanel.add(txtCustomerName, gbc);

        // Row 1 Select Product
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.35;
        mainPanel.add(new JLabel("Select Product:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        cmbProduct = new JComboBox<>(PRODUCTS);
        mainPanel.add(cmbProduct, gbc);

        // Row 2 Enter Price
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.35;
        mainPanel.add(new JLabel("Enter Price:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        txtPrice = new JTextField(15);
        mainPanel.add(txtPrice, gbc);

        // Row 3 Process Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(12, 8, 4, 8);
        btnProcess = new JButton("PROCESS");
        mainPanel.add(btnProcess, gbc);

        add(mainPanel);
    }

     // Attaches action listeners to the button and menu items
    private void attachEventListeners() {
        // Process button and Tools
        btnProcess.addActionListener(e -> processSale());
        menuItemProcess.addActionListener(e -> processSale());

        // Tools - Clear resets fields
        menuItemClear.addActionListener(e -> clearForm());

        // File - Exit closes the application
        menuItemExit.addActionListener(e -> System.exit(0));
    }

    private void processSale() {
        String customerName = txtCustomerName.getText().trim();
        String selectedProduct = (String) cmbProduct.getSelectedItem();
        String priceText = txtPrice.getText().trim();

        // Input validation
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the customer's name.", "Input Error", JOptionPane.ERROR_MESSAGE);
            txtCustomerName.requestFocus();
            return;
        }

        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the product price.", "Input Error", JOptionPane.ERROR_MESSAGE);
            txtPrice.requestFocus();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Product price cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                txtPrice.requestFocus();
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric value for the price.", "Input Error", JOptionPane.ERROR_MESSAGE);
            txtPrice.requestFocus();
            return;
        }

        // calculations (ProductCalculations)
        ProductCalculations calculator = new ProductCalculations(price);
        double vat = calculator.GetVat();
        double commission = calculator.GetCommission();
        double total = calculator.GetTotal();

        // Formats output
        String summaryOutput = String.format(
                "Customer Name: %s\n" +
                        "Product: %s\n" +
                        "VAT: R %.1f\n" +
                        "COMMISSION: R %.1f\n" +
                        "TOTAL: R %.1f",
                customerName, selectedProduct, vat, commission, total
        );

        // Display summary in Message Box
        JOptionPane.showMessageDialog(this, summaryOutput, "Message", JOptionPane.INFORMATION_MESSAGE);

        // Save log to data.txt
        saveToFile(summaryOutput);
    }

     // Saves the sales log into data.txt

    private void saveToFile(String summaryOutput) {
        String separator = "*********************************************";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE, true))) {
            writer.write("DATA LOG");
            writer.newLine();
            writer.write(separator);
            writer.newLine();
            writer.write(summaryOutput.replace("\n", System.lineSeparator()));
            writer.newLine();
            writer.write(separator);
            writer.newLine();
            writer.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data to " + DATA_FILE + ": " + ex.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     // Clears all text boxes and resets the product box
    private void clearForm() {
        txtCustomerName.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);
        txtCustomerName.requestFocus();
    }

    // Launches the GUI
    public static void main(String[] args) {
        try {
            // Apply system look and feel for a clean native appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            ProductApp app = new ProductApp();
            app.setVisible(true);
        });
    }
}