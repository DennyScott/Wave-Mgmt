package dbaccess;
	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

	import exceptions.EmptySetException;

	import structures.Role;

public class RoleDB extends DB {
	

		
		/**
		 * Run the passed SQL statement through the connected Database.
		 * @param sql statement to be run
		 * @return A result set, that the data can be extracted from
		 * @throws EmptySetException An error has occurred in the SQL statement.
		 */
		public ArrayList<Role> query(String sql) throws EmptySetException{
			
			
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
						ArrayList<Role> role = new ArrayList<Role>();
						
						//Collect the first pass-through of data
						role.add(collectData(r));
						
						//Collect any further data
						while(r.next()){
							role.add(collectData(r));
						}

						//Close database connections.
						s.close();
						conn.close();
						
					return role;
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
		
		/**
		 * Collect a Result set of data into a temp Role structure.
		 * 
		 * @param r Result set to collect data from
		 * @return Role collected data
		 * @throws SQLException
		 */
		private Role collectData(ResultSet r)throws SQLException{
			Role temp = new Role();
			temp.setRoleID(Integer.parseInt(r.getString(1)));
			temp.setRoleName(r.getString(2));
			temp.setRoleDescription(r.getString(3));
			return temp;
		}
		
		

	}

