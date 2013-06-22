package models;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import structures.Project;
import structures.Task;
import dbaccess.ProjectDB;
import dbaccess.TaskDB;
import aurelienribon.slidinglayout.demo.TheFrame;

public class TaskModel {
	
	
	public TaskModel(){

	}
	
	/**
	 * Collect Tasks assigned to the specified User ID
	 * 
	 * @param id Int of the desired User
	 * @param completed True if completed should be included
	 * @return ArrayList<Task> Found Data
	 */
public ArrayList<Task> getUserAssignedTasks(int id){
		
		ArrayList<Task> tasks = new TaskDB().query("SELECT *" +
				" FROM Task  WHERE Task.assignedTo = " + id + " AND Task.Deleted = no ORDER BY startDate DESC" );
		
		if(tasks!=null){
		UserModel um = new UserModel();
		
		for(Task task: tasks){
			task.setAssignedTo(um.getUserByID(id));
			task.setRequestedBy(um.getUserByID(task.getRequestedBy().getUserID()));
		}
		
		return tasks;
		}
		return new ArrayList<Task>();
	}

public ArrayList<Task> getTaskByProjectID(int id, boolean complete){
	ArrayList<Task> tasks;
	
	
	if(!complete){
	 tasks = new TaskDB().query("SELECT *" +
			" FROM Task  WHERE Task.projectID = "+ id + " AND Task.Completed = no AND Task.Deleted = no ORDER BY startDate ASC" );
	}else{
		 tasks = new TaskDB().query("SELECT *" +
				" FROM Task  WHERE Task.projectID = "+ id + " AND Task.Deleted = no ORDER BY startDate ASC" );
	}
	if(tasks!=null){
	UserModel um = new UserModel();
	
	for(Task task: tasks){
		task.setAssignedTo(um.getUserByID(task.getAssignedTo().getUserID()));
		task.setRequestedBy(um.getUserByID(task.getRequestedBy().getUserID()));
	}
	
	return tasks;
	}
	return new ArrayList<Task>();
}

public boolean addTask(String taskName, String startDate, String endDate, String description, int assignedTo, int createdBy,
		int projectID, String expectedDate){
	boolean success = false;
	try {
		if(endDate.isEmpty() || endDate.length()<10){
		 success = new TaskDB().insert("INSERT INTO Task(projectID, startDate, endDate, description, RequestedBy, " +
				"assignedTo, taskName, completed, deleted, expectedEndDate) VALUES(" + projectID + ", #" + startDate + "#, #" + expectedDate + "#, '" +
				description + "', " + createdBy + ", " + assignedTo + ", '" + taskName + "', no, no, #" + expectedDate + "#)");
		}else{
			success = new TaskDB().insert("INSERT INTO Task(projectID, startDate, endDate, description, RequestedBy, " +
					"assignedTo, taskName, completed, deleted, expectedEndDate) VALUES(" + projectID + ", #" + startDate + "#, #" + endDate + "#, '" +
					description + "', " + createdBy + ", " + assignedTo + ", '" + taskName + "', no, no, #" + expectedDate + "#)");
		}
		
		return success;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return success;
	}
}

public boolean taskUpdate(int id, String taskName, String startDate, String endDate, String description, int assignedTo, String expectedDate){
	boolean success = false;
	Task task = getTaskByID(id);
	try {
		if(endDate.isEmpty() || endDate.length()<10){
		success = new TaskDB().update("UPDATE Task SET taskName = '" + taskName + "', startDate = #" + startDate + "#, endDate = #"+ expectedDate + "#, " +
				"description = '" + description + "', assignedTo = " + assignedTo + ", expectedEndDate = #" + expectedDate + "# WHERE taskID = " + id);
		}else{
			success = new TaskDB().update("UPDATE Task SET taskName = '" + taskName + "', startDate = #" + startDate + "#, endDate = #"+ endDate + "#, " +
					"description = '" + description + "', assignedTo = " + assignedTo + ", expectedEndDate = #" + expectedDate + "# WHERE taskID = " + id);
		}
		
		if(!task.getExpectedEndDate().equals(expectedDate)){
			String input = JOptionPane.showInputDialog("Why have you changed the expected end date?");
			new TaskDB().insert("INSERT INTO TaskChangeDate(taskID, formerDate, newDate, reason) " +
					"VALUES(" + id + ", #" + task.getExpectedEndDate().split("\\s+")[0] + "#, #" + expectedDate.split("\\s+")[0] + "#, '" + input + "' )");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	return success;
}

/**
 * Collect the recent ending tasks for an assignedby user.
 * 
 * @param id int ID of the user
 * @return ArrayList<Task> data collected
 */
public ArrayList<Task> getRecentTasks(int id){
	
	ArrayList<Task> tasks = new TaskDB().query("SELECT *" +
			" FROM Task  WHERE Task.assignedTo = " + id + " AND Task.Deleted = no ORDER BY endDate" );
	
	if(tasks!=null){
	UserModel um = new UserModel();
	
	for(Task task: tasks){
		task.setAssignedTo(um.getUserByID(id));
		task.setRequestedBy(um.getUserByID(task.getRequestedBy().getUserID()));
	}
	
	return tasks;
	}
	return new ArrayList<Task>();
}

public void removeTaskById(int id,TheFrame frame){
	try {
		boolean success = new TaskDB().update("UPDATE Task SET Task.deleted = yes WHERE Task.taskID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void completeTaskById(int id,TheFrame frame){
	try {
		boolean success = new TaskDB().update("UPDATE Task SET Task.completed = yes WHERE Task.taskID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void removeTaskByProjectID(int id, TheFrame frame){
	try {
		boolean success = new TaskDB().update("UPDATE Task SET Task.Deleted = yes WHERE Task.projectID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void completeTaskByProjectID(int id, TheFrame frame){
	try {
		boolean success = new TaskDB().update("UPDATE Task SET Task.Completed = yes WHERE Task.projectID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


/**
 * Collect a Task based on the Task ID value
 * 
 * @param id ID of the desired Task
 * @return Task collected Task
 */
public Task getTaskByID(int id){
	
	ArrayList<Task> tasks = new TaskDB().query("SELECT *" +
			" FROM Task  WHERE Task.taskID = " + id );
	
	if(tasks!=null){
	UserModel um = new UserModel();
	
	for(Task task: tasks){
		task.setAssignedTo(um.getUserByID(task.getAssignedTo().getUserID()));
		task.setRequestedBy(um.getUserByID(task.getRequestedBy().getUserID()));
	}
	
		return tasks.get(0);
	}
	return new Task();
}

}
