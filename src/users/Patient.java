package users;
import java.util.Scanner;
import java.util.LinkedList;

public class Patient extends User {

    /**
     * Variables for patient's personal info
     */
    // since a doctors extends a patient, this will list all doctors in their department. (for now)
    private LinkedList<Doctor> doctors = null; 

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

    /**
     * Constructor to create a patient account which calls parent class (User) constructor
     * @param username
     * @param password
     * @param accountType
     */
    public Patient(String username, String password, int accountType) {
        super(username, password, accountType);
    }
}
