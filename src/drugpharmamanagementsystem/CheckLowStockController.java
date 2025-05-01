package drugpharmamanagementsystem;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckLowStockController {

    @FXML
    private TableView<InventoryItem> tableView;

    @FXML
    private TableColumn<InventoryItem, Integer> itemIdColumn;

    @FXML
    private TableColumn<InventoryItem, String> itemNameColumn;

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
        itemIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        itemNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        unitCostColumn.setCellValueFactory(cellData -> cellData.getValue().unitCostProperty().asObject());
        minStockColumn.setCellValueFactory(cellData -> cellData.getValue().minStockProperty().asObject());
        isLockedColumn.setCellValueFactory(cellData -> cellData.getValue().isLockedProperty().asObject());

        displayLowStockItems();
    }

    private void displayLowStockItems() {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb";
        String user = "root";
        String password = "ahmad2022";
        String query = "SELECT * FROM inventory WHERE quantity-min_stock < 100";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            tableView.getItems().clear(); // Clear previous data

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
            e.printStackTrace();
        }
    }

    public static class InventoryItem {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty name;
        private final SimpleIntegerProperty quantity;
        private final SimpleDoubleProperty unitCost;
        private final SimpleIntegerProperty minStock;
        private final SimpleBooleanProperty isLocked;

        public InventoryItem(int id, String name, int quantity, double unitCost, int minStock, boolean isLocked) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.unitCost = new SimpleDoubleProperty(unitCost);
            this.minStock = new SimpleIntegerProperty(minStock);
            this.isLocked = new SimpleBooleanProperty(isLocked);
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public SimpleIntegerProperty quantityProperty() {
            return quantity;
        }

        public SimpleDoubleProperty unitCostProperty() {
            return unitCost;
        }

        public SimpleIntegerProperty minStockProperty() {
            return minStock;
        }

        public SimpleBooleanProperty isLockedProperty() {
            return isLocked;
        }
    }
}
