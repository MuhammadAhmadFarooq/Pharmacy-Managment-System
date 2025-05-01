package drugpharmamanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PharmacistOptionsController {

    @FXML
    private Button inventoryButton;

    @FXML
    private Button viewPrescriptionsButton;

    @FXML
    private Button issueOrdersButton;

    @FXML
    private Button lowStockButton;

    @FXML
    private Button processPaymentButton;

    private int pharmacistId;

    public void setPharmacistId(int pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    @FXML
    private void inventoryClicked(ActionEvent event) {
        // Open the View Inventory window
        openWindow("View Inventory", "ViewInventory.fxml");
    }

    @FXML
    private void viewPrescriptionsClicked(ActionEvent event) {
        // Open the View Prescriptions window
        openWindow("View Prescriptions", "ViewPrescriptions.fxml");
    }

    @FXML
    private void issueOrdersClicked(ActionEvent event) {
        // Open the Issue Orders window
        openWindow("Issue Orders to Supplier", "IssueOrders.fxml");
    }

    @FXML
    private void stockButtonClicked(ActionEvent event) {
        // Open the Check Low Stock window
        openWindow("Check Low Stock", "CheckLowStock.fxml");
    }

    @FXML
    private void processPaymentClicked(ActionEvent event) {
        // Open the Process Payments window and pass the pharmacist ID
        openProcessPaymentsWindow();
    }

    private void openWindow(String title, String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while opening the window.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            showErrorAlert("Error", "FXML file not found or could not be loaded.");
        }
    }

    private void openProcessPaymentsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProcessPayments.fxml"));
            Parent root = loader.load();

            // Get the controller of the Process Payments window
            ProcessPaymentsController controller = loader.getController();
            controller.setPharmacistId(pharmacistId); // Pass the pharmacist ID

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Process Payments");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error", "An error occurred while opening the window.");
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
