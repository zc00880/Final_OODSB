package application.jobTable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import application.Main;
import application.WriteExcel;

public class addJobController {
	private Main main;
	public String name;
	public String locationString;
	public String description;
	public String estimate;
	public String startDate;
	public String endDate;

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
    private TextArea jobEndDate;

    @FXML
    private Button saveJob;

    @FXML
    void saveJob(ActionEvent event) throws WriteException, BiffException, IOException {
    	name = jobName.getText(); //Gets text from textFields
    	locationString = jobLocation.getText();
    	description = jobDescription.getText();
    	estimate = jobEstimate.getText();
    	startDate = jobStartDate.getText();
    	endDate = jobEndDate.getText();
    	
    	Job job = new Job(name, locationString, description, estimate, startDate, endDate); //Create new job
    	WriteExcel jobWriter = new WriteExcel();
    	jobWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
    	jobWriter.write(job); //Adds new row to excel file
    	System.out.println("Job successfully added");
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

    }
}
