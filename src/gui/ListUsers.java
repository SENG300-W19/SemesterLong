package gui;

import users.Doctor;
import users.Patient;
import users.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListUsers {
    private JPanel panel1;
    private JTable table1;
    private User participant;

    private static JFrame frame = new JFrame("Select a User");
    private static DefaultTableModel model = new DefaultTableModel();

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public ListUsers(User user){
        model.setColumnIdentifiers(new Object[] {
                "Name, Last", "Name, First", "Username"
        });
        switch(user.getAccountType()){
            case 2:
                Doctor doc = new Doctor(user);
                for (User p : doc.returnPatients()) {
                    model.addRow(new Object[] {
                            p.getLastName(),
                            p.getFirstName(),
                            p.getUsername()
                    });
                }
                break;
            case 3:
                Patient acc = new Patient(user);
                for (User d : acc.getDoctors()) {
                    model.addRow(new Object[] {
                            d.getLastName(),
                            d.getFirstName(),
                            d.getUsername()
                    });
                }
                break;
        }

    }
}
