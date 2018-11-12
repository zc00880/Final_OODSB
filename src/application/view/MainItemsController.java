package application.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.ReadExcel;
import application.WriteExcel;
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
	public static int index;
	public String fileName = "test";

	@FXML
	private ListView<String> jobList;

	@FXML
	private TextField textBox;

	@FXML //Show selected item
	void displaySelected(MouseEvent event) {
		int jobNo = jobList.getSelectionModel().getSelectedIndex(); //Returns the index of selected job in the jobList
		index = (jobNo + 1); //Sets global variable of selected index
		textBox.setText(String.valueOf(jobNo + 1)); //Shows current selected item
	}
	
	@FXML //Delete job
    void deleteJob(ActionEvent event) throws BiffException, IOException, WriteException {
    	WriteExcel jobWriter = new WriteExcel();
    	jobWriter.setOutputFile(System.getProperty("user.home") + "/Desktop/" + fileName + ".xls");
    	jobWriter.deleteJob(index);
    	System.out.println("Job successfully deleted");
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
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			String fileName = "test";
			reader.setInputFile(System.getProperty("user.home") + "/Desktop/" + fileName + ".xls");
			ObservableList<String> jobNames = reader.readJobNames(); //Get all job names from excel file
			jobList.setItems(jobNames); //Show job names
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}