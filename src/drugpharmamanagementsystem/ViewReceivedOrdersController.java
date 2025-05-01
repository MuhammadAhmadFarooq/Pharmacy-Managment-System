package drugpharmamanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ViewReceivedOrdersController implements Initializable {

    @FXML
    private TableView<Order> tableView;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, Integer> itemIdColumn;

    @FXML
    private TableColumn<Order, Integer> quantityColumn;

    @FXML
    private TableColumn<Order, Integer> supplierIdColumn;

    @FXML
    private TableColumn<Order, Integer> pharmacistIdColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        pharmacistIdColumn.setCellValueFactory(new PropertyValueFactory<>("pharmacistId"));

        loadDataIntoTableView();
    }

    private void loadDataIntoTableView() {
        String url = "jdbc:mysql://localhost:3306/drugpharmadb"; // Update with your database URL
        String username = "root"; // Update with your database username
        String password = "ahmad2022"; // Update with your database password

        String query = "SELECT * FROM orders";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                String orderDate = resultSet.getString("order_date");
                int itemId = resultSet.getInt("item_id");
                int quantity = resultSet.getInt("quantity");
                int supplierId = resultSet.getInt("supplier_id");
                int pharmacistId = resultSet.getInt("pharmacist_id");

                Order order = new Order(orderId, orderDate, itemId, quantity, supplierId, pharmacistId);
                tableView.getItems().add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Order {
        private final SimpleIntegerProperty orderId;
        private final SimpleStringProperty orderDate;
        private final SimpleIntegerProperty itemId;
        private final SimpleIntegerProperty quantity;
        private final SimpleIntegerProperty supplierId;
        private final SimpleIntegerProperty pharmacistId;

        public Order(int orderId, String orderDate, int itemId, int quantity, int supplierId, int pharmacistId) {
            this.orderId = new SimpleIntegerProperty(orderId);
            this.orderDate = new SimpleStringProperty(orderDate);
            this.itemId = new SimpleIntegerProperty(itemId);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.supplierId = new SimpleIntegerProperty(supplierId);
            this.pharmacistId = new SimpleIntegerProperty(pharmacistId);
        }

        public int getOrderId() {
            return orderId.get();
        }

        public SimpleIntegerProperty orderIdProperty() {
            return orderId;
        }

        public String getOrderDate() {
            return orderDate.get();
        }

        public SimpleStringProperty orderDateProperty() {
            return orderDate;
        }

        public int getItemId() {
            return itemId.get();
        }

        public SimpleIntegerProperty itemIdProperty() {
            return itemId;
        }

        public int getQuantity() {
            return quantity.get();
        }

        public SimpleIntegerProperty quantityProperty() {
            return quantity;
        }

        public int getSupplierId() {
            return supplierId.get();
        }

        public SimpleIntegerProperty supplierIdProperty() {
            return supplierId;
        }

        public int getPharmacistId() {
            return pharmacistId.get();
        }

        public SimpleIntegerProperty pharmacistIdProperty() {
            return pharmacistId;
        }
    }
}
