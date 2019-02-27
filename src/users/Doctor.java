package users;
import java.text.Collator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;  

public class Doctor extends User {
	private String specialty; // TODO error checking for valid specialization
	private Boolean surgeon = false;
	// sort list alphabetically by last name.
	private LinkedList<Patient> patients;
	
	public Doctor(String username, String password, int accountType){
		super(username, password, accountType); 	
	}
	public void addPatient(Patient toAdd) {
		if (patients.isEmpty()) patients.add(toAdd);
		else {
			Collator sort = Collator.getInstance(Locale.US);
			for (Patient p : this.patients) {
				int comparison = sort.compare(toAdd.lastName.toUpperCase(), p.lastName.toUpperCase());
				if (comparison == 0) {
					// same last name, compare first names
					comparison = sort.compare(toAdd.firstName.toUpperCase(), p.lastName.toUpperCase());
					if (comparison > 0) {
						this.patients.add(this.patients.indexOf(p), toAdd);
						break;
						}
				} else if (comparison > 0) {
					// toAdd has greater alphabetical precedent than the compared index
					this.patients.add(this.patients.indexOf(p), toAdd);
					break;
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
		// remove patient at certian index (1 . . . . N), use 0 to cancel
		Scanner input = new Scanner(System.in);
		this.listPatients();
		System.out.println("Select the number of the user you would like to remove, or press 0 to exit: ");
		Boolean inputValid = false; 
		while (!inputValid) {
			String usrInput = input.next();
			if (usrInput.matches("\\d")) {
				
				switch (usrInput) {
			
				case "0": 
					System.out.println("Exiting, no changes made...\n");
					inputValid = true;
					break;
				default:
					if (Integer.parseInt(usrInput) <= patients.size() && Integer.parseInt(usrInput) > 0) {
						patients.remove(Integer.parseInt(usrInput) - 1);
						inputValid = true;
					} else {
						System.out.println("Input out of range");
					}
				
				}
			} else {
				System.out.println("Invalid token\n");
			}
		}
		input.close();
	}
	
	public void setSpecialty() {
		Boolean inputValid = false;
		Scanner input = new Scanner(System.in);
		while (!inputValid) {
			System.out.print("Set specialty: ");
			String specialty = input.next();
			if (specialty.matches("\\w")) { 
				inputValid = true; // use REGEX
				this.specialty = specialty.toString();
				System.out.println("Specialty set for Doctor "+this.lastName+": "+this.specialty);
				
			} else {
				System.out.println("Error in processing specialty, please try again");
			}
		}
		input.close();
		
	}
	
	public void toggleSurgeon() {
		this.surgeon = !(this.surgeon);
		String toDisplay = "Doctor "+this.lastName+" is "; 
		if (this.surgeon) toDisplay.concat("a Surgeon");
		else toDisplay.concat("not a Surgeon");
		System.out.println(toDisplay);
	}
}
	