package application.view;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.ReadExcel;
import application.WriteExcel;
import application.jobTable.Job;
import application.dateCalendar.dateController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class MainItemsController implements Initializable {
	private Main main;
	ReadExcel reader = new ReadExcel();
	public static int index = 0;
	public static String dateIndex;
	ObservableList<String> dayInfo = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> jobList;

	@FXML
	private ListView<String> resourceList;

	@FXML
	public ListView<String> dateList;

	@FXML
	private DatePicker dateString;

	@FXML //Show selected item
	void displaySelected(MouseEvent event) {
	}

	@FXML
	public
	void setDate() {
		dateList.getItems().clear();
		String date = dateString.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		String month = date.substring(0, 2);
		String day = date.substring(3,5);
		String year = date.substring(6,10);
		String jobDateTotal = year + "-" +month +"-" +day;
		boolean done = false;
		int j=0;
		for(int i=0;i<ReadExcel.allJobs.size();i++) {
			String s = ReadExcel.allJobs.get(i).startDate;
			String e = ReadExcel.allJobs.get(i).endDate;

			String monthstart = s.substring(0, 2);
			String daystart = s.substring(3,5);
			String yearstart = s.substring(6,10);
			String completestart = yearstart + "-" + monthstart+ "-" + daystart;

			String monthend = e.substring(0, 2);
			String dayend = e.substring(3,5);
			String yearend = e.substring(6,10);
			String completeend = yearend + "-" + monthend + "-" + dayend;

			LocalDate start = LocalDate.parse(completestart);
			LocalDate end = LocalDate.parse(completeend);
			ArrayList<LocalDate> totalDates = new ArrayList<>();
			while (!start.isAfter(end)) {
				totalDates.add(start);
				start = start.plusDays(1);
			}
			j=0;
			done =false;
			while(j < totalDates.size() && done == false) {
				String temp = totalDates.get(j).toString();
				if(temp.equals(jobDateTotal)) {
					String x = ReadExcel.allJobs.get(i).name;
					if(!dayInfo.contains(x)) {
						dayInfo.add(x);
					}
					done = true;

				}

				j++;
			}
		}
		dateList.setItems(dayInfo);

	}
	@FXML
	public void assignResource() throws IOException {
		int index1 = getSelectedIndex();
		if (dateIndex == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "No job selected.", null);
			alert.setTitle("Job ERROR!");
			alert.showAndWait();
		}
		else {
			main.showAssignResourceScene();
			dateIndex = null;
		}
	}
	@FXML //Delete job
	void deleteJob(ActionEvent event) throws BiffException, IOException, WriteException {
		WriteExcel jobWriter = new WriteExcel();
		jobWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
		if (index == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "No job selected.", null);
			alert.setTitle("Job ERROR!");
			alert.showAndWait();
		}
		else {
			jobWriter.deleteJob(index);
			//ReadExcel re = new ReadExcel();
			//re.readResourceNames();	
			main.showMainItems();
		}
	}

	@FXML //delete resource
	void deleteResource(ActionEvent event) throws BiffException, IOException, WriteException {
		WriteExcel resourceWriter = new WriteExcel();
		resourceWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
		if (index == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "No resource selected.", null);
			alert.setTitle("Resource ERROR!");
			alert.showAndWait();
		}
		else {
			resourceWriter.deleteResource(index);
			main.showMainItems();
		}
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
		if (index == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "No job selected.", null);
			alert.setTitle("Job ERROR!");
			alert.showAndWait();
		}
		else {
			main.showEditJobScene();
		}
	}

	@FXML
	public void goAddResource() throws IOException {
		main.showAddResourceScene();
	}

	@FXML
	public void goEditResource() throws IOException {
		if (index == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "No resource selected.", null);
			alert.setTitle("resource ERROR!");
			alert.showAndWait();
		}
		else {
			main.showEditResourceScene();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			ReadExcel.allJobs.clear();
			ReadExcel.allResources.clear();
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
			dateList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {					
					for (int i = 0; i < dayInfo.size(); i++) 
						if (dayInfo.get(i).equals(newValue)) {
							index = i + 1;
							dateIndex = dateList.getSelectionModel().getSelectedItems().toString();
						}

				}	
			});
		} 
		catch (IOException | WriteException | BiffException e) {
			e.printStackTrace();
		}
	}
}