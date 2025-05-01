package drugpharmamanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.sql.*;

public class PrescriptionDetailsController {

    @FXML
    private Label itemIdLabel;

    @FXML
    private Label patientIdLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Button proceedWithPaymentButton;

    private String patientID;
    private String prescriptionID;
    private int pharmacistId;

    public void setPrescriptionDetails(String patientID, String prescriptionID) {
        this.patientID = patientID;
        this.prescriptionID = prescriptionID;
        loadPrescriptionDetails();
    }

    public void setPharmacistId(int pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    private void loadPrescriptionDetails() {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT * FROM prescriptions WHERE patient_id = ? AND prescription_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientID);
            statement.setString(2, prescriptionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                itemIdLabel.setText(String.valueOf(resultSet.getInt("item_id")));
                patientIdLabel.setText(patientID);
                detailsLabel.setText(resultSet.getString("details"));
                quantityLabel.setText(String.valueOf(resultSet.getInt("quantity")));
                calculateAndDisplayTotalCost(resultSet.getInt("item_id"), resultSet.getInt("quantity"));
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Prescription details not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading the prescription details.");
        }
    }

    private void calculateAndDisplayTotalCost(int itemId, int quantity) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT unit_cost FROM inventory WHERE item_id = ?";
        double totalCost = 0;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, itemId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalCost = resultSet.getDouble("unit_cost") * quantity;
                totalCostLabel.setText(String.format("$%.2f", totalCost)); // Format the total cost as currency
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while calculating the total cost.");
        }
    }

    @FXML
    private void proceedWithPayment() {
        double totalCost = Double.parseDouble(totalCostLabel.getText().substring(1)); // Remove the '$' sign

        if (totalCost > 0 && checkAndDeductAccountBalance(totalCost)) {
            updatePrescriptionStatus();
            logTransaction(totalCost);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment processed successfully.");
            closeWindow();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Insufficient balance or error in processing payment.");
        }
    }

    private boolean checkAndDeductAccountBalance(double totalCost) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT * FROM accounts WHERE user_id = ?";
        boolean success = false;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(patientID));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                double accountBalance = resultSet.getDouble("account_balance");

                if (accountBalance >= totalCost) {
                    int accountId = resultSet.getInt("account_id");
                    updateAccountBalance(accountId, accountBalance - totalCost);
                    success = true;
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    private void updateAccountBalance(int accountId, double newBalance) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "UPDATE accounts SET account_balance = ? WHERE account_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, newBalance);
            statement.setInt(2, accountId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePrescriptionStatus() {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "UPDATE prescriptions SET is_paid = 1 WHERE prescription_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(prescriptionID));
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void logTransaction(double transactionAmount) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb";
                String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "INSERT INTO transactions (transaction_timestamp, account_id, pharmacist_id, transaction_amount) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getCurrentTimestamp());
            statement.setInt(2, Integer.parseInt(patientID));
            statement.setInt(3, pharmacistId);
            statement.setDouble(4, transactionAmount);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) proceedWithPaymentButton.getScene().getWindow();
        stage.close();
    }
}