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
    private static DefaultTableModel model = new DefaultTableModel(0,0);
    private final static String[] title = new String[]{
        "Date", "Start", "Finish"
    };

    private final static String[] months = new String[] {
            "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"
    };

    /**
     * constructor for ScheduleView Class
     * @todo create monthly view class, which calls this class for a given day
     * @param user
     */
    public ScheduleView(User user) {
        getSchedule(user);
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
    }

    /**
     * Takes user as input, returns their schedule
     * @param user
     * @TODO fix dupe issues
     */
    private void getSchedule(User user) {
        for (Appointment a : user.getSchedule().list) {
            Object[] toAdd = {
              a.getStart().toLocalDate().toString(), a.getStart().toLocalTime().toString(), a.getFinish().toLocalTime().toString(),
            };
            model.addRow(toAdd);
        }
    }

    /**
     * initialize columns for list of appointments
     */
    private void createUIComponents() {
        for (String s : title) {
            model.addColumn(s);
        }
        appointmentList = new JTable(model);
    }
}
