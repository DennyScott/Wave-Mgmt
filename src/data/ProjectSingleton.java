package data;

import structures.Project;

public class ProjectSingleton {
	
private static Project project;
	
	public static Project getInstance(){
		
		if(project==null){
			project = new Project();
		}
		
		return project;
	}
	
	public static void setInstance(Project project){
		ProjectSingleton.project = project;
	}

}
