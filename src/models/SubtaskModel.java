package models;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import structures.Subtask;
import structures.Task;
import aurelienribon.slidinglayout.demo.TheFrame;
import dbaccess.SubtaskDB;
import dbaccess.TaskDB;

public class SubtaskModel {
	

	
	public SubtaskModel(){
	}
	
/**
 * Collect all Subtasks assigned to a specified User
 * 	
 * @param id Id of the user
 * @param completed Boolean, true for completed tasks, false to not include them
 * @return ArrayList<Subtask> Of Subtasks
 */
public ArrayList<Subtask> getUserAssignedSubtasks(int id, boolean completed){
		
		String complete = completed==true?"yes":"no";
		ArrayList<Subtask> tasks = new SubtaskDB().query("SELECT *" +
				" FROM Subtask  WHERE Subtask.assignedTo= " + id + " AND Subtask.Completed = " + complete + " AND Subtask.Deleted = no ORDER BY startDate DESC");
		
		if(tasks!=null){
		UserModel um = new UserModel();
		
		
		
		for(Subtask task: tasks){
			task.setAssignedTo(um.getUserByID(id));
			task.setRequestedBy(um.getUserByID(task.getRequestedBy().getUserID()));
		}
		
		return tasks;
		}
		return new ArrayList<Subtask>();
	}

public void removeSubtaskByProjectID(int id, TheFrame frame){
	try {
		boolean success = new SubtaskDB().update("UPDATE Subtask SET Subtask.Deleted = yes WHERE Subtask.projectID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void completeSubtaskByProjectID(int id, TheFrame frame){
	try {
		boolean success = new SubtaskDB().update("UPDATE Subtask SET Subtask.Completed = yes WHERE Subtask.projectID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public boolean addTask(String taskName, String startDate, String endDate, String description, int assignedTo, int createdBy,
		int projectID, int taskID, String expectedDate){
	boolean success = false;
	try {
		if(endDate.isEmpty() || endDate.length()<10){
		 success = new SubtaskDB().insert("INSERT INTO Subtask(taskID, description, startDate, endDate, RequestedBy, " +
				"assignedTo, subtaskName, Completed, Deleted, projectID, expectedEndDate) VALUES(" + taskID + ", '" + description + "', #" + startDate + "#, #" + expectedDate +
				"#, " + createdBy + ", " + assignedTo + ", '" + taskName + "', no, no, " + projectID + ", #" + expectedDate + "#)");
		}else{
			 success = new SubtaskDB().insert("INSERT INTO Subtask(taskID, description, startDate, endDate, RequestedBy, " +
						"assignedTo, subtaskName, Completed, Deleted, projectID, expectedEndDate) VALUES(" + taskID + ", '" + description + "', #" + startDate + "#, #" + endDate +
						"#, " + createdBy + ", " + assignedTo + ", '" + taskName + "', no, no, " + projectID + ", #" + expectedDate + "#)");
		}
		return success;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return success;
	}
}

public boolean subtaskUpdate(int id, String taskName, String startDate, String endDate, String description, int assignedTo, String expectedDate){
	boolean success = false;
	Subtask task = getSubtaskByID(id);
	try {
		if(endDate.isEmpty() || endDate.length()<10){
		success = new SubtaskDB().update("UPDATE Subtask SET subtaskName = '" + taskName + "', startDate = #" + startDate + "#, endDate = #"+ expectedDate + "#, " +
				"description = '" + description + "', assignedTo = " + assignedTo + ", expectedEndDate = #" + expectedDate + "# WHERE subtaskID = " + id);
		}else{
			success = new SubtaskDB().update("UPDATE Subtask SET subtaskName = '" + taskName + "', startDate = #" + startDate + "#, endDate = #"+ endDate + "#, " +
					"description = '" + description + "', assignedTo = " + assignedTo + ", expectedEndDate = #" + expectedDate + "# WHERE subtaskID = " + id);
		
		}
		
		
		
		if(!task.getExpectedEndDate().equals(expectedDate)){
			String input = JOptionPane.showInputDialog("Why have you changed the expected end date?");
			new SubtaskDB().insert("INSERT INTO SubtaskChangeDate(subtaskID, formerDate, newDate, reason) " +
					"VALUES(" + id + ", #" + task.getExpectedEndDate() + "#, #" + expectedDate + "#, '" + input + "')");
							
		}
		
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	return success;
}

public void removeSubtaskByID(int id, TheFrame frame){
	try {
		boolean success = new SubtaskDB().update("UPDATE Subtask SET Subtask.deleted = yes WHERE Subtask.subtaskID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void removeSubtaskByTaskID(int id, TheFrame frame){
	try {
		boolean success = new SubtaskDB().update("UPDATE Subtask SET Subtask.deleted = yes WHERE Subtask.taskID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void completeSubtaskByTaskID(int id, TheFrame frame){
	try {
		boolean success = new SubtaskDB().update("UPDATE Subtask SET Subtask.completed = yes WHERE Subtask.taskID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void completeSubtaskByID(int id, TheFrame frame){
	try {
		boolean success = new SubtaskDB().update("UPDATE Subtask SET Subtask.completed = yes WHERE Subtask.subtaskID = " + id);
		
		if(!success){
			JOptionPane.showMessageDialog(frame, "An error has occured");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public ArrayList<Subtask> getSubtaskByTaskID(int id, boolean completed){
	ArrayList<Subtask> tasks;
	
	if(!completed){
	tasks = new SubtaskDB().query("SELECT *" +
			" FROM Subtask  WHERE Subtask.taskID= " + id + " AND Subtask.Completed = no AND Subtask.Deleted = no ORDER BY startDate DESC");
	}else{
		tasks = new SubtaskDB().query("SELECT *" +
				" FROM Subtask  WHERE Subtask.taskID= " + id + " AND Subtask.Deleted = no ORDER BY startDate DESC");
	}
	if(tasks!=null){
	UserModel um = new UserModel();
	
	
	
	for(Subtask task: tasks){
		task.setAssignedTo(um.getUserByID(task.getAssignedTo().getUserID()));
		task.setRequestedBy(um.getUserByID(task.getRequestedBy().getUserID()));
	}
	
	return tasks;
	}
	return new ArrayList<Subtask>();
}

public Subtask getSubtaskByID(int id){
	

	ArrayList<Subtask> tasks = new SubtaskDB().query("SELECT *" +
			" FROM Subtask  WHERE Subtask.subtaskID= " + id);
	Subtask task;
	if(tasks==null){
		task = new Subtask();
	}else{
		UserModel um = new UserModel();
		
		for(Subtask t: tasks){
			t.setAssignedTo(um.getUserByID(t.getAssignedTo().getUserID()));
			t.setRequestedBy(um.getUserByID(t.getRequestedBy().getUserID()));
		}
		task = tasks.get(0);
	}
	

	return task;
}



/**
 * Collect Recent Subtasks, sort by there EndDate
 * 
 * @param id Id of the user attached to Subtasks (Assigned To)
 * @return ArrayList<Subtask>
 */ 
public ArrayList<Subtask> getRecentSubtasks(int id){
	

	ArrayList<Subtask> tasks = new SubtaskDB().query("SELECT *" +
			" FROM Subtask  WHERE Subtask.assignedTo= " + id + "AND Subtask.Completed = no AND Subtask.Deleted = no ORDER BY endDate");
	
	if(tasks!=null){
	UserModel um = new UserModel();
	TaskModel tm = new TaskModel();
	
	for(Subtask task: tasks){
		task.setAssignedTo(um.getUserByID(id));
		task.setRequestedBy(um.getUserByID(task.getRequestedBy().getUserID()));
		task.setProjectID(tm.getTaskByID(task.getTaskID()).getProjectID());
	}
	
	return tasks;
	}
	return new ArrayList<Subtask>();
}

}
