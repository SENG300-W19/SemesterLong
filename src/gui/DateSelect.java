package gui;

import data.Account;
import data.Appointment;
import data.Schedule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author dylnstwrt
 * @todo figure out how to return the values correctly
 */


public class DateSelect {
    private LocalDateTime toReturn;
    private JPanel panel1;
    private JButton addButton;
    private JComboBox monthBox;
    private JComboBox dayBox;
    private JComboBox yearBox;
    private JComboBox<String> timeSlots;

    private static JFrame frame = new JFrame("Select Date");

    public DateSelect(Schedule schedule) {
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addButton) {
                    int year = Integer.parseInt(yearBox.getSelectedItem().toString());
                    int month = monthBox.getSelectedIndex()+1;
                    int day = dayBox.getSelectedIndex()+1;

                    int time = timeSlots.getSelectedIndex()+8;
                    try {
                        schedule.addAppointment(new Appointment(day, month, year, time, 0));
                        JOptionPane.showMessageDialog(null, "Appointment Added");
                        Account.writeToFile();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, "Invalid Date");
                    }
                }
            }
        });
    }
}
