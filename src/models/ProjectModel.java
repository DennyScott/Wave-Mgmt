package models;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import aurelienribon.slidinglayout.demo.TheFrame;

import data.ProjectSingleton;
import dbaccess.ProjectDB;
import structures.Attachment;
import structures.Project;
import structures.User;
import views.MainWindow;

public class ProjectModel {
	TheFrame frame;

	/**
	 * Constructor
	 * 
	 * @param frame Window to display data
	 */
	public ProjectModel(TheFrame frame){
		this.frame = frame;
	}
	
	/**
	 * Collect all current Projects in database, and create a table with them.
	 */
	public Object[][] getAllProjects(){
		
		ArrayList<Project> projects = new ProjectDB().query("SELECT * FROM Project WHERE Project.deleted = no");
		Object[][] data;
		
		
		
		//If no data was found in database
		if(projects == null){
			data = new Object[0][6];
		}
		else{
		data = new Object[projects.size()][6];
		
		for(int i = 0; i<projects.size(); i++){
			Project project = projects.get(i);
			
			ArrayList<User> users = new UserModel().getAllUsersOnProject(project.getProjectID());
		
			data[i][0] = project.getProjectID();
			data[i][1] = project.getProjectName();
			data[i][2] = new UnitModel().getUnitByID(project.getUnitID()).getUnitName();
			data[i][3] = "";
			for(int j = 0; j<users.size(); j++){
				User user = users.get(j);
				if(j<users.size()-1){
					data[i][3] += user.getFirstName() + " " + user.getLastName() + ",";
				}else{
					data[i][3] += user.getFirstName() + " " + user.getLastName();
				}
				
			}			
			
			data[i][4] = project.getStartDate().split("\\s+")[0];
			data[i][5] = project.getEndDate().split("\\s+")[0];
		}	

	}
		
		return data;
}
	
	public boolean onProject(int projectID, int userID){
		ArrayList<Project> project = new ProjectDB().query("SELECT *" +
				" FROM Project INNER JOIN UserProject ON Project.projectID = UserProject.projectID" +
				" WHERE UserProject.projectID = " + projectID + " AND UserProject.userID = " + userID);
		
		if(project==null){
			JOptionPane.showMessageDialog(frame,"A Manager or Admin must add you to this project before you can enter it.");
			return false;
			
		}
		return true;
	}
	
	
	public void addProject(String projectName, String startDate, String endDate, 
			String description, String reqOrg, int userID, int unitID, String expectedDate, int leadAnalyst){
		String sql;
		
		if(endDate.isEmpty() || endDate.length()<10){
			sql = "INSERT INTO Project (projName, startDate, endDate, description, reqOrg, createdBy, unitID, completed, deleted,expectedEndDate, leadAnalyst)  VALUES ('" + 
				projectName + "', #" + startDate + "#, #" + expectedDate + "#, '" + description + "', '" + reqOrg + "', " + 
				userID + ", "+ unitID + ", no, no, #" + expectedDate + "#, " + leadAnalyst + ");";
		}else{
			 sql = "INSERT INTO Project (projName, startDate, endDate, description, reqOrg, createdBy, unitID, completed, deleted, leadAnalyst)  VALUES ('" + 
					projectName + "', #" + startDate + "#, #" + endDate + "#, '" + description + "', '" + reqOrg + "', " + 
					userID + ", "+ unitID + ", no, no,  #" + expectedDate + "#, " + leadAnalyst + ");";
		}
		boolean success = false;
		try {
			success = new ProjectDB().insert(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(success){
			JOptionPane.showMessageDialog(frame, "Project Added!");
		}else{
			JOptionPane.showMessageDialog(frame, "There was error while adding your Project.");
		}
		
	}
	
	
	public void editProject(String projectName, String startDate, String endDate, String description, 
			String reqOrg, int userID, int unitID, String expectedDate, int leadAnalyst){
		Project oldProject = getProjectByID(ProjectSingleton.getInstance().getProjectID());
		String sql;
		if(endDate.isEmpty() || endDate.length()<10){
			sql = "UPDATE Project SET projName = '" + projectName + "', startDate = #" + startDate + "#, endDate = #" + expectedDate + "#, " +
					"description = '" + description + "', reqOrg = '" + reqOrg + "', createdBy = " + userID + ", unitID = " + unitID + 
					", completed = no, deleted = no, expectedEndDate = #" + expectedDate + "#, leadAnalyst =" + leadAnalyst + "" +
							" WHERE projectID = " + ProjectSingleton.getInstance().getProjectID();
		}else{
		 sql = "UPDATE Project SET projName = '" + projectName + "', startDate = #" + startDate + "#, endDate = #" + endDate + "#, " +
				"description = '" + description + "', reqOrg = '" + reqOrg + "', createdBy = " + userID + ", unitID = " + unitID + 
				", completed = no, deleted = no, expectedEndDate = #" + expectedDate + "#, leadAnalyst = " + leadAnalyst + 
				" WHERE projectID = " + ProjectSingleton.getInstance().getProjectID();
		}
		
		boolean success = false;
		try {
			success = new ProjectDB().update(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(success){
			
			if(!oldProject.getExpectedEndDate().equals(expectedDate)){
				try {
					String input = JOptionPane.showInputDialog("Why have you changed the expected end date?");
					new ProjectDB().insert("INSERT INTO ProjectChangeDate(projectID, formerDate, newDate, reason) VALUES (" + ProjectSingleton.getInstance().getProjectID() +
						", #" + oldProject.getExpectedEndDate() + "#, #" + expectedDate + "#, '" + input + "')"	);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			JOptionPane.showMessageDialog(frame, "Project Changed!");
		}else{
			JOptionPane.showMessageDialog(frame, "There was error while changing your Project.");
		}
		
	}
	
	/**
	 * Remove Project and all Tasks/Subtasks from Tool. This will still exist in the database, but will be hidden from the tool.
	 * @param id
	 */
	public void removeProject(int id){
		try {
			boolean success = new ProjectDB().update("UPDATE Project SET Project.deleted = yes WHERE Project.projectID = " + id);
			if(!success){
				JOptionPane.showMessageDialog(frame, "An error has occured");
			}
			
			new TaskModel().removeTaskByProjectID(id, frame);
			new SubtaskModel().removeSubtaskByProjectID(id, frame);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove Project and all Tasks/Subtasks from Tool. This will still exist in the database, but will be hidden from the tool.
	 * @param id
	 */
	public void completeProject(int id){
		try {
			boolean success = new ProjectDB().update("UPDATE Project SET Project.completed = yes WHERE Project.projectID = " + id);
			if(!success){
				JOptionPane.showMessageDialog(frame, "An error has occured");
			}
			
			new TaskModel().completeTaskByProjectID(id, frame);
			new SubtaskModel().completeSubtaskByProjectID(id, frame);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Get all projects associated with a User
	 * @param id ID of User
	 * @return
	 */
	public ArrayList<Project> getUserProjects(int id){
		
		ArrayList<Project> projects = new ProjectDB().query("SELECT Project.projectID, Project.projName, Project.startDate, " +
				"Project.endDate, Project.description, Project.reqOrg, Project.createdBy," +
				" Project.unitID, Project.completed, Project.deleted, Project.expectedEndDate, Project.leadAnalyst" +
				" FROM Project INNER JOIN UserProject ON Project.projectID = UserProject.projectID" +
				" WHERE UserProject.userID = " + id + " AND Project.completed = no AND Project.deleted = no " +
				" UNION SELECT * FROM Project WHERE Project.createdBy = " + id + " AND Project.completed = no AND" +
				" Project.deleted = no ORDER BY expectedEndDate");
		

		
		if(projects == null)
			projects = new ArrayList<Project>();
		return projects;
	}
	
	/**
	 * Get Project by ID
	 * 
	 * @param id Id of project
	 * @return
	 */
public Project getProjectByID(int id){
		
		ArrayList<Project> projects = new ProjectDB().query("SELECT *" +
				" FROM Project WHERE projectID = " + id);
		
		projects.get(0).setCreatedBy(new UserModel().getUserByID(projects.get(0).getCreatedBy().getUserID()));
		
		return projects.get(0);
	}
}
