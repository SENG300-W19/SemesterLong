package data;
import java.time.*;
import java.util.LinkedList;

import users.*;

/**
 * 
 * @author dylnstwrt
 *
 */
public class Appointment {
	
	final String FORMAT_ERROR = "Invalid Formatting";
	final String OOR = "Out of Range";
	
	protected LocalDateTime start;
	protected LocalDateTime finish;
	protected User patient;
	protected User doctor; 

	
	/**
	 * Constructor for the Appointment class. Assumes a default hour long appointment
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @param hour - formatted in 24hr time
	 * @param minute
	 * @throws Exception Throws and exception if the appointment is in the past. 
	 */
	public Appointment(int day, int month, int year, int hour, int minute) throws Exception {
		try {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime start = LocalDateTime.of(year, month, day, hour, minute);
			if (start.isBefore(now) || start.isAfter(now.plusMonths(2))) throw new Exception(OOR); // edit this for determining how soon in advance a person can book
			LocalDateTime finish = start.plusHours(1);
			this.start = start;
			this.finish = finish;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Check to see that the patient and doctor are assigned to each other before creating
	 * an appointment
	 * @param patient
	 * @param doctor
	 * @throws Exception
	 */
	/**public void setParticipants(user patient, user doctor) throws Exception {
		LinkedList<User> toCompare = this.doctor.returnPatients();
		if (toCompare.contains(patient)) {
			this.patient = patient;
			this.doctor = doctor;
		} else {
			throw new Exception("Patient Not Assigned to Doctor");
		}
	}
	*/
}
