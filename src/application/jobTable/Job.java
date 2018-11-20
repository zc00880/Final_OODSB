package application.jobTable;

public class Job {

	public String name;
	public String location;
	public String description;
	public String estimate;
	public String startDate;
	public String endDate;
	public String requirements;
	
	public Job(String name, String location, String description, String estimate, String startDate, String endDate, String requirements) {
		this.name = name;
		this.location = location;
		this.description = description;
		this.estimate = estimate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.requirements = requirements;
	}
}
