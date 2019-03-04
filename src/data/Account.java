package data;
import java.io.*;
import java.util.*;

import users.Doctor;
import users.Patient;
import users.User;

public class Account{

    // HashMap of all the accounts in the database. Maps a User object to a username.
    private static HashMap<String, User> accDictionary;

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
     * Method to create a new account
     * @param username
     * @param password
     * @param accountType 1 - admin, 2 - doctor, 3 patient.
     */
    public static void createAccount(String username, String password, int accountType) {
       // if (accountType == 1) {
            User acc = new User(username, password, accountType);
            System.out.println("Enter the user's real name: ");
            acc.setName();
            accDictionary.put(username, acc);
      //  } else if (accountType == 2) {
        //	Doctor acc = new Doctor(username,password);
        //	accDictionary.put(username, acc);
        //	acc.setName();
        //	acc.setSpecialty();
    //    }else if (accountType == 3) {
      //      Patient acc = new Patient(username, password);
       //     accDictionary.put(username, acc);
       //     acc.setName();
     //   } 
        
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
            i.printStackTrace();
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
            User acc = accDictionary.get(username);
            if (!password.equals(acc.getPassword())) {
                return 0;
            } else if (password.equals(acc.getPassword())) {
                return acc.getAccountType();
            }
        }
        return 0;
    }



}
