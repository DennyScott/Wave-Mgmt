package dbaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import structures.Attachment;
import structures.Project;
import structures.User;
import exceptions.EmptySetException;

public class AttachmentDB extends DB{

	/**
	 * Run the passed SQL statement through the connected Database.
	 * @param sql statement to be run
	 * @return A result set, that the data can be extracted from
	 * @throws EmptySetException An error has occurred in the SQL statement.
	 */
	public ArrayList<Attachment> query(String sql) throws EmptySetException{


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
				ArrayList<Attachment> attachments = new ArrayList<Attachment>();

				//Add Initial Pass-through
				attachments.add(collectData(r));

				//Add any further Pass-throughs
				while(r.next()){
					attachments.add(collectData(r));

				}
				
				//Close database connections.
				s.close();
				conn.close();

				return attachments;
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

	private Attachment collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into Project Object
		Attachment attachment = new Attachment();
		attachment.setAttachmentID(Integer.parseInt(r.getString(1)));
		attachment.setTaskID(Integer.parseInt(r.getString(2)));
		attachment.setLink(r.getString(4));
		attachment.setTitle(r.getString(5));
		
		User temp = new User();
		temp.setUserID(Integer.parseInt(r.getString(3)));
		attachment.setLoadedBy(temp);
		

		//Return Project
		return attachment;
	}

}
