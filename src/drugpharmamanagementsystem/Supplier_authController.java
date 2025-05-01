package drugpharmamanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class Supplier_authController {

    @FXML
    private TextField supplierID;

    @FXML
    private TextField supplierName;

    @FXML
    private Button loginButton;

    @FXML
    private void loginIsClicked(ActionEvent event) {
        String id = supplierID.getText();
        String name = supplierName.getText();

        if (!id.isEmpty() && !name.isEmpty()) {
            // Check supplier credentials in the database
            if (authenticateSupplier(id, name)) {
                // Successful login, load SupplierOptions.fxml
                loadSupplierOptions();
            } else {
                // Failed login
                showAlert(Alert.AlertType.ERROR, "Error", "Login Failed", "Invalid Supplier ID or Name");
            }
        } else {
            // Empty fields
            showAlert(Alert.AlertType.WARNING, "Warning", "Missing Information", "Please fill in all fields");
        }
    }

    private boolean authenticateSupplier(String id, String name) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT * FROM suppliers WHERE supplier_id = ? AND supplier_name = ?";
        
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, name);
            ResultSet resultSet = statement.executeQuery();
            
            // Return true if any row matches both criteria
            return resultSet.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadSupplierOptions() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SupplierOptions.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to Load Supplier Options", "An error occurred while loading the Supplier Options.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
