package dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import structures.Task;
import structures.User;
import exceptions.EmptySetException;

public class TaskIFDB extends DB{

	/**
	 * Run the passed SQL statement through the connected Database.
	 * @param sql statement to be run
	 * @return A result set, that the data can be extracted from
	 * @throws EmptySetException An error has occurred in the SQL statement.
	 */
	public ArrayList<Task> query(String sql) throws EmptySetException{


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
				ArrayList<Task> tasks = new ArrayList<Task>();

				//Add Initial Pass-through
				tasks.add(collectData(r));

				//Add any further Pass-throughs
				while(r.next()){
					tasks.add(collectData(r));

				}
				
				//Close database connections.
				s.close();
				conn.close();

				return tasks;
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
	 * Collect the data from the result set, and place into the specified structure object.
	 * 
	 * @param r Result set to extract data from
	 * @return Task Structure collected
	 * @throws SQLException
	 */
	private Task collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into Project Object
		Task task = new Task();
		task.setTaskID(Integer.parseInt(r.getString(1)));
		task.setProjectID(Integer.parseInt(r.getString(2)));
		task.setStartDate(r.getString(3));
		task.setDescription(r.getString(5));
		int requestedBy = Integer.parseInt(r.getString(6));
		int assignedTo = Integer.parseInt(r.getString(7));
		task.setEndDate(r.getString(4));
		task.setTaskName(r.getString(8));
		
		String temp = r.getString(9);
		
		task.setCompleted(temp.equals("yes")?true:false);
		
		temp = r.getString(10);
		
		task.setDeleted(temp.equals("yes")?true:false);
		
		User rTemp = new User();
		rTemp.setUserID(requestedBy);
		
		User aTemp = new User();
		aTemp.setUserID(assignedTo);
		
		task.setAssignedTo(aTemp);
		task.setRequestedBy(rTemp);
	

		//Return Project
		return task;
	}
}
