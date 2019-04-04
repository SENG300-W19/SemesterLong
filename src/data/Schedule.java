package data;

import java.util.LinkedList;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    /**
     * method for adding appointment to the schedule object
     * @param toAdd schedule to add to the schedule
     * @throws ScheduleException when the start time of the appointment is:
     *          a) before the instance in which the method is called
     *          b) after the two month limit from which the method is called
     */
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

				if (app.getStart().getHour() <= toAdd.getStart().getHour() || toAdd.getStart().getHour() <= app.getFinish().getHour()){
					throw new Exception("Sorry! Appointment time is filled up. Try another hour or minute");
				}
			}
			list.add(toAdd);


		} catch (Exception e) {
			e.printStackTrace();
			throw new ScheduleException();
		}
	}

    /**
     * @todo
     */
	public void removeAppointment () {
		
	}

    /**
     * prints list of appointments to command line
     */
	public void listAppointments() {
		System.out.println("List of appoinments: ");
		for (Appointment app : list) {
			System.out.print((list.indexOf(app)+1)+". ");
			System.out.print(app.getStart().toString()+"\n");
		}
	}
}
