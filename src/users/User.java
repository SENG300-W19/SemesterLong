package users;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;

import data.Schedule;
import gui.Info;

public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    private String lastName;
    private String username;
    private String password;
    //privateLinkedList<User> list = new LinkedList<User>();
    private Schedule schedule = new Schedule();

    /**
     * uses copy constructor in schedule class.
     * @return copy of schedule
     */
    public Schedule getSchedule() {
        return new Schedule(this.schedule);
    }

    /** 0 = Not a user
     * 1 = Admin
     * 2 = Doctor
     * 3 = Patient
     */
    private int accountType;

    /**
     * Empty default constructor
     */
    public User() {
    	
    }

    /**
     * Constructor for creating a new user
     * @param username
     * @param password
     * @param accountType
     */
    public User(String username, String password, int accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    /**
     * Getter method for username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter method for password
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter method for account type
     * @return account type
     */
    public int getAccountType() {
        return this.accountType;
    }
     /**
      * prints message to terminal depending on the value of the accountType
      */
    public void displayAccountType() {
    	String toDisplay = null;
    	switch (this.accountType){
    	case 1: toDisplay = "This user is an Administrator";
    	break;
    	case 2: toDisplay = "This user is a Doctor";
    	break;
    	case 3: toDisplay = "This use is a Patient";
    	break;
    	}
    	System.out.println(toDisplay);
    }
    
    /**
     * prints name to terminal
     */
    public void displayName() {
    	System.out.println("Last Name: "+this.lastName);
    	System.out.println("First Name: "+this.firstName);
    	
    }

    /**
     * takes input from terminal to set the name of the user object
     */
    public void setName() {
        Info form = new Info();
        form.init();

        /**
    	Scanner in = new Scanner(System.in);
    	boolean valid = false;
    	while(!valid) {
    		System.out.print("First Name: ");
    		String first = in.next();
    		if (first.matches("[a-zA-Z]+") == false) break;
    		this.firstName = first;
    		System.out.print("Last Name: ");
    		String last = in.next();
    		if (last.matches("[a-zA-Z]+") == false) break;
    		this.lastName = last; 
    		valid = true; 
    	}
         */
    }

}
