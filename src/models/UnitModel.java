package models;

import java.sql.SQLException;
import java.util.ArrayList;

import structures.Unit;
import dbaccess.UnitDB;

public class UnitModel {

	public ArrayList<Unit> getUnits(){
		ArrayList<Unit> units = new UnitDB().query("SELECT * FROM Unit");
		return units;
	}
	
	public Unit getUnitByID(int id){
		return new UnitDB().query("SELECT * FROM Unit WHERE unitID = " + id).get(0);
	}
	
	public boolean addUnit(String name){
		boolean success = false;
		
		try {
			success = new UnitDB().insert("INSERT INTO Unit(unitName) VALUES('" + name + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return success;
	}
}
