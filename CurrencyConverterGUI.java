import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CurrencyConverterGUI extends JFrame implements ActionListener {

    String[] currencies = {"USD", "INR", "EUR", "GBP"};
    JComboBox<String> fromCurrency, toCurrency;
    JTextField amountField;
    JLabel resultLabel;
    JButton convertButton;

    public CurrencyConverterGUI() {
        setTitle("Currency Converter");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        amountField = new JTextField();
        fromCurrency = new JComboBox<>(currencies);
        toCurrency = new JComboBox<>(currencies);
        convertButton = new JButton("Convert");
        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);

        add(new JLabel("Enter Amount:", SwingConstants.CENTER));
        add(amountField);
        add(new JLabel("From Currency:", SwingConstants.CENTER));
        add(fromCurrency);
        add(new JLabel("To Currency:", SwingConstants.CENTER));
        add(toCurrency);
        add(convertButton);
        add(resultLabel);

        convertButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();
            double rate = getExchangeRate(from, to);
            double result = amount * rate;
            resultLabel.setText(String.format("Result: %.2f %s", result, to));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        }
    }

    private double getExchangeRate(String from, String to) {
        // Sample static exchange rates (you can update with live API)
        double usdToInr = 83.15;
        double eurToInr = 89.23;
        double gbpToInr = 105.50;

        double inrToUsd = 1 / usdToInr;
        double eurToUsd = eurToInr / usdToInr;
        double gbpToUsd = gbpToInr / usdToInr;

        // First convert 'from' to INR
        double inrAmount;
        switch (from) {
            case "USD": inrAmount = 83.15; break;
            case "EUR": inrAmount = 89.23; break;
            case "GBP": inrAmount = 105.50; break;
            default: inrAmount = 1.0; break; // INR
        }

        double inr = 1;
        if (!from.equals("INR")) inr = 1 * inrAmount;

        // Then convert INR to 'to'
        double toRate;
        switch (to) {
            case "USD": toRate = inrToUsd; break;
            case "EUR": toRate = 1 / eurToInr; break;
            case "GBP": toRate = 1 / gbpToInr; break;
            default: toRate = 1.0; break; // INR
        }

        return inr * toRate;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverterGUI().setVisible(true));
    }
}
