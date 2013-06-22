package models;

import java.sql.SQLException;
import java.util.ArrayList;

import structures.FFLocations;

import dbaccess.DataDeliveredDB;
import dbaccess.FFLocationsDB;

public class FFLocationsModel {

	public ArrayList<FFLocations> getLocationsByFileID(int id){
		ArrayList<FFLocations> locations;
		
		locations = new FFLocationsDB().query("SELECT * FROM FFLocations WHERE fileID = " + id);
		
		if(locations == null){
			locations = new ArrayList<FFLocations>();
		}
		
		return locations;
	}
	
	public FFLocations getLocationByID(int id){
		ArrayList<FFLocations> locations;
		
		locations = new FFLocationsDB().query("SELECT * FROM FFLocations WHERE ffID = " + id);
		
		if(locations == null){
			return null;
		}
		
		return locations.get(0);
	}
	
	public boolean addNewFolder(String location, String description, int id){
		boolean success = false;
		
		try {
			success = new DataDeliveredDB().insert("INSERT INTO FFLocations(fileID, location, description) " +
					"VALUES(" + id + ", '" + location + "', '"+ description + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
}
