package application.jobTable;

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

public class editJobController {
	private Main main;
	ReadExcel reader = new ReadExcel();
	MainItemsController mainItems = new MainItemsController();
	public static int index;
	public String fileName = "test";

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
    void editJob(ActionEvent event) throws WriteException, BiffException, IOException {
    	String name = jobName.getText(); //Gets text from textFields to update job
    	String locationString = jobLocation.getText();
    	String description = jobDescription.getText();
    	String estimate = jobEstimate.getText();
    	String startDate = jobStartDate.getText();
    	String endDate = jobEndDate.getText();
    	
    	Job job = new Job(name, locationString, description, estimate, startDate, endDate); //Create new job to replace old job
    	WriteExcel jobWriter = new WriteExcel();
    	jobWriter.setOutputFile(System.getProperty("user.home") + "/Desktop/" + fileName + ".xls");
    	jobWriter.updateJob(job, index); //Adds new job to selected index
    	System.out.println("Job successfully updated");
    	main.showMainItems();
    }
    
    
    @FXML
    public void initialize() throws BiffException, IOException{
    	String fileName = "test";
		reader.setInputFile(System.getProperty("user.home") + "/Desktop/" + fileName + ".xls");
		Job job = reader.readJob(mainItems.getSelectedIndex()); //Gets selected job to inject into new window
		index = mainItems.getSelectedIndex();
		
		jobName.setText(job.name); //Sets textFields with selected job attributes to be edited 
		jobLocation.setText(job.location);
		jobDescription.setText(job.description);
		jobEstimate.setText(job.estimate);
		jobStartDate.setText(job.startDate);
		jobEndDate.setText(job.endDate);
		
    }

}
