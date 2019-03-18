package gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import users.*;

public class Info {
    private JFormattedTextField lastNameFormattedTextField;
    private JFormattedTextField firstNameFormattedTextField;
    private JComboBox yearBox;
    private JComboBox dayBox;
    private JComboBox monthBox;
    private JFrame frame = new JFrame("Info");;
    private JPanel content;
    private JButton cancelButton;
    private JButton confirmButton;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Boolean complete = false;
    public Boolean isComplete() {
        return ((firstName != null) && (lastName != null) && (birthday!=null));
    }

    public Info(User user) {
        confirmButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int option = JOptionPane.showConfirmDialog(null,"Proceed with changes?");
                switch(option) {
                    case 0:
                        System.out.println("YES");//YES
                        setFirstName(user);
                        setLastName(user);
                        JOptionPane.showMessageDialog(null, "Changes Saved");
                        frame.dispose();
                        break;
                    case 1: //NO
                        JOptionPane.showMessageDialog(null, "No changes saved");
                        frame.dispose();
                        break;
                    case 2: //CANCEL
                        break;
                }
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                close();
            }
        });
    }

    public void init() {
        frame.setContentPane(content);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }

    private void setFirstName(User user) {user.setFirstName(firstNameFormattedTextField.getText());}
    public String getFirstName() {return this.firstName;}
    private void setLastName(User user) {user.setLastName(lastNameFormattedTextField.getText());}
    public String getLastName() {return this.lastName;}


}

