package users;

import data.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author dylnstwrt
 * not used, refactor is required
 */
public class Admin extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Admin(String userName, String password) {
		super (userName,password,1);
	}
}
