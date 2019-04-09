package data;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class is the schedule for each user
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
     * Method to add appointments
     * @param toAdd is the appointment being added
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
     * Remove an appointment from a user's appointment list
     * @param appointment is the appointment being removed
     */
	public void removeAppointment(Appointment appointment) {
		for (int i = 0; i < list.size(); i++) {
		    if (list.get(i).equals(appointment)) {
		        System.out.println("appointment removed");
		        list.remove(i);
		        break;
            }
        }
        System.out.println("after for");
		try {
            Account.writeToFile();
        } catch (Exception e){

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
     * @param toAdd is the appointment to add
     * @param accType is the account type of the user
     * @throws Exception
     */
	public void isValid(Appointment toAdd, int accType) throws Exception {
        LocalDateTime now = LocalDateTime.now();
		if (toAdd.getStart().isBefore(now) || toAdd.getStart().isAfter(now.plusMonths(2))) {
			throw new Exception("Date must be within two months of today");
		}
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
