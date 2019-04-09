package gui;

import data.Account;
import users.Admin;
import users.Doctor;
import users.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class is for the menu for the administrator
 */
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
    private JButton signOut;
    private JLabel nameLabel;
    private DefaultTableModel tableModel;

    private final static String[] title = new String[]{
            "Date", "Start", "Finish", "User"
    };
    private static String[] columnNames = {
            "Name, First", "Name, Last", "Role", "Username", "Department", "DOB"
    };

    /**
     * Constructor for the administrator's menu
     * @param admin is the admin account using the menu
     */
    public AdminConsole(Admin admin) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        nameLabel.setText("    " + admin.getLastName() + ", " + admin.getFirstName());
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        AdminConsole console = this;
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
                    String toDisplay = "Create account " + username + "?";
                    int choice = JOptionPane.showConfirmDialog(null, toDisplay);
                    if (choice == 0) { // CONFIRM
                        try {
                            Account.createAccount(username, password, accountType);
                            JOptionPane.showMessageDialog(null, "Account Created");
                        } catch (Exception e4) {
                            JOptionPane.showMessageDialog(null, "Username already exists.");
                            e4.printStackTrace();
                        }
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
                tableModel.setRowCount(0);
                ArrayList<User> users = Account.listUserObjects();
                users = sortUsers(users);
                for (User user : users) {
                    String accountType;
                    switch (user.getAccountType()) {
                        case 1:
                            accountType = "Administrator";
                            String department = "Administration";
                            try {
                                Object[] toDisplay = {user.getFirstName(), user.getLastName(),
                                        accountType, user.getUsername(), department, user.getBirthday()};
                                tableModel.addRow(toDisplay);
                            } catch (Exception e1) {
                                Object[] toDisplay = {user.getFirstName(), user.getLastName(),
                                        accountType, user.getUsername(), department, "2019-01-01"};
                                tableModel.addRow(toDisplay);
                            }
                            break;
                        case 2:
                            accountType = "Doctor";
                            try {
                                Object[] toDisplay2 = {user.getFirstName(), user.getLastName(),
                                        accountType, user.getUsername(), ((Doctor) user).getDepartmentGUI(), user.getBirthday()};
                                tableModel.addRow(toDisplay2);
                            } catch (Exception e2) {
                                Object[] toDisplay2 = {user.getFirstName(), user.getLastName(),
                                        accountType, user.getUsername(), ((Doctor) user).getDepartmentGUI(), "2019-01-01"};
                                tableModel.addRow(toDisplay2);
                            }
                            break;
                        case 3:
                            accountType = "Patient";
                            String department3 = "N/A";
                            try {
                                Object[] toDisplay3 = {user.getFirstName(), user.getLastName(),
                                        accountType, user.getUsername(), department3, user.getBirthday()};
                                tableModel.addRow(toDisplay3);
                            } catch (Exception e3) {
                                Object[] toDisplay3 = {user.getFirstName(), user.getLastName(),
                                        accountType, user.getUsername(), department3, "2019-01-01"};
                                tableModel.addRow(toDisplay3);
                            }
                            break;
                    }
                }
            }
        });
        /**
         * listener for doubleclick on a user to get their schedule or to edit their information.
         */
        accountsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row0 = accountsList.getSelectedRow();
                    String username0 = accountsList.getValueAt(row0, 3).toString();
                    User acc0 = Account.getDictionary().get(username0);
                    if (!(acc0 instanceof Admin)) {
                        int option =
                                JOptionPane.showOptionDialog(null, "Show schedule or edit?", "User Info",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"View Schedule", "Edit", "Cancel"}, null);
                        switch (option) {
                            // view schedule
                            case 0:
                                ScheduleView view = new ScheduleView(acc0);
                                break;
                            // edit information
                            case 1:
                            /*
                            requires restart to show changes to info in the accounts page
                             */
                                Info init = new Info(acc0, console);
                                //init.init();
                        }
                    } else if (acc0.equals(admin)) {
                        int option =
                                JOptionPane.showOptionDialog(null, "Show schedule or edit?", "User Info",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Edit", "Cancel"}, null);
                        switch (option) {
                            // view schedule
                            case 0:
                                Info init = new Info(acc0, console);
                                break;
                        }
                    }
                }
            }
        });
        signOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to logout?",
                        "Logout Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Login login = new Login();
                    login.init();
                    frame.dispose();
                }
            }
        });
    }


    /**
     * Used to initialize and create the columns names for the table
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
        tableModel = new DefaultTableModel(0, 0) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (String s : columnNames) {
            tableModel.addColumn(s);
        }
        accountsList = new JTable(tableModel);
        accountsList.setDragEnabled(false);
    }

    /**
     * Sorts all the users by account type
     * @param users is the list of all users
     * @return the sorted list of users
     */
    public ArrayList<User> sortUsers(ArrayList<User> users) {
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return Integer.compare(user1.getAccountType(), user2.getAccountType());
            }
        });
        return users;
    }

    /**
     * Used to refresh the administrator menu
     */
    public void refreshAdminConsole() {
        tableModel.setRowCount(0);
        //HashMap<String, User> dict = Account.getDictionary();
        ArrayList<User> users = Account.listUserObjects();
        users = sortUsers(users);
        for (User user : users) {
            String accountType;
            switch (user.getAccountType()) {
                case 1:
                    accountType = "Administrator";
                    String department = "Administration";
                    Object[] toDisplay = {user.getFirstName(), user.getLastName(),
                            accountType, user.getUsername(), department, user.getBirthday()};
                    tableModel.addRow(toDisplay);
                    break;
                case 2:
                    accountType = "Doctor";
                    Object[] toDisplay2 = {user.getFirstName(), user.getLastName(),
                            accountType, user.getUsername(), ((Doctor) user).getDepartmentGUI(), user.getBirthday()};
                    tableModel.addRow(toDisplay2);
                    break;
                case 3:
                    accountType = "Patient";
                    String department3 = "N/A";
                    Object[] toDisplay3 = {user.getFirstName(), user.getLastName(),
                            accountType, user.getUsername(), department3, user.getBirthday()};
                    tableModel.addRow(toDisplay3);
                    break;
            }
        }
    }
}


