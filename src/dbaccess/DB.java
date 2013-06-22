package dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import exceptions.EmptySetException;

public abstract class DB {
	
	Connection conn;
	Statement s;

	/**
	 * Connect through the JdbcOdbcDriver to the Access Database. 
	 * 
	 * @return Statement Contains a statement of the DB.
	 */
	protected Statement connect(){
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=U:\\ProjectMgmt.accdb;";

			conn=DriverManager.getConnection(database, "", "");  
			
			return conn.createStatement();

			//		} catch (ClassNotFoundException || SQLException e) {
			//			// TODO Auto-generated catch block
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean insert(String sql) throws SQLException{
		//Connect to database
		s = connect();

		//If S is null, an error has occurred in the SQL statement.
		if(s!=null){
			try {
				s.execute(sql);
				
				//Close database connections.
				s.close();
				conn.close();

				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

		}else{
			//An error occurred in the SQL Statement
			throw new EmptySetException();
		}
	}
	
	/**
	 * Update Task Database with passed SQL statement.
	 * 
	 * @param sql Statement to be performed
	 * @return boolean Did the update pass?
	 * @throws SQLException
	 */
	public boolean update(String sql) throws SQLException{
		//Connect to database
				s = connect();

				//If S is null, an error has occurred in the SQL statement.
				if(s!=null){
					try {
						s.execute(sql);
						
						//Close database connections.
						s.close();
						conn.close();

						return true;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}

				}else{
					//An error occurred in the SQL Statement
					throw new EmptySetException();
				}
	}

}
