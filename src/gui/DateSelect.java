package gui;

import data.Account;
import data.Appointment;
import data.Schedule;
import users.Admin;
import users.Doctor;
import users.Patient;
import users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author dylnstwrt
 * @todo figure out how to return the values correctly
 */


public class DateSelect {
    private LocalDateTime toReturn;
    private JPanel panel1;
    private JButton addButton;
    private JComboBox monthBox;
    private JComboBox dayBox;
    private JComboBox yearBox;
    private JComboBox<String> timeSlots;
    private JComboBox nameBox;
    private ArrayList<User> names;

    private static JFrame frame = new JFrame("Select Date");

    public DateSelect(Schedule schedule) {
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

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

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addButton) {
                    int year = Integer.parseInt(yearBox.getSelectedItem().toString());
                    int month = monthBox.getSelectedIndex() + 1;
                    int day = dayBox.getSelectedIndex() + 1;

                    int time = timeSlots.getSelectedIndex() + 8;
                    try {
                        schedule.addAppointment(new Appointment(day, month, year, time, 0));
                        JOptionPane.showMessageDialog(null, "Appointment Added");
                        Account.writeToFile();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, "Invalid Date");
                    }
                }
            }
        });
    }

    /**
     * Constructor for creating an appointment.
     * Drop down menu behaves according to user who opened the menu
     *
     * @param schedule of the user
     * @param user     account
     */
    public DateSelect(Schedule schedule, User user, ScheduleView view) {
        setNameBox(user);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addButton) {
                    int year = Integer.parseInt(yearBox.getSelectedItem().toString());
                    int month = monthBox.getSelectedIndex() + 1;
                    int day = dayBox.getSelectedIndex() + 1;

                    int time = timeSlots.getSelectedIndex() + 8;
                    int nameIndex = nameBox.getSelectedIndex();
                    User otherUser = names.get(nameIndex);
                    Schedule other;
                    try {
                        if (user instanceof Patient) {
                            Doctor doctor = (Doctor) otherUser;
                            other = doctor.getSchedule();
                            schedule.isValid(new Appointment(day, month, year, time, 0), 3);
                            other.isValid(new Appointment(day, month, year, time, 0), 2);
                            Appointment appointment = new Appointment(day, month, year, time, 0, doctor, (Patient) user);
                            schedule.addAppt(appointment);
                            other.addAppt(appointment);
                            addDoctor((Patient) user, doctor);
                            addPatient(doctor, (Patient) user);
                            view.refreshScheduleView();

                        } else if (user instanceof Doctor) {
                            Patient patient = (Patient) otherUser;
                            other = patient.getSchedule();
                            schedule.isValid(new Appointment(day, month, year, time, 0), 2);
                            other.isValid(new Appointment(day, month, year, time, 0), 3);
                            Appointment appointment = new Appointment(day, month, year, time, 0, (Doctor) user, patient);
                            schedule.addAppt(appointment);
                            other.addAppt(appointment);
                            addPatient((Doctor) user, patient);
                            addDoctor(patient, (Doctor) user);
                            view.refreshScheduleView();
                        }
                        JOptionPane.showMessageDialog(null, "Appointment Added");
                        Account.writeToFile();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, e2.getMessage());
                    }
                }
            }
        });
    }

    /**
     * Date select constructor for opening DateSelect for requesting time off
     *
     * @param doctor
     */
    public DateSelect(Doctor doctor, DoctorConsole console) {
        setRequestBox();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addButton) {
                    int year = Integer.parseInt(yearBox.getSelectedItem().toString());
                    int month = monthBox.getSelectedIndex() + 1;
                    int day = dayBox.getSelectedIndex() + 1;

                    int time = timeSlots.getSelectedIndex() + 8;
                    try {
                        Schedule schedule = doctor.getSchedule();
                        schedule.isValid(new Appointment(day, month, year, time, 0), 2);
                        Appointment appointment = new Appointment(day, month, year, time, 0, doctor);
                        schedule.addAppt(appointment);
                        Admin.getRequests().addRequests(appointment);
                        JOptionPane.showMessageDialog(null, "Request Made.");
                        Account.writeToFile();
                        console.refreshDoctorConsole();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, e2.getMessage());
                    }
                }
            }
        });
    }

    /**
     * Set the name box to "Request Time Off"
     */
    private void setRequestBox() {
        ArrayList<String> request = new ArrayList<>();
        request.add("Request Time Off");
        nameBox.setModel(new DefaultComboBoxModel((request.toArray())));
    }

    /**
     * Adds doctor to patient list
     *
     * @param patient
     * @param doctor
     */
    private void addDoctor(Patient patient, Doctor doctor) {
        LinkedList<User> doctors = patient.getDoctors();
        for (User user : doctors) {
            if (doctor.equals(user)) {
                return;
            }
        }
        doctors.add(doctor);
    }

    /**
     * Adds patient to doctor list
     *
     * @param doctor
     * @param patient
     */
    private void addPatient(Doctor doctor, Patient patient) {
        LinkedList<User> patients = doctor.getPatients();
        for (User user : patients) {
            if (patient.equals(user)) {
                return;
            }
        }
        patients.add(patient);
    }

    /**
     * Sets name box depending on user who opened DateSelect
     * TODO: change name box to doctor's that are approved for the patient (i.e. doctor list of patient)
     *
     * @param user
     */
    private void setNameBox(User user) {
        switch (user.getAccountType()) {
            case 2:
                names = Account.getNames(3);
                break;
            case 3:
                names = Account.getNames(2);
                break;
        }
        nameBox.setModel(new DefaultComboBoxModel(names.toArray()));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 1, false));
        monthBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("January");
        defaultComboBoxModel1.addElement("February");
        defaultComboBoxModel1.addElement("March");
        defaultComboBoxModel1.addElement("April");
        defaultComboBoxModel1.addElement("May");
        defaultComboBoxModel1.addElement("June");
        defaultComboBoxModel1.addElement("July");
        defaultComboBoxModel1.addElement("August");
        defaultComboBoxModel1.addElement("September");
        defaultComboBoxModel1.addElement("Octor");
        defaultComboBoxModel1.addElement("November");
        defaultComboBoxModel1.addElement("December");
        monthBox.setModel(defaultComboBoxModel1);
        panel2.add(monthBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dayBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("1");
        defaultComboBoxModel2.addElement("2");
        defaultComboBoxModel2.addElement("3");
        defaultComboBoxModel2.addElement("4");
        defaultComboBoxModel2.addElement("5");
        defaultComboBoxModel2.addElement("6");
        defaultComboBoxModel2.addElement("7");
        defaultComboBoxModel2.addElement("8");
        defaultComboBoxModel2.addElement("9");
        defaultComboBoxModel2.addElement("10");
        defaultComboBoxModel2.addElement("11");
        defaultComboBoxModel2.addElement("12");
        defaultComboBoxModel2.addElement("13");
        defaultComboBoxModel2.addElement("14");
        defaultComboBoxModel2.addElement("15");
        defaultComboBoxModel2.addElement("16");
        defaultComboBoxModel2.addElement("17");
        defaultComboBoxModel2.addElement("18");
        defaultComboBoxModel2.addElement("19");
        defaultComboBoxModel2.addElement("20");
        defaultComboBoxModel2.addElement("21");
        defaultComboBoxModel2.addElement("22");
        defaultComboBoxModel2.addElement("23");
        defaultComboBoxModel2.addElement("24");
        defaultComboBoxModel2.addElement("25");
        defaultComboBoxModel2.addElement("26");
        defaultComboBoxModel2.addElement("27");
        defaultComboBoxModel2.addElement("28");
        defaultComboBoxModel2.addElement("29");
        defaultComboBoxModel2.addElement("30");
        defaultComboBoxModel2.addElement("31");
        dayBox.setModel(defaultComboBoxModel2);
        panel2.add(dayBox, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        yearBox = new JComboBox();
        yearBox.setEditable(false);
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("2019");
        yearBox.setModel(defaultComboBoxModel3);
        panel2.add(yearBox, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("Add");
        panel2.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Date: ");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(24, 210), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Time Slot: ");
        panel2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timeSlots = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("8:00");
        defaultComboBoxModel4.addElement("9:00");
        defaultComboBoxModel4.addElement("10:00");
        defaultComboBoxModel4.addElement("11:00");
        defaultComboBoxModel4.addElement("12:00");
        defaultComboBoxModel4.addElement("13:00");
        defaultComboBoxModel4.addElement("14:00");
        defaultComboBoxModel4.addElement("15:00");
        defaultComboBoxModel4.addElement("16:00");
        defaultComboBoxModel4.addElement("17:00");
        defaultComboBoxModel4.addElement("18:00");
        timeSlots.setModel(defaultComboBoxModel4);
        panel2.add(timeSlots, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        nameBox = new JComboBox();
        panel1.add(nameBox, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(334, 38), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
