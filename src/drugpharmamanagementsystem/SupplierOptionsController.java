package drugpharmamanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplierOptionsController {

    @FXML
    private Button ordersButton;

    @FXML
    private Button completeOrdersButton;

    @FXML
    private void ordersClicked() {
        loadFXML("ViewReceivedOrders.fxml", "Received Orders");
    }

    @FXML
    private void completeOrdersIsClicked() {
        loadFXML("CompleteOrders.fxml", "Complete Orders");
    }

    private void loadFXML(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            showErrorAlert("Error", "Failed to load " + title + ".");
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
