package dbaccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import exceptions.EmptySetException;

import structures.Role;
import structures.User;

public class UserDB extends DB {

	/**
	 * Run the passed SQL statement through the connected Database.
	 * @param sql statement to be run
	 * @return A result set, that the data can be extracted from
	 * @throws EmptySetException An error has occurred in the SQL statement.
	 */
	public ArrayList<User> query(String sql) throws EmptySetException{


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
				ArrayList<User> us = new ArrayList<User>();

				//Add Initial Pass-through
				us.add(collectData(r));

				//Add any further Pass-throughs
				while(r.next()){
					us.add(collectData(r));

				}
				
				//Close database connections.
				s.close();
				conn.close();

				return us;
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

	private User collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into User Object
		User u = new User();
		u.setUserID(Integer.parseInt(r.getString(1)));
		u.setFirstName(r.getString(2));
		u.setLastName(r.getString(3));
		u.setUsername(r.getString(4));
		int roleNum = Integer.parseInt(r.getString(6));

		//Create the role aggregation, filling out only the role id.
		Role role = new Role();
		role.setRoleID(roleNum);
		u.setRole(role);

		//Return User
		return u;
	}



}
