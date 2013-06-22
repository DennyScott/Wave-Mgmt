package models;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import aurelienribon.slidinglayout.demo.TheFrame;

import data.ProjectSingleton;
import data.UserSingleton;
import dbaccess.ProjectDB;
import dbaccess.UserDB;

import structures.Project;
import structures.User;

public class UserModel {
	/**
	 * Run Login Sequence
	 * 
	 * @param username Username of desired user
	 * @param password Password of desired User
	 * @return The User structure, that will be used in accessing there data and permissions.
	 */
	public User signIn(String username, String password){
		UserDB udb = new UserDB();
		
		try{
			ArrayList<User> users = udb.query("SELECT * FROM USER WHERE username='"+ username +"' AND password='"+ password +"'");

			//If result were found
			if(users!=null){
				User user = users.get(0);
				
				//Set Role based on the ID stored in the RoleID
				user.setRole(new RoleModel().getRoleByID(user.getRole().getRoleID()));
				UserSingleton.setInstance(user);
				return user;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

		//Results were not found;
		return null;
	}
	
	public User getLeadAnalyst(int projectID){
		ArrayList<User> user = new UserDB().query("SELECT *" +
				" FROM User INNER JOIN UserProject ON User.userID = UserProject.userID" +
				" WHERE UserProject.projectID = " + projectID + " AND UserProject.leadAnalyst = yes");
		
		if(user.size()==0){
			return null;
		}
		
		return user.get(0);
	}
	
	public Object[][] getAnalysts(){
		ArrayList<User> users = new UserDB().query("SELECT * FROM User");
		
		Object[][] data;
		if(users == null){
			data = new Object[0][5];
		}else{
			for(User roleUser: users){
				roleUser.setRole(new RoleModel().getRoleByID(roleUser.getRole().getRoleID()));
			}
		}
		
		ArrayList<User> currentAnalysts = new UserDB().query("SELECT *" +
				" FROM User INNER JOIN UserProject ON User.userID = UserProject.userID" +
				" WHERE UserProject.projectID = " + ProjectSingleton.getInstance().getProjectID());
		
		
		
		if(currentAnalysts == null){
			currentAnalysts = new ArrayList<User>();
		}
		
		
		//If no data was found in database
		if(users == null){
			data = new Object[0][5];
		}
		else{
		data = new Object[users.size()][5];
		
		
		for(int i = 0; i<users.size(); i++){
			User user = users.get(i);
			boolean check = false;
			
			for(User cA : currentAnalysts){
				if(cA.getUserID() == user.getUserID()){
					check = true;
					break;
				}
			}
		
			data[i][0] = check;
			data[i][1] = user.getFirstName();
			data[i][2] = user.getLastName();
			data[i][3] = user.getRole().getRoleName();
			data[i][4] = user.getUserID();
			
		}	

	}
		
		return data;
	}
	
	public ArrayList<User> getAllUsersOnProject(int id){
		ArrayList<User> users = new UserDB().query("SELECT *" +
				" FROM User INNER JOIN UserProject ON User.userID = UserProject.userID" +
				" WHERE UserProject.projectID = " + id);
		
		if(users==null)
			users = new ArrayList<User>();
		return users;
	}
	
	public void addUser(String firstName, String lastName, String userName, String password, int roleID, TheFrame frame){
		String sql = "INSERT INTO User (firstName, lastName, username, password, roleID)  VALUES ('" + 
				firstName + "', '" + lastName + "', '" + userName + "', '" + password + "', " + roleID + ");";
		boolean success = false;
		try {
			success = new UserDB().insert(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(success){
			JOptionPane.showMessageDialog(frame, "User Added!");
		}else{
			JOptionPane.showMessageDialog(frame, "There was error while adding the User.");
		}
		
	}
	
	/**
	 * Get a user based on there ID Value.
	 * 
	 * @param id ID to query the DB with
	 * @return The Found User
	 */
	public User getUserByID(int id){
		
		UserDB udb = new UserDB();
		
		ArrayList<User> users = udb.query("SELECT * FROM User WHERE userID= " + id);

		//If result were found
		if(users!=null){
			User user = users.get(0);
			
			//Set Role based on the ID stored in the RoleID
			user.setRole(new RoleModel().getRoleByID(user.getRole().getRoleID()));
			return user;
		}
		
		
	
	//Results were not found;
	return null;
}
	
	public boolean insertAnalysts(int id){
		boolean success = false;
		try {
			success = new UserDB().update("INSERT INTO UserProject (userID, projectID)  VALUES (" + 
					id + ", " + ProjectSingleton.getInstance().getProjectID() + "); ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		if(success)
			return true;
		return false;
	}
	
	public boolean deleteAnalysts(int id){
		boolean success = false;
		try {
			success = new UserDB().update("DELETE FROM UserProject WHERE UserProject.UserID = " + id + " AND UserProject.projectID = " + 
		ProjectSingleton.getInstance().getProjectID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		if(success)
			return true;
		return false;
	}
	
	public ArrayList<User> getAllUsers(){
		ArrayList<User> users = new UserDB().query("SELECT * From User");
		
		if(users == null){
			users = new ArrayList<User>();
		}
		
		return users;
	}

}
