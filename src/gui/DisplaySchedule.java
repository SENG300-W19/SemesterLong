package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

/* TODO make this */
public class DisplaySchedule extends JFrame{
    DefaultTableModel model;
    Calendar cal = new GregorianCalendar();

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Calendar");
        this.setLayout(new BorderLayout());
        this.setVisible(true);
    }
}
