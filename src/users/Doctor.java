package users;
import java.util.LinkedList;
import java.util.Scanner;  

public class Doctor extends User {
	private String specialty; // TODO error checking for valid specialization
	private Boolean surgeon = false;
	// sort list alphabetically by last name.
	private LinkedList<Patient> patients = null;
	
	public Doctor(String username, String password, int accountType){
		super(username, password, accountType); 	
	}
	public void addPatient(Patient toAdd) {
		
	}
	
	public void removePatient() {
		// list all patients
		// remove patient at certian index (1 . . . . N), use 0 to cancel
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
	