package structures;

import java.sql.Date;

public class Project {
	private int projectID;
	private String projectName;
	private int unitID;
	private String startDate;
	private String endDate;
	private String description;
	private String reqOrg;
	private User createdBy;
	private boolean completed;
	private boolean deleted;
	private String expectedEndDate;
	private int leadAnalyst;
	//private Unit unit;
	
	
	
	
	
	
	
	/**
	 * @return the projectID
	 */
	public int getProjectID() {
		return projectID;
	}
	/**
	 * @return the leadAnalyst
	 */
	public int getLeadAnalyst() {
		return leadAnalyst;
	}
	/**
	 * @param leadAnalyst the leadAnalyst to set
	 */
	public void setLeadAnalyst(int leadAnalyst) {
		this.leadAnalyst = leadAnalyst;
	}
	/**
	 * @return the expectedEndDate
	 */
	public String getExpectedEndDate() {
		return expectedEndDate;
	}
	/**
	 * @param string the expectedEndDate to set
	 */
	public void setExpectedEndDate(String string) {
		this.expectedEndDate = string;
	}
	/**
	 * @return the unitID
	 */
	public int getUnitID() {
		return unitID;
	}
	/**
	 * @param unitID the unitID to set
	 */
	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the reqOrg
	 */
	public String getReqOrg() {
		return reqOrg;
	}
	/**
	 * @param reqOrg the reqOrg to set
	 */
	public void setReqOrg(String reqOrg) {
		this.reqOrg = reqOrg;
	}
	/**
	 * @return the createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


}
