package gui;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

import data.Account;
import users.*;

/**
 * @author dylnstwrt
 */
public class Info {
    private JFormattedTextField lastNameFormattedTextField;
    private JFormattedTextField firstNameFormattedTextField;
    private JComboBox<String> yearBox;
    private JComboBox dayBox;
    private JComboBox monthBox;
    private JFrame frame = new JFrame("Info");

    private final static String[] months = new String[]{
            "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"
    };

    private static final String[] departments = {"General Services", "Cardiology", "Nephrology", "Neurology",
            "Psychiatry", "Oncology", "Gastroenterology", "Haemotology", "Orthopaedics"};

    private JPanel content;
    private JButton cancelButton;
    private JButton confirmButton;
    private JLabel departmentLabel;
    private JComboBox departmentBox;
    private String firstName;
    private String lastName;
    private LocalDate birthday;


    /**
     * Constructor to edit a user's info
     * @param user is the user info being edited
     */
    public Info(User user) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        if (user instanceof Patient || user instanceof Admin) {
            departmentLabel.setVisible(false);
            departmentBox.setVisible(false);
        }
        birthday = user.getBirthdayDate();
        if (birthday != null) {
            int day = birthday.getDayOfMonth();
            dayBox.setSelectedIndex(day - 1);
            int month = birthday.getMonthValue();
            monthBox.setSelectedItem(months[month - 1]);
            yearBox.setSelectedItem(birthday.getYear());
        }
        if (user.getFirstName() == null) {
            firstNameFormattedTextField.setText("FirstName");
        } else {
            firstNameFormattedTextField.setText(user.getFirstName());
        }
        if (user.getLastName() == null) {
            firstNameFormattedTextField.setText("LastName");
        } else {
            lastNameFormattedTextField.setText(user.getLastName());
        }
        init();
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to close the window?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    try {
                        Account.writeToFile();
                    } catch (Exception e3) {
                        JOptionPane.showMessageDialog(null, "Could not save to file");
                    }
                    frame.dispose();
                }
            }
        };
        frame.addWindowListener(exitListener);
        confirmButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int option = JOptionPane.showConfirmDialog(null, "Proceed with changes?");
                switch (option) {
                    case 0:
                        try {
                            setBirthday(user);
                            setFirstName(user);
                            setLastName(user);

                            if (user instanceof Doctor) {
                                int index = departmentBox.getSelectedIndex();
                                ((Doctor) user).setDepartmentGUI(departments[index]);
                            }
                            try {
                                Account.writeToFile();
                            } catch (IOException i) {
                                JOptionPane.showMessageDialog(null, "Unable to write to file");
                            }
                            JOptionPane.showMessageDialog(null, "Changes Saved");
                            frame.dispose();
                        } catch (Exception e2) {
                            JOptionPane.showMessageDialog(null, e2.getMessage());
                        }
                        break;
                    case 1: //NO
                        JOptionPane.showMessageDialog(null, "No changes saved");
                        frame.dispose();
                        break;
                    case 2: //CANCEL
                        break;
                }
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                close();
            }
        });
    }

    /**
     * Constructor to edit a user's info from the Admin Console
     * @param user is the user info being edited
     */
    public Info(User user, AdminConsole console) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        if (user instanceof Patient || user instanceof Admin) {
            departmentLabel.setVisible(false);
            departmentBox.setVisible(false);
        } else {
            departmentBox.setSelectedItem(((Doctor) user).getDepartmentGUI());
        }
        birthday = user.getBirthdayDate();
        if (birthday != null) {
            int day = birthday.getDayOfMonth();
            dayBox.setSelectedIndex(day - 1);
            int month = birthday.getMonthValue();
            monthBox.setSelectedItem(months[month - 1]);
            yearBox.setSelectedItem(birthday.getYear());
        }
        if (user.getFirstName().equals("")) {
            firstNameFormattedTextField.setText("FirstName");
        } else {
            firstNameFormattedTextField.setText(user.getFirstName());
        }
        if (user.getLastName().equals("")) {
            firstNameFormattedTextField.setText("LastName");
        } else {
            lastNameFormattedTextField.setText(user.getLastName());
        }
        init();
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to close the window?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    try {
                        Account.writeToFile();
                    } catch (Exception e3) {
                        JOptionPane.showMessageDialog(null, "Could not save to file");
                    }
                    frame.dispose();
                }
            }
        };
        frame.addWindowListener(exitListener);
        confirmButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int option = JOptionPane.showConfirmDialog(null, "Proceed with changes?");
                switch (option) {
                    case 0:
                        try {
                            setBirthday(user);
                            setFirstName(user);
                            setLastName(user);

                            if (user instanceof Doctor) {
                                int index = departmentBox.getSelectedIndex();
                                ((Doctor) user).setDepartmentGUI(departments[index]);
                            }
                            try {
                                Account.writeToFile();
                            } catch (IOException i) {
                                JOptionPane.showMessageDialog(null, "Unable to write to file");
                            }
                            JOptionPane.showMessageDialog(null, "Changes Saved");
                            console.refreshAdminConsole();
                            frame.dispose();
                        } catch (Exception e2) {
                            JOptionPane.showMessageDialog(null, e2.getMessage());
                        }
                        break;
                    case 1: //NO
                        JOptionPane.showMessageDialog(null, "No changes saved");
                        console.refreshAdminConsole();
                        frame.dispose();
                        break;
                    case 2: //CANCEL
                        console.refreshAdminConsole();
                        break;
                }
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                close();
            }
        });
    }

    /**
     * Constructor to edit a user's info from the Doctor's console
     * @param user is the user info being edited
     */
    public Info(User user, DoctorConsole console) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        departmentLabel.setVisible(false);
        departmentBox.setVisible(false);
        birthday = user.getBirthdayDate();
        if (birthday != null) {
            int day = birthday.getDayOfMonth();
            dayBox.setSelectedIndex(day - 1);
            int month = birthday.getMonthValue();
            monthBox.setSelectedItem(months[month - 1]);
            yearBox.setSelectedItem(birthday.getYear());
        }
        if (user.getFirstName().equals("")) {
            firstNameFormattedTextField.setText("FirstName");
        } else {
            firstNameFormattedTextField.setText(user.getFirstName());
        }
        if (user.getLastName().equals("")) {
            firstNameFormattedTextField.setText("LastName");
        } else {
            lastNameFormattedTextField.setText(user.getLastName());
        }
        init();
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to close the window?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    try {
                        Account.writeToFile();
                    } catch (Exception e3) {
                        JOptionPane.showMessageDialog(null, "Could not save to file");
                    }
                    frame.dispose();
                }
            }
        };
        frame.addWindowListener(exitListener);
        confirmButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int option = JOptionPane.showConfirmDialog(null, "Proceed with changes?");
                switch (option) {
                    case 0:
                        try {
                            setBirthday(user);
                            setFirstName(user);
                            setLastName(user);
                            try {
                                Account.writeToFile();
                            } catch (IOException i) {
                                JOptionPane.showMessageDialog(null, "Unable to write to file");
                            }
                            JOptionPane.showMessageDialog(null, "Changes Saved");
                            console.refreshDoctorConsole();
                            frame.dispose();
                        } catch (Exception e2) {
                            JOptionPane.showMessageDialog(null, e2.getMessage());
                        }
                        break;
                    case 1: //NO
                        JOptionPane.showMessageDialog(null, "No changes saved");
                        console.refreshDoctorConsole();
                        frame.dispose();
                        break;
                    case 2: //CANCEL
                        console.refreshDoctorConsole();
                        break;
                }
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                close();
            }
        });
    }


    /**
     * Initialize the frame
     */
    public void init() {
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Close the window
     */
    public void close() {
        frame.dispose();
    }

    /**
     * Set first name of user
     * @param user is the user's name being changed
     */
    private void setFirstName(User user) {
        String text = firstNameFormattedTextField.getText();
        //if (!text.equals("") && !text.equals("FirstName")) {
        user.setFirstName(text);
        //}
    }

    /**
     * Set last name of user
     * @param user is the user's name being changed
     */
    private void setLastName(User user) {
        String text = lastNameFormattedTextField.getText();
        //if (!text.equals("") && !text.equals("LastName")) {
        user.setLastName(text);
        //}
    }


    /**
     * Set birthday of the user. Throws an exception if day is not valid
     * @param user is the user's birthday being changed
     */
    private void setBirthday(User user) throws Exception {
        int month = monthBox.getSelectedIndex() + 1;
        int day = dayBox.getSelectedIndex() + 1;
        int year = Integer.parseInt(yearBox.getSelectedItem().toString());
        if (year > 2019 || (year == 2019 && month > 4)) {
            throw new Exception("Date is in the future");
        }
        switch (month) {
            case 2:
                if (day > 29) {
                    throw new Exception("February has up to 29 days.");
                }
                break;
            case 4:
                if (day > 30) {
                    throw new Exception("April has 30 days.");
                }
                break;
            case 6:
                if (day > 30) {
                    throw new Exception("June has 30 days.");
                }
                break;
            case 9:
                if (day > 30) {
                    throw new Exception("September has 30 days.");
                }
                break;
            case 11:
                if (day > 30) {
                    throw new Exception("November has 30 days.");
                }
                break;
        }
        user.setBirthday(LocalDate.of(year, month, day));
    }
}

