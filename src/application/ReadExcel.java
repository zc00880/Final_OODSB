package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import application.dateCalendar.dateController;
import application.jobTable.Job;
import application.resourceTable.Resource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ReadExcel {

	private String inputFile;
	ArrayList<String> list = new ArrayList<String>();
	public static ArrayList<Job> allJobs = new ArrayList<Job>();
	public static ArrayList<Resource> allResources = new ArrayList<Resource>();
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void read() throws IOException, WriteException, BiffException { //Read ALL excel file data, not used currently
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);

			for (int j = 0; j < sheet.getRows(); j++) {
				for (int i = 0; i < sheet.getColumns(); i++) {
					Cell cell = sheet.getCell(i, j);
					list.add(cell.getContents().toString());
				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public Job readJob(int index) throws BiffException, IOException { //Reads job row and returns job object
		File inputWorkbook = new File(inputFile);
		Workbook w = Workbook.getWorkbook(inputWorkbook);
		Sheet sheet = w.getSheet(0); //Initialize excel file

		Cell cell = sheet.getCell(0, index); //Current cell directory (x, y) axis
		String jobName = cell.getContents(); //Gets contents of current cell
		cell = sheet.getCell(1, index);
		String jobLocation = cell.getContents();
		cell = sheet.getCell(2, index);
		String description = cell.getContents();
		cell = sheet.getCell(3, index);
		String estimate = cell.getContents();
		cell = sheet.getCell(4, index);
		String startDate = cell.getContents();
		cell = sheet.getCell(5, index);
		String endDate = cell.getContents();
		cell = sheet.getCell(6, index);
		String requirements = cell.getContents();
		Job job = new Job(jobName, jobLocation, description, estimate, startDate, endDate,requirements);
		return job;
	}

	public ObservableList<String> readJobNames() throws IOException, RowsExceededException, WriteException { //Gets job names for live list on home screen

		File inputWorkbook = new File(inputFile);
		Workbook w;
		ObservableList<String> jobList = FXCollections.observableArrayList();
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			dateController dc = new dateController();
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(0, i);
				Cell cell2 = sheet.getCell(1, i);
				Cell cell4 = sheet.getCell(4,i);
				Cell cell5 = sheet.getCell(5,i);
				dc.addDates(cell4.getContents().toString(), cell5.getContents().toString());
				jobList.add(cell4.getContents().toString()+"\t" +cell.getContents().toString() + " - " + cell2.getContents().toString()); //Adds all names to a list 
				Cell cell0_0  = sheet.getCell(0,i);
				String jobName = cell0_0.getContents();
				Cell cell1_1 = sheet.getCell(1,i);
				String jobLocation = cell1_1.getContents();
				Cell cell2_2 = sheet.getCell(2,i);
				String description = cell2_2.getContents();
				Cell cell3_3 = sheet.getCell(3,i);
				String estimate = cell3_3.getContents();
				Cell cell4_4 = sheet.getCell(4,i);
				String startDate = cell4_4.getContents();
				Cell cell5_5 = sheet.getCell(5,i);
				String endDate = cell5_5.getContents();
				Cell cell6_6 = sheet.getCell(6,i);
				String requirements = cell6_6.getContents();
				Job job = new Job(jobName, jobLocation, description, estimate, startDate, endDate,requirements);
				allJobs.add(job);
			}

		} catch (Exception e) {

			File newSpreadsheet = new File("Stewart_Concrete_Finishing.xls");

			WritableWorkbook ww = Workbook.createWorkbook(newSpreadsheet);
			WritableSheet job_Pool = ww.createSheet("Job Pool", 0);
			WritableSheet resource_Pool = ww.createSheet("Resource Pool", 1);

			job_Pool.addCell(new Label(0, 0, "Job Name"));
			job_Pool.addCell(new Label(1, 0, "Job Description"));
			job_Pool.addCell(new Label(2, 0, "Job Estimate"));
			job_Pool.addCell(new Label(3, 0, "Job Location"));
			job_Pool.addCell(new Label(4, 0, "Job Start Date"));
			job_Pool.addCell(new Label(5, 0, "Job End Date"));
			job_Pool.addCell(new Label(6, 0, "Requirements"));
			
			resource_Pool.addCell(new Label(0, 0, "Resource Name"));
			resource_Pool.addCell(new Label(1, 0, "Resource Quantity"));
			resource_Pool.addCell(new Label(2, 0, "Resource Description"));
			for(int i=0; i < allJobs.size();i++) {
				resource_Pool.addCell(new Label(i+3, 0, "Dates"));
			}

			ww.write();

			ww.close();
		}
		return jobList;
	}

	public Resource readResource(int index) throws BiffException, IOException, WriteException { //Reads job row and returns job object
		File inputWorkbook = new File(inputFile);
		Workbook w = Workbook.getWorkbook(inputWorkbook);
		Sheet sheet = w.getSheet(1); //Initialize excel file

		Cell 
		cell = sheet.getCell(0, index); //Current cell directory (x, y) axis
		String resourceName = cell.getContents(); //Gets contents of current cell
		cell = sheet.getCell(1, index);
		String resourceQuantity = cell.getContents();
		cell = sheet.getCell(2, index);
		String resourceDescription = cell.getContents();

		Resource resource = new Resource(resourceName, resourceQuantity, resourceDescription);
		return resource;
	}

	public ObservableList<String> readResourceNames() throws IOException, RowsExceededException, WriteException, BiffException {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		ObservableList<String> resourceList = FXCollections.observableArrayList();

		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the second sheet
			Sheet sheet = w.getSheet(1);
			allResources.clear();
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell cell1_1 = sheet.getCell(0, i);
				String date="";
				for(int j=0; j< allJobs.size();j++) {
					Cell cell3 = sheet.getCell(j+3,i);
					if(!date.isEmpty())
						if (cell3.getContents().toString().equals("")) {
							break;
						}
						else {
							date = date+ " , " + cell3.getContents().toString();
						}
					else
						date = cell3.getContents().toString();
				}
				
				resourceList.add(cell1_1.getContents().toString() + " " + date); //Adds all resource names to a list 
				Cell cell = sheet.getCell(0, i); //Current cell directory (x, y) axis
				String resourceName = cell.getContents(); //Gets contents of current cell
				cell = sheet.getCell(1, i);
				String resourceQuantity = cell.getContents();
				cell = sheet.getCell(2, i);
				String resourceDescription = cell.getContents();

				Resource resource = new Resource(resourceName, resourceQuantity, resourceDescription);
				allResources.add(resource);
				
			}
		}
		

		catch (Exception e) {

			File newSpreadsheet = new File("Stewart_Concrete_Finishing.xls");

			WritableWorkbook ww = Workbook.createWorkbook(newSpreadsheet);
			WritableSheet job_Pool = ww.createSheet("Job Pool", 0);
			WritableSheet resource_Pool = ww.createSheet("Resource Pool", 1);

			job_Pool.addCell(new Label(0, 0, "Job Name"));
			job_Pool.addCell(new Label(1, 0, "Job Description"));
			job_Pool.addCell(new Label(2, 0, "Job Estimate"));
			job_Pool.addCell(new Label(3, 0, "Job Location"));
			job_Pool.addCell(new Label(4, 0, "Job Start Date"));
			job_Pool.addCell(new Label(5, 0, "Job End Date"));

			resource_Pool.addCell(new Label(0, 0, "Resource Name"));
			resource_Pool.addCell(new Label(1, 0, "Resource Quantity"));
			resource_Pool.addCell(new Label(2, 0, "Resource Description"));
			for(int i=0; i < allJobs.size();i++) {
				resource_Pool.addCell(new Label(i+3, 0, "Dates"));
			}
			
			ww.write();

			ww.close();
		}

		return resourceList;
	}

	public static void main(String[] args) throws IOException { //Testing purposes

	}

}
