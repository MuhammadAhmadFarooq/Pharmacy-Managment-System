package drugpharmamanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor_authController {

    @FXML
    private TextField license_no;

    @FXML
    private TextField l_name;

    @FXML
    private Button loginButton;

    @FXML
    private void loginButtonIsClicked(ActionEvent event) {
        String licenseNo = license_no.getText();
        String lastName = l_name.getText();

        if (!licenseNo.isEmpty() && !lastName.isEmpty()) {
            // Check doctor credentials in the database
            if (authenticateDoctor(licenseNo, lastName)) {
                // Successful login
                showAlert(Alert.AlertType.INFORMATION, "Success", "Login Successful", "Welcome, Doctor!");
                // Load DoctorOptions.fxml and pass the doctor ID
                loadDoctorOptions(licenseNo);
                // Close the login window (optional)
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
            } else {
                // Failed login
                showAlert(Alert.AlertType.ERROR, "Error", "Login Failed", "Invalid License Number or Last Name");
            }
        } else {
            // Empty fields
            showAlert(Alert.AlertType.WARNING, "Warning", "Missing Information", "Please fill in all fields");
        }
    }

    private boolean authenticateDoctor(String licenseNo, String lastName) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT * FROM doctors WHERE license_no = ? AND l_name = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, licenseNo);
            statement.setString(2, lastName);
            ResultSet resultSet = statement.executeQuery();

            // Return true if any row matches both criteria
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadDoctorOptions(String doctorId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorOptions.fxml"));
            Parent root = loader.load();

            DoctorOptionsController controller = loader.getController();
            controller.setDoctorId(doctorId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to Load Doctor Options", "An error occurred while loading the Doctor Options.");
        }
    }
}
