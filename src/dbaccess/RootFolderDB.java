package dbaccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import structures.RootFolder;

import exceptions.EmptySetException;

public class RootFolderDB extends DB {

	/**
	 * Run the passed SQL statement through the connected Database.
	 * @param sql statement to be run
	 * @return A result set, that the data can be extracted from
	 * @throws EmptySetException An error has occurred in the SQL statement.
	 */
	public RootFolder query(String sql) throws EmptySetException{


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
				RootFolder rf = collectData(r);

				
				//Close database connections.
				s.close();
				conn.close();

				return rf;
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

	private RootFolder collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into Project Object
		

		//Return Project
		RootFolder rf = new RootFolder();
		rf.setRootID(Integer.parseInt(r.getString(1)));
		rf.setRootFolder(r.getString(2));
		rf.setCurrent(r.getString(3).equals("yes")?true:false);
		
		return rf;
	}
	
}
