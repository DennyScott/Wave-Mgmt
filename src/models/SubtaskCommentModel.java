package models;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ibm.icu.util.Calendar;

import structures.Comment;
import dbaccess.SubtaskCommentDB;
import dbaccess.TaskCommentDB;

public class SubtaskCommentModel {
	
	public ArrayList<Comment> getCommentsBySubtaskID(int id){
		
		ArrayList<Comment> comments = new SubtaskCommentDB().query("SELECT * FROM SubtaskComment WHERE subtaskID = " + id);
		
		if(comments == null){
			comments = new ArrayList<Comment>();
		}else{
			for(Comment comment: comments){
				comment.setCreatedBy(new UserModel().getUserByID(comment.getCreatedBy().getUserID()));
			}
		}
		
		return comments;
	}
	
	public boolean addComment(int taskId, String comment, int createdBy){
		boolean success = false;
		Date now = Calendar.getInstance().getTime();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(now);
		
		try {
			success = new SubtaskCommentDB().insert("INSERT INTO SubtaskComment(subtaskID, comment, createdBy, postedAt) VALUES(" + taskId + ", '" + 
					comment + "', " + createdBy + ", #" + date + "#)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}

}
