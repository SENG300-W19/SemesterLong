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
                    int month = monthBox.getSelectedIndex()+1;
                    int day = dayBox.getSelectedIndex()+1;

                    int time = timeSlots.getSelectedIndex()+8;
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
     * @param schedule of the user
     * @param user account
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
                    int month = monthBox.getSelectedIndex()+1;
                    int day = dayBox.getSelectedIndex()+1;

                    int time = timeSlots.getSelectedIndex()+8;
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
                    int month = monthBox.getSelectedIndex()+1;
                    int day = dayBox.getSelectedIndex()+1;

                    int time = timeSlots.getSelectedIndex()+8;
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
     * @param user
     */
    private void setNameBox(User user) {
        switch(user.getAccountType()) {
            case 2:
                names = Account.getNames(3);
                break;
            case 3:
                names = Account.getNames(2);
                break;
        }
        nameBox.setModel(new DefaultComboBoxModel(names.toArray()));
    }

}
