package data;

import structures.Project;

public class CompletedSingleton {
	
private static boolean completed = true;
	
	public static Boolean getInstance(){
		
		return completed;
	}
	
	public static void setInstance(boolean completed){
		CompletedSingleton.completed = completed;
	}

}
