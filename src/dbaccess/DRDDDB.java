package dbaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import structures.DRDD;
import structures.FFLocations;
import structures.User;
import exceptions.EmptySetException;

public class DRDDDB extends DB{
	
	/**
	 * Run the passed SQL statement through the connected Database.
	 * @param sql statement to be run
	 * @return A result set, that the data can be extracted from
	 * @throws EmptySetException An error has occurred in the SQL statement.
	 */
	public ArrayList<DRDD> query(String sql) throws EmptySetException{


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
				ArrayList<DRDD> drdd = new ArrayList<DRDD>();

				//Add Initial Pass-through
				drdd.add(collectData(r));

				//Add any further Pass-throughs
				while(r.next()){
					drdd.add(collectData(r));

				}
				
				//Close database connections.
				s.close();
				conn.close();

				return drdd;
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

	private DRDD collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into DRDD Object
		DRDD drdd = new DRDD();
		
		drdd.setId(Integer.parseInt(r.getString(1)));
		drdd.setProjectID(Integer.parseInt(r.getString(2)));
		drdd.setActionRequested(r.getString(3));
		drdd.setWorkPerformed(r.getString(4));
		
		int temp = Integer.parseInt(r.getString(5));
		
		User user = new User();
		user.setUserID(temp);
		drdd.setLastEditedBy(user);
		
		//Return DRDD
		return drdd;
	}

}
