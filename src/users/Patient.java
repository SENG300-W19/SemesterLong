package users;
import java.util.Scanner;
import java.util.LinkedList;
/**
 * 
 * not used, appropriate methods moved into user class, refactoring required
 *
 */
public class Patient extends User {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Variables for patient's personal info
     */
    // since a doctors extends a patient, this will list all doctors in their department. (for now)
    public LinkedList<Doctor> doctors; 

    /**
     * Method to edit the patients info on account creation
     * TODO add confirmation in later iterations
     */
    public void editInfo() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Input first name: ");
        firstName = scan.next();

        System.out.print("Input last name: ");
        lastName = scan.next();

        System.out.println("Patient name: " + firstName + " " + lastName);
        
    }

    public void getInfo() {
        System.out.println("Patient's name: " + firstName + " " + lastName);
    }
    
    public String getFirstName() {
    	return this.firstName.toString();
    }
    
    public String getLastName() {
    	return this.lastName.toString();
    }

    /**
     * Constructor to create a patient account which calls parent class (User) constructor
     * @param username
     * @param password
     * @param accountType
     */
    public Patient(String username, String password) {
        super(username, password, 3);
    }

	public LinkedList<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(LinkedList<Doctor> doctors) {
		this.doctors = doctors;
	}
}
