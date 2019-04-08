package gui;

import data.Account;
import data.Appointment;
import users.Admin;
import users.Doctor;
import users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.HashMap;

/**
 * @author dylnstwrt
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
    private DefaultTableModel tableModel;
   // private DefaultTableModel requestModel;

    private final static String[] title = new String[]{
            "Date", "Start", "Finish", "User"
    };
    private static String[] columnNames = {
      "Name, First","Name, Last","Role","Username","Department", "DOB"
    };

    /**
     * constructor for adminConsole
     * @todo refractor all classes for consistency
     */
    public AdminConsole() {
        //getRequests();
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

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
                    String accountType = "";
                    String department = "";
                    switch (toAdd.getAccountType()) {
                        case 1:
                            accountType = "Administrator";
                            department = "Administration";
                            break;
                        case 2:
                            accountType = "Doctor";
                            Doctor acc = new Doctor(toAdd);
                            department = acc.getDepartment();
                            break;
                        default:
                            accountType = "Patient";
                            department = "N/A";
                    }
                     Object[] toDisplay  = {toAdd.getFirstName(), toAdd.getLastName(),
                             accountType, toAdd.getUsername(),department, toAdd.getBirthday()};
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
        /**
         * listener for doubleclick on a user to get their schedule or to edit their information.
         */
        accountsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int option =
                            JOptionPane.showOptionDialog(null, "Show schedule or edit?", "User Info",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"View", "Edit", "Cancel"}, null );
                    System.out.println(option);
                    switch (option) {
                        // view schedule
                        case 0:
                            int row = accountsList.getSelectedRow();
                            String username = accountsList.getValueAt(row, 3).toString();
                            User acc = Account.getDictionary().get(username);
                            ScheduleView view = new ScheduleView(acc);
                            break;
                        // edit information
                        case 1:
                            /*
                            requires restart to show changes to info in the accounts page
                             */
                            int row1 = accountsList.getSelectedRow();
                            String username1 = accountsList.getValueAt(row1, 3).toString();
                            User acc2 = Account.getDictionary().get(username1);
                            Info init = new Info(Account.getDictionary().get(acc2));
                            init.init();
                    }
                }
            }
        });
        /*
        acceptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to accept the request?",
                        "Accept Request", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    try {
                        int requestIndex = requestTable.getSelectedRow();
                        Appointment request = Admin.getRequests().list.get(requestIndex);
                        request.setRequestStatus("Time Off Accepted");
                        Admin.getRequests().removeAppointment(request);
                        Account.writeToFile();
                    } catch (Exception e3) {
                        JOptionPane.showMessageDialog(null, "Could not save to file");
                    }
                }
                refreshAdminConsole();
            }
        });
        declineButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to decline the request?",
                        "Decline Request", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    try {
                        int requestIndex = requestTable.getSelectedRow();
                        Appointment request = Admin.getRequests().list.get(requestIndex);
                        Doctor doctor = request.getApptDoctor();
                        doctor.getSchedule().removeAppointment(request);
                        Admin.getRequests().removeAppointment(request);
                        Account.writeToFile();
                    } catch (Exception e3) {
                        JOptionPane.showMessageDialog(null, "Could not save to file");
                    }
                }
                refreshAdminConsole();
            }
        });*/
    }

    public void close() {
        frame.dispose();
    }

    private void displayTable() {

    }
/*
    private void getRequests() {
        Admin.getRequests().sortSchedule();
        if (Admin.getRequests().list.isEmpty()) {
            System.out.println("it's empty");
        }
        for (Appointment a : Admin.getRequests().list) {
            if (a.isRequest()) {
                System.out.println("is request");
                Object[] toAdd = {
                        a.getStart().toLocalDate().toString(),
                        a.getStart().toLocalTime().toString(),
                        a.getFinish().toLocalTime().toString(),
                        a.getApptDoctor().getLastName() + ", " +
                                a.getApptDoctor().getFirstName()

                };
                requestModel.addRow(toAdd);
            }
        }
    }
*/


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
        /*
        requestModel = new DefaultTableModel(0,0);
        for (String s : title) {
            requestModel.addColumn(s);
        }
        requestTable = new JTable(requestModel);
        */
    }
    /*
    public void refreshAdminConsole() {
        requestModel.setRowCount(0);
        getRequests();
    }
    */
}
