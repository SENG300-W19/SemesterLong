package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import data.Account;
import users.Doctor;
import users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JButton signOutButton;
    private JButton signOutButton1;
    private DefaultTableModel tableModel;
    private static String[] columnNames = {
            "Name, First", "Name, Last", "Role", "Username", "Department", "DOB"
    };

    /**
     * constructor for adminConsole
     *
     * @todo refractor all classes for consistency
     */
    public AdminConsole() {
        $$$setupUI$$$();
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
                    String toDisplay = "Create account " + username + "?";
                    int choice = JOptionPane.showConfirmDialog(null, toDisplay);
                    if (choice == 0) { // CONFIRM
                        Account.createAccount(username, password, accountType);
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
                    Object[] toDisplay = {toAdd.getFirstName(), toAdd.getLastName(),
                            accountType, toAdd.getUsername(), department, toAdd.getBirthday()};
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (s.equals(tableModel.getValueAt(i, 3))) {
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
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"View", "Edit", "Cancel"}, null);
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
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == (signOutButton)) {
                    signOut();
                }
            }
        });
        signOutButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == signOutButton1) {
                    signOut();
                }
            }
        });
    }

    public void close() {
        frame.dispose();
    }

    private void signOut() {
        try {
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to sign out?");
            if (choice == 0) {
                Account.writeToFile();
                frame.dispose();
                Login init = new Login();
                init.init();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Issues saving accounts, changes may have been reverted");
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        tableModel = new DefaultTableModel(0, 0) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            }

            ;
        };
        for (String s : columnNames) {
            tableModel.addColumn(s);
        }
        accountsList = new JTable(tableModel);
        accountsList.setDragEnabled(false);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        content = new JPanel();
        content.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.setEnabled(true);
        content.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(800, 400), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Administration", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createUser = new JPanel();
        createUser.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(createUser, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        formattedTextField1 = new JFormattedTextField();
        createUser.add(formattedTextField1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField1 = new JPasswordField();
        createUser.add(passwordField1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField2 = new JPasswordField();
        createUser.add(passwordField2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Username: ");
        createUser.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Password: ");
        createUser.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Confirm Password: ");
        createUser.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Account Type: ");
        createUser.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Administrator");
        defaultComboBoxModel1.addElement("Doctor");
        defaultComboBoxModel1.addElement("Patient");
        comboBox1.setModel(defaultComboBoxModel1);
        createUser.add(comboBox1, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Create a New Account: ");
        panel2.add(label5, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        signOutButton = new JButton();
        signOutButton.setText("Sign Out");
        panel2.add(signOutButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton = new JButton();
        confirmButton.setText("Confirm");
        panel2.add(confirmButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Accounts", panel3);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        scrollPane1.setViewportView(scrollPane2);
        accountsList.setAutoCreateRowSorter(true);
        accountsList.setFillsViewportHeight(true);
        scrollPane2.setViewportView(accountsList);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        signOutButton1 = new JButton();
        signOutButton1.setText("Sign Out");
        panel4.add(signOutButton1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel4.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return content;
    }

}
