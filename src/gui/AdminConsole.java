package gui;

import data.Account;
import users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

public class AdminConsole {
    private JFrame frame = new JFrame("Admin Portal");
    private JTabbedPane tabbedPane1;
    private JPanel content;
    private JFormattedTextField formattedTextField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton confirmButton;
    private JPanel createUser;
    private JComboBox comboBox1;
    private JTable accountsList;
    private DefaultTableModel tableModel;
    private static String[] columnNames = {
      "Name, First","Name, Last","Role","Username","Department", "DOB"
    };

    public AdminConsole() {
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        /**
         * Listener for  creating new account, implements input validation to make sure that user name password exist.
         */
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
                        if (choice == 0) { // CONFIRM
                            Account.createAccount(username,password,accountType);
                            JOptionPane.showMessageDialog(null, "Account Created");
                            formattedTextField1.setText(null);
                            passwordField1.setText(null);
                            passwordField2.setText(null);
                        } else if (choice == 1) { // REJECT AND CLEAR I.E. RESTART
                            formattedTextField1.setText(null);
                            passwordField1.setText(null);
                            passwordField2.setText(null);
                        }
                }
            }
        });
        /**
         * listener for accounts tabbed pane
         */
        tabbedPane1.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                HashMap<String, User> dict = Account.getDictionary();
                for (String s : Account.listUsernames()) {
                    User toAdd = dict.get(s);
                    boolean dupe = false;
                     Object[] toDisplay  = {toAdd.getFirstName(), toAdd.getLastName(),
                             toAdd.getAccountType(), toAdd.getUsername()};
                     for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (s.equals(tableModel.getValueAt(i,3))) {
                            dupe = true;
                            break;
                         }
                     }
                     if (!dupe) tableModel.addRow(toDisplay);
                }
            }
        });

        accountsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int option =
                            JOptionPane.showConfirmDialog(null, "Open this users schedule?");
                    switch (option) {
                        case 0:
                            int row = accountsList.getSelectedRow();
                            String username = accountsList.getValueAt(row, 3).toString();
                            break;
                    }
                }
            }
        });
    }

    public void close() {
        frame.dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        tableModel = new DefaultTableModel(0,0){
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        for (String s : columnNames) {
            tableModel.addColumn(s);
        }
        accountsList = new JTable(tableModel);
        accountsList.setDragEnabled(false);
    }
}
