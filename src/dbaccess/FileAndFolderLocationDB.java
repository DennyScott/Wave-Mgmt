package dbaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import structures.FFLocations;
import structures.FileAndFolderLocation;
import exceptions.EmptySetException;

public class FileAndFolderLocationDB extends DB{
	/**
	 * Run the passed SQL statement through the connected Database.
	 * @param sql statement to be run
	 * @return A result set, that the data can be extracted from
	 * @throws EmptySetException An error has occurred in the SQL statement.
	 */
	public ArrayList<FileAndFolderLocation> query(String sql) throws EmptySetException{


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
				ArrayList<FileAndFolderLocation> locations = new ArrayList<FileAndFolderLocation>();

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

	private FileAndFolderLocation collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into FFLocations Object
		FileAndFolderLocation location = new FileAndFolderLocation();
		
		location.setId(Integer.parseInt(r.getString(1)));
		location.setDDRDID((Integer.parseInt(r.getString(2))));
		location.setOs(r.getString(3));
		location.setWorkingLocation(r.getString(4));
		
		//Return FFLocations
		return location;
	}

}
