package models;

import java.util.ArrayList;

import dbaccess.RoleDB;
import structures.Role;

public class RoleModel {
	
	public Role getRoleByID(int id){
		RoleDB rdb = new RoleDB();
		ArrayList<Role> roles = rdb.query("Select * FROM Role WHERE roleID=" + id);
		
		return roles.get(0);
	}
	
	public ArrayList<Role> getAllRoles(){
		return new RoleDB().query("SELECT * FROM Role");
	}

}
