package gui;

import data.Account;
import data.Appointment;
import data.Schedule;
import exceptions.ScheduleException;
import users.Doctor;
import users.Patient;
import users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * @author dylnstwrt
 */
public class ScheduleView {

    private JPanel panel1;
    private JTable appointmentList;
    private JButton removeButton;
    private JButton addButton1;
    private JFrame frame = new JFrame("Appointments");
    private User user;
    private DefaultTableModel model;
    private final static String[] title = new String[]{
            "Date", "Start", "Finish", "Appointment with"
    };

    private final static String[] patTitle = new String[]{
            "Date", "Start", "Finish",
    };

    private final static String[] months = new String[]{
            "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"
    };

    private static Schedule schedule;

    /**
     * Constructor for viewing the schedule of the user
     * @param user is the user's schedule being displayed
     */
    public ScheduleView(User user) {
        this.user = user;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        schedule = user.getSchedule();
        getSchedule(user);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        ScheduleView view = this;

        addButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                try {
                    DateSelect getDate = new DateSelect(user.getSchedule(), user, view);
                    Account.writeToFile();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Issue with date");
                    e1.printStackTrace();
                }
            }
        });
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to remove the appointment?",
                        "Remove Appointment", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    int apptIndex = appointmentList.getSelectedRow();
                    Appointment appt = schedule.list.get(apptIndex);
                    Doctor doctor = appt.getApptDoctor();
                    Patient patient = appt.getApptPatient();
                    doctor.getSchedule().removeAppointment(appt);
                    patient.getSchedule().removeAppointment(appt);
                    refreshScheduleView();
                }
            }
        });
    }

    /**
     * Constructor for viewing the schedule of a doctor
     * @param user is the user's schedule being displayed
     * @param noUse is to differentiate this constructor from the other since Doctor is an object of user
     */
    public ScheduleView(Doctor doctor, int noUse) {
        this.user = doctor;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        createUIPatient();
        schedule = user.getSchedule();
        getDocSchedForPat(user);
        addButton1.setVisible(false);
        removeButton.setVisible(false);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        ScheduleView view = this;

        addButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                try {
                    DateSelect getDate = new DateSelect(user.getSchedule(), user, view);
                    Account.writeToFile();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Issue with date");
                    e1.printStackTrace();
                }
            }
        });
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to remove the appointment?",
                        "Remove Appointment", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    int apptIndex = appointmentList.getSelectedRow();
                    Appointment appt = schedule.list.get(apptIndex);
                    Doctor doctor = appt.getApptDoctor();
                    Patient patient = appt.getApptPatient();
                    doctor.getSchedule().removeAppointment(appt);
                    patient.getSchedule().removeAppointment(appt);
                    refreshScheduleView();
                }
            }
        });
    }


    /**
     * Takes user as input, returns their schedule
     * @param user is the user's schedule being displayed
     * @TODO fix dupe issues
     */
    private void getSchedule(User user) {
        if (user instanceof Doctor) {
            for (Appointment a : user.getSchedule().list) {
                if (!a.isRequest()) {
                    Object[] toAdd = {
                            a.getStart().toLocalDate().toString(),
                            a.getStart().toLocalTime().toString(),
                            a.getFinish().toLocalTime().toString(),
                            a.getApptPatient().getLastName() + ", " +
                                    a.getApptPatient().getFirstName()

                    };
                    model.addRow(toAdd);
                } else {
                    Object[] toAdd = {
                            a.getStart().toLocalDate().toString(),
                            a.getStart().toLocalTime().toString(),
                            a.getFinish().toLocalTime().toString(),
                            a.getRequestStatus()
                    };
                    model.addRow(toAdd);
                }
            }
        } else if (user instanceof Patient) {
            for (Appointment a : user.getSchedule().list) {
                Object[] toAdd = {
                        a.getStart().toLocalDate().toString(),
                        a.getStart().toLocalTime().toString(),
                        a.getFinish().toLocalTime().toString(),
                        a.getApptDoctor().getLastName() + ", " +
                                a.getApptDoctor().getFirstName()

                };
                model.addRow(toAdd);
            }
        }
    }

    /**
     * Similar to getSchedule(), this function displays the schedule for a doctor
     * for a patient hiding the name of the patient
     * @param user
     */
    private void getDocSchedForPat(User user) {
        for (Appointment a : user.getSchedule().list) {
            Object[] toAdd = {
                    a.getStart().toLocalDate().toString(),
                    a.getStart().toLocalTime().toString(),
                    a.getFinish().toLocalTime().toString(),
            };
            model.addRow(toAdd);
        }
    }

    /**
     * Initialize columns for list of appointments
     */
    private void createUIComponents() {
        model = new DefaultTableModel(0, 0);
        for (String s : title) {
            model.addColumn(s);
        }
        appointmentList = new JTable(model);
    }

    /**
     * Initialize columns for list of appointments of doctor's for the patient to view
     */
    private void createUIPatient() {
        model.setColumnCount(0);
        for (String s : patTitle) {
            model.addColumn(s);
        }
        appointmentList = new JTable(model);
    }

    /**
     * Refresh Schedule View
     */
    public void refreshScheduleView() {
        model.setRowCount(0);
        getSchedule(user);
    }

}
