package users;

import java.io.Serializable;
import java.time.LocalDate;
import data.Schedule;
import gui.Info;

/**
 * This class is the parent User class that all other account type classes inherit
 */
public class User implements Serializable {


	private static final long serialVersionUID = 1L;
	private String firstName;

    /**
     * Get the first name of the user
     * @return the first name of the user
     */
    public String getFirstName() {
		return firstName;
	}

    private LocalDate birthday;

    /**
     * Set the birthday of the user
     * @param date is the LocalDate object of the user's birthday
     */
    public void setBirthday(LocalDate date) {
        this.birthday = date;
    }

    /**
     * Get the birthday of the user in string form
     * @return
     */
    public String getBirthday() {
        return this.birthday.toString();
    }

    /**
     * Get birthday of the user as a LocalDate object
     * @return
     */
    public LocalDate getBirthdayDate() {
        return birthday;
    }

    /**
     * Set the first name of the user
     * @param firstName is the name of the user that it's going to be set to
     */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    /**
     * Get the last of the user
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the user
     * @param lastName is the name of the user that it's going to be set to
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    private String lastName;
    private String username;
    private String password;
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


    public String toString() {
        return this.firstName + " " + this.lastName;
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
    public void setInfo(User user) {
        Info form = new Info(user);
        form.init();
    }

}
