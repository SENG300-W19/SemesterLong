import java.util.Scanner;

public class Patient extends Users {

    /**
     * Variables for patient's personal info
     */
    private String firstName;
    private String lastName;

    /**
     * Method to edit the patients info on account creation
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
     * Constructor to create a patient account which calls parent class (Users) constructor
     * @param username
     * @param password
     * @param accountType
     */
    public Patient(String username, String password, int accountType) {
        super(username, password, accountType);
    }
}
