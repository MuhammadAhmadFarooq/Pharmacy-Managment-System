package drugpharmamanagementsystem;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.*;

public class ViewInventoryController {

    @FXML
    private TextField itemIdField;

    @FXML
    private TableView<InventoryItem> tableView;

    @FXML
    private TableColumn<InventoryItem, Integer> idColumn;

    @FXML
    private TableColumn<InventoryItem, String> nameColumn;

    @FXML
    private TableColumn<InventoryItem, Integer> quantityColumn;

    @FXML
    private TableColumn<InventoryItem, Double> unitCostColumn;

    @FXML
    private TableColumn<InventoryItem, Integer> minStockColumn;

    @FXML
    private TableColumn<InventoryItem, Boolean> isLockedColumn;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        unitCostColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getUnitCost()).asObject());
        minStockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMinStock()).asObject());
        isLockedColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isLocked()));
    }

    @FXML
    private void enterClicked(ActionEvent event) {
        String itemId = itemIdField.getText();

        if (!itemId.isEmpty()) {
            displayInventory(itemId);
        } else {
            showErrorAlert("Error", "Please enter an Item ID.");
        }
    }

    private void displayInventory(String itemId) {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your DB details
        String user = "root"; // Update with your DB username
        String password = "ahmad2022"; // Update with your DB password

        String query = "SELECT * FROM inventory WHERE item_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, itemId);
            ResultSet resultSet = statement.executeQuery();

           // tableView.getItems().clear(); // Clear previous data

            while (resultSet.next()) {
                int id = resultSet.getInt("item_id");
                String name = resultSet.getString("item_name");
                int quantity = resultSet.getInt("quantity");
                double unitCost = resultSet.getDouble("unit_cost");
                int minStock = resultSet.getInt("min_stock");
                boolean isLocked = resultSet.getBoolean("is_locked");

                tableView.getItems().add(new InventoryItem(id, name, quantity, unitCost, minStock, isLocked));
            }

        } catch (SQLException e) {
            showErrorAlert("Database Error", "An error occurred while fetching inventory details.");
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

    // Define a class to represent inventory items
    public static class InventoryItem {
        private final int id;
        private final String name;
        private final int quantity;
        private final double unitCost;
        private final int minStock;
        private final boolean isLocked;

        public InventoryItem(int id, String name, int quantity, double unitCost, int minStock, boolean isLocked) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.unitCost = unitCost;
            this.minStock = minStock;
            this.isLocked = isLocked;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getUnitCost() {
            return unitCost;
        }

        public int getMinStock() {
            return minStock;
        }

        public boolean isLocked() {
            return isLocked;
        }
    }
}
