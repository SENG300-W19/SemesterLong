package gui;

import javax.swing.*;
import javax.swing.event.ListDataListener;
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
    private JFrame frame = new JFrame("Info");;
    private JPanel content;
    private JButton cancelButton;
    private JButton confirmButton;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Boolean complete = false;
    public Boolean isComplete() {
        return ((firstName != null) && (lastName != null) && (birthday!=null));
    }

    public Info(User user) {
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
                int option = JOptionPane.showConfirmDialog(null,"Proceed with changes?");
                switch(option) {
                    case 0:
                        System.out.println("YES");//YES
                        setFirstName(user);
                        setLastName(user);
                        setBirthday(user);
                        try {
                            Account.writeToFile();
                        } catch (IOException i) {
                            JOptionPane.showMessageDialog(null, "Unable to write to file");
                        }
                        JOptionPane.showMessageDialog(null, "Changes Saved");
                        frame.dispose();
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

    public Info(User user, DoctorConsole console) {
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
                int option = JOptionPane.showConfirmDialog(null,"Proceed with changes?");
                switch(option) {
                    case 0:
                        System.out.println("YES");//YES
                        setFirstName(user);
                        setLastName(user);
                        setBirthday(user);
                        try {
                            Account.writeToFile();
                        } catch (IOException i) {
                            JOptionPane.showMessageDialog(null, "Unable to write to file");
                        }
                        JOptionPane.showMessageDialog(null, "Changes Saved");
                        console.refreshDoctorConsole();
                        frame.dispose();
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

    public void init() {
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }

    private void setFirstName(User user) {user.setFirstName(firstNameFormattedTextField.getText());}
    public String getFirstName() {return this.firstName;}
    private void setLastName(User user) {user.setLastName(lastNameFormattedTextField.getText());}
    public String getLastName() {return this.lastName;}

    /**
     * TODO needs error checking
     * @param user
     */
    private void setBirthday(User user) {
        int month = monthBox.getSelectedIndex()+1;
        int day = dayBox.getSelectedIndex()+1;
        int year = Integer.parseInt(yearBox.getSelectedItem().toString());
        user.setBirthday(LocalDate.of(year, month, day));
    }

}

