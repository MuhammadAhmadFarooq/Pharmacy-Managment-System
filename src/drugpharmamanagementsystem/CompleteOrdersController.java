package drugpharmamanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompleteOrdersController {

    @FXML
    private TextField orderID;
    private TextField itemID;
    @FXML
    private Button completeOrderButton;
   

    @FXML
    private void completeIsClicked(ActionEvent event) {
        String orderId = orderID.getText().trim();

        if (!orderId.isEmpty()) {
            try {
                // Check if the order ID exists in the orders table
                if (orderExists(orderId)) {
                    // Get the item ID corresponding to the order ID
                    int itemId = getItemIdForOrder(orderId);

                    if (itemId != -1) {
                        // Update inventory if item exists
                        updateInventory(itemId, orderId);
                    } else {
                        // Add new item to inventory if item does not exist
                        createNewItemInInventory(orderId);
                    }

                    // Delete the order from the orders table
                    deleteOrder(orderId);

                    // Show success message
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Order Completed", "Order completed successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Order ID Not Found", "The specified order ID was not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Database Error", "An error occurred while completing the order.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Empty Order ID", "Please enter the order ID.");
        }
    }

    private boolean orderExists(String orderId) throws SQLException {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/drugpharmadb", "root", "zoinks!!12");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    private int getItemIdForOrder(String orderId) throws SQLException {
        String query = "SELECT item_id FROM orders WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/drugpharmadb", "root", "zoinks!!12");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getInt("item_id") : -1;
        }
    }

    private void updateInventory(int itemId, String orderId) throws SQLException {
        String query = "UPDATE inventory SET quantity = quantity + (SELECT quantity FROM orders WHERE order_id = ?) WHERE item_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/drugpharmadb", "root", "zoinks!!12");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderId);
            statement.setInt(2, itemId);
            statement.executeUpdate();
        }
    }

    private void createNewItemInInventory(String orderId) throws SQLException {
        String query = "INSERT INTO inventory (item_id, quantity) SELECT item_id, quantity FROM orders WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/drugpharmadb", "root", "zoinks!!12");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderId);
            statement.executeUpdate();
        }
    }

    private void deleteOrder(String orderId) throws SQLException {
        String query = "DELETE FROM orders WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/drugpharmadb", "root", "zoinks!!12");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, orderId);
            statement.executeUpdate();
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
