package controllers;


import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.ProjectSingleton;

import aurelienribon.slidinglayout.demo.TheFrame;

import models.UserModel;

import structures.Role;
import structures.User;
import views.Login;
import views.MainWindow;

public class UserController implements UserIF{
	
	
	
	Login signin;
	UserModel um;
	PagesController pc;
	
	public UserController(PagesController pc){
		this.pc = pc;
		um = new UserModel();
	}
	
	@Override
	public boolean add(String firstName, String lastName, String username,
			String password, int roleID, TheFrame frame) {
		if(firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()){
			JOptionPane.showMessageDialog(pc.getFrame(), "Please Fill in All Fields.");
			return false;
		}else{
			um.addUser(firstName, lastName, username, password, roleID, frame);
			frame.createAnalystTable();
			return true;
		}
		
	}

	@Override
	public void remove(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String username, String firstName, String lastName,
			String password, int roleID) {
		// TODO Auto-generated method stub
		
	}
	

	
	public ArrayList<Object> getProjectAnalysts(){
		ArrayList<User> users = um.getAllUsersOnProject(ProjectSingleton.getInstance().getProjectID());
		ArrayList<Object> temp = new ArrayList<Object>();
		String[] names = new String[users.size()];
		
		for(int i = 0; i<users.size(); i++){
			User user = users.get(i);
			names[i] = user.getFirstName() + " " + user.getLastName();
		}
		
		
		temp.add(users);
		temp.add(names);
		
		return temp;
	}
	
	public ArrayList<Object> getUsers(){
		ArrayList<User> users = um.getAllUsers();
		ArrayList<Object> holder = new ArrayList<Object>();
		holder.add(users);
		
		String[] names = new String[users.size()];
		
		for(int i=0; i<names.length; i++){
			names[i] = users.get(i).getFirstName() + " " + users.get(i).getLastName();
		}
		holder.add(names);
		
		return holder;
	}
	
	/**
	 * Update the UserProjects for analysts
	 */
	public void updateAnalysts(ArrayList<Object[]> values) {
		// TODO Auto-generated method stub
		Object[][] users = um.getAnalysts();
		
		for(int i = 0; i<values.size(); i++){
			for(int j = 0; j<users.length; j++){
				if(values.get(i)[1] == users[j][4]){
					if(values.get(i)[0] != users[j][0]){
						if((boolean)values.get(i)[0]){
							um.insertAnalysts((int)values.get(i)[1]);
						}else{
							um.deleteAnalysts((int)values.get(i)[1]);
						}
						break;
					}else{
						break;
					}
				}
			}
		}
	}
	
	
	public void launch(){
		signin = new Login(this);
		signin.launch();
		
	}
	
	public Object[][] getAnalysts(){
		return um.getAnalysts();
	}

	@Override
	public void signIn(String username, String password) {
		//check if data is filled
		boolean checkFields = checkText(username, password);
		
		//If textfields have content
		if(checkFields){
			
			//Get User Data
			User you = um.signIn(username,password);

			//The signin failed
			if(you==null){
				JOptionPane.showMessageDialog(null, "The Username or Password you entered is incorrect");
				
			//The sign in was a success	
			}else{
				
				pc.signSuccess();
				
			}
		//If textfields do not have content	
		}else{
			JOptionPane.showMessageDialog(null, "Please enter both a username and a password.");
		}
		
	}
	
	/**
	 * Check that both username and password have a String within the textfields.
	 * 
	 * @return boolean, false if either textfields are empty.
	 */
	private boolean checkText(String uName, String pass){
		
		if(uName.isEmpty() || pass.isEmpty()){
			return false;
		}else{
			return true;
		}
		
	}

}
