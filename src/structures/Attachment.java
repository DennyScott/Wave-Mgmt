package structures;

public class Attachment {

	private int attachmentID;
	private int taskID;
	private User loadedBy;
	private String link;
	private String title;
	/**
	 * @return the attachmentID
	 */
	public int getAttachmentID() {
		return attachmentID;
	}
	/**
	 * @param attachmentID the attachmentID to set
	 */
	public void setAttachmentID(int attachmentID) {
		this.attachmentID = attachmentID;
	}
	/**
	 * @return the projectID
	 */
	public int getTaskID() {
		return taskID;
	}
	/**
	 * @param projectID the projectID to set
	 */
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	/**
	 * @return the loadedBy
	 */
	public User getLoadedBy() {
		return loadedBy;
	}
	/**
	 * @param loadedBy the loadedBy to set
	 */
	public void setLoadedBy(User loadedBy) {
		this.loadedBy = loadedBy;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
