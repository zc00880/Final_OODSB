package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.ReadExcel;
import application.WriteExcel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class MainItemsController implements Initializable {
	private Main main;
	ReadExcel reader = new ReadExcel();
	public static int index = 1;
	public int newIndex;

	@FXML
	private ListView<String> jobList;

	@FXML
	private ListView<String> resourceList;

	@FXML
	private TextField textBox;

	@FXML //Show selected item
	void displaySelected(MouseEvent event) {
		//		int jobNo = jobList.getSelectionModel().getSelectedIndex(); //Returns the index of selected job in the jobList
		//		index = (jobNo + 1); //Sets global variable of selected index
		//		textBox.setText(String.valueOf(jobNo + 1)); //Shows current selected item
	}

	@FXML //Delete job
	void deleteJob(ActionEvent event) throws BiffException, IOException, WriteException {
		WriteExcel jobWriter = new WriteExcel();
		jobWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
		System.out.println(index);
		jobWriter.deleteJob(index);
		System.out.println("Job successfully deleted");
		main.showMainItems();
	}

	@FXML //delete resource
	void deleteResource(ActionEvent event) throws BiffException, IOException, WriteException {
		WriteExcel resourceWriter = new WriteExcel();
		resourceWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
		System.out.println(index);

		resourceWriter.deleteResource(index);
		System.out.println("Resource successfully deleted");
		main.showMainItems();
	}


	public int getSelectedIndex() { //Get selected job index to perform functions throughout the program
		System.out.println(index);
		return index;
	}

	@FXML
	public void goAddJob() throws IOException {
		main.showAddJobScene();
	}

	@FXML
	public void goEditJob() throws IOException {
		main.showEditJobScene();
	}

	@FXML
	public void goAddResource() throws IOException {
		main.showAddResourceScene();
	}

	//	@FXML
	//	public void goEditesource() throws IOException {
	//		main.showAddResourceScene();
	//	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			//String fileName = "test";
			reader.setInputFile("Stewart_Concrete_Finishing.xls");
			ObservableList<String> jobNames = reader.readJobNames(); //Get all job names from excel file
			ObservableList<String> resourceNames = reader.readResourceNames(); //Get all job names from excel file

			jobList.setItems(jobNames); //Show job names
			resourceList.setItems(resourceNames);

			resourceList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {

					for (int i = 0; i < newValue.length(); i++) 
						if (newValue.substring(i, i+1).equals(":")) {
							newValue = newValue.substring(0, i);
							newIndex = Integer.parseInt(newValue);
							index = Integer.parseInt(newValue);
						}
				}
			});
			
			jobList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {
						index = Integer.parseInt(newValue);
						
						//below loop depends on format of cell name
//					for (int i = 0; i < newValue.length(); i++) 
//						if (newValue.substring(i, i+1).equals(":")) {
//							newValue = newValue.substring(0, i);
//							newIndex = Integer.parseInt(newValue);
//							index = Integer.parseInt(newValue);
//						}
				}
			});
		} catch (IOException | WriteException | BiffException e) {
			e.printStackTrace();
		}

	}
}