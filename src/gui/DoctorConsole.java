package gui;

import data.Account;
import data.Appointment;
import users.Doctor;
import users.Patient;
import users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.LinkedList;

public class DoctorConsole {
    private JPanel doctorPanel;
    private JTabbedPane tabbedPane1;
    private JTable patientList;
    private JScrollPane scrollPanel;
    private JTable appointmentTable;
    private JButton signOutButton;
    //private JButton requestButton;
    private JFrame frame = new JFrame("Doctor Console");
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private final static String[] names = {"Last", "First"};
    private final static String[] appts = {"Date", "Start", "Finish", "Patient"};
    private LinkedList<User> linkedPatientList;
    private Doctor docUser;


    /**
     * Constructor to instantiate doctor console
     * @param doctor
     */
    public DoctorConsole(Doctor doctor) {
        docUser = doctor;
        linkedPatientList = doctor.getPatients();
        displayPatients(linkedPatientList);
        displayAppointments(doctor);
        frame.setContentPane(doctorPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        DoctorConsole console = this;
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

        patientList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = patientList.rowAtPoint(e.getPoint());
                    Patient patient = (Patient) linkedPatientList.get(row);
                    System.out.println(patient.getFirstName());
                    Info infoView = new Info(patient, console);
                    e.consume();
                }
            }
        });
        signOutButton.addMouseListener(new MouseAdapter() {
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
        /*
        requestButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DateSelect request = new DateSelect(doctor, console);
            }
        });*/
    }

    /**
     * Display doctor's patients
     * @param patients is the list of doctor's patients
     */
    private void displayPatients(LinkedList<User> patients) {
        for (User patient : patients) {
            Object[] toAdd = {
                    patient.getLastName(), patient.getFirstName()
            };
            model.addRow(toAdd);
        }
    }

    /**
     * Display doctor's appointments
     * @param doctor is the user
     */
    public void displayAppointments(Doctor doctor) {
        for (Appointment a : doctor.getSchedule().list) {
            if (!a.isRequest()) {
                Object[] toAdd = {
                        a.getStart().toLocalDate().toString(),
                        a.getStart().toLocalTime().toString(),
                        a.getFinish().toLocalTime().toString(),
                        a.getApptPatient().getLastName() + ", " +
                        a.getApptPatient().getFirstName()

                };
                model2.addRow(toAdd);
            } else {
                Object[] toAdd = {
                        a.getStart().toLocalDate().toString(),
                        a.getStart().toLocalTime().toString(),
                        a.getFinish().toLocalTime().toString(),
                        a.getRequestStatus()
                };
                model2.addRow(toAdd);
            }
        }
    }

    /**
     * Instantiate column names
     */
    public void createUIComponents() {
        model = new DefaultTableModel(0,0);
        model2 = new DefaultTableModel(0,0);
        for (String s : names) {
            model.addColumn(s);
        }
        patientList = new JTable(model) {
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        for (String s : appts) {
            model2.addColumn(s);
        }
        appointmentTable = new JTable(model2) {
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
    }

    /**
     * Refresh doctor console
     */
    public void refreshDoctorConsole() {
        model.setRowCount(0);
        model2.setRowCount(0);
        linkedPatientList = docUser.getPatients();
        displayPatients(linkedPatientList);
        displayAppointments(docUser);
    }
}
