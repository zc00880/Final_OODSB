package application.resourceTable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.ReadExcel;
import application.WriteExcel;
import application.view.MainItemsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class editResourceController {
	private Main main;
	ReadExcel reader = new ReadExcel();
	MainItemsController mainItems = new MainItemsController();
	public static int index;

	@FXML
	private TextArea resourceName;

	@FXML
	private TextArea resourceQuantity;

	@FXML
	private TextArea resourceDescription;

	@FXML
	private Button editResource;

	@FXML
	void editResource(ActionEvent event) throws WriteException, BiffException, IOException {
		String name = resourceName.getText(); //Gets text from textFields to update job
		String quantity = resourceQuantity.getText();
		String description = resourceDescription.getText();
		
		if (quantity.equals(""))
			quantity = "0";

		if (name.equals("")) 
			Main.showMainItems();

		else {

			Resource resource = new Resource(name, quantity, description); //Create new job to replace old job
			WriteExcel jobWriter = new WriteExcel();
			jobWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
			jobWriter.updateResource(resource, index); //Adds new job to selected index
			main.showMainItems();
		}
	}

	@FXML
	public void initialize() throws BiffException, IOException, WriteException{
		reader.setInputFile("Stewart_Concrete_Finishing.xls");
		Resource resource = reader.readResource(mainItems.getSelectedIndex()); //Gets selected job to inject into new window
		index = mainItems.getSelectedIndex();
		resourceName.setText(resource.name); //Sets textFields with selected job attributes to be edited 
		resourceQuantity.setText(resource.quantity);
		resourceDescription.setText(resource.description);
	}
}