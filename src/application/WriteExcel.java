package application;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import application.jobTable.Job;
import application.resourceTable.Resource;
import javafx.scene.paint.Color;
import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteExcel {
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;

	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public void write(Job job) throws IOException, WriteException, BiffException {
		File file = new File(inputFile);
		if (file.exists()) {
			Workbook workbookCopy = Workbook.getWorkbook(new File(inputFile)); //Get excel file
			WritableWorkbook workbook = Workbook.createWorkbook(new File(inputFile), workbookCopy); //Make a writable excel
			WritableSheet excelSheet = workbook.getSheet(0);  //Get sheet within excel file to edit
			createJob(excelSheet, job.name, job.location, job.description, job.estimate, job.startDate, job.endDate, job.requirements); //Add job to new row
			ReadExcel.allJobs.add(job);
			
			
			WritableSheet excelSheet1 = workbook.getSheet(1);
			for(int i=0; i < ReadExcel.allJobs.size();i++) {
				excelSheet1.addCell(new Label(i+3, 0, "Dates"));
			}
			
			
			workbook.write(); //MUST HAVE ON ALL WRITE METHODS
			workbook.close(); //MUST HAVE ON ALL WRITE METHODS

		} else { //If file doesn't exist, make a new excel file on the desktop
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));

			WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings); //All defaults from jxl api
			workbook.createSheet("Report", 0);
			WritableSheet excelSheet = workbook.getSheet(0);
			createLabel(excelSheet); //Creates headers
			createContent(excelSheet); //Null method
			createJob(excelSheet, job.name, job.location, job.description, job.estimate, job.startDate, job.endDate, job.requirements); //Creates a new job for the new file
			
			workbook.write();
			workbook.close();
		}
	}

	public void write(Resource resource) throws IOException, WriteException, BiffException {
		File file = new File(inputFile);
		Workbook workbookCopy = Workbook.getWorkbook(new File(inputFile)); //Get excel file
		WritableWorkbook workbook = Workbook.createWorkbook(new File(inputFile), workbookCopy); //Make a writable excel
		WritableSheet excelSheet = workbook.getSheet(1);  //Get sheet within excel file to edit
		createResource(excelSheet, resource.name, resource.quantity, resource.description); //Add job to new row
		workbook.write(); //MUST HAVE ON ALL WRITE METHODS
		workbook.close(); //MUST HAVE ON ALL WRITE METHODS
	}

	public void updateJob(Job job, int index) throws BiffException, IOException, RowsExceededException, WriteException {
		File file = new File(inputFile);
		Workbook workbookCopy = Workbook.getWorkbook(new File(inputFile));
		WritableWorkbook workbook = Workbook.createWorkbook(new File(inputFile), workbookCopy);
		WritableSheet excelSheet = workbook.getSheet(0);
		ReadExcel.allJobs.add(job);
		addLabel(excelSheet, 0, index, job.name); //Gets job and replaces row with new job
		addLabel(excelSheet, 1, index, job.location);
		addLabel(excelSheet, 2, index, job.description);
		addLabel(excelSheet, 3, index, job.estimate);
		addLabel(excelSheet, 4, index, job.startDate);
		addLabel(excelSheet, 5, index, job.endDate);
		addLabel(excelSheet, 6, index, job.requirements);
		
		WritableSheet excelSheet1 = workbook.getSheet(1);
		for(int i=0; i < ReadExcel.allJobs.size();i++) {
			excelSheet1.addCell(new Label(i+3, 0, "Dates"));
		}
	
		workbook.write();
		workbook.close();
	}

	public void updateResource(Resource resource , int index) throws BiffException, IOException, RowsExceededException, WriteException {
		File file = new File(inputFile);
		Workbook workbookCopy = Workbook.getWorkbook(new File(inputFile));
		WritableWorkbook workbook = Workbook.createWorkbook(new File(inputFile), workbookCopy);
		WritableSheet excelSheet = workbook.getSheet(1);
		String blank = "";
		addLabel(excelSheet, 0, index, resource.name); //Gets job and replaces row with new job
		addLabel(excelSheet, 1, index, resource.quantity);
		addLabel(excelSheet, 2, index, resource.description);
		addLabel(excelSheet, 3, index, blank);

		workbook.write();
		workbook.close();
	}

	private void createJob(WritableSheet sheet, String name, String location, String description, String estimate,
		String startDate, String endDate, String requirements) throws WriteException, RowsExceededException {
		int rows = sheet.getRows(); //Gets num of rows and adds job to bottom row
		//working on sorting jobs in window
//		ArrayList<Job> sortedJobs = new ArrayList<Job>();
//		ArrayList<String> dates = new ArrayList<String>();
//		for(int i=1;i < rows;i++) {
//			String temp = sheet.getCell(4, i).getContents();
//			dates.add(temp);
//		}
//		Collections.sort(dates, new Comparator<String>() {
//	        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy '@'hh:mm a");
//	        @Override
//	        public int compare(String o1, String o2) {
//	            try {
//	                return f.parse(o1).compareTo(f.parse(o2));
//	            } catch (ParseException e) {
//	                throw new IllegalArgumentException(e);
//	            }
//	        }
//	    });
		//System.out.println(dates.toString());
		addLabel(sheet, 0, rows, name);
		addLabel(sheet, 1, rows, location);
		addLabel(sheet, 2, rows, description);
		addLabel(sheet, 3, rows, estimate);
		addLabel(sheet, 4, rows, startDate);
		addLabel(sheet, 5, rows, endDate);
		addLabel(sheet, 6, rows, requirements);
		
	}
	
	public void writeResourceInUse(String date, String resourcename) throws IOException, WriteException, BiffException {
		WriteExcel jobWriter = new WriteExcel();
    	jobWriter.setOutputFile("Stewart_Concrete_Finishing.xls");
		String file = "Stewart_Concrete_Finishing.xls";
		Workbook workbookCopy = Workbook.getWorkbook(new File(file));
		WritableWorkbook workbook = Workbook.createWorkbook(new File(file), workbookCopy);
		WritableSheet excelSheet = workbook.getSheet(1);
		//createResource(excelSheet, resource.name, resource.quantity, resource.description); //Add job to new row
		int rows = 0; //Gets num of rows and adds job to bottom row
		String[] rn = resourcename.split(",");
		date = date.substring(1, date.length()-1);
		for(int i =0; i < rn.length;i++) {
			for(int j=0; j< ReadExcel.allResources.size();j++) {
				if(ReadExcel.allResources.get(j).name.equals(rn[i])) {
					rows = j+1;
				}
			}
		}
		for(int i = 0;i < ReadExcel.allJobs.size();i++) {
			Cell cell = excelSheet.getCell(i+3, rows);
			if(cell.getContents().isEmpty()) {
				Label label;
				label = new Label(i+3, rows, date);
				excelSheet.addCell(label);
				workbook.write();
				workbook.close();
				i = ReadExcel.allJobs.size();
			}
		}
		
		
	}
	private void createResource(WritableSheet sheet, String name, String quantity, String description) throws WriteException, RowsExceededException {
		int rows = sheet.getRows(); //Gets num of rows and adds job to bottom row
//		for (int i = 0; i < rows; i++) {
//			
//		}
		
		addLabel(sheet, 0, rows, name);
		addLabel(sheet, 1, rows, quantity);
		addLabel(sheet, 2, rows, description);
	}

	public void deleteJob(int index) throws BiffException, IOException, WriteException {
		File file = new File(inputFile);
		Workbook workbookCopy = Workbook.getWorkbook(new File(inputFile));
		WritableWorkbook workbook = Workbook.createWorkbook(new File(inputFile), workbookCopy);
		WritableSheet excelSheet = workbook.getSheet(0);
		int removeindex = index-1;
		ReadExcel.allJobs.remove(removeindex);
		excelSheet.removeRow(index);

		workbook.write();
		workbook.close();
	}

	public void deleteResource(int index) throws BiffException, IOException, WriteException {
		File file = new File(inputFile);
		Workbook workbookCopy = Workbook.getWorkbook(new File(inputFile));
		WritableWorkbook workbook = Workbook.createWorkbook(new File(inputFile), workbookCopy);
		WritableSheet excelSheet = workbook.getSheet(1);

		excelSheet.removeRow(index);

		workbook.write();
		workbook.close();
	}

	private void createLabel(WritableSheet sheet) throws WriteException { //Default jxl api
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// create create a bold font
		WritableFont times10ptBold = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
		timesBoldUnderline = new WritableCellFormat(times10ptBold);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write a few headers
		addCaption(sheet, 0, 0, "Job name");
		addCaption(sheet, 1, 0, "Location");
		addCaption(sheet, 2, 0, "Description");
		addCaption(sheet, 3, 0, "Estimate");
		addCaption(sheet, 4, 0, "Start Date");
		addCaption(sheet, 5, 0, "End Date");

	}

	private void createContent(WritableSheet sheet) throws WriteException, RowsExceededException {
		// Write a few number
		/*
		 * for (int i = 1; i < 10; i++) { // First column addNumber(sheet, 0, i, i +
		 * 10); // Second column addNumber(sheet, 1, i, i * i); addLabel(sheet, 0, 1,
		 * "name"); }
		 */
		// Lets calculate the sum of it
		/*
		 * StringBuffer buf = new StringBuffer(); buf.append("SUM(A2:A10)"); Formula f =
		 * new Formula(0, 10, buf.toString()); sheet.addCell(f); buf = new
		 * StringBuffer(); buf.append("SUM(B2:B10)"); f = new Formula(1, 10,
		 * buf.toString()); sheet.addCell(f);
		 */

		// now a bit of text
		/*
		 * for (int i = 12; i < 20; i++) { // First column addLabel(sheet, 0, i,
		 * "Boring text " + i); // Second column addLabel(sheet, 1, i, "Another text");
		 * }
		 */
	}
	
	private void formatExcel(WritableSheet sheet) throws RowsExceededException, WriteException {
		for (int i = 0; i < sheet.getColumns(); i++) {
			for (int j = 1; j < sheet.getRows() - 1; j++) {
				String cellOneString = sheet.getCell(i, j).getContents();
				String cellTwoString = sheet.getCell(i, j + 1).getContents();
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
				LocalDate dateOne = LocalDate.parse(cellOneString, formatter);
				LocalDate dateTwo = LocalDate.parse(cellTwoString, formatter);
				
				addLabel(sheet, i, j, cellTwoString);
				addLabel(sheet, i, j + 1, cellOneString);
			}
		}
		
		//ONLY PROBLEM IS TO KEEP ON REASSIGNING DATES UNTIL THE DATE IS IN THE CORRECT ORDER USING localdate.compareto(localdate)
	}


	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	private void addNumber(WritableSheet sheet, int column, int row, Integer integer)
			throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer, times);
		sheet.addCell(number);
	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s);
		sheet.addCell(label);
	}
	
	private WritableCellFormat getCellFormat(Colour colour) throws WriteException {
	    WritableFont cellFont = new WritableFont(WritableFont.COURIER, 12);
	    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
	    cellFormat.setBackground(colour);
	    cellFont.setBoldStyle(WritableFont.BOLD);
	    return cellFormat;
	  }

	public static void main(String[] args) throws WriteException, IOException, BiffException {
		/*WriteExcel test = new WriteExcel();
		String fileName = "test";
		test.setOutputFile(System.getProperty("user.home") + "/Desktop/" + fileName + ".xls");
		test.write();
		System.out.println("Please check the result file under F:/Downloads/test.xls ");*/
	}
}
