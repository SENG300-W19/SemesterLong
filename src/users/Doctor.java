package users;
import java.text.Collator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

import data.Schedule;  

/**
 * not used, refactoring is required. 
 * @author dylnstwrt
 * 
 * TODO fix variable visibility and implement missing functionality
 *  
 */
public class Doctor extends User {
	
	
	private static final long serialVersionUID = 1L;
	private String specialty = "General Practicioner";
	private static Boolean surgeon = false;
	private LinkedList<User> patients = new LinkedList<User>();
	private Schedule schedule = new Schedule();


	
	public Doctor(String username, String password){
		super(username, password, 2); 	
	}
	 /**
	  * used to add user/patient object into linked list according to last name major
	  * alphabetical order.
	  * @param toAdd - Patient object to be added to the linked-list
	  */
	
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
		Doctor.surgeon = !(Doctor.surgeon);
		String toDisplay = "Doctor "+this.getLastName()+" is ";
		if (Doctor.surgeon) toDisplay.concat("a Surgeon");
		else toDisplay.concat("not a Surgeon");
		System.out.println(toDisplay);
	}
	 /**
	  * 
	  * @return weather or not the doctor is a surgeon.
	  */
	public boolean isSurgeon() {
		return Boolean.valueOf(Doctor.surgeon);
	}
	
	/**
	 * 
	 * @return string of specialty, if not set, will return that doctor is a GP
	 */
	public String getSpecialty() {
		String toReturn;
		toReturn = this.specialty.toString();
		return toReturn; 
	}
	
	/**
	 * 
	 * @return linked list object copy of the list of patients.
	 */
	public LinkedList<User> returnPatients() {
		LinkedList<User> toReturn = new LinkedList<User>(this.patients);
		return toReturn; 
	}
}
	