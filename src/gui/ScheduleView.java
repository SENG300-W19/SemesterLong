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
        "Date", "Start", "Finish", "User"
    };

    private final static String[] months = new String[] {
            "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"
    };

    private static Schedule schedule;
    /**
     * constructor for ScheduleView Class
     * @todo create monthly view class, which calls this class for a given day
     * @param user
     */
    public ScheduleView(User user) {
        this.user = user;
        schedule = user.getSchedule();
        getSchedule(user);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        ScheduleView view = this;
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
     * @param user
     * @TODO fix dupe issues
     */
    private void getSchedule(User user) {

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

    }

    /**
     * initialize columns for list of appointments
     */
    private void createUIComponents() {
        System.out.println("Creating UI components");
        model = new DefaultTableModel(0,0);
        for (String s : title) {
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
