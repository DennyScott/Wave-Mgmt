package models;

import java.sql.SQLException;
import java.util.ArrayList;

import structures.Attachment;
import dbaccess.AttachmentDB;

public class AttachmentModel {
	
	public ArrayList<Attachment> getAttachmentsByTaskID(int id){
		ArrayList<Attachment> attachments = new AttachmentDB().query("SELECT * FROM Attachments WHERE Attachments.taskID = " + id + "AND Attachments.deleted = no");
		
		if(attachments == null){
			attachments = new ArrayList<Attachment>();
		}
		for(Attachment a: attachments){
			a.setLoadedBy(new UserModel().getUserByID(a.getLoadedBy().getUserID()));
		}
		
		
		
		return attachments;
	}
	
	public boolean deleteAttachmentByID(int id){
		boolean success = false;
		
		try {
			success = new AttachmentDB().update("UPDATE Attachments SET deleted = yes WHERE attachmentsID = " + id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	public Attachment getAttachmentByID(int id){
		return new AttachmentDB().query("SELECT * FROM Attachments WHERE attachmentsID = "+ id + "AND deleted = no " ).get(0);
	}
	
	public boolean insertAttachment(String name, int loadedBy, int taskID, String link){
		boolean success = false;
		
		try {
			success = new AttachmentDB().insert("INSERT INTO Attachments(taskID, loadedBy, link, title) VALUES (" + taskID +
					", " + loadedBy + ", '" + link + "', '" + name + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return success;
	}
	
	public Object[][] attachmentToTable(ArrayList<Attachment> attachments){
		
		Object[][] data;
		
		
		
		//If no data was found in database
		if(attachments == null){
			data = new Object[0][4];
		}
		else{
		data = new Object[attachments.size()][4];
		
		for(int i = 0; i<attachments.size(); i++){
			Attachment a = attachments.get(i);
			
			data[i][0] = a.getTitle();
			data[i][1] = a.getLink();
			data[i][2] = a.getLoadedBy().getFirstName() + " " + a.getLoadedBy().getLastName();
			data[i][3] = a.getAttachmentID();
		}
		
		
	}
		return data;
}
}
