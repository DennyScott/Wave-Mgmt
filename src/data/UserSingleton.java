package data;

import structures.User;

public class UserSingleton {

	private static User user;
	
	public static User getInstance(){
		
		if(user==null){
			user = new User();
		}
		
		return user;
	}
	
	public static void setInstance(User user){
		UserSingleton.user = user;
	}
}
