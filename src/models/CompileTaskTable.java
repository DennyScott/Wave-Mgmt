package models;

import java.util.ArrayList;

import components.TreeNode;

import structures.Task;
import controllers.PagesController;
import data.ProjectSingleton;

public class CompileTaskTable extends Thread {
	
	TreeNode parent;
	int projectID;
	boolean complete;
	
	public CompileTaskTable(TreeNode parent, int projectID, boolean complete){
		this.parent = parent;
		this.projectID = projectID;
		this.complete = complete;
	}
	
	public void run(){
		ArrayList<Task> tasks = new TaskModel().getTaskByProjectID(projectID, complete);
		ArrayList<Thread> threads = new ArrayList<Thread>();
		
		for(Task task: tasks){
			CollectTasks ct = new CollectTasks(task, parent, complete);
			threads.add(ct);
		}
		
		for(Thread thread: threads){
			
				thread.run();
			
		}
		
	}
}
