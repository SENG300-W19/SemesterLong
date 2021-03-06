package users;

import java.text.Collator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;
import data.Schedule;

/**
 * The class used for a doctor account
 */
public class Doctor extends User {
	
	
	private static final long serialVersionUID = 1L;
	private String specialty = "General Services";
	private Boolean surgeon = false;
	private LinkedList<User> patients = new LinkedList<User>();
	private Schedule schedule = new Schedule();
	private int department = 1;				// 1. General Services department by default.


	public Doctor(User user) {
	    super(user.getUsername(),user.getPassword(),user.getAccountType());
    }
	public Doctor(String username, String password){
		super(username, password, 2); 	
	}
	 /**
	  * used to add user/patient object into linked list according to last name major
	  * alphabetical order.
	  * @param toAdd - Patient object to be added to the linked-list
	  */

	/**
	 * Console menu for doctors.
	 * Currently has no functionality.
	 */
	public static void doctorMenu() {
		Scanner scan = new Scanner(System.in);
		int input = 1;
		while (input != 0) {
			System.out.print("Doctor Menu:\n" +
					"0. Exit");
			input = scan.nextInt();
			while (input != 0) {
				System.out.print("Please input 0 to exit: ");
				input = scan.nextInt();
			}
		}
		System.exit(0);
	}

	public void addPatient(Patient toAdd) {
		if (patients.isEmpty()) patients.add(toAdd);
		else {
			Collator sort = Collator.getInstance(Locale.US);
			for (User p : this.patients) {
				int comparison = sort.compare(toAdd.getLastName().toUpperCase(), p.getLastName().toUpperCase());
				if (comparison == 0) { // same last name, compare first names
					comparison = sort.compare(toAdd.getFirstName().toUpperCase(), p.getFirstName().toUpperCase());
					if (comparison > 0) {
						this.patients.add(this.patients.indexOf(p), toAdd);
						break;
						}
				} else if (comparison > 0) { // toAdd has greater alphabetical precedent than the compared index
					this.patients.add(this.patients.indexOf(p), toAdd);
					break;
				} else if (patients.indexOf(p) == patients.size()-1) {
					// if toAdd is the last element to be appended
					this.patients.add(toAdd);
				}
					
			}
		}
	}
	
	/**
	 * displays all members of the patient list to terminal
	 */
	public void listPatients() {
		if (!patients.isEmpty()) {
			for (User p : this.patients) {
				System.out.println((this.patients.indexOf(p) + 1) + " " + p.getLastName() + ", " + p.getFirstName());
			}
		}
	}
	
	/**
	 * lists all patients, with an index next to their name, takes that index as input from terminal
	 * and removes said patient from the list.
	 * 
	 * TODO confirmation
	 * TODO refactor to do something
	 */
	public void removePatient() {
		// list all patients
		// remove patient at certain index (1 . . . . N), use 0 to cancel
		Scanner input = new Scanner(System.in);
		this.listPatients();
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
	 * takes input from terminal, checks if is a single word with alphabetical characters, sets as doctors specialty
	 * and then displays confirmation of said specialty
	 */
	public void setSpecialty() {
		Boolean inputValid = false;
		Scanner input = new Scanner(System.in);
		while (!inputValid) {
			System.out.print("Set specialty: ");
			String specialty = input.next();
			if (specialty.matches("[a-zA-Z]+")) { 
				inputValid = true; // use REGEX
				this.specialty = specialty.toString();
				System.out.println("Specialty set for Doctor "+this.getLastName()+": "+this.specialty);
				
			} else {
				System.out.println("Error in processing specialty, please try again");
			}
		}
		
	}
	
	/**
	 * toggles boolean of weather or not the doctor is a surgeon, which is a doctor is not on creation of the object.
	 */
	public void toggleSurgeon() {
		this.surgeon = !(this.surgeon);
		String toDisplay = "Doctor "+this.getLastName()+" is ";
		if (this.surgeon) {
			toDisplay = toDisplay.concat("a Surgeon");
		}
		else  {
			toDisplay = toDisplay.concat("not a Surgeon");
		}
		System.out.println(toDisplay);
	}

	 /**
	  * 
	  * @return weather or not the doctor is a surgeon.
	  */
	public boolean isSurgeon() {
		return this.surgeon;
	}

	
	/**
	 * @return linked list object copy of the list of patients.
	 */
	public LinkedList<User> getPatients() {
		return patients;
	}

	/**
	 * Set doctor department (used for terminal version)
	 * @param department is the department to be set
	 */
	public void setDepartment(int department) {
		this.department = department;
	}

	/**
	 * Get the department a doctor is in (used for GUI version)
	 * @return the doctor's specialty
	 */
	public String getDepartmentGUI() {
		return specialty;
	}

	/**
	 * Set the department a doctor is in (used for GUI version)
	 * @param department is the department the doctor's being set to
	 */
	public void setDepartmentGUI(String department) {
		this.specialty = department;
	}

	/**
	 * Get doctor department (for terminal version)
	 */
	public String getDepartment() {
		switch (department) {
			case 1:
				return "General Services";
			case 2:
				return "Cardiology";
			case 3:
				return "Nephrology";
			case 4:
				return "Neurology";
			case 5:
				return "Psychiatry";
			case 6:
				return "Oncology";
			case 7:
				return "Gastroenterology";
			case 8:
				return "Haemotology";
			case 9:
				return "Orthopaedics";
		}
		return "";
	}
}
	