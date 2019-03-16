package gui;

import users.Admin;
import data.Account;
import users.User;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Login {
    private JButton CANCELButton;
    private JButton CONFIRMButton;
    private JPasswordField passwordField1;
    private JPanel outer;
    private JFormattedTextField formattedTextField1;
    private JComboBox comboBox1;
    private JRadioButton debugRadioButton;
    private Account account;

    private Login() {

        CONFIRMButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                String username  = formattedTextField1.getText();
                    String password = String.valueOf(passwordField1.getPassword());
                    boolean debug = debugRadioButton.isSelected();
                    Integer accountType = comboBox1.getSelectedIndex()+1;
                    if (debug) {
                        System.out.println(username);
                        System.out.println(password);
                        System.out.println(accountType);
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
                        switch (accountType) {
                            case 1:

                                break;
                            case 2: // deal with on other iteration

                                break;
                            case 3: // deal with on other iteration

                                 break;
                        }


                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(outer, "No accounts detected, creating new admin account");
                        HashMap<String, User> dictionary = new HashMap<>();
                        Account.setDictionary(dictionary);
                        Account.createAccount("admin", "123", 1);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(outer, "Issues getting accounts");
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
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().outer);
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

