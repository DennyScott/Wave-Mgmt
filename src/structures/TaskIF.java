package structures;

public interface TaskIF {

	public int getProjectID();
	public String getTaskName();
	public String getEndDate();
	public String getStartDate();
	public User getRequestedBy();
	public User getAssignedTo();
	public String getDescription();
	public String getExpectedEndDate();
}
