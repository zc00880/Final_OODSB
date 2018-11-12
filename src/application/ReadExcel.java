package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.jobTable.Job;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

	private String inputFile;
	ArrayList<String> list = new ArrayList();

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void read() throws IOException { //Read ALL excel file data, not used currently
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines

			for (int j = 0; j < sheet.getRows(); j++) {
				for (int i = 0; i < sheet.getColumns(); i++) {
					Cell cell = sheet.getCell(i, j);
					CellType type = cell.getType();
					if (type == CellType.LABEL) {
						System.out.println("I got a label " + cell.getContents());
						list.add(cell.getContents().toString());
					}

					if (type == CellType.NUMBER) {
						System.out.println("I got a number " + cell.getContents());
					}

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
		
		Job job = new Job(jobName, jobLocation, description, estimate, startDate, endDate);
		return job;
	}

	public ObservableList<String> readJobNames() throws IOException { //Gets job names for live list on home screen
		File inputWorkbook = new File(inputFile);
		Workbook w;
		ObservableList<String> jobList = FXCollections.observableArrayList();
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);

			for (int i = 1; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(0, i);
				CellType type = cell.getType();
				System.out.println(cell.getContents());
				jobList.add(cell.getContents().toString()); //Adds all names to a list 
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}
		return jobList;
	}

	public static void main(String[] args) throws IOException { //Testing purposes
		/*ReadExcel test = new ReadExcel();
		String fileName = "test";
		test.setInputFile(System.getProperty("user.home") + "/Desktop/" + fileName + ".xls");
		test.readJobNames();
		System.out.println(test.readJobNames().get(0).toString());*/
	}

}
