package drugpharmamanagementsystem;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.*;

public class ViewPrescriptionsController {

    @FXML
    private TextField prescriptionID;

    @FXML
    private TextField patientID;

    @FXML
    private TableView<Prescription> tableView;

    @FXML
    private TableColumn<Prescription, Integer> prescriptionid;

    @FXML
    private TableColumn<Prescription, Integer> itemID;

    @FXML
    private TableColumn<Prescription, Integer> patientid;

    @FXML
    private TableColumn<Prescription, String> details;

    @FXML
    private TableColumn<Prescription, Integer> quantity;

    @FXML
    private TableColumn<Prescription, Boolean> isPaid;

    @FXML
    private TableColumn<Prescription, Integer> doctorID;

    @FXML
    private void initialize() {
        prescriptionid.setCellValueFactory(cellData -> cellData.getValue().prescriptionIdProperty().asObject());
        itemID.setCellValueFactory(cellData -> cellData.getValue().itemIdProperty().asObject());
        patientid.setCellValueFactory(cellData -> cellData.getValue().patientIdProperty().asObject());
        details.setCellValueFactory(cellData -> cellData.getValue().detailsProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        isPaid.setCellValueFactory(cellData -> cellData.getValue().isPaidProperty());
        doctorID.setCellValueFactory(cellData -> cellData.getValue().doctorIdProperty().asObject());
    }

    @FXML
    private void isClicked(ActionEvent event) {
        String prescriptionIdValue = prescriptionID.getText();
        String patientIdValue = patientID.getText();

        if (!prescriptionIdValue.isEmpty() && !patientIdValue.isEmpty()) {
            displayPrescriptions(prescriptionIdValue, patientIdValue);
        } else {
            showErrorAlert("Error", "Please enter both Prescription ID and Patient ID.");
        }
    }

    private void displayPrescriptions(String prescriptionId, String patientId) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT * FROM prescriptions WHERE prescription_id = ? AND patient_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, prescriptionId);
            statement.setString(2, patientId);

            ResultSet resultSet = statement.executeQuery();

           // tableView.getItems().clear(); // Clear previous data

            while (resultSet.next()) {
                int prescriptionID = resultSet.getInt("prescription_id");
                int itemID = resultSet.getInt("item_id");
                int patientID = resultSet.getInt("patient_id");
                String details = resultSet.getString("details");
                int quantity = resultSet.getInt("quantity");
                boolean isPaid = resultSet.getBoolean("is_paid");
                int doctorID = resultSet.getInt("doctor_id");

                tableView.getItems().add(new Prescription(prescriptionID, itemID, patientID, details, quantity, isPaid, doctorID));
            }

        } catch (SQLException e) {
            showErrorAlert("Database Error", "An error occurred while fetching prescriptions.");
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

    // Define a class to represent prescriptions
    public static class Prescription {
        private final SimpleIntegerProperty prescriptionId;
        private final SimpleIntegerProperty itemId;
        private final SimpleIntegerProperty patientId;
        private final SimpleStringProperty details;
        private final SimpleIntegerProperty quantity;
        private final SimpleBooleanProperty isPaid;
        private final SimpleIntegerProperty doctorId;

        public Prescription(int prescriptionId, int itemId, int patientId, String details, int quantity, boolean isPaid, int doctorId) {
            this.prescriptionId = new SimpleIntegerProperty(prescriptionId);
            this.itemId = new SimpleIntegerProperty(itemId);
            this.patientId = new SimpleIntegerProperty(patientId);
            this.details = new SimpleStringProperty(details);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.isPaid = new SimpleBooleanProperty(isPaid);
            this.doctorId = new SimpleIntegerProperty(doctorId);
        }

        public SimpleIntegerProperty prescriptionIdProperty() {
            return prescriptionId;
        }

        public SimpleIntegerProperty itemIdProperty() {
            return itemId;
        }

        public SimpleIntegerProperty patientIdProperty() {
            return patientId;
        }

        public SimpleStringProperty detailsProperty() {
            return details;
        }

        public SimpleIntegerProperty quantityProperty() {
            return quantity;
        }

        public SimpleBooleanProperty isPaidProperty() {
            return isPaid;
        }

        public SimpleIntegerProperty doctorIdProperty() {
            return doctorId;
        }
    }
}
