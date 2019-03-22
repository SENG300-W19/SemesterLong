package data;
import users.*;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import exceptions.ScheduleException;

/**
 * 
 * @author dylnstwrt
 *
 */
public class Schedule implements Serializable {
	public LinkedList<Appointment> list = new LinkedList<Appointment>(); 
	
	/**
	 * Default constructor for doctors schedule
	 */
	
	public Schedule() {
	}

    /**
     * copy constuctor for ScheduleClass
     * TODO Update with new data types if needed.
     * @param toCopy
     */
	public Schedule(Schedule toCopy) {
	    this.list = toCopy.list;
    }
	
	public void addAppointment(Appointment toAdd) throws ScheduleException {
		try {
			LocalDateTime now = LocalDateTime.now();
			//check start date
			if (toAdd.getStart().isBefore(now) || toAdd.getStart().isAfter(now.plusMonths(2))) {throw new Exception();}
			// check for conflicts
			for (Appointment app : list) {
				if (toAdd.getStart().isAfter(app.getStart()) && toAdd.getStart().isBefore(app.getFinish())) {
					throw new Exception("Conflicting Date");
				}
			}
			// insert according to date


		} catch (Exception e) {
			e.printStackTrace();
			throw new ScheduleException();
		}
	}
	
	public void removeAppointment () {
		
	}
	
	public void listAppointments() {
		System.out.println("List of appoinments: ");
		for (Appointment app : list) {
			System.out.print((list.indexOf(app)+1)+". ");
			System.out.print(app.getStart().toString()+"\n");
		}
	}
}
