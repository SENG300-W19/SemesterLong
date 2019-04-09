package gui;

import data.Account;
import data.Appointment;
import users.Doctor;
import users.Patient;
import users.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

/**
 * This class is for the menu for a patient
 */
public class PatientConsole {
    private JPanel patientPanel;
    private JTabbedPane tabbedPane1;
    private JButton signOut;
    private JTable patApptTable;
    private JTable doctorTable;
    private JLabel nameLabel;
    private JFrame frame = new JFrame("Patient Console");
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private final static String[] names = {"Last", "First", "Department"};
    private final static String[] appts = {"Date", "Start", "Finish", "Doctor"};
    private Patient patientUser;
    private LinkedList<User> doctorLinkedList;


    /**
     * Menu for the patient to view
     * @param patient is the user
     */
    public PatientConsole(Patient patient) {
        nameLabel.setText("    " + patient.getLastName() + ", " + patient.getFirstName());
        patientUser = patient;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width / 2, height / 2);
        doctorLinkedList = patient.getDoctors();
        displayDoctors(doctorLinkedList);
        displayAppointments(patient);
        frame.setContentPane(patientPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to close the application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    try {
                        Account.writeToFile();
                    } catch (Exception e3) {
                        JOptionPane.showMessageDialog(null, "Could not save to file");
                    }
                    System.exit(0);
                }
            }
        };

        frame.addWindowListener(exitListener);
        signOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure that you want to logout?",
                        "Logout Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Login login = new Login();
                    login.init();
                    frame.dispose();
                }
            }
        });
        doctorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = doctorTable.rowAtPoint(e.getPoint());
                    Doctor doctor = (Doctor) doctorLinkedList.get(row);
                    ScheduleView view = new ScheduleView(doctor, 0);
                    e.consume();
                }
            }
        });
    }

    /**
     * Display the appointments that the user has
     * @param patient is the user
     */
    private void displayAppointments(Patient patient) {
        for (Appointment a : patient.getSchedule().list) {
            Object[] toAdd = {
                    a.getStart().toLocalDate().toString(),
                    a.getStart().toLocalTime().toString(),
                    a.getFinish().toLocalTime().toString(),
                    a.getApptDoctor().getLastName() + ", " +
                            a.getApptDoctor().getFirstName()

            };
            model2.addRow(toAdd);
        }
    }

    /**
     * Display the doctor's that the patient is approved for
     * @param doctors is the list of doctor's the patient has
     */
    private void displayDoctors(LinkedList<User> doctors) {
        for (User doctor : doctors) {
            Object[] toAdd = {
                    doctor.getLastName(), doctor.getFirstName(), ((Doctor) doctor).getDepartmentGUI()
            };
            model.addRow(toAdd);
        }
    }

    /**
     * Used to set up the column names for the tables in the menu
     */
    private void createUIComponents() {
        model = new DefaultTableModel(0, 0);
        model2 = new DefaultTableModel(0, 0);
        for (String s : names) {
            model.addColumn(s);
        }
        doctorTable = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (String s : appts) {
            model2.addColumn(s);
        }
        patApptTable = new JTable(model2) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
