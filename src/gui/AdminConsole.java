package gui;

import data.Account;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class AdminConsole {
    private JFrame frame = new JFrame("Admin Portal");
    private JTabbedPane tabbedPane1;
    private JPanel content;
    private JFormattedTextField formattedTextField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton confirmButton;
    private JPanel createUser;
    private JComboBox accountsBox;
    private JComboBox comboBox1;
    private JFormattedTextField formattedTextField2;
    private JFormattedTextField formattedTextField4;
    private JFormattedTextField formattedTextField5;
    private JPasswordField passwordField3;
    private JTabbedPane tabbedPane2;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;

    public AdminConsole() {
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        confirmButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                String username = formattedTextField1.getText();
                String password = String.valueOf(passwordField1.getPassword());
                String confirmPassword = String.valueOf(passwordField2.getPassword());
                int accountType = comboBox1.getSelectedIndex() + 1;
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match");
                } else {
                    String toDisplay = "Create account "+username+"?";
                    int choice = JOptionPane.showConfirmDialog(null, toDisplay);
                        if (choice == 0) {
                            Account.createAccount(username,password,accountType);
                            JOptionPane.showMessageDialog(null, "Account Created");
                            formattedTextField1.setText(null);
                            passwordField1.setText(null);
                            passwordField2.setText(null);
                        } else if (choice == 1) {
                            formattedTextField1.setText(null);
                            passwordField1.setText(null);
                            passwordField2.setText(null);
                        }
                }
            }
        });
        tabbedPane1.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                List<String> keys = Account.listUsernames();
                for (String key : keys) {
                    boolean alreadyInList = false;
                   for (int i = 0; i < accountsBox.getItemCount(); i ++) {
                       if (accountsBox.getItemAt(i) == key) {
                           alreadyInList = true;
                           break;
                       }
                   }
                   if (!alreadyInList) {accountsBox.addItem(key);}
                }
            }
        });
        accountsBox.addItemListener(new ItemListener() {
            /**
             * Invoked when an item has been selected or deselected by the user.
             * The code written for this method performs the operations
             * that need to occur when an item is selected (or deselected).
             *
             * @param e the event to be processed
             */
            @Override
            public void itemStateChanged(ItemEvent e) {
                String key = e.getItem().toString();
                formattedTextField2.setText(key);
                passwordField3.setText(Account.getDictionary().get(key).getPassword());
                formattedTextField4.setText(Account.getDictionary().get(key).getFirstName());
                formattedTextField5.setText(Account.getDictionary().get(key).getLastName());
            }
        });
    }

    public void close() {
        frame.dispose();
    }

}
