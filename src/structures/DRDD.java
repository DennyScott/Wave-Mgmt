package structures;

import java.util.ArrayList;

public class DRDD {

	private int id;
	private String actionRequested;
	private String workPerformed;
	private ArrayList<DataDelivered> dataDelivered;
	private ArrayList<FileAndFolderLocation> fileAndFolder;
	private int projectID;
	private User lastEditedBy;
	
	
	/**
	 * @return the lastEditedBy
	 */
	public User getLastEditedBy() {
		return lastEditedBy;
	}
	/**
	 * @param lastEditedBy the lastEditedBy to set
	 */
	public void setLastEditedBy(User lastEditedBy) {
		this.lastEditedBy = lastEditedBy;
	}
	/**
	 * @return the projectID
	 */
	public int getProjectID() {
		return projectID;
	}
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the actionRequested
	 */
	public String getActionRequested() {
		return actionRequested;
	}
	/**
	 * @param actionRequested the actionRequested to set
	 */
	public void setActionRequested(String actionRequested) {
		this.actionRequested = actionRequested;
	}
	/**
	 * @return the workPerformed
	 */
	public String getWorkPerformed() {
		return workPerformed;
	}
	/**
	 * @param workPerformed the workPerformed to set
	 */
	public void setWorkPerformed(String workPerformed) {
		this.workPerformed = workPerformed;
	}
	/**
	 * @return the dataDelivered
	 */
	public ArrayList<DataDelivered> getDataDelivered() {
		return dataDelivered;
	}
	/**
	 * @param dataDelivered the dataDelivered to set
	 */
	public void setDataDelivered(ArrayList<DataDelivered> dataDelivered) {
		this.dataDelivered = dataDelivered;
	}
	/**
	 * @return the fileAndFolder
	 */
	public ArrayList<FileAndFolderLocation> getFileAndFolder() {
		return fileAndFolder;
	}
	/**
	 * @param fileAndFolder the fileAndFolder to set
	 */
	public void setFileAndFolder(ArrayList<FileAndFolderLocation> fileAndFolder) {
		this.fileAndFolder = fileAndFolder;
	}
	
	public String[] getDataDeliveredNames(){
		
		String[] names = new String[dataDelivered.size()];
		for(int i = 0; i<names.length; i++){
			names[i] = dataDelivered.get(i).getData();
		}
		return names;
	}
	
public String[] getFileNames(){
		
		String[] names = new String[fileAndFolder.size()];
		for(int i = 0; i<names.length; i++){
			names[i] = fileAndFolder.get(i).getOs();
		}
		return names;
	}
	
	
}
