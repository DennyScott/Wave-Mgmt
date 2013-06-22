package models;

import java.sql.SQLException;
import java.util.ArrayList;


import structures.FileAndFolderLocation;
import dbaccess.DataDeliveredDB;
import dbaccess.FileAndFolderLocationDB;

public class FileAndFolderLocationModel {
	
	public ArrayList<FileAndFolderLocation> getFileByDRDDID(int id){
		ArrayList<FileAndFolderLocation> locations;
		
		locations = new FileAndFolderLocationDB().query("SELECT * FROM FileAndFolderLocations WHERE DRDDID = " + id);
		
		if(locations == null){
			locations = new ArrayList<FileAndFolderLocation>();
		}else{
			for(FileAndFolderLocation location: locations){
				location.setData(new FFLocationsModel().getLocationsByFileID(location.getId()));
			}
		}
		
		return locations;
	}
	
	public FileAndFolderLocation getFileByID(int id){
		ArrayList<FileAndFolderLocation> locations;
		
		locations = new FileAndFolderLocationDB().query("SELECT * FROM FileAndFolderLocations WHERE fileID = " + id);
		
		if(locations == null){
			return null;
		}else{
			for(FileAndFolderLocation location: locations){
				location.setData(new FFLocationsModel().getLocationsByFileID(location.getId()));
			}
		}
		
		return locations.get(0);
	}
	
	public boolean addNewOS(int id, String text){
		boolean success = false;
		
		try {
			success = new FileAndFolderLocationDB().insert("INSERT INTO FileAndFolderLocations(DRDDID, os) VALUES(" + id + ", '" + text + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public boolean updateWorkingFolder(int id, String text){
		boolean success = false;
		
		try {
			success = new FileAndFolderLocationDB().insert("UPDATE FileAndFolderLocations SET workingFolder = '" + text + "' WHERE fileID = " + id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	

}
