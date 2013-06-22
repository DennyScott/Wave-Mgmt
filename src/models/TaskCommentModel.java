package models;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ibm.icu.util.Calendar;

import structures.Comment;
import structures.User;
import dbaccess.TaskCommentDB;

public class TaskCommentModel {
	
	public ArrayList<Comment> getCommentsByTaskID(int id){
		
		ArrayList<Comment> comments = new TaskCommentDB().query("SELECT * FROM TaskComment WHERE taskID = " + id);
		
		if(comments == null){
			comments = new ArrayList<Comment>();
		}else{
			for(Comment comment: comments){
				User user = new UserModel().getUserByID(comment.getCreatedBy().getUserID());
				comment.setCreatedBy(user);
			}
		}
		
		return comments;
	}
	
	public boolean addComment(int taskId, String comment, int createdBy){
		boolean success = false;
		Date now = Calendar.getInstance().getTime();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(now);
		
		try {
			success = new TaskCommentDB().insert("INSERT INTO TaskComment(taskID, comment, createdBy, postedAt) VALUES(" + taskId + ", '" + 
					comment + "', " + createdBy + ", #" + date + "#)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}

}
