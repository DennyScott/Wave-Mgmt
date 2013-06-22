package models;

import java.sql.SQLException;
import java.util.ArrayList;

import structures.DataDelivered;
import dbaccess.DRDDDB;
import dbaccess.DataDeliveredDB;

public class DataDeliveredModel {

	public ArrayList<DataDelivered> getDataByDRDDID(int id){
		ArrayList<DataDelivered> data;
		
		data = new DataDeliveredDB().query("SELECT * FROM DataDelivered WHERE DRDDID = " + id);
		
		if(data == null){
			data = new ArrayList<DataDelivered>();
		}
		
		return data;
	}
	
	public DataDelivered getDataByID(int id){
		ArrayList<DataDelivered> data;
		
		data = new DataDeliveredDB().query("SELECT * FROM DataDelivered WHERE fileID = " + id);
		
		if(data == null){
			return null;
		}
		
		return data.get(0);
	}
	
	public boolean addNewData(int id, String text){
		boolean success = false;
		
		try {
			success = new DataDeliveredDB().insert("INSERT INTO DataDelivered(DRDDID, data) VALUES(" + id + ", '" + text + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public boolean addNewDataDescription(int id, String text){
		boolean success = false;
		
		try {
			success = new DataDeliveredDB().insert("UPDATE DataDelivered SET description = '" + text + "' WHERE dataDeliveredID = " + id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
}
