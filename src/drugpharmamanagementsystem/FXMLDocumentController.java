package drugpharmamanagementsystem;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLDocumentController {

    @FXML
    private void pharm_is_clicked(ActionEvent event) {
        try {
            // Load the Pharmacist Authorization Screen
            Parent root = FXMLLoader.load(getClass().getResource("pharmacist_auth.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Pharmacist Authorization");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supplier_is_clicked(ActionEvent event) {
        try {
            // Load the Supplier Authorization Screen
            Parent root = FXMLLoader.load(getClass().getResource("supplier_auth.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Supplier Authorization");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void patient_is_clicked(ActionEvent event) {
        try {
            // Load the Patient Authorization Screen
            Parent root = FXMLLoader.load(getClass().getResource("Patient_auth.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Patient Authorization");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void doc_is_clicked(ActionEvent event) {
        try {
            // Load the Doctor Authorization Screen
            Parent root = FXMLLoader.load(getClass().getResource("doctor_auth.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Doctor Authorization");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
