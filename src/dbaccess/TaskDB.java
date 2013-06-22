package dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import structures.Project;
import structures.Task;
import structures.User;
import exceptions.EmptySetException;

public class TaskDB extends DB{


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
		
		int temp = Integer.parseInt(r.getString(9));
		
		task.setCompleted(temp==1?true:false);
		
		temp = Integer.parseInt(r.getString(10));
		
		task.setDeleted(temp==1?true:false);
		
		User rTemp = new User();
		rTemp.setUserID(requestedBy);
		
		User aTemp = new User();
		aTemp.setUserID(assignedTo);
		
		task.setAssignedTo(aTemp);
		task.setRequestedBy(rTemp);
		
		task.setExpectedEndDate(r.getString(11));
	

		//Return Project
		return task;
	}
	
	


}
