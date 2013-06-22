package dbaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import structures.Project;
import structures.Unit;
import structures.User;
import exceptions.EmptySetException;

public class UnitDB extends DB{
	
	public ArrayList<Unit> query(String sql) throws EmptySetException{


		//Connect to database
		s = connect();

		//If S is null, an error has occurred in the SQL statement.
		if(s!=null){
			try {
				s.execute(sql);
				ResultSet r = s.getResultSet();

				if (!r.next()){
					return null;
				}

				//Create Structures
				ArrayList<Unit> units = new ArrayList<Unit>();

				//Add Initial Pass-through
				units.add(collectData(r));

				//Add any further Pass-throughs
				while(r.next()){
					units.add(collectData(r));

				}
				
				//Close database connections.
				s.close();
				conn.close();

				return units;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			//An error occurred in the SQL Statement
			throw new EmptySetException();
		}
		return null;
	}

	private Unit collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into Project Object
		Unit unit = new Unit();
		
		unit.setUnitID(Integer.parseInt(r.getString(1)));
		unit.setUnitName(r.getString(2));

		//Return Project
		return unit;
	}

}
