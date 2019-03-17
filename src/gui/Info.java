package gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Info {
    private JFormattedTextField lastNameFormattedTextField;
    private JFormattedTextField firstNameFormattedTextField;
    private JComboBox year;
    private JComboBox day;
    private JComboBox month;
    private JPanel frame;
    private JButton cancelButton;
    private JButton confirmButton;

    public Info() {
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
                    case 0: //YES
                        break;
                    case 1: //NO
                        break;
                    case 2: //CANCEL
                        break;
                }
            }
        });
    }

    public JFrame init() {
        JFrame frame = new JFrame("Info");
        frame.setContentPane(new Info().frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    public String getFirstName() { return firstNameFormattedTextField.getText();}
    public String getLastName() {return lastNameFormattedTextField.getText();}

    public void close() {

    }
}
