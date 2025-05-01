package drugpharmamanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Pharmacist_authController {

    @FXML
    private TextField license_no;

    @FXML
    private TextField l_name;

    private int pharmacistId;

    @FXML
    private void loginClicked(ActionEvent event) {
        try {
            int licenseNumber = Integer.parseInt(license_no.getText());
            String lastName = l_name.getText();

            if (authenticatePharmacist(licenseNumber, lastName)) {
                loadPharmacistOptions();
            } else {
                showErrorAlert("Invalid Login", "License Number or Last Name is incorrect.");
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid Input", "License Number must be a number.");
        }
    }

    private boolean authenticatePharmacist(int licenseNumber, String lastName) {
    String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
    String user = "root"; // Update with your DB username
    String password = "ahmad2022"; // Update with your DB password

    String query = "SELECT * FROM pharmacists WHERE license_no = ? AND l_name = ?";

    try (Connection connection = DriverManager.getConnection(url, user, password);
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, licenseNumber);
        statement.setString(2, lastName);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            pharmacistId = resultSet.getInt("license_no");
            return true;
        } else {
            return false;
        }

    } catch (Exception e) {
        e.printStackTrace();
        showErrorAlert("Database Error", "An error occurred while connecting to the database.");
        return false;
    }
}

    private void loadPharmacistOptions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pharmacistOptions.fxml"));
            Parent root = loader.load();

            // Get the controller of the Pharmacist Options window
            PharmacistOptionsController controller = loader.getController();
            controller.setPharmacistId(pharmacistId); // Pass the pharmacist ID

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pharmacist Options");
            stage.show();

            // Close the current login window
            Stage currentStage = (Stage) license_no.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Loading Error", "An error occurred while loading the options screen.");
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
