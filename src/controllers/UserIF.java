package controllers;

import aurelienribon.slidinglayout.demo.TheFrame;
import structures.Role;

public interface UserIF {

	public boolean add(String username, String firstName, String lastName, String password, int roleID, TheFrame frame);
	public void remove(String username);
	public void update(String username, String firstName, String lastName, String password, int roleID);
	public void signIn(String username, String password);
}
