package gui;

import data.Account;
import users.Doctor;
import users.User;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

@SuppressWarnings("unchecked")

/**
 * @author dylnstwrt
 * Class for GUI login screen.
 */
public class Login {
    private JButton CANCELButton;
    private JButton CONFIRMButton;
    private JPasswordField passwordField1;
    private JPanel outer;
    private JFrame frame = new JFrame("Login");
    private JFormattedTextField formattedTextField1;
    private JRadioButton debugRadioButton;
    private Account account;

    public Login() {
        /**
         * Listener for confirm button, which checks if an accounts.ser db has been initialized; makes a new one and
         * default admin account with credentials admin:123.
         * If accounts.ser already exists, will try to login with the selected credentials.
         * Then opens the corresponding GUI class for their terminal.
         */
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to close the application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    try {
                        Account.writeToFile();
                    } catch (Exception e3) {
                        JOptionPane.showMessageDialog(null, "Could not save to file");
                    }
                    System.exit(0);
                }
            }
        };
        frame.addWindowListener(exitListener);
        CONFIRMButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                String username  = formattedTextField1.getText();
                    String password = String.valueOf(passwordField1.getPassword());
                    boolean debug = debugRadioButton.isSelected();
                    if (debug) {
                        System.out.println(username);
                        System.out.println(password);
                    }
                    try {
                        FileInputStream fileIn;
                        ObjectInputStream in;
                        fileIn = new FileInputStream("accounts.ser");
                        in = new ObjectInputStream(fileIn);
                        HashMap<String, User> dictionary = (HashMap<String, User>)in.readObject();
                        Account.setDictionary(dictionary);
                        in.close();
                        fileIn.close();
                        User acc = Account.login(username, password);
                        if (acc != null) {
                            switch (acc.getAccountType()) {
                                case 1:
                                    frame.dispose();
                                    AdminConsole console = new AdminConsole();
                                    break;
                                case 2: // deal with on other iteration
                                    DoctorConsole docView = new DoctorConsole((Doctor) acc);
                                    frame.dispose();
                                    break;
                                case 3: // deal with on other iteration
                                    ScheduleView view = new ScheduleView(acc);
                                    frame.dispose();
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, "Invalid Username/Password");
                            }
                        }


                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(outer, "No accounts detected, creating new admin account,.");
                        HashMap<String, User> dictionary = new HashMap<>();
                        Account.setDictionary(dictionary);
                        Account.createAccount("admin", "123", 1);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(outer, "Issues getting account.");
                        ex.printStackTrace();
                    }
            }
        });
        CANCELButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                System.exit(0);
            }
        });
    }

    public void init() {
        frame.setContentPane(this.outer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args ) {
        Login log = new Login();
        log.init();
    }
}

