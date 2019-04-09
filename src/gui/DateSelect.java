package gui;

import data.Account;
import data.Appointment;
import data.Schedule;
import users.Doctor;
import users.Patient;
import users.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * This class is the pop-up menu to select a date for an appointment
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

    /**
     * Constructor for creating an appointment.
     * Drop down menu behaves according to user who opened the menu
     * @param schedule of the user
     * @param user the user whose appointment is being changed
     * @ScheduleView view is the schedule view window that needs to be refreshed
     */
    public DateSelect(Schedule schedule, User user, ScheduleView view) {
        setNameBox(user);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
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
     * Adds doctor to patient list
     * @param patient is the patient being approved for a doctor
     * @param doctor is the doctor being added to patient's list of doctor's
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
     * @param doctor is the doctor whose list of patients is being changed
     * @param patient is the patient being added to the doctor's list of patients
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
     * @param user is the user to check for type and display the appropriate user
     */
    private void setNameBox(User user) {
        switch (user.getAccountType()) {
            case 2:
                names = Account.getNames(3);
                break;
            case 3:
                names = new ArrayList<>(((Patient) user).getDoctors());
                break;
        }
        nameBox.setModel(new DefaultComboBoxModel(names.toArray()));
    }
}
