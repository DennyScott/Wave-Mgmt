package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import structures.DRDD;
import structures.FileAndFolderLocation;
import dbaccess.DRDDDB;
import dbaccess.FileAndFolderLocationDB;

import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.elements.Heading1;
import word.w2004.elements.Paragraph;

public class DRDDModel {

	public void exportDRDD(){
		IDocument doc = new Document2004();
		doc.getBody().addEle(Heading1.with("Heading01").create());
		doc.getBody().addEle(Paragraph.with("This document is an example of paragraph").create());
		
		File fileObj = new File("Java2word_allInOne.doc");

		PrintWriter writer = null;
		try {
		    writer = new PrintWriter(fileObj);
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}
		String myWord = doc.getContent();

		writer.println(myWord);
		writer.close(); 
		
	}
	
	public DRDD getDRDDByProjectID(int id){
			ArrayList<DRDD> drdd;
			
			drdd = new DRDDDB().query("SELECT * FROM DRDD WHERE projectID = " + id);
			
			if(drdd == null){
				return null;
			}else{
				for(DRDD d : drdd){
					d.setLastEditedBy(new UserModel().getUserByID(d.getLastEditedBy().getUserID()));
					d.setDataDelivered(new DataDeliveredModel().getDataByDRDDID(d.getId()));
					d.setFileAndFolder(new FileAndFolderLocationModel().getFileByDRDDID(d.getId()));
				}
			}
			
			return drdd.get(0);
		}
		
		public DRDD getDRDDByID(int id){
			ArrayList<DRDD> drdd;
			
			drdd = new DRDDDB().query("SELECT * FROM DRDD WHERE DRDDID = " + id);
			
			if(drdd == null){
				return null;
			}else{
				for(DRDD d : drdd){
					d.setLastEditedBy(new UserModel().getUserByID(d.getLastEditedBy().getUserID()));
					d.setDataDelivered(new DataDeliveredModel().getDataByDRDDID(d.getId()));
					d.setFileAndFolder(new FileAndFolderLocationModel().getFileByDRDDID(d.getId()));
				}
			}
			
			return drdd.get(0);
		}
		
		public boolean updateWorkPerformed(int id, String text){
			boolean success = false;
			
			try {
				success = new DRDDDB().update("UPDATE DRDD SET workPer = '" + text + "' WHERE projectID = " + id );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			return success;
		}
		
		public boolean updateActionRequested(int id, String text){
			boolean success = false;
			
			try {
				success = new DRDDDB().update("UPDATE DRDD SET actionRequested = '" + text + "' WHERE projectID = " + id );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
			return success;
		}
	
}
