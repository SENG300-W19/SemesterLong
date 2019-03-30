package gui;

import javax.swing.*;

/**
 * @author dylnstwrt
 * @todo figure out how to return the values correctly
 */
public class DateSelect {
    private JPanel panel1;
    private JButton addButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;

    private static JFrame frame = new JFrame("Select Date");

    public DateSelect() {
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
