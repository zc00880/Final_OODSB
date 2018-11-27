package application.jobTable;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import application.ReadExcel;
import application.WriteExcel;
import application.view.MainItemsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class editJobController {
	private Main main;
	ReadExcel reader = new ReadExcel();
	MainItemsController mainItems = new MainItemsController();
	public static int index;

	@FXML
	private TextArea jobName;

	@FXML
	private TextArea jobLocation;

	@FXML
	private TextArea jobDescription;

	@FXML
	private TextArea jobEstimate;

	@FXML
	private TextArea jobStartDate;

	@FXML
	private DatePicker jobStartDateDP;

	@FXML
	private TextArea jobEndDate;

	@FXML
	private DatePicker jobEndDateDP;

	@FXML
	private TextArea jobRequirements;

	@FXML
	private Button saveJob;

	@FXML
	void editJob(ActionEvent event) throws WriteException, BiffException, IOException {
		String name = jobName.getText(); //Gets text from textFields to update job
		if (name.equals(""))
			main.showMainItems();

		else {
			String locationString = jobLocation.getText();
			String description = jobDescription.getText();
			String estimate = jobEstimate.getText();
			String startDate = jobStartDateDP.getValue().format(DateTimeFormatter.ofPattern("MM/dd/uuuu"));
			String endDate = jobEndDateDP.getValue().format(DateTimeFormatter.ofPattern("MM/dd/uuuu"));
			String requirements = jobRequirements.getText();
			Date date1;
			Date date2;
			Boolean isBefore = true;
			try {
				date1 = new SimpleDateFormat("MM/dd/uuuu").parse(startDate);
				date2 = new SimpleDateFormat("MM/dd/uuuu").parse(endDate);
				if(date1.after(date2)) {
					isBefore = false;
				}
			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(isBefore == false) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Start Date is after End Date!", null);
				alert.setTitle("Date ERROR!");
				alert.showAndWait();
			}else {



				Job job = new Job(name, locationString, description, estimate, startDate, endDate, requirements); //Create new job to replace old job
				ReadExcel.allJobs.remove(index-1);
				ReadExcel.allJobs.add(job);
				WriteExcel jobWriter = new WriteExcel();
				jobWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
				jobWriter.updateJob(job, index); //Adds new job to selected index
				main.showMainItems();
			}
		}
	}

	@FXML
	public void initialize() throws BiffException, IOException{
		reader.setInputFile("Stewart_Concrete_Finishing.xls");
		Job job = reader.readJob(mainItems.getSelectedIndex()); //Gets selected job to inject into new window
		index = mainItems.getSelectedIndex();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
		LocalDate startDateLD = LocalDate.parse(job.startDate, formatter);
		LocalDate endDateLD = LocalDate.parse(job.endDate, formatter);

		jobName.setText(job.name); //Sets textFields with selected job attributes to be edited 
		jobLocation.setText(job.location);
		jobDescription.setText(job.description);
		jobEstimate.setText(job.estimate);
		jobStartDateDP.setValue(startDateLD);
		jobEndDateDP.setValue(endDateLD);
		jobRequirements.setText(job.requirements);
	}
}