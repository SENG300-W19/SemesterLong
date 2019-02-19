import java.io.*;
import java.util.*;

public class Account {

    // HashMap of all the accounts in the database. Maps a Users object to a username.
    private static HashMap<String, Users> accDictionary;

    /**
     * Setter method to set the HashMap to a previous save state on load up
     * @param dictionary
     */
    public static void setDictionary(HashMap<String, Users> dictionary) {
        accDictionary = dictionary;
    }

    /**
     * Getter for the HashMap object
     * @return
     */
    public static HashMap<String, Users> getDictionary() {
        return accDictionary;
    }

    /**
     * Empty default constructor
     */
    public Account() {
    }

    /**
     * Method to create a new account
     * @param username
     * @param password
     * @param accountType 1 - admin, 2 - doctor, 3 patient.
     */
    public static void createAccount(String username, String password, int accountType) {
        if (accountType == 1) {
            Users acc = new Users(username, password, accountType);
            accDictionary.put(username, acc);
        } else if (accountType == 3) {
            Patient acc = new Patient(username, password, accountType);
            accDictionary.put(username, acc);
            acc.editInfo();
        }
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("accounts.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(accDictionary);
            out.close();
            fileOut.close();
            System.out.println("\nAccount " + username + " is created and saved.\n");
        } catch (IOException i) {
            System.out.println("Could not successfully create/save accounts.ser.");
        }

    }

    /**
     * Method that attempts to log in with user given username and password
     * @param username
     * @param password
     * @return 0 if username/password is incorrect, and 1-3 if users exists for the account type
     */
    public static int login(String username, String password) {
        boolean exists = accDictionary.containsKey(username);
        if (!exists) {
            return 0;
        } else {
            Users acc = accDictionary.get(username);
            if (!password.equals(acc.getPassword())) {
                return 0;
            } else if (password.equals(acc.getPassword())) {
                return acc.getAccountType();
            }
        }
        return 0;
    }



}
