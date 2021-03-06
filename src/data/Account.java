package data;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import gui.AdminConsole;
import users.Admin;
import users.Doctor;
import users.Patient;
import users.User;

/**
 * This class is manages all the User accounts
 */
public class Account{

    // HashMap of all the accounts in the database. Maps a User object to a username.
    private static HashMap<String, User> accDictionary;
    //private static ArrayList<>


    /**
     * Setter method to set the HashMap to a previous save state on load up
     * @param dictionary
     */
    public static void setDictionary(HashMap<String, User> dictionary) {
        accDictionary = dictionary;
    }


    /**
     * Getter for the HashMap object
     * @return
     */
    public static HashMap<String, User> getDictionary() {
        return accDictionary;
    }

    /**
     * Empty default constructor
     */
    public Account() {
    }

    /**
     * Method to create a new account. Saves to accounts.ser file after creation.
     * @param username
     * @param password
     * @param accountType 1 - admin, 2 - doctor, 3 patient.
     */
    public static void createAccount(String username, String password, int accountType) throws Exception {
        username = username.toLowerCase();
        boolean exists = accDictionary.containsKey(username);
        if (!exists) {
            switch (accountType) {
                case 1:
                    Admin admin = new Admin(username, password);
                    if (username.equals("admin")) {
                        admin.setFirstName("Administrator");
                        admin.setLastName("System");
                        LocalDate now = LocalDate.of(LocalDate.now().getYear(),
                                LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
                        admin.setBirthday(now);
                    } else {
                        admin.setInfo(admin);
                    }
                    accDictionary.put(username, admin);
                    break;
                case 2:
                    Doctor doctor = new Doctor(username, password);
                    doctor.setInfo(doctor);
                    accDictionary.put(username, doctor);
                    break;
                case 3:
                    Patient acc = new Patient(username, password);
                    acc.setInfo(acc);
                    accDictionary.put(username, acc);
                    break;
            }
            try {
                writeToFile();
                System.out.println("\nAccount " + username + " is created and saved.\n");
            } catch (IOException i) {
                System.out.println("Could not successfully create/save accounts.ser.");
                i.printStackTrace();
            }
        } else {
            throw new Exception("Username already exists");
        }
    }

    /**
     * Method to create a new account for GUI. Saves to accounts.ser file after creation.
     * @param username
     * @param password
     * @param accountType 1 - admin, 2 - doctor, 3 patient.
     * @param console is the administrator console to refresh
     */
    /*
    public static void createAccountGUI(String username, String password, int accountType, AdminConsole console) throws Exception {
        username = username.toLowerCase();
        boolean exists = accDictionary.containsKey(username);
        if (!exists) {
            switch (accountType) {
                case 1:
                    Admin admin = new Admin(username, password);
                    if (username.equals("admin")) {
                        admin.setFirstName("Administrator");
                        admin.setLastName("System");
                        LocalDate now = LocalDate.of(LocalDate.now().getYear(),
                                LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
                        admin.setBirthday(now);
                    } else {
                        admin.setInfo(admin);
                    }
                    accDictionary.put(username, admin);
                    break;
                case 2:
                    Doctor doctor = new Doctor(username, password);
                    doctor.setInfo(doctor);
                    accDictionary.put(username, doctor);
                    break;
                case 3:
                    Patient acc = new Patient(username, password);
                    acc.setInfo(acc);
                    accDictionary.put(username, acc);
                    break;
            }
            try {
                writeToFile();
                System.out.println("\nAccount " + username + " is created and saved.\n");
            } catch (IOException i) {
                System.out.println("Could not successfully create/save accounts.ser.");
                i.printStackTrace();
            }
        } else {
            throw new Exception("Username already exists");
        }
    }
    */

    /**
     * Method that attempts to log in with user given username and password
     * @param username
     * @param password
     * @return 0 if username/password is incorrect, and 1-3 if users exists for the account type
     */
    public static User login(String username, String password) {
        username = username.toLowerCase();
        boolean exists = accDictionary.containsKey(username);
        if (!exists) {
            return null;
        } else {
            User user = accDictionary.get(username);
            if (!password.equals(user.getPassword())) {
                return null;
            } else if (password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Prompts user to input username and password
     * @return the account type of the user
     */
    public static User inputLogin() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Login...");
        System.out.print("Username: ");
        String username = scan.nextLine();

        System.out.print("Password: ");
        String password = scan.nextLine();
        System.out.print("\n");
        User user = login(username, password);

        return user;
    }


    /**
     * author @Dylan
     * @return list of strings which represent the list of usernames in the dictionary
     */
    public static List<String> listUsernames() {
        return new ArrayList<>(getDictionary().keySet());
    }

    public static ArrayList<User> listUserObjects() {
        ArrayList<String> usernames = new ArrayList<>(accDictionary.keySet());
        ArrayList<User> users = new ArrayList<>();
        for (String username : usernames) {
            users.add(accDictionary.get(username));
        }
        return users;
    }

    /**
     * method to write out the dictionary to the .ser file, should be called when wanting to save changes in the dictionary.
     * @throws IOException
     * @author Dylan
     */
    public static void writeToFile() throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream("accounts.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(accDictionary);
            out.close();
            fileOut.close();
            System.out.println("Changes made.");
        } catch (IOException e) {
            System.out.println("Issues making edits");
            e.printStackTrace();
        }
    }

    /**
     * Get the names of all the users of an account type
     * @param accType is the account type of the user
     * @return an array of all the users of an account type
     */
    public static ArrayList<User> getNames(int accType) {
        ArrayList<User> users = new ArrayList<>();
        switch (accType) {
            case 1:
                HashMap<String, User> dictionary = Account.getDictionary();
                for (Map.Entry<String, User> entry : dictionary.entrySet()) {
                    if (entry.getValue() instanceof Admin) {
                        users.add(entry.getValue());
                    }
                }
                break;
            case 2:
                HashMap<String, User> dictionary1 = Account.getDictionary();
                for (Map.Entry<String, User> entry : dictionary1.entrySet()) {
                    if (entry.getValue() instanceof Doctor) {
                        users.add(entry.getValue());
                    }
                }
                break;
            case 3:
                HashMap<String, User> dictionary2 = Account.getDictionary();
                for (Map.Entry<String, User> entry : dictionary2.entrySet()) {
                    if (entry.getValue() instanceof Patient) {
                        users.add(entry.getValue());
                    }
                }
                break;
        }
        return users;
    }

}
