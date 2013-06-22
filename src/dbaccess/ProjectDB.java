package dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import structures.Project;
import structures.Role;
import structures.User;
import exceptions.EmptySetException;

public class ProjectDB extends DB{

	/**
	 * Run the passed SQL statement through the connected Database.
	 * @param sql statement to be run
	 * @return A result set, that the data can be extracted from
	 * @throws EmptySetException An error has occurred in the SQL statement.
	 */
	public ArrayList<Project> query(String sql) throws EmptySetException{


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
				ArrayList<Project> projects = new ArrayList<Project>();

				//Add Initial Pass-through
				projects.add(collectData(r));

				//Add any further Pass-throughs
				while(r.next()){
					projects.add(collectData(r));

				}
				
				//Close database connections.
				s.close();
				conn.close();

				return projects;
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

	private Project collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into Project Object
		Project project = new Project();
		project.setProjectID(Integer.parseInt(r.getString(1)));
		project.setProjectName(r.getString(2));
		project.setStartDate(r.getString(3));
		project.setEndDate(r.getString(4));
		project.setDescription(r.getString(5));
		project.setReqOrg(r.getString(6));
		
		User temp = new User();
		temp.setUserID(Integer.parseInt(r.getString(7)));
		project.setCreatedBy(temp);
		
		project.setUnitID(Integer.parseInt(r.getString(8)));
		
		String temp1 = r.getString(9);
		
		project.setCompleted(temp1.equals("yes")?true:false);
		
		temp1 = r.getString(10);
		
		project.setDeleted(temp1.equals("yes")?true:false);
		
		project.setExpectedEndDate(r.getString(11));
		
		project.setLeadAnalyst(Integer.parseInt(r.getString(12)));

		//Return Project
		return project;
	}



}


