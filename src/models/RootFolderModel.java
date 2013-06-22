package models;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import aurelienribon.slidinglayout.demo.TheFrame;

import structures.RootFolder;
import dbaccess.RootFolderDB;

public class RootFolderModel {
	
	public String getRootFolder(){
		String sql = "SELECT * FROM RootFolder WHERE current = yes";
		
		RootFolder rf = new RootFolderDB().query(sql);
		if(rf == null){
			return "";
		}
		String result = rf.getRootFolder();

		return result;
	}
	
	public boolean insertRootFolder(TheFrame frame, String rootFolder){
		String sql = "SELECT * FROM RootFolder WHERE current = yes";
		RootFolder result = new RootFolderDB().query(sql);
		boolean first = true;
		if(result!=null){
		 first = updateRootFolder(result.getRootID(), frame);
		}
		boolean success = false;
		
		if(first){
		 try {
			success = new RootFolderDB().insert("INSERT INTO RootFolder(rootFolder, current) VALUES('" + rootFolder + "', yes)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return success;
	}
	
	public boolean updateRootFolder(int id, TheFrame frame){
		boolean success = false;
		try {
			success = new RootFolderDB().update("UPDATE RootFolder SET current = no WHERE rootID = " + id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!success){
			JOptionPane.showMessageDialog(frame, "There was an error while updating the root folder.");
		}
		return success;
	}

}
