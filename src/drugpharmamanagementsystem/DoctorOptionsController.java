package drugpharmamanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class DoctorOptionsController {

    @FXML
    private TextField itemId;

    @FXML
    private TextField patientId;

    @FXML
    private TextField prescriptionDetails;

    @FXML
    private TextField quantity;

    private String doctorId;

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @FXML
    private void createNewPrescription(ActionEvent event) {
        String item = itemId.getText();
        String patient = patientId.getText();
        String details = prescriptionDetails.getText();
        String qty = quantity.getText();

        // Validate input
        if (item.isEmpty() || patient.isEmpty() || details.isEmpty() || qty.isEmpty()) {
            showAlert("Error", "Missing Information", "Please fill in all fields");
            return;
        }

        // Insert data into prescriptions table
        String url = "jdbc:mysql://localhost:3306/drugpharmadb";
        String user = "root";
        String password = "ahmad2022"; // Update with your DB password

        String query = "INSERT INTO prescriptions (item_id, patient_id, details, quantity, is_paid, doctor_id) VALUES (?, ?, ?, ?, 0, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, item);
            statement.setString(2, patient);
            statement.setString(3, details);
            statement.setString(4, qty);
            statement.setString(5, doctorId);
            statement.executeUpdate();

            showAlert("Success", "Prescription Created", "New prescription has been created successfully");
        } catch (SQLException e) {
            showAlert("Error", "Database Error", "Failed to create prescription. Please try again.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
