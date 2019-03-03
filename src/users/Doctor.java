package users;
import java.text.Collator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

import data.Schedule;  

public class Doctor extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String specialty;
	private static Boolean surgeon = false;
	private LinkedList<Patient> patients;
	public Schedule schedule;
	
	public Doctor(String username, String password){
		super(username, password, 2); 	
	}
	
	public void addPatient(Patient toAdd) {
		if (patients.isEmpty()) patients.add(toAdd);
		else {
			Collator sort = Collator.getInstance(Locale.US);
			for (Patient p : this.patients) {
				int comparison = sort.compare(toAdd.lastName.toUpperCase(), p.lastName.toUpperCase());
				if (comparison == 0) { // same last name, compare first names
					comparison = sort.compare(toAdd.firstName.toUpperCase(), p.lastName.toUpperCase());
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
	
	public void listPatients() {
		for (Patient p : this.patients) {
			System.out.println((this.patients.indexOf(p)+1)+" "+p.lastName+", "+p.firstName);
		}
	}
	
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
	
	public void setSpecialty() {
		Boolean inputValid = false;
		Scanner input = new Scanner(System.in);
		while (!inputValid) {
			System.out.print("Set specialty: ");
			String specialty = input.next();
			if (specialty.matches("[a-zA-Z]+")) { 
				inputValid = true; // use REGEX
				this.specialty = specialty.toString();
				System.out.println("Specialty set for Doctor "+this.lastName+": "+this.specialty);
				
			} else {
				System.out.println("Error in processing specialty, please try again");
			}
		}
		
	}
	
	public void toggleSurgeon() {
		Doctor.surgeon = !(Doctor.surgeon);
		String toDisplay = "Doctor "+this.lastName+" is "; 
		if (Doctor.surgeon) toDisplay.concat("a Surgeon");
		else toDisplay.concat("not a Surgeon");
		System.out.println(toDisplay);
	}
	
	public boolean isSurgeon() {
		return Boolean.valueOf(Doctor.surgeon);
	}
	
	public String getSpecialty() {
		String toReturn;
		if (this.specialty != null) {
			toReturn = this.specialty.toString();
		} else {
			toReturn = "General Practitioner";
		}
		return toReturn; 
	}
	
	public LinkedList<Patient> returnPatients() {
		LinkedList<Patient> toReturn = new LinkedList<Patient>(this.patients);
		return toReturn; 
	}
}
	