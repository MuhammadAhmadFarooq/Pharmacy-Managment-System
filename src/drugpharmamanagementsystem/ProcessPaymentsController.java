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

public class ProcessPaymentsController {

    @FXML
    private TextField patientId;

    @FXML
    private TextField prescriptionId;

    @FXML
    private Button processPaymentButton;

    private int pharmacistId;

    public void setPharmacistId(int pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    @FXML
    private void processPaymentButtonIsClicked(ActionEvent event) {
        String patientID = patientId.getText();
        String prescriptionID = prescriptionId.getText();

        if (!patientID.isEmpty() && !prescriptionID.isEmpty()) {
            if (checkPrescriptionValidity(patientID, prescriptionID)) {
                displayPrescriptionDetails(patientID, prescriptionID);
            } else {
                showErrorAlert("Error", "Payment Processing Failed", "Invalid Patient ID or Prescription ID or Prescription already paid.");
            }
        } else {
            showErrorAlert("Warning", "Missing Information", "Please fill in all fields.");
        }
    }

    private boolean checkPrescriptionValidity(String patientID, String prescriptionID) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT * FROM prescriptions WHERE patient_id = ? AND prescription_id = ? AND is_paid = 0";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientID);
            statement.setString(2, prescriptionID);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void displayPrescriptionDetails(String patientID, String prescriptionID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PrescriptionDetails.fxml"));
            Parent root = loader.load();

            // Get the controller of the prescription details window
            PrescriptionDetailsController controller = loader.getController();
            controller.setPrescriptionDetails(patientID, prescriptionID);
            controller.setPharmacistId(pharmacistId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Prescription Details");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while opening the prescription details window.");
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
