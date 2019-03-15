
import java.util.HashMap;
import java.util.Scanner;

import data.*;
import users.*;
import gui.*;

import java.io.*;

public class ManagementSystem implements Serializable {

    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        HashMap<String, User> accDictionary = null;
        try {
            FileInputStream fileIn = new FileInputStream("accounts.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // if you get a class not found error at the line below, try deleting/recreating the .ser file and run main again.
            accDictionary = (HashMap<String, User>)in.readObject();
            Account.setDictionary(accDictionary);
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("accounts.ser not found.\nCreating a new account dictionary...");
            accDictionary = new HashMap<String, User>();
            Account.setDictionary(accDictionary);
            Account.createAccount("admin", "123", 1);
        } catch (ClassNotFoundException c) {
            System.out.println("Accounts class not found.\nExiting program.");
            c.printStackTrace();
            System.exit(1);
        }

        Scanner scan = new Scanner(System.in);

        /** 0 = Not a user
         * 1 = Admin
         * 2 = Doctor
         * 3 = Patient
         */

        int user = 0;

        while (user == 0) {

            user = Menu.inputLogin();           // Prompt user to input log in information

            // If username/password is incorrect loop if user wants to try again
            while (user == 0) {
                System.out.println("Incorrect username or password.");
                System.out.print("Would you like to try again (y or n)? ");
                String input = scan.nextLine();

                // Loop to get the correct "y" or "n" input
                while (!input.equals("y") && !input.equals("n")) {
                    System.out.print("Please input y or n: ");
                }

                // Exit program if user inputs "n"
                if (input.equals("n")) {
                    System.exit(0);
                } else if (input.equals("y")) {
                    user = Menu.inputLogin();
                }
            }
        }

        Menu.prompt(user);
    }
}
