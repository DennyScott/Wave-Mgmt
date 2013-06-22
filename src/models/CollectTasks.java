package models;

import java.util.ArrayList;

import structures.Subtask;
import structures.Task;

import components.TreeNode;
import controllers.PagesController;





public class CollectTasks extends Thread {
	
	private Task task;
	private TreeNode parent;
	private boolean complete;
	
	public CollectTasks(Task task, TreeNode parent, boolean complete){
		this.task = task;
		this.parent = parent;
		this.complete = complete;
	}
	
	public void run(){
	
		TreeNode child = new TreeNode(new Object[6]);
		child.setValueAt(task.getTaskID(), 4);
		child.setValueAt(task.getTaskName(), 0);
		child.setValueAt(task.getAssignedTo().getFirstName() + " " + task.getAssignedTo().getLastName(), 1);
		child.setValueAt(task.getCompleted()?"Completed":"",5);
		child.setValueAt(task.getStartDate().split("\\s+")[0], 2);
		child.setValueAt(task.getEndDate().split("\\s+")[0], 3);
		
		ArrayList<Subtask> subtasks = new SubtaskModel().getSubtaskByTaskID(task.getTaskID(), complete);
		
		for(Subtask sTask : subtasks){
			CollectSubtasks subtask = new CollectSubtasks(sTask, child);
			subtask.run();
		}
		
		parent.add(child);
	}
		
		
		
	
	
}
