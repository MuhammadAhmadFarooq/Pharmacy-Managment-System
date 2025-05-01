package drugpharmamanagementsystem;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientOptionsController implements Initializable {

    @FXML
    private TableView<Prescription> tableView;
    @FXML
    private TableColumn<Prescription, Integer> prescriptionIdColumn;
    @FXML
    private TableColumn<Prescription, Integer> itemIdColumn;
    @FXML
    private TableColumn<Prescription, Integer> patientIdColumn;
    @FXML
    private TableColumn<Prescription, String> detailsColumn;
    @FXML
    private TableColumn<Prescription, Integer> quantityColumn;
    @FXML
    private TableColumn<Prescription, String> isPaidColumn;
    @FXML
    private TableColumn<Prescription, Integer> doctorIdColumn;

    private int patientId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configure the table columns
        prescriptionIdColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionId"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        isPaidColumn.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
        doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
        loadPatientPrescriptions();
    }

    private void loadPatientPrescriptions() {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT * FROM prescriptions WHERE patient_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int prescriptionId = resultSet.getInt("prescription_id");
                int itemId = resultSet.getInt("item_id");
                int patientId = resultSet.getInt("patient_id");
                String details = resultSet.getString("details");
                int quantity = resultSet.getInt("quantity");
                boolean isPaid = resultSet.getBoolean("is_paid");
                int doctorId = resultSet.getInt("doctor_id");

                Prescription prescription = new Prescription(prescriptionId, itemId, patientId, details, quantity, isPaid ? "Yes" : "No", doctorId);
                tableView.getItems().add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to Load Prescriptions", "An error occurred while loading the prescriptions.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class Prescription {
        private final IntegerProperty prescriptionId;
        private final IntegerProperty itemId;
        private final IntegerProperty patientId;
        private final StringProperty details;
        private final IntegerProperty quantity;
        private final StringProperty isPaid;
        private final IntegerProperty doctorId;

        public Prescription(int prescriptionId, int itemId, int patientId, String details, int quantity, String isPaid, int doctorId) {
            this.prescriptionId = new SimpleIntegerProperty(prescriptionId);
            this.itemId = new SimpleIntegerProperty(itemId);
            this.patientId = new SimpleIntegerProperty(patientId);
            this.details = new SimpleStringProperty(details);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.isPaid = new SimpleStringProperty(isPaid);
            this.doctorId = new SimpleIntegerProperty(doctorId);
        }

        public int getPrescriptionId() {
            return prescriptionId.get();
        }

        public IntegerProperty prescriptionIdProperty() {
            return prescriptionId;
        }

        public int getItemId() {
            return itemId.get();
        }

        public IntegerProperty itemIdProperty() {
            return itemId;
        }

        public int getPatientId() {
            return patientId.get();
        }

        public IntegerProperty patientIdProperty() {
            return patientId;
        }

        public String getDetails() {
            return details.get();
        }

        public StringProperty detailsProperty() {
            return details;
        }

        public int getQuantity() {
            return quantity.get();
        }

        public IntegerProperty quantityProperty() {
            return quantity;
        }

        public String getIsPaid() {
            return isPaid.get();
        }

        public StringProperty isPaidProperty() {
            return isPaid;
        }

        public int getDoctorId() {
            return doctorId.get();
        }

        public IntegerProperty doctorIdProperty() {
            return doctorId;
        }
    }
}
