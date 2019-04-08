package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
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
     *
     * @param doctor
     */
    public DoctorConsole(Doctor doctor) {
        docUser = doctor;
        $$$setupUI$$$();
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
     *
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
     *
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
        model = new DefaultTableModel(0, 0);
        model2 = new DefaultTableModel(0, 0);
        for (String s : names) {
            model.addColumn(s);
        }
        patientList = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (String s : appts) {
            model2.addColumn(s);
        }
        appointmentTable = new JTable(model2) {
            public boolean isCellEditable(int row, int column) {
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        doctorPanel = new JPanel();
        doctorPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        doctorPanel.setMinimumSize(new Dimension(240, 267));
        doctorPanel.setPreferredSize(new Dimension(804, 404));
        tabbedPane1 = new JTabbedPane();
        doctorPanel.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setDoubleBuffered(true);
        tabbedPane1.addTab("Appointments", panel1);
        scrollPanel = new JScrollPane();
        panel1.add(scrollPanel, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        appointmentTable.setDragEnabled(false);
        scrollPanel.setViewportView(appointmentTable);
        signOutButton = new JButton();
        signOutButton.setLabel("Sign Out");
        signOutButton.setText("Sign Out");
        panel1.add(signOutButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Patients", panel2);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(patientList);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return doctorPanel;
    }
}
