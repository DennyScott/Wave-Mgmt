package dbaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import structures.DataDelivered;
import structures.FileAndFolderLocation;
import exceptions.EmptySetException;

public class DataDeliveredDB extends DB{
	/**
	 * Run the passed SQL statement through the connected Database.
	 * @param sql statement to be run
	 * @return A result set, that the data can be extracted from
	 * @throws EmptySetException An error has occurred in the SQL statement.
	 */
	public ArrayList<DataDelivered> query(String sql) throws EmptySetException{


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
				ArrayList<DataDelivered> locations = new ArrayList<DataDelivered>();

				//Add Initial Pass-through
				locations.add(collectData(r));

				//Add any further Pass-throughs
				while(r.next()){
					locations.add(collectData(r));

				}
				
				//Close database connections.
				s.close();
				conn.close();

				return locations;
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

	private DataDelivered collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into DataDelivered Object
		DataDelivered location = new DataDelivered();
		
		location.setId(Integer.parseInt(r.getString(1)));
		location.setDDRDID((Integer.parseInt(r.getString(2))));
		location.setData(r.getString(3));
		location.setDescription(r.getString(4));
		
		//Return DataDelivered
		return location;
	}
}
