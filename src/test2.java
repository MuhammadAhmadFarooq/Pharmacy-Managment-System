import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import drugpharmamanagementsystem.IssueOrdersController;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;
import java.lang.reflect.Field;

class test2 {


	    private IssueOrdersController controller;

	    @BeforeEach
	    void setUp() throws Exception {
	        controller = new IssueOrdersController();

	        // Mock and inject FXML controls manually
	        setField("itemID", createTextField("1001"));
	        setField("quantity", createTextField("50"));
	        setField("supplierID", createTextField("2001"));
	        setField("pharmID", createTextField("3001"));
	        setField("orderButton", new Button());
	    }

	    private TextField createTextField(String value) {
	        TextField tf = new TextField();
	        tf.setText(value);
	        return tf;
	    }

	    private void setField(String fieldName, Object value) throws Exception {
	        Field field = IssueOrdersController.class.getDeclaredField(fieldName);
	        field.setAccessible(true);
	        field.set(controller, value);
	    }

	    @Test
	    void testOrderButtonClicked_withValidInputs() {
	        assertDoesNotThrow(() -> controller.orderButtonClicked(new ActionEvent()));
	    }

	    @Test
	    void testOrderButtonClicked_withInvalidInputs() throws Exception {
	        // Simulate non-numeric input
	        setField("itemID", createTextField("abc"));

	        assertDoesNotThrow(() -> controller.orderButtonClicked(new ActionEvent()));
	    }
	}