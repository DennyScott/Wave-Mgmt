package dbaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import structures.Comment;
import structures.Project;
import structures.User;
import exceptions.EmptySetException;

public class SubtaskCommentDB extends DB{

	/**
	 * Run the passed SQL statement through the connected Database.
	 * @param sql statement to be run
	 * @return A result set, that the data can be extracted from
	 * @throws EmptySetException An error has occurred in the SQL statement.
	 */
	public ArrayList<Comment> query(String sql) throws EmptySetException{


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
				ArrayList<Comment> comments = new ArrayList<Comment>();

				//Add Initial Pass-through
				comments.add(collectData(r));

				//Add any further Pass-throughs
				while(r.next()){
					comments.add(collectData(r));

				}
				
				//Close database connections.
				s.close();
				conn.close();

				return comments;
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

	private Comment collectData(ResultSet r) throws SQLException{

		//Collect data from database result set into Comment Object
		Comment comment = new Comment();
		comment.setCommentID(Integer.parseInt(r.getString(1)));
		comment.setTaskID(Integer.parseInt(r.getString(2)));
		comment.setComment(r.getString(3));
		
		User user =new User();
		user.setUserID(Integer.parseInt(r.getString(4)));
		comment.setCreatedBy(user);
		
		comment.setPostedAt(r.getString(5));

		//Return Comment
		return comment;
	}
}
