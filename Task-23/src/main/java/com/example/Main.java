import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class InvoiceGenerator extends JFrame implements ActionListener {
    private JTextField customerNameField, contactNumberField, numItemsField;
    private JComboBox<String>[] itemComboBoxes;
    private JTextField[] quantityFields;
    private JButton generateButton;

    private static final String[] items = {"Almond milk", "strawberry", "Rice", "Eggs", "Biscuits", "Lays"};
    private static final double[] prices = {105.0, 35.0, 25.0, 5.5, 10.0, 45.0};  // Sample prices for the items

    public InvoiceGenerator() {
        // Set up the JFrame
        setTitle("Grocery Store Invoice Generator");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Customer details panel
        JPanel customerPanel = new JPanel(new GridLayout(3, 2));
       
        customerPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField();
        customerPanel.add(customerNameField);

        customerPanel.add(new JLabel("Contact Number:"));
        contactNumberField = new JTextField();
        customerPanel.add(contactNumberField);

        customerPanel.add(new JLabel("Number of Items:"));
        numItemsField = new JTextField();
        customerPanel.add(numItemsField);

        add(customerPanel, BorderLayout.NORTH);

        // Items panel
        JPanel itemsPanel = new JPanel(new GridLayout(6, 3));
        itemComboBoxes = new JComboBox[6];
        quantityFields = new JTextField[6];

        for (int i = 0; i < 6; i++) {
            itemsPanel.add(new JLabel("Select Item:"));
            itemComboBoxes[i] = new JComboBox<>(items);
            itemsPanel.add(itemComboBoxes[i]);

            itemsPanel.add(new JLabel("Enter Quantity:"));
            quantityFields[i] = new JTextField();
            itemsPanel.add(quantityFields[i]);
        }

        add(itemsPanel, BorderLayout.CENTER);

        // Generate invoice button
        generateButton = new JButton("Generate Invoice");
        generateButton.addActionListener(this);
        add(generateButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            try {
                String customerName = customerNameField.getText();
                String contactNumber = contactNumberField.getText();
                int numItems = Integer.parseInt(numItemsField.getText());

                String[][] invoiceData = new String[numItems][4];
                double totalAmount = 0;

                for (int i = 0; i < numItems; i++) {
                    String item = (String) itemComboBoxes[i].getSelectedItem();
                    int quantity = Integer.parseInt(quantityFields[i].getText());
                    double price = prices[itemComboBoxes[i].getSelectedIndex()];
                    double amount = quantity * price;
                    totalAmount += amount;

                    invoiceData[i][0] = item;
                    invoiceData[i][1] = String.valueOf(quantity);
                    invoiceData[i][2] = String.valueOf(price);
                    invoiceData[i][3] = String.valueOf(amount);
                }

                // Column names for the table
                String[] columnNames = {"Item", "Quantity", "Price per Unit", "Amount"};

                // Create the JTable with invoice data
                JTable invoiceTable = new JTable(invoiceData, columnNames);

                // Display invoice in a new window
                JDialog invoiceDialog = new JDialog(this, "Invoice", true);
                invoiceDialog.setSize(500, 400);
                invoiceDialog.setLayout(new BorderLayout());

                // Header information
                JPanel headerPanel = new JPanel();
                headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
                headerPanel.add(new JLabel("<html><h1>Grocery Store</h1></html>", SwingConstants.CENTER));
                headerPanel.add(new JLabel("Customer Name: " + customerName, SwingConstants.LEFT));
                headerPanel.add(new JLabel("Contact Number: " + contactNumber, SwingConstants.LEFT));
                invoiceDialog.add(headerPanel, BorderLayout.NORTH);

                // Table scroll pane
                JScrollPane scrollPane = new JScrollPane(invoiceTable);
                invoiceDialog.add(scrollPane, BorderLayout.CENTER);

                // Footer information
                JPanel footerPanel = new JPanel();
                footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
                footerPanel.add(new JLabel("Total Amount: " + totalAmount, SwingConstants.RIGHT));

                invoiceDialog.add(footerPanel, BorderLayout.SOUTH);

                // OK button to close the dialog
                JButton okButton = new JButton("OK");
                okButton.addActionListener(event -> invoiceDialog.dispose());
                footerPanel.add(okButton);

                invoiceDialog.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for quantity and number of items.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvoiceGenerator().setVisible(true));
    }
}
