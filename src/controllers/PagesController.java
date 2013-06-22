package controllers;

import java.awt.Dialog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import com.ibm.icu.util.Calendar;

import components.TreeNode;

import data.CompletedSingleton;
import data.ProjectSingleton;
import data.UserSingleton;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.demo.TheFrame;
import aurelienribon.slidinglayout.demo.ThePanel;
import aurelienribon.tweenengine.Tween;
import models.AttachmentModel;
import models.CollectTasks;
import models.DRDDModel;
import models.DataDeliveredModel;
import models.FFLocationsModel;
import models.FileAndFolderLocationModel;
import models.ProjectModel;
import models.RoleModel;
import models.RootFolderModel;
import models.SubtaskCommentModel;
import models.SubtaskModel;
import models.TaskCommentModel;
import models.TaskModel;
import models.UnitModel;
import structures.Attachment;
import structures.Comment;
import structures.DRDD;
import structures.Project;
import structures.Role;
import structures.Subtask;
import structures.Task;
import structures.TaskIF;
import structures.Unit;
import structures.User;
import views.MainWindow;

public class PagesController {

	TheFrame frame;
	ProjectModel pm;
	TaskModel tm;
	SubtaskModel sm;
	UnitModel um;
	RoleModel rm;
	UserController uc;
	AttachmentModel am;
	SubtaskCommentModel scm;
	TaskCommentModel tcm;
	DRDDModel drdd;
	RootFolderModel rfm;
	
	public void launch(){
		
		pm = new ProjectModel(frame);
		tm = new TaskModel();
		sm = new SubtaskModel();
		um = new UnitModel();
		rm = new RoleModel();
		uc = new UserController(this);
		am = new AttachmentModel();
		scm = new SubtaskCommentModel();
		tcm = new TaskCommentModel();
		drdd = new DRDDModel();
		rfm = new RootFolderModel();
		
		
		Tween.registerAccessor(ThePanel.class, new ThePanel.Accessor());
		SLAnimator.start();
		frame = new TheFrame(this,uc);
		frame.setVisible(true);
	
		
		 
	}
	
	public void signSuccess(){
		frame.loadUser();
		
		frame.userLogIn();
	}
	
	public DRDD getDRDD(){
		return drdd.getDRDDByProjectID(ProjectSingleton.getInstance().getProjectID());
	}
	
	public void removeProject(int projectID, JTable table){
		Project project = pm.getProjectByID(projectID);
		if(project.getCreatedBy().getUserID() == UserSingleton.getInstance().getUserID()){
			JOptionPane.showMessageDialog(frame, "The Project was Deleted.");
			pm.removeProject(projectID);
			removeRow(table);
		}else{
			JOptionPane.showMessageDialog(frame, "You do not have permission to delete this.");
		}
	}
	
	public void completeProject(int projectID, JTable table){
		Project project = pm.getProjectByID(projectID);
		if(project.getCreatedBy().getUserID() == UserSingleton.getInstance().getUserID()){
			JOptionPane.showMessageDialog(frame, "The Project was Completed");
			pm.completeProject(projectID);

		}else{
			JOptionPane.showMessageDialog(frame, "You do not have permission to complete this.");
		}
	}
	
	
	public void updateWorkPerformed(String text){
		if(text.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please Enter a Description");
		}else{
			boolean success = drdd.updateWorkPerformed(ProjectSingleton.getInstance().getProjectID(), text);
			
			if(success){
				JOptionPane.showMessageDialog(frame, "Work Performed Updated!");
			}else{
				JOptionPane.showMessageDialog(frame, "There was an error while updating.");
			}
		}
	}
	
	public void updateActionRequested(String text){
		if(text.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please Enter a Description");
		}else{
			boolean success = drdd.updateActionRequested(ProjectSingleton.getInstance().getProjectID(), text);
			
			if(success){
				JOptionPane.showMessageDialog(frame, "Work Performed Updated!");
			}else{
				JOptionPane.showMessageDialog(frame, "There was an error while updating.");
			}
		}
	}
	
	public void addData(String text, int id){
		if(text.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please Enter a Name");
		}else{
			boolean success = new DataDeliveredModel().addNewData(id, text);
			
			if(success){
				JOptionPane.showMessageDialog(frame, "Data Added!");
			}else{
				JOptionPane.showMessageDialog(frame, "There was an error while adding your data.");
			}
		}
	}
	
	public void addDataDescription(String text, int id){
		if(text.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please Enter a Description");
		}else{
			boolean success = new DataDeliveredModel().addNewDataDescription(id, text);
			
			if(success){
				JOptionPane.showMessageDialog(frame, "Description Updated!");
			}else{
				JOptionPane.showMessageDialog(frame, "There was an error while updating your description.");
			}
		}
	}
	
	public void addOS(String text, int id){
		if(text.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please Enter a Name");
		}else{
			boolean success = new FileAndFolderLocationModel().addNewOS(id, text);
			
			if(success){
				JOptionPane.showMessageDialog(frame, "Data Added!");
			}else{
				JOptionPane.showMessageDialog(frame, "There was an error while adding your data.");
			}
		}
	}
	
	public void addLocation(String location, String description, int id){
		if(location.isEmpty() || description.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
		}else{
			boolean success = new FFLocationsModel().addNewFolder(location, description, id);
			
			if(success){
				JOptionPane.showMessageDialog(frame, "Location Added!");
			}else{
				JOptionPane.showMessageDialog(frame, "There was an error while adding your data.");
			}
		}
	}
	
	public void updateWorkingFolder(String text, int id){
		if(text.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please Enter a Working Folder");
		}else{
			boolean success = new FileAndFolderLocationModel().updateWorkingFolder(id, text);
			
			if(success){
				JOptionPane.showMessageDialog(frame, "Woring Folder Updated!");
			}else{
				JOptionPane.showMessageDialog(frame, "There was an error while updating your data.");
			}
		}
	}
	
	/**
	 * Collect All Names from the Gathered Units, and then return an array. Spot 0 with the units, spot 1 with there names.
	 * @return
	 */
	public ArrayList<Object> getUnits(){
		ArrayList<Unit> units = um.getUnits();
		String[] unitNames = new String[units.size()];
		
		//Collect Names
		for(int i = 0; i< units.size(); i++){
			unitNames[i] = units.get(i).getUnitName();
		}
		
		ArrayList<Object> returnList = new ArrayList<Object>();
		returnList.add(units);
		returnList.add(unitNames);
		
		return returnList;
	}
	
	public boolean addProject(String projectName, String startDate, String endDate, String description,
			String reqOrg, int unitID, String expectedDate, User user){
		if(projectName.isEmpty() || startDate.isEmpty() || description.isEmpty() || 
				reqOrg.isEmpty() || expectedDate.isEmpty()){
			JOptionPane.showMessageDialog(frame, "All fields must be filled except end date.");
			return false;
		}else if(before(expectedDate, startDate)){
			JOptionPane.showMessageDialog(frame, "Start Date Must be Before the End Date");
			return false;
		}
		else{
		pm.addProject(projectName, startDate, endDate, description, reqOrg, UserSingleton.getInstance().getUserID(),
				unitID, expectedDate, user.getUserID());
		return true;
		}
		
	}
	
	public int getLeadNum(ArrayList<User> listLead){
		int num = ProjectSingleton.getInstance().getLeadAnalyst();
		int position = 0;
		
		for(int i = 0; i<listLead.size(); i++){
			if(listLead.get(i).getUserID() == num){
				position = i;
				break;
			}
		}
		
		return position;
	}
	
	public boolean editProject(String projectName, String startDate, String endDate, String description,
			String reqOrg, int unitID, String expectedDate, User user){
		if(projectName.isEmpty() || startDate.isEmpty() || description.isEmpty() || 
				reqOrg.isEmpty() || expectedDate.isEmpty()){
			JOptionPane.showMessageDialog(frame, "All fields must be filled except end date.");
			return false;
		}else if(before(expectedDate, startDate)){
			JOptionPane.showMessageDialog(frame, "Start Date Must be Before the End Date");
			return false;
		}else{
			pm.editProject(projectName, startDate, endDate, description, reqOrg, 
				UserSingleton.getInstance().getUserID(), unitID, expectedDate, user.getUserID());
			ProjectSingleton.setInstance(pm.getProjectByID(ProjectSingleton.getInstance().getProjectID()));
			
			return true;
		}
	}
	
	public void addAnalysts(JTable table){
		ArrayList<Object[]> temp = new ArrayList<Object[]>();
		for(int i = 0; i<table.getRowCount(); i++){
			Object[] container = new Object[2];
			boolean analyst = (boolean) table.getValueAt(i, 0);
			container[0] = analyst;
			container[1] = table.getValueAt(i, 4);
			temp.add(container);
		}
		uc.updateAnalysts(temp);
		JOptionPane.showMessageDialog(frame, "Analysts Updated!");
		
	}
	
	public boolean onProject(int id){
		return pm.onProject(id, UserSingleton.getInstance().getUserID());
	}
	
	public void loadCommentData(JTextPane comments, int id, int child){
		ArrayList<Comment> commentList;
		if(child==2){
			commentList = tcm.getCommentsByTaskID(id);
		}
		else{
			commentList = scm.getCommentsBySubtaskID(id);
		}
		String str = "<html><table>";
		
		for(Comment comment: commentList){
			String date = comment.getPostedAt().split("\\s+")[0];
			String roleName = ProjectSingleton.getInstance().getLeadAnalyst()==comment.getCreatedBy().getUserID()?
					"Lead Analyst":comment.getCreatedBy().getRole().getRoleName();
			roleName = ProjectSingleton.getInstance().getCreatedBy().getUserID()==comment.getCreatedBy().getUserID()?
					"Project Manager":comment.getCreatedBy().getRole().getRoleName();
		
		str += ("<tr style=\"background-color:rgb(237,237,237);\"><td>" + comment.getCreatedBy().getFirstName() + " " + 
				comment.getCreatedBy().getLastName() + "</td>" +
				"<td>Posted On: " + date + "</td><td><b>" + roleName + "</b></td></tr>" +
				"<tr><td colspan = \"3\">" + comment.getComment() + "</td></tr>");
		}
		str += "</table></html>";
		
		comments.setText(str);
	}
	
	public void addComment(int id, String comment, int child, JTextPane comments){
		
		if(comment.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please enter a comment.");
		}else{
			boolean success = false;
			if(child==2){
				success = tcm.addComment(id, comment, UserSingleton.getInstance().getUserID());
			}else{
				success = scm.addComment(id, comment, UserSingleton.getInstance().getUserID());
			}
			
			if(success){
				JOptionPane.showMessageDialog(frame, "Comment was Added Successfully!");
				loadCommentData(comments, id, child);
			}else{
				JOptionPane.showMessageDialog(frame, "There was an error while adding your comment.");
			}
		}
	}
	
	public ArrayList<Object> getRoles(){
		ArrayList<Role> roles = rm.getAllRoles();
		String[] roleNames = new String[roles.size()];
		
		for(int i = 0; i<roles.size(); i++){
			roleNames[i] = roles.get(i).getRoleName();
		}
		
		ArrayList<Object> returnList = new ArrayList<Object>();
		returnList.add(roles);
		returnList.add(roleNames);
		
		return returnList;
		
	}
	
	public void removeRow(JTable table){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int selected = table.getSelectedRow();
		model.removeRow(selected);
	}
	
public void openExcel(JTable table){
	JFileChooser chooser = new JFileChooser(); 
    chooser.setDialogTitle("Save As");
 
    if (chooser.showSaveDialog(new Dialog(frame)) == JFileChooser.APPROVE_OPTION) { 
      toExcel(table, chooser.getSelectedFile());
      
		
	}else{
		System.out.println("Not Approved");
	}
}

public void downloadAttachment(int id){
	JFileChooser chooser = new JFileChooser(); 
    chooser.setDialogTitle("Save As");
  
    //    
    if (chooser.showSaveDialog(new Dialog(frame)) == JFileChooser.APPROVE_OPTION) { 
    	
    	File source = new File(rfm.getRootFolder() + "\\" + am.getAttachmentByID(id).getLink());
    	File destination = chooser.getSelectedFile();
    	String ext = "." + source.getName().split("\\.")[1];
    	String path = destination.getPath().split("\\.")[0] + ext;
    	File d = new File(path);
    	
       
       loadAttachment(source, d);
      
		
	}else{
		System.out.println("Not Approved");
	}
}



	public void loadAttachment(File source, File destination){
		
		try {
			Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
			JOptionPane.showMessageDialog(frame, "Attachment has been Downloaded!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateRootFolder(File file){
		rfm.insertRootFolder(frame, file.getPath());
	}
	
	public String getRootFolder(){
		return rfm.getRootFolder();
	}
	
	
	public boolean addAttachment(String name, File file, JXTreeTable treeTable){
		
		if(name.isEmpty()){
			JOptionPane.showMessageDialog(frame, "Please enter a name for your attachment!");
			return false;
		}else{
		int node = treeTable.getPathForRow(treeTable.getSelectedRow()).getPathCount();
		int id = (int)treeTable.getValueAt(treeTable.getSelectedRow(), 4);
		
		if(node != 2){
			id = sm.getSubtaskByID(id).getTaskID();
		}

    	File targetFile = new File(rfm.getRootFolder() + "\\attachments\\" + file.getName());
    	File directory = new File(rfm.getRootFolder() + "\\attachments\\");
    		
    	if(!directory.isDirectory()){
    		directory.mkdir();
    	}

    	int i = 1;
    	while(targetFile.exists()){
    		String[] tokens = file.getName().split("\\.");
    		targetFile = new File(rfm.getRootFolder() + "\\attachments\\" + tokens[0] + i + "." + tokens[1] );
    		i++;
    	}
    	
    	try {
			Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	boolean success = new AttachmentModel().insertAttachment(name, UserSingleton.getInstance().getUserID(),
    			id, "\\attachments\\" + targetFile.getName());
    	
    	if(success){
    		JOptionPane.showMessageDialog(frame, "Attachment was added Successfully!");
    		frame.createAttachmentTable();
    	}else{
    		JOptionPane.showMessageDialog(frame, "There was an error while adding the attachment.");
    	}
    	return true;
		}
	}
	
	public void removeAttachment(int id){
		boolean success = am.deleteAttachmentByID(id);
		
		if(success){	
		JOptionPane.showMessageDialog(frame, "Attachment was removed Successfully!");
		frame.createAttachmentTable();
	}else{
		JOptionPane.showMessageDialog(frame, "There was an error while removing the attachment.");
	}
	}

	public void toExcel(JTable table, File file){
	    try{
	    	
	    	
	    	if(file.getPath().isEmpty()){
	    		file = new File("tempData_" + Calendar.getInstance().getTime().getTime() + ".xls");
	    	}
	    	
	    	String path = file.getPath();
	    	String[] tokens = path.split("\\.");
	    	
	    	
	    	System.out.println(tokens.length);
	    	
	    	if(tokens.length==1){
	    		file = new File(file.getPath() + ".xls");
	    	}
	    	
	        TableModel model = table.getModel();
	        FileWriter excel = new FileWriter(file);


	        for(int i = 0; i < model.getColumnCount(); i++){
	            excel.write(model.getColumnName(i) + "\t");
	        }


	        excel.write("\n");


	        for(int i=0; i< model.getRowCount(); i++) {
	            for(int j=0; j < model.getColumnCount(); j++) {
	                excel.write(model.getValueAt(i,j).toString()+"\t");
	            }
	            excel.write("\n");
	        }


	        excel.close();


	    }catch(IOException e){ System.out.println(e); }
	}
	
	public int getManager(int projectID){
//		Project project = pm.getManager(projectID);
		return 1;
	}
	
	public void setProject(int id){
		ProjectSingleton.setInstance(pm.getProjectByID(id));
	}
	
	public Object[][] getAnalysts(){
		return uc.getAnalysts();
	}
	/**
	 * Remove Task or Subtask there respective tables. Note, in teh database, this is not deleted, but
	 * rather has a "deleted" flag.
	 * @param taskID
	 * @param child
	 */
	public void removeTask(int taskID, int child){
		int task;
		if(child==2){
			 TaskModel tm = new TaskModel();
			 task = tm.getTaskByID(taskID).getProjectID();
			 tm.removeTaskById(taskID, frame);
			 new SubtaskModel().removeSubtaskByTaskID(taskID, frame);
		}else{
			SubtaskModel sm = new SubtaskModel();
			task = sm.getSubtaskByID(taskID).getProjectID();
			sm.removeSubtaskByID(taskID, frame);
		}
		
		frame.runCompile(task, CompletedSingleton.getInstance());
	}
	
	/**
	 * Complete task or subtask. This may cause it to disappear in the visible table, and will create a 
	 * completed flag in the database.
	 * @param taskID
	 * @param child
	 */
	public void completeTask(int taskID, int child){
		int task;
		if(child==2){
			 TaskModel tm = new TaskModel();
			 task = tm.getTaskByID(taskID).getProjectID();
			 tm.completeTaskById(taskID, frame);
			 new SubtaskModel().completeSubtaskByTaskID(taskID, frame);
		}else{
			SubtaskModel sm = new SubtaskModel();
			task = sm.getSubtaskByID(taskID).getProjectID();
			sm.completeSubtaskByID(taskID, frame);
		}
		
		frame.runCompile(task, CompletedSingleton.getInstance());
	}
	
	public boolean addTask(String taskName, String startDate, String endDate, 
			String description, int assignedTo, String expectedDate){
		
		if(taskName.isEmpty() || startDate.isEmpty() || description.isEmpty()
				|| expectedDate.isEmpty()){
			JOptionPane.showMessageDialog(frame, "All fields must be filled except end date!");
			return false;
		}else if(before(expectedDate, startDate)){
			JOptionPane.showMessageDialog(frame, "Start Date Must be Before the End Date");
			return false;
		}else if(before(startDate,ProjectSingleton.getInstance().getStartDate()) ||
				after(expectedDate,ProjectSingleton.getInstance().getExpectedEndDate())){
			JOptionPane.showMessageDialog(frame, "Both the start and end Date was fall between the Projects respective dates.");
			return false;
		}else{
		int projectID = ProjectSingleton.getInstance().getProjectID();
		boolean success = tm.addTask(taskName, startDate, endDate, description, assignedTo,
				UserSingleton.getInstance().getUserID(), projectID, expectedDate);
		
		if(success){
			JOptionPane.showMessageDialog(frame, "Task Succesfully Added!");
		}else{
			JOptionPane.showMessageDialog(frame, "There was an error while adding the task.");
		}
		
		frame.runCompile(projectID, CompletedSingleton.getInstance());
		return true;
		}
	}
	
	public boolean before(String input, String parent){
		String[] tokens = input.split("\\/");
		if(tokens.length == 1){
			tokens = input.split("\\-");
			String monthTemp = tokens[1];
			tokens[1] = tokens[2].split("\\s+")[0];
			tokens[2] = tokens[0];
			tokens[0] = monthTemp;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(tokens[2]));
		cal.set(Calendar.MONTH,Integer.parseInt(tokens[0]));
		cal.set(Calendar.DATE,Integer.parseInt(tokens[1]));
		
		java.util.Date date = cal.getTime();
		
		String[] parentTokens = parent.split("\\/");
		if(parentTokens.length == 1){
			parentTokens = parent.split("\\-");
			String monthTemp = parentTokens[1];
			parentTokens[1] = parentTokens[2].split("\\s+")[0];
			parentTokens[2] = parentTokens[0];
			parentTokens[0] = monthTemp;
		}
		Calendar parentCal = Calendar.getInstance();
		
		System.out.println(parent);
		System.out.println(parentTokens[1]);
		parentCal.set(Calendar.YEAR,Integer.parseInt(parentTokens[2]));
		parentCal.set(Calendar.MONTH,Integer.parseInt(parentTokens[0]));
		parentCal.set(Calendar.DATE,Integer.parseInt(parentTokens[1]));
		
		java.util.Date parentDate = parentCal.getTime();
		
		if(date.before(parentDate)){
			return true;
		}
		return false;
		
		
	}
	
	public boolean after(String input, String parent){
		String[] tokens = input.split("\\/");
		if(tokens.length == 1){
			tokens = input.split("\\-");
			String monthTemp = tokens[1];
			tokens[1] = tokens[2].split("\\s+")[0];
			tokens[2] = tokens[0];
			tokens[0] = monthTemp;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,Integer.parseInt(tokens[2]));
		cal.set(Calendar.MONTH,Integer.parseInt(tokens[0]));
		cal.set(Calendar.DATE,Integer.parseInt(tokens[1]));
		
		java.util.Date date = cal.getTime();
		
		String[] parentTokens = parent.split("\\/");
		if(parentTokens.length == 1){
			parentTokens = parent.split("\\-");
			String monthTemp = parentTokens[1];
			parentTokens[1] = parentTokens[2].split("\\s+")[0];
			parentTokens[2] = parentTokens[0];
			parentTokens[0] = monthTemp;
		}
		Calendar parentCal = Calendar.getInstance();
		
		System.out.println(parent);
		System.out.println(parentTokens[1]);
		parentCal.set(Calendar.YEAR,Integer.parseInt(parentTokens[2]));
		parentCal.set(Calendar.MONTH,Integer.parseInt(parentTokens[0]));
		parentCal.set(Calendar.DATE,Integer.parseInt(parentTokens[1]));
		
		java.util.Date parentDate = parentCal.getTime();
		
		if(date.after(parentDate)){
			return true;
		}
		return false;
		
		
	}
	
	public boolean addUnit(String unitName){
		
		if(unitName.isEmpty()){
			JOptionPane.showMessageDialog(frame, "All fields must be filled!");
			return false;
		}
		else{
		boolean success = new UnitModel().addUnit(unitName);
		
		if(success){
			JOptionPane.showMessageDialog(frame, "Unit Succesfully Added!");
		}else{
			JOptionPane.showMessageDialog(frame, "There was an error while adding the unit.");
		}
		return true;
		}
	}
	
	public boolean addSubtask(String taskName, String startDate, String endDate, String description,
			int assignedTo, int taskID, String expectedDate, JXTreeTable treeTable){
		if(taskName.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || description.isEmpty()
				|| expectedDate.isEmpty()){
			JOptionPane.showMessageDialog(frame, "All fields must be filled except end date!");
			return false;
		}else if(before(expectedDate, startDate)){
			JOptionPane.showMessageDialog(frame, "Start Date Must be Before the End Date");
			return false;
		}else{
		
		int node = treeTable.getPathForRow(treeTable.getSelectedRow()).getPathCount();
		int id = (int)treeTable.getValueAt(treeTable.getSelectedRow(), 4);
		
		if(node != 2){
			taskID = sm.getSubtaskByID(id).getTaskID();
		}
		
		Task task = tm.getTaskByID(taskID);
		
		if(before(startDate,task.getStartDate()) || after(expectedDate,task.getExpectedEndDate())){
			JOptionPane.showMessageDialog(frame, "Nothing can start before or end after parent's respective dates.");
			return false;
		}
		int projectID = ProjectSingleton.getInstance().getProjectID();
		boolean success = sm.addTask(taskName, startDate, endDate, description, assignedTo,
				UserSingleton.getInstance().getUserID(), projectID, taskID, expectedDate);
		
		if(success){
			JOptionPane.showMessageDialog(frame, "Subtask Succesfully Added!");
		}else{
			JOptionPane.showMessageDialog(frame, "There was an error while adding the subtask.");
		}
		
		frame.runCompile(projectID, CompletedSingleton.getInstance());
		return true;
		}

	}
	
	public int getUnitFromList(String[] list){
		
		Unit unit = um.getUnitByID(ProjectSingleton.getInstance().getUnitID());
		for(int i = 0; i<list.length; i++){
			if(unit.getUnitName().equals(list[i])){
				return i;
			}
		}
		return 0;
	}
	
	public Subtask getSubtaskById(int id){
		return sm.getSubtaskByID(id);
	}
	
	
	public void exportDRDD(){
		new DRDDModel().exportDRDD();
	}
	
	public Object[][] getProjects(){
		return pm.getAllProjects();
	}
	
	public ArrayList<Project> getUserProjects(){
		return pm.getUserProjects(UserSingleton.getInstance().getUserID());
	}
	
	public ArrayList<Task> getUserAssignedTask(boolean completed){
		return tm.getUserAssignedTasks(UserSingleton.getInstance().getUserID());
	}
	
	public ArrayList<Subtask> getUserAssignedSubtask(boolean completed){
		return sm.getUserAssignedSubtasks(UserSingleton.getInstance().getUserID(), completed);
	}
	
	public ArrayList<TaskIF> getDeadlineTasks(){
		ArrayList<Task> tasks = tm.getRecentTasks(UserSingleton.getInstance().getUserID());
		ArrayList<Subtask> subtasks = sm.getRecentSubtasks(UserSingleton.getInstance().getUserID());
		
		ArrayList<TaskIF> masterTask = new ArrayList<TaskIF>();
		
		int i = 0;
		for(Task task: tasks){
			masterTask.add(task);
			i++;
			
		}
		
		for(Subtask subtask: subtasks){
			masterTask.add(subtask);
			i++;
			
		}
		
		ArrayList<TaskIF> returnList = new ArrayList<TaskIF>();
		
		
		while(!masterTask.isEmpty()){
			
			TaskIF compare = masterTask.get(0);
			
			for(int u = 0; u<masterTask.size(); u++){
				compare = compare(compare, masterTask.get(u));
			}
			
			returnList.add(compare);
			if(returnList.size()>5){
				break;
			}
			masterTask.remove(compare);
		}
		
		return returnList;
		
		
		
		
	}
	
	
	public boolean editTask(String taskName, String startDate, String endDate, String description, 
		   int assignedTo, boolean leaf, int taskID, String expectedDate){
		if(taskName.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || description.isEmpty() || expectedDate.isEmpty()){
			JOptionPane.showMessageDialog(frame, "All fields must be filled in except end date");
			return false;
		}else if(before(expectedDate, startDate)){
			JOptionPane.showMessageDialog(frame, "Start Date Must be Before the End Date");
			return false;
		}else{
			boolean success = false;
			if(!leaf){
				if(before(startDate,ProjectSingleton.getInstance().getStartDate()) ||
						after(expectedDate,ProjectSingleton.getInstance().getExpectedEndDate())){
					JOptionPane.showMessageDialog(frame, "Both the start and end Date was fall between the Projects respective dates.");
					return false;
				 }
				success = tm.taskUpdate(taskID, taskName, startDate, endDate, description, assignedTo, expectedDate);
			}else{
				Task task = tm.getTaskByID(sm.getSubtaskByID(taskID).getTaskID());
				
				if(before(startDate,task.getStartDate()) || after(expectedDate,task.getExpectedEndDate())){
					JOptionPane.showMessageDialog(frame, "Nothing can start before or end after parent's respective dates.");
					return false;
				}
				success = sm.subtaskUpdate(taskID, taskName, startDate, endDate, description, assignedTo, expectedDate);
			}
			
			if(success){
				JOptionPane.showMessageDialog(frame, "The Task was Updated Succesfully");
			}else{
				JOptionPane.showMessageDialog(frame, "There was a problem while updating the Task");
			}
			return true;
		}
	}
	
	public TheFrame getFrame(){
		return frame;
	}
	
	public int getAnalystFromList(String[] names, TaskIF task){
		for(int i = 0; i<names.length; i++){
			if(names[i].equals(task.getTaskName())){
				return i;
			}
		}
		return 0;
	}
	
	public Object[][] getAttachments(JXTreeTable treeTable){
		int node = treeTable.getPathForRow(treeTable.getSelectedRow()).getPathCount();
		int id = (int)treeTable.getValueAt(treeTable.getSelectedRow(), 4);
		
		if(node != 2){
			id = sm.getSubtaskByID(id).getTaskID();
		}
		
		AttachmentModel am = new AttachmentModel();
		return am.attachmentToTable(am.getAttachmentsByTaskID(id));
	}
	
	public ArrayList<Task> getTaskByProjectID(int id){
		return tm.getTaskByProjectID(id, CompletedSingleton.getInstance());
	}
	
	public Task getTaskByID(int id){
		return tm.getTaskByID(id);
	}
	
	public ArrayList<Subtask> getSubtaskByTaskID(int id, boolean completed){
		return sm.getSubtaskByTaskID(id,completed);
	}
	
	public TaskIF compare(TaskIF one, TaskIF two){
		
		if(one.getEndDate().compareTo(two.getEndDate())<0){
			return one;
		}
		return two;
	}
	
}
