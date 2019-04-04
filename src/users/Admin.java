package users;

import data.Account;
import data.Appointment;
import exceptions.ScheduleException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * @author dylnstwrt
 * not used, refactor is required
 */
public class Admin extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Admin(String userName, String password) {
		super (userName,password,1);
	}




//////////////// Admin Functionality //////////////////

	/**
	 * Methods for admin that are in other classes
	 * @param username
	 */
	// Account.createAccount(String username, String password, int accountType)
	// username.setFirstName(String firstName)
	// username.setLastName(String lastName)
	// ((Doctor) user).setDepartment(int department);

	/**
	 * Add doctor to patient's LinkedList
	 * @param username
	 */
	private static void addDoctor(User username) {
		User doctor = userExists();
		if (doctor instanceof Doctor) {
			((Patient) username).addDoctor((Doctor) doctor);
		} else {
			System.out.println("User is not a patient. Returning to edit menu.");
		}
	}

	/**
	 * Remove doctor from patient's LinkedList
	 * @param username
	 */
	private static void removeDoctor(User username) {
		((Patient) username).removeDoctor();
	}

	/**
	 * Add patient to doctor's LinkedList
	 * @param username
	 */
	private static void addPatient(User username) {
		User patient = userExists();
		if (patient instanceof Patient) {
			((Doctor) username).addPatient((Patient) patient);
		} else {
			System.out.println("User is not a doctor. Returning to edit menu.");
		}
	}

	/**
	 * Remove patient from doctor's LinkedList
	 * @param username
	 */
	private static void removePatient(User username) {
		((Doctor) username).removePatient();
	}

	/**
	 * Change the speciality of a doctor
	 * TODO: Create departments
	 * @param username
	 */
	private static void changeSpecialty(User username, int department) {
		((Doctor) username).setDepartment(department);
	}

	/**
	 * Change the surgeon status of a doctor
	 * @param username
	 */
	private static void toggleSurgeon(User username) {
		((Doctor) username).toggleSurgeon();
	}





//////////// Console Admin Menu Methods ///////////////

	/**
	 * Console menu for administrator
	 */
	public static void adminMenu() {
		Scanner scan = new Scanner(System.in);
		int input = 1;
		while (input != 0) {
			System.out.println("Administrative gui.Menu:\n" +
					"1. Create a new account\n" +
					"2. Edit account\n" +
					"3. Get user info\n" +
					"4. List Users\n" +
					"5. Add appointment\n" +
					"6. List Appointments for User\n" +
					"0. Exit\n");
			System.out.print("What would you like to do: ");
			input = scan.nextInt();
			while (input < 0 || input > 6) {
				System.out.print("\n");
				System.out.print("Please input a valid menu item: ");
				input = scan.nextInt();
			}
			switch (input) {
				case 1:
					createAccount();
					break;
				case 2:
					User user = userExists();
					if (user != null) {
						editUser(user);
					}
					break;
				case 3:
					User user2 = userExists();
					if (user2 != null) {
						reviewUser(user2);
					}
					break;
				case 4:
					listUsers();
					break;
				case 5:
					System.out.println("Adding appointment for user\n ...press 0 to exit");
					System.out.print("Enter username: ");
					String username = scan.next();
					User acc = Account.getDictionary().get(username);
					while (acc.getFirstName() == null) {
						if (username.equals("0")) break;
						System.out.print("Enter Username: ");
						username = scan.next();
						acc = Account.getDictionary().get(username);
					}
					if ((username.equals("0")) == false) {
						//acc.getSchedule().addAppointment();

						try {
							System.out.println("Enter Year");
							int year = scan.nextInt();
							System.out.println("Enter Month");
							int month = scan.nextInt();
							System.out.println("Enter Day");
							int day = scan.nextInt();
							System.out.println("Enter Hour");
							int hour = scan.nextInt();
							System.out.println("Enter minute");
							int min = scan.nextInt();
							Appointment add_app = new Appointment(day, month, year, hour, min);
							acc.getSchedule().addAppointment(add_app);
							Account.writeToFile();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					break;
				case 6:
					System.out.println("Listing Appointments, enter in the name of the user or press 0");
					System.out.print("Enter Username: ");
					String username2 = scan.next();
					while (!(username2.contentEquals("0"))) {
						User account = Account.getDictionary().get(username2);
						if (account != null) {
							account.getSchedule().listAppointments();
							break;
						} else {
							System.out.println("Please enter a valid username, or press 0 to exit");
							username2 = scan.next();
						}
					}
					break;
				case 7:
					System.out.println("Assigning Patient to Doctor, press 0 (for both) to Exit");
					System.out.print("Enter Doctor's Username: ");
					String patientUsername = "";
					String doctorUsername = scan.next();
					User doctor = Account.getDictionary().get(doctorUsername);
					while (doctor.getAccountType() != 2) {
						if (doctorUsername == "0") {
							patientUsername = "0";
							break;
						} else {
							doctorUsername = scan.next();
							doctor = Account.getDictionary().get(doctorUsername);
						}
					}
					System.out.print("Enter Patient's Username: ");
					patientUsername = scan.next();
					User patient = Account.getDictionary().get(patientUsername);
					while (patient.getAccountType() != 3) {
						patientUsername = scan.next();
						if (patientUsername.equals("0")) {
							doctorUsername = "0";
							break;
						} else {
							patientUsername = scan.next();
							patient = Account.getDictionary().get(patientUsername);
						}
					}
					if (!(doctorUsername.equals("0") || patientUsername.equals("0"))) {
						//	doctor.;
						System.out.print("Assigned patient: ");
						patient.displayName();
						System.out.print("To Doctor: ");
						doctor.displayName();
					}
					break;
			}
		}
			try {
				FileOutputStream fileOut =
						new FileOutputStream("accounts.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(Account.getDictionary());
				out.close();
				fileOut.close();
				System.out.println("\nAccounts are saved.\n");
			} catch (IOException i) {
				System.out.println("Could not successfully save accounts.ser.");
				i.printStackTrace();
			}
			System.exit(0);

	}


	/**
	 * Console prompts to create a new account
	 */
	private static void createAccount() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\nCreating a new account.\n");
		System.out.print("Enter username: ");
		String username = scan.next();

		if (!Account.getDictionary().containsKey(username)) {
			System.out.print("Enter password: ");
			String password = scan.next();

			System.out.print("Enter account type: ");
			int accountType = scan.nextInt();
			Account.createAccount(username, password, accountType);
		} else {
			System.out.println("Username already exists.");
		}
	}

	/**
	 * Method so that the admin can list all the patient and doctor accounts currently in the HashMap
	 * This is the console version.
	 */
	private static void listUsers() {
		Scanner scan = new Scanner(System.in);
		System.out.println("List Users:\n" +
				"1. List Patients\n" +
				"2. List Doctors\n" +
				"3. List All\n" +
				"0. Return to Main gui.Menu");
		System.out.print("What would you like to do: ");
		int input = scan.nextInt();

		while (input < 0 || input > 3) {
			System.out.print("\n");
			System.out.print("Please input a valid menu item: ");
			input = scan.nextInt();
		}

		switch (input) {
			case 1:
				System.out.println("Listing Patients...");
				HashMap<String, User> dictionary = Account.getDictionary();
				for (Map.Entry<String, User> entry : dictionary.entrySet()) {
					if (entry.getValue() instanceof Patient) {
						System.out.println(entry.getValue().getUsername() + ": " +
								entry.getValue().getFirstName() + " " +
								entry.getValue().getLastName());
					}
				}
				break;
			case 2:
				System.out.println("Listing Doctors...");
				HashMap<String, User> dictionary1 = Account.getDictionary();
				for (Map.Entry<String, User> entry : dictionary1.entrySet()) {
					if (entry.getValue() instanceof Doctor) {
						System.out.println(entry.getValue().getUsername() + ": " +
								entry.getValue().getFirstName() + " " +
								entry.getValue().getLastName());
					}
				}
				break;
			case 3:
				System.out.println("Listing All Users...");
				HashMap<String, User> dictionary2 = Account.getDictionary();
				for (Map.Entry<String, User> entry : dictionary2.entrySet()) {
					if (!(entry.getValue() instanceof Admin)) {
						System.out.println(entry.getValue().getUsername() + ": " +
								entry.getValue().getFirstName() + " " +
								entry.getValue().getLastName());
					}
				}
				break;
			default:
				break;
		}
		System.out.println(" ");
	}


	/**
	 * Console prompt to display a user's information
	 * @param username
	 */
	private static void reviewUser(User username) {
		System.out.println("User " + username.getUsername() + " information:");
		System.out.print("Name: " + username.getFirstName() + " " +
				username.getLastName() + "\n");
		switch(username.getAccountType()) {
			case 1:
				break;
			case 2:
				((Doctor) username).listPatients();
				System.out.print("Department: " + ((Doctor) username).getDepartment() + "\n");
				System.out.print("Surgeon status: " + ((Doctor) username).isSurgeon() + "\n");
				break;
			case 3:
				((Patient) username).listDoctors();
				break;
			default:
				break;
		}
		Scanner scan = new Scanner(System.in);
		System.out.print("Would you like to edit user information (y or n)? ");
		String input = scan.next();

		// Loop to get the correct "y" or "n" input
		while (!input.equals("y") && !input.equals("n")) {
			System.out.print("Please input y or n: ");
			input = scan.next();
		}

		if (input.equals("y")) {
			editUser(username);
		} else if (input.equals("n")) {
			System.out.println("Returning to main menu.");
		}
	}

	/**
	 * Method to open a console edit menu based on user type
	 * @param username
	 */
	private static void editUser(User username) {
		switch(username.getAccountType()) {
			case 1:
				editAdmin(username);
				break;
			case 2:
				editDoctor(username);
				break;
			case 3:
				editPatient(username);
				break;
		}
	}

	/**
	 * Console menu to edit admin information
	 * @param username
	 */
	private static void editAdmin(User username) {
		Scanner scan = new Scanner(System.in);
		int input = -1;
		while (input != 0) {
			System.out.println("Edit " + username.getUsername() + " information:\n" +
					"1. Edit first name\n" +
					"2. Edit last name\n" +
					"0. Return to main menu");
			System.out.print("What would you like to do: ");
			input = scan.nextInt();
			while (input < 0 || input > 2) {
				System.out.print("Please input a valid menu option: ");
				input = scan.nextInt();
			}
			switch (input) {
				case 1:
					editFirstName(username);
					break;
				case 2:
					editLastName(username);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Console menu to edit doctor information
	 * @param username
	 */
	private static void editDoctor(User username) {
		Scanner scan = new Scanner(System.in);
		int input = -1;
		while (input != 0) {
			System.out.println("Edit " + username.getUsername() + " information:\n" +
					"1. Edit first name\n" +
					"2. Edit last name\n" +
					"3. Add patient\n" +
					"4. Remove patient (Disabled)\n" +
					"5. Change specialty\n" +
					"6. Toggle Surgeon Status\n" +
					"0. Return to main menu");
			System.out.print("What would you like to do: ");
			input = scan.nextInt();
			while (input < 0 || input > 6 || input == 4) {
				System.out.print("Please input a valid menu option: ");
				input = scan.nextInt();
			}
			switch (input) {
				case 1:
					editFirstName(username);
					break;
				case 2:
					editLastName(username);
					break;
				case 3:
					addPatient(username);
					break;
				case 4:
					removePatient(username);
					break;
				case 5:
					editDepartment(username);
					break;
				case 6:
					toggleSurgeon(username);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Console menu to edit patient information
	 * @param username
	 */
	private static void editPatient(User username) {
		Scanner scan = new Scanner(System.in);
		int input = -1;
		while (input != 0) {
			System.out.println("Edit " + username.getUsername() + " information:\n" +
					"1. Edit first name\n" +
					"2. Edit last name\n" +
					"3. Add doctor\n" +
					"4. Remove doctor (Disabled)\n" +
					"0. Return to main menu");
			System.out.print("What would you like to do: ");
			input = scan.nextInt();
			while (input < 0 || input > 3) {
				System.out.print("Please input a valid menu option: ");
				input = scan.nextInt();
			}
			switch (input) {
				case 1:
					editFirstName(username);
					break;
				case 2:
					editLastName(username);
					break;
				case 3:
					addDoctor(username);
					break;
				case 4:
					removeDoctor(username);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Console prompt to edit the first name of a user
	 * @param username
	 */
	private static void editFirstName(User username) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter new first name: ");
		String str = scan.next();
		System.out.print("Change first name from " + username.getFirstName()
				+ " to " + str + " (y or n)? ");
		String yn = scan.next();
		while (!yn.equals("y") && !yn.equals("n")) {
			System.out.print("Please input y or n: ");
			yn = scan.next();
		}
		if (yn.equals("y")) {
			username.setFirstName(str);
		}
	}

	/**
	 * Console prompt to edit the last name of a user
	 * @param username
	 */
	private static void editLastName(User username) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter new last name: ");
		String str2 = scan.next();
		System.out.print("Change last name from " + username.getLastName()
				+ " to " + str2 + " (y or n)? ");
		String yn2 = scan.next();
		while (!yn2.equals("y") && !yn2.equals("n")) {
			System.out.print("Please input y or n: ");
			yn2 = scan.next();
		}
		if (yn2.equals("y")) {
			username.setLastName(str2);
		}
	}

	/**
	 * Method to check if a username exists
	 * @return boolean for if a username exists
	 */
	private static User userExists() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Please input username: ");
		String username = scan.next();
		boolean exists = Account.getDictionary().containsKey(username);
		while (!exists) {
			System.out.println("That username does not exist.");
			System.out.print("Would you like to try again (y or n)? ");
			String input = scan.next();

			// Loop to get the correct "y" or "n" input
			while (!input.equals("y") && !input.equals("n")) {
				System.out.print("Please input y or n: ");
			}

			if (input.equals("y")) {
				System.out.print("Please input a valid username: ");
				username = scan.next();
				exists = Account.getDictionary().containsKey(username);
			} else if (input.equals("n")) {
				return null;
			}
		}
		return Account.getDictionary().get(username);
	}

	/**
	 * Console menu to edit the doctor's department
	 * @param user
	 */
	private static void editDepartment(User user) {
		System.out.println("1. General Services" + "     " + "2. Cardiology" + "     " + "3. Nephrology\n" +
				"4. Neurology" + "     " + " 5. Psychiatry" + "     " + "6. Oncology\n" +
				"7. Gastroenterology" + "     " + "8. Haemotology" + "     " + "9. Orthopaedics\n" +
				"0. Exit");
		Scanner scan = new Scanner(System.in);
		System.out.print("Choose department number: ");
		int input = scan.nextInt();
		while (input < 0 || input > 9) {
			System.out.print("Please input a valid department number: ");
			input = scan.nextInt();
		}
		if (input == 0) {
			System.out.print("Returning to edit menu.");
		} else {
			((Doctor) user).setDepartment(input);
			System.out.println(user.getUsername() + "'s department has been change to "
					+ ((Doctor) user).getDepartment());
		}
	}
}
