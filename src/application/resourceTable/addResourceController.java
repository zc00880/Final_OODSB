package application.resourceTable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import application.Main;
import application.WriteExcel;

public class addResourceController {
	private Main main;
	public String name;
	public String quantity;
	public String description;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea resourceName;

	@FXML
	private TextArea resourceQuantity;

	@FXML
	private TextArea resourceDescription;

	@FXML
	private Button saveResource;

	@FXML
	void saveResource(ActionEvent event) throws WriteException, BiffException, IOException {
		name = resourceName.getText(); //Gets text from textFields
		quantity = resourceQuantity.getText();
		description = resourceDescription.getText();
		
		if (quantity.equals(""))
			quantity = "0";

		if (name.equals("")) 
			Main.showMainItems();

		else {
			Resource resource = new Resource(name, quantity, description); //Create new job
			WriteExcel resourceWriter = new WriteExcel();
			resourceWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
			resourceWriter.write(resource); //Adds new row to excel file
			main.showMainItems();
		}
	}

	@FXML
	void initialize() {
		assert resourceName != null : "fx:id=\"resourceName\" was not injected: check your FXML file 'addResource.fxml'.";
		assert resourceQuantity != null : "fx:id=\"resourceQuantity\" was not injected: check your FXML file 'addResource.fxml'.";
		assert resourceDescription != null : "fx:id=\"resourceDescription\" was not injected: check your FXML file 'addResource.fxml'.";
		assert saveResource != null : "fx:id=\"saveResource\" was not injected: check your FXML file 'addResource.fxml'.";
	}
}