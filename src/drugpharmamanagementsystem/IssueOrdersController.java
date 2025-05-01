package drugpharmamanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.*;
import java.time.LocalDateTime;

public class IssueOrdersController {

    @FXML
    private TextField itemID;

    @FXML
    private TextField quantity;

    @FXML
    private TextField supplierID;

    @FXML
    private TextField pharmID;

    @FXML
    private Button orderButton;

    @FXML
	public void orderButtonClicked(ActionEvent event) {
        try {
            int itemIDValue = Integer.parseInt(itemID.getText());
            int quantityValue = Integer.parseInt(quantity.getText());
            int supplierIDValue = Integer.parseInt(supplierID.getText());
            int pharmacistIDValue = Integer.parseInt(pharmID.getText());

            insertOrder(itemIDValue, quantityValue, supplierIDValue, pharmacistIDValue);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Order placed successfully!");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            showErrorAlert("Error", "Please enter valid numbers for Item ID, Quantity, Supplier ID, and Pharmacist ID.");
        }
    }

    private void insertOrder(int itemID, int quantity, int supplierID, int pharmacistID) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "INSERT INTO orders (order_date, item_id, quantity, supplier_id, pharmacist_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            LocalDateTime orderDate = LocalDateTime.now();
            statement.setObject(1, orderDate);
            statement.setInt(2, itemID);
            statement.setInt(3, quantity);
            statement.setInt(4, supplierID);
            statement.setInt(5, pharmacistID);

            statement.executeUpdate();
        } catch (SQLException e) {
            showErrorAlert("Database Error", "Failed to place the order. Please try again.");
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
