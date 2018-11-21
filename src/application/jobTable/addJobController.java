package application.jobTable;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import application.Main;
import application.ReadExcel;
import application.WriteExcel;
import dateCalendar.dateController;

public class addJobController {
	private Main main;
	public String name;
	public String locationString;
	public String description;
	public String estimate;
	public String startDate;
	public String endDate;
	public String requirements;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void saveJob(ActionEvent event) throws WriteException, BiffException, IOException {
    	name = jobName.getText(); //Gets text from textFields
    	locationString = jobLocation.getText();
    	description = jobDescription.getText();
    	estimate = jobEstimate.getText();
    	startDate = jobStartDateDP.getValue().format(DateTimeFormatter.ofPattern("MM/dd/uuuu"));
    	endDate = jobEndDateDP.getValue().format(DateTimeFormatter.ofPattern("MM/dd/uuuu"));
    	requirements = jobRequirements.getText();
    	//dateController.addDates(startDate,endDate);
    	dateController dc = new dateController();
    	dc.addDates(startDate, endDate);
    	Job job = new Job(name, locationString, description, estimate, startDate, endDate, requirements); //Create new job
    	ReadExcel.allJobs.add(job);
    	WriteExcel jobWriter = new WriteExcel();
    	jobWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
    	jobWriter.write(job); //Adds new row to excel file
    	main.showMainItems();
    }

    @FXML
    void initialize() {
        assert jobName != null : "fx:id=\"jobName\" was not injected: check your FXML file 'addJob.fxml'.";
        assert jobLocation != null : "fx:id=\"jobLocation\" was not injected: check your FXML file 'addJob.fxml'.";
        assert jobDescription != null : "fx:id=\"jobDescription\" was not injected: check your FXML file 'addJob.fxml'.";
        assert jobEstimate != null : "fx:id=\"jobEstimate\" was not injected: check your FXML file 'addJob.fxml'.";
        assert jobStartDate != null : "fx:id=\"jobStartDate\" was not injected: check your FXML file 'addJob.fxml'.";
        assert jobEndDate != null : "fx:id=\"jobEndDate\" was not injected: check your FXML file 'addJob.fxml'.";
        assert saveJob != null : "fx:id=\"saveJob\" was not injected: check your FXML file 'addJob.fxml'.";
        assert jobRequirements != null : "fx:id=\"jobRequirements\" was not injected: check your FXML file 'addJob.fxml'.";

    }
}
