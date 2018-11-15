package application.view;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import application.ReadExcel;
import application.WriteExcel;
import dateCalendar.dateSelection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class MainItemsController implements Initializable {
	private Main main;
	ReadExcel reader = new ReadExcel();
	public static int index;

	@FXML
	private ListView<String> jobList;

	@FXML
	private ListView<String> resourceList;

	@FXML
	private TextField textBox;
	
	@FXML
	private DatePicker dateString;

	@FXML //Show selected item
	void displaySelected(MouseEvent event) {
//				int jobNo = jobList.getSelectionModel().getSelectedIndex(); //Returns the index of selected job in the jobList
//				index = (jobNo + 1); //Sets global variable of selected index
//				textBox.setText(String.valueOf(jobNo + 1)); //Shows current selected item
	}
	
	@FXML
	void printOutDate() {
		String date = dateString.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		String month = date.substring(0, 2);
		String day = date.substring(3,5);
		String year = date.substring(6,10);
		dateSelection newDate = new dateSelection(month, day, year);

		textBox.setText(date);
		
	}

	@FXML //Delete job
	void deleteJob(ActionEvent event) throws BiffException, IOException, WriteException {
		WriteExcel jobWriter = new WriteExcel();
		jobWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
		jobWriter.deleteJob(index);
		main.showMainItems();
	}

	@FXML //delete resource
	void deleteResource(ActionEvent event) throws BiffException, IOException, WriteException {
		WriteExcel resourceWriter = new WriteExcel();
		resourceWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
		resourceWriter.deleteResource(index);
		main.showMainItems();
	}


	public int getSelectedIndex() { //Get selected job index to perform functions throughout the program
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

	@FXML
	public void goEditResource() throws IOException {
		main.showEditResourceScene();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			reader.setInputFile("Stewart_Concrete_Finishing.xls");
			ObservableList<String> jobNames = reader.readJobNames(); //Get all job names from excel file
			ObservableList<String> resourceNames = reader.readResourceNames(); //Get all job names from excel file

			jobList.setItems(jobNames); //Show job names
			resourceList.setItems(resourceNames);

			resourceList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {					
					for (int i = 0; i < resourceNames.size(); i++) 
						if (resourceNames.get(i).equals(newValue))
							index = i + 1;
				}
			});

			jobList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {					
					for (int i = 0; i < jobNames.size(); i++) 
						if (jobNames.get(i).equals(newValue)) 
							index = i + 1;
				}
			});
		} 
		catch (IOException | WriteException | BiffException e) {
			e.printStackTrace();
		}
	}
}