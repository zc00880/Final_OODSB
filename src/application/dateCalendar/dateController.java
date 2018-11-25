package application.dateCalendar;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import application.ReadExcel;
import application.WriteExcel;
import application.jobTable.Job;
import application.view.MainItemsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class dateController {
	public ArrayList<String> jobDatesStart = new ArrayList<String>();
	public ArrayList<String> jobDatesEnd = new ArrayList<String>();
	ArrayList<String> resourceNames = new ArrayList<String>();
	ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
	ObservableList<String> dayInfo = FXCollections.observableArrayList();
	public String myStartDate;
	public String myEndDate;
	public String myResourceName="";
	public ArrayList<String> resourceNameChecker = new ArrayList<String>();

	@FXML
	private Text jobName;

	@FXML
	private Text jobLocation;

	@FXML
	private Text jobEstimate;

	@FXML
	private Text jobStartDate;

	@FXML
	private Text jobEndDate;

	@FXML
	private Text jobRequirements;

	@FXML
	private TextArea availableResources;

	@FXML
	private CheckBox checkboxResource;

	@FXML
	private VBox checkboxVbox;
	
	@FXML
	private ScrollPane checkboxScrollPane;

	@FXML
	private Button addResourcetoJob;
	
	@FXML
	void addvboxtoscrollpane (ActionEvent event) {
		checkboxScrollPane.setContent(checkboxVbox);
		checkboxScrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		checkboxScrollPane.maxHeight(Integer.MAX_VALUE);
	}
	
	@FXML
	void addResourcetoJob(ActionEvent event) throws WriteException, BiffException, IOException {
		String temp = jobRequirements.getText();
		for(int i=0; i < checkBoxes.size();i++) {
			temp = jobRequirements.getText();
			if(checkBoxes.get(i).isSelected()) {
				jobRequirements.setText(temp + "\n" + checkBoxes.get(i).getText().toString());
				if(myResourceName.isEmpty()) {
					myResourceName = checkBoxes.get(i).getText().toString();
				}else {
					myResourceName = myResourceName + ","+ checkBoxes.get(i).getText().toString();
				}
				checkboxVbox.getChildren().remove(i);
				checkBoxes.remove(i);

				String s = myStartDate;
				String e = myEndDate;

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
				ArrayList<String> totalDatesString = new ArrayList<>();
				while (!start.isAfter(end)) {
					int month = start.getMonthValue();
					int day = start.getDayOfMonth();
					int year = start.getYear();
					String total = month +"/"+day+"/"+year;
					totalDates.add(start);
					totalDatesString.add(total);
					start = start.plusDays(1);
				}


				WriteExcel we = new WriteExcel();
				we.writeResourceInUse(totalDatesString.toString(), myResourceName);
			}
		}
	}

	@FXML
	public void addDates(String start, String end) {
		jobDatesStart.add(start);
		jobDatesEnd.add(end);
	}
	
	public ArrayList<String> resourceCheck() {
		ArrayList<String> resourceChecker = new ArrayList<String>();
		try {
			WriteExcel jobWriter = new WriteExcel();
			jobWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
			String file = "Stewart_Concrete_Finishing.xls";
			Workbook w = Workbook.getWorkbook(new File(file));
			Sheet sheet = w.getSheet(1);
			for (int i = 1; i < sheet.getRows(); i++) {
				String temp ="";
				for(int j=0; j < ReadExcel.allJobs.size();j++) {
					Cell firstCell = sheet.getCell(3,i);
					Cell cell = sheet.getCell(j+3, i);
					if(!cell.getContents().isEmpty()) {
						if(!temp.isEmpty())
							temp = temp +" ,"+ cell.getContents();
						else
							temp = cell.getContents();
					}
					if(firstCell.getContents().isEmpty()) {
						temp = firstCell.getContents();
					}

				}
				Cell cell2 = sheet.getCell(1,i);
				resourceNameChecker.add(cell2.getContents());
				resourceChecker.add(temp);
			}



		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return resourceChecker;
	}
	@FXML
	public int getIndex() {
		
		int index = 0;
		String search = MainItemsController.dateIndex;
		search = search.substring(1, search.length()-1);
		for(int i=0; i < ReadExcel.allJobs.size();i++) {
			if(ReadExcel.allJobs.get(i).name.toString().equals(search)) {
				index = i;
			}
			
		}
		
		
		return index;
	}
	@FXML
	public void initialize() throws BiffException, IOException{
		ArrayList<String> inuse = new ArrayList<String>();
		ArrayList<Integer> notinuse = new ArrayList<Integer>();
		
		inuse.clear();
		notinuse.clear();
		resourceNames.clear();
		checkBoxes.clear();
		resourceNameChecker.clear();
		int myIndex = getIndex();
		
		Job j = ReadExcel.allJobs.get(myIndex);
		jobName.setText("Job Name: " + j.name);
		jobLocation.setText("Location: " + j.location);
		jobEstimate.setText("Estimate: " + j.estimate);
		jobStartDate.setText("Start Date: " + j.startDate);
		jobEndDate.setText("End Date: " + j.endDate);
		String jobreq = "Requirements: " + j.requirements + "\n";
		
		inuse = resourceCheck();

		jobRequirements.setText(jobreq);
		for(int i=0; i < inuse.size();i++) {
			if(inuse.get(i).contains(j.startDate) || inuse.get(i).contains(j.endDate)) {
				jobreq = jobreq +"\n"+ ReadExcel.allResources.get(i).name +"\n";
			}else {
				notinuse.add(i);
			}
		}
		jobRequirements.setText(jobreq);
		for(int i=0;i<notinuse.size();i++) {
			resourceNames.add(ReadExcel.allResources.get(notinuse.get(i)).name);
			
			checkBoxes.add(new CheckBox(ReadExcel.allResources.get(notinuse.get(i)).name));
		}
		checkboxVbox.getChildren().addAll(checkBoxes);
		myStartDate = j.startDate;
		myEndDate = j.endDate;

	}
}
