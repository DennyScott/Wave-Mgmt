package models;


import structures.Subtask;

import components.TreeNode;

public class CollectSubtasks extends Thread{
	
	
	private Subtask sTask;
	private TreeNode parent;
	
	public CollectSubtasks(Subtask sTask, TreeNode parent){
		this.sTask = sTask;
		this.parent = parent;
	}
	
	public void run(){
	
		
			TreeNode sChild = new TreeNode(new Object[6]);
			
			sChild.setValueAt(sTask.getSubtaskID(), 4);
			sChild.setValueAt(sTask.getTaskName(), 0);
			sChild.setValueAt(sTask.getAssignedTo().getFirstName() + " " + sTask.getAssignedTo().getLastName(), 1);
			sChild.setValueAt(sTask.getCompleted()?"Completed":"", 5);
			sChild.setValueAt(sTask.getStartDate().split("\\s+")[0], 2);
			sChild.setValueAt(sTask.getEndDate().split("\\s+")[0], 3);
			
			parent.add(sChild);
		}
		
	}
