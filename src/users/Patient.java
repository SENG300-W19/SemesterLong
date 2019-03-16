package users;
import java.text.Collator;
import java.util.Locale;
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
    private LinkedList<User> doctors = new LinkedList<>();

    public void addDoctor(Doctor toAdd) {
        if (doctors.isEmpty()) doctors.add(toAdd);
        else {
            Collator sort = Collator.getInstance(Locale.US);
            for (User d : this.doctors) {
                int comparison = sort.compare(toAdd.getLastName().toUpperCase(), d.getLastName().toUpperCase());
                if (comparison == 0) { // same last name, compare first names
                    comparison = sort.compare(toAdd.getFirstName().toUpperCase(), d.getFirstName().toUpperCase());
                    if (comparison > 0) {
                        this.doctors.add(this.doctors.indexOf(d), toAdd);
                        break;
                    }
                } else if (comparison > 0) { // toAdd has greater alphabetical precedent than the compared index
                    this.doctors.add(this.doctors.indexOf(d), toAdd);
                    break;
                } else if (doctors.indexOf(d) == doctors.size()-1) {
                    // if toAdd is the last element to be appended
                    this.doctors.add(toAdd);
                }

            }
        }
    }

    public void removeDoctor() {
        // list all patients
        // remove patient at certain index (1 . . . . N), use 0 to cancel
        Scanner input = new Scanner(System.in);
        this.listDoctors();
        System.out.println("Select the number of the user you would like to remove, or press 0 to exit: ");
        Boolean inputValid = false;
        while (!inputValid) {
            String usrInput = input.next();
            if (usrInput.matches("\\d")) {



            } else {
                System.out.println("Invalid token\n");
            }
        }
    }

    /**
     * displays all members of the patient list to terminal
     */
    public void listDoctors() {
        if (!(this.doctors.isEmpty())) {
            for (User p : this.doctors) {
                System.out.println((this.doctors.indexOf(p) + 1) + " " + p.getLastName() + ", " + p.getFirstName());
            }
        }
    }

    /**
     * Method to edit the patients info on account creation
     * TODO add confirmation in later iterations
     */
    protected void editInfo() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Input first name: ");
        String first = scan.next();
        this.setFirstName(first);

        System.out.print("Input last name: ");
        String last = scan.next();
        this.setLastName(last);

        System.out.println("Patient name: " + this.getFirstName() + " " + this.getLastName());
        
    }

    public void getInfo() {
        System.out.println("Patient's name: " + this.getFirstName() + " " + this.getLastName());
    }

    /**
     * Constructor to create a patient account which calls parent class (User) constructor
     * @param username
     * @param password
     */
    public Patient(String username, String password) {
        super(username, password, 3);
    }

	public LinkedList<User> getDoctors() {
		return doctors;
	}

	public void setDoctors(LinkedList<User> doctors) {
		this.doctors = doctors;
	}
}
