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

public class Patient_authController {

    @FXML
    private TextField patientId;

    @FXML
    private TextField Cnic;

    @FXML
    private Button loginButton;

    @FXML
    private void loginIsClicked(ActionEvent event) {
        String id = patientId.getText();
        String cnic = Cnic.getText();

        if (!id.isEmpty() && !cnic.isEmpty()) {
            // Check patient credentials in the database
            if (authenticatePatient(id, cnic)) {
                // Successful login, load PatientOptions.fxml and pass patient ID
                loadPatientOptions(Integer.parseInt(id));
            } else {
                // Failed login
                showAlert(Alert.AlertType.ERROR, "Error", "Login Failed", "Invalid Patient ID or CNIC");
            }
        } else {
            // Empty fields
            showAlert(Alert.AlertType.WARNING, "Warning", "Missing Information", "Please fill in all fields");
        }
    }

    private boolean authenticatePatient(String id, String cnic) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT * FROM patients WHERE patient_id = ? AND CNIC = ?";
        
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, cnic);
            ResultSet resultSet = statement.executeQuery();
            
            // Return true if any row matches both criteria
            return resultSet.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadPatientOptions(int patientId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientOptions.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the patient ID
            PatientOptionsController controller = loader.getController();
            controller.setPatientId(patientId);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to Load Patient Options", "An error occurred while loading the Patient Options.");
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
