package structures;

public class Comment {
	
	private int taskID;
	private String comment;
	private User createdBy;
	private String postedAt;
	private int commentID;
	
	
	
	
	/**
	 * @return the commentID
	 */
	public int getCommentID() {
		return commentID;
	}
	/**
	 * @param commentID the commentID to set
	 */
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	/**
	 * @return the taskID
	 */
	public int getTaskID() {
		return taskID;
	}
	/**
	 * @param taskID the taskID to set
	 */
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	/**
	 * @return the postedAt
	 */
	public String getPostedAt() {
		return postedAt;
	}
	/**
	 * @param postedAt the postedAt to set
	 */
	public void setPostedAt(String postedAt) {
		this.postedAt = postedAt;
	}
	
	

}
