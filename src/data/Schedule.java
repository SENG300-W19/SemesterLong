package data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
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
	public LinkedList<Appointment> list;
	
	/**
	 * Default constructor for doctors schedule
	 */
	
	public Schedule() {
	    list = new LinkedList<>();
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
     * Alternate method to add appointments
     * @param toAdd
     * @throws Exception if the date is out of range or clashes with a different appointment
     */
	public void addAppt(Appointment toAdd) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        if (toAdd.getStart().isBefore(now) || toAdd.getStart().isAfter(now.plusMonths(2))) {
            throw new Exception("Date needs to be within 2 months from today's date");
        }

        for (Appointment appointment : list) {
            if (appointment.getStart().getMonth() == toAdd.getStart().getMonth()) {
                if (appointment.getStart().getDayOfMonth() == toAdd.getStart().getDayOfMonth()) {
                    if (toAdd.getStart().getHour() >= appointment.getStart().getHour()
                            && toAdd.getStart().getHour() < appointment.getFinish().getHour()) {
                        throw new Exception("Time slot is already taken.");
                    }
                }
            }
        }
        list.add(toAdd);
        sortSchedule();
    }

    /**
     * Method to add a request
     * @param appointment gets added to Admin.requests Schedule
     */
	public void addRequests(Appointment appointment) {
        list.add(appointment);
        sortSchedule();
    }

    /**
     * @todo
     */
	public void removeAppointment(Appointment appointment) {
		for (int i = 0; i < list.size(); i++) {
		    if (list.get(i).equals(appointment)) {
		        list.remove(i);
		        break;
            }
        }
        sortSchedule();
	}

    /**
     * prints list of appointments to command line
     */
	public void listAppointments() {
		System.out.println("List of appointments: ");
		for (Appointment app : list) {
			System.out.print((list.indexOf(app)+1)+". ");
			System.out.print(app.getStart().toString()+"\n");
		}
	}

    /**
     * Checks to see if an appointment is valid
     * @param toAdd
     * @param accType
     * @throws Exception
     */
	public void isValid(Appointment toAdd, int accType) throws Exception {
        LocalDateTime now = LocalDateTime.now();
		if (toAdd.getStart().isBefore(now) || toAdd.getStart().isAfter(now.plusMonths(2))) {
			throw new Exception("Date must be within two months of today");
		}
		//System.out.println(list.get(0).getStart().toString());
		if (!list.isEmpty()) {
            if (accType == 3) {
                if (toAdd.getStart().isBefore(now) || toAdd.getStart().isAfter(now.plusMonths(2))) {
                    throw new Exception("Date needs to be within 2 months from today's date");
                }
                for (Appointment appointment : list) {
                    if (appointment.getStart().getMonth() == toAdd.getStart().getMonth()) {
                        if (appointment.getStart().getDayOfMonth() == toAdd.getStart().getDayOfMonth()) {
                            if (toAdd.getStart().getHour() >= appointment.getStart().getHour()
                                    && toAdd.getStart().getHour() < appointment.getFinish().getHour()) {
                                throw new Exception("Time slot is already taken.");
                            }
                        }
                    }
                }
            } else {
                if (toAdd.getStart().isBefore(now) || toAdd.getStart().isAfter(now.plusMonths(2))) {
                    throw new Exception("Date needs to be within 2 months from today's date");
                }
                for (Appointment appointment : list) {
                    if (appointment.getStart().getMonth() == toAdd.getStart().getMonth()) {
                        if (appointment.getStart().getDayOfMonth() == toAdd.getStart().getDayOfMonth()) {
                            if (toAdd.getStart().getHour() >= appointment.getStart().getHour()
                                    && toAdd.getStart().getHour() < appointment.getFinish().getHour()) {
                                throw new Exception("Time slot is already taken.");
                            }
                        }
                    }
                }
            }
        }
	}

    /**
     * Sort schedule in increasing order of time and date
     */
	public void sortSchedule() {
		Collections.sort(list, new Comparator<Appointment>() {
			@Override
			public int compare(Appointment appt1, Appointment appt2) {
				return appt1.getStart().compareTo(appt2.getStart());
			}
		});
	}
}
