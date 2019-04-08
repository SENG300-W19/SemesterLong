package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton signOutButton;
    private JFrame frame = new JFrame("Appointments");
    private User user;
    private DefaultTableModel model;
    private final static String[] title = new String[]{
            "Date", "Start", "Finish", "User"
    };

    private final static String[] months = new String[]{
            "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"
    };

    private static ArrayList<Appointment> sharedAppt;

    /**
     * constructor for ScheduleView Class
     *
     * @param user
     * @todo create monthly view class, which calls this class for a given day
     */
    public ScheduleView(User user) {
        $$$setupUI$$$();
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
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int choice = JOptionPane.showConfirmDialog(null, "Do you want to sign out?");
                    if (choice == 0) {
                        Account.writeToFile();
                        System.exit(0);
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Issues saving accounts, changes may have been reverted");
                }
            }
        });
    }

    public ScheduleView(Doctor doctor, Patient patient) {
        $$$setupUI$$$();
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
     *
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
        model = new DefaultTableModel(0, 0);
        for (String s : title) {
            model.addColumn(s);
        }
        appointmentList = new JTable(model);
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
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(appointmentList);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        removeButton = new JButton();
        removeButton.setText("Remove");
        panel2.add(removeButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton1 = new JButton();
        addButton1.setText("Add");
        panel2.add(addButton1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        signOutButton = new JButton();
        signOutButton.setText("Sign Out");
        panel2.add(signOutButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
