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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

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

    private static ArrayList<Appointment> sharedAppt;
    /**
     * constructor for ScheduleView Class
     * @todo create monthly view class, which calls this class for a given day
     * @param user
     */
    public ScheduleView(User user) {
        getSchedule(user);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        addButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                    try {
                        DateSelect getDate = new DateSelect(user.getSchedule(), user);
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
            }
        });
    }

    public ScheduleView(Doctor doctor, Patient patient) {
        getDoctorPatientAppt(doctor, patient);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        addButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                try {
                    DateSelect getDate = new DateSelect(user.getSchedule());
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
                int apptIndex = appointmentList.getSelectedRow();
                System.out.println("Selected row: " + apptIndex);
                Appointment appt = sharedAppt.get(apptIndex);
                doctor.getSchedule().removeAppointment(appt);
                patient.getSchedule().removeAppointment(appt);
                getDoctorPatientAppt(doctor, patient);
                appointmentList.repaint();
            }
        });
    }




    private void getDoctorPatientAppt(Doctor doctor, Patient patient) {
        for (Appointment a : patient.getSchedule().list) {
            for (Appointment b : doctor.getSchedule().list) {
                if (a.equals(b)) {
                    sharedAppt.add(a);
                    Object[] toAdd = {a.getStart().toLocalDate().toString(), a.getStart().toLocalTime().toString(), a.getFinish().toLocalTime().toString(),
                    };
                    model.addRow(toAdd);
                }
            }
        }

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
                        "Time Off Request"
                };
                model.addRow(toAdd);
            }
        }

    }

    /**
     * initialize columns for list of appointments
     */
    private void createUIComponents() {
        model = new DefaultTableModel(0,0);
        for (String s : title) {
            model.addColumn(s);
        }
        appointmentList = new JTable(model);
    }
}
