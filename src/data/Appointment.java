package data;

import java.io.Serializable;
import java.time.*;
import users.*;

/**
 * class for an appointment object, which contains a start, and end time (by default is 1 hour)
 * patient and doctor aren't implemented as we want to do accountType dependent validation, and thus
 * would like to implement separate classes properly
 *
 */
public class Appointment implements Serializable{
	final String FORMAT_ERROR = "Invalid Formatting";
	final String OOR = "Out of Range";

    /**
     * getter for start time of an appointment
     * @return
     */
	public LocalDateTime getStart() {
		return start;
	}

    /**
     * setter for start time of an appointment
     * @param start
     */
	public void setStart(LocalDateTime start) {
		this.start = start;
	}

    /**
     * getter for endTime of appointment
     * @return
     */
	public LocalDateTime getFinish() {
		return finish;
	}

    /**
     * setter for end time of appointment
     * @param finish
     */
	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}

	private LocalDateTime start;
	private LocalDateTime finish;

	private Patient patient;
	private Doctor doctor;

	private boolean isRequest = false;
	private String requestStatus;

	
	/**
	 * Constructor for the Appointment class. Assumes a default hour long appointment 
	 * and isn't being scheduled more than two months in advanced.
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @param hour - formatted in 24hr time
	 * @param minute
	 * @throws Exception Throws and exception if the appointment is in the past. 
	 */
	public Appointment(int day, int month, int year, int hour, int minute) throws Exception {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(year, month, day, hour, minute);
        if (start.isBefore(now) || start.isAfter(now.plusMonths(2))) throw new Exception("The date is out of range"); // edit this for determining how soon in advance a person can book
        LocalDateTime finish = start.plusHours(1);
        this.start = start;
        this.finish = finish;


	}

    /**
     * Constructor for setting the doctor and patient variables for the appointment
     * @param day
     * @param month
     * @param year
     * @param hour
     * @param minute
     * @param doctor
     * @param patient
     * @throws Exception
     */
	public Appointment(int day, int month, int year, int hour, int minute, Doctor doctor, Patient patient) throws Exception {
		try {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime start = LocalDateTime.of(year, month, day, hour, minute);
			if (start.isBefore(now) || start.isAfter(now.plusMonths(2))) throw new Exception(OOR); // edit this for determining how soon in advance a person can book
			LocalDateTime finish = start.plusHours(1);
			this.start = start;
			this.finish = finish;
			this.doctor = doctor;
			this.patient = patient;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    /**
     * Constructor for setting up a time-off request
     * @param day
     * @param month
     * @param year
     * @param hour
     * @param minute
     * @param doctor
     * @throws Exception
     */
    public Appointment(int day, int month, int year, int hour, int minute, Doctor doctor) throws Exception {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = LocalDateTime.of(year, month, day, hour, minute);
            if (start.isBefore(now) || start.isAfter(now.plusMonths(2))) throw new Exception(OOR); // edit this for determining how soon in advance a person can book
            LocalDateTime finish = start.plusHours(1);
            this.start = start;
            this.finish = finish;
            this.doctor = doctor;
            isRequest = true;
            requestStatus = "Time Off Request";
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getRequestStatus() {
    	String copy = requestStatus;
    	return copy;
	}

    /**
     * Get the patient associated with the appointment
     * @return
     */
	public Patient getApptPatient() {
		return patient;
	}

    /**
     * Get the doctor associated with the appointment
     * @return
     */
	public Doctor getApptDoctor() {
		return doctor;
	}

	public boolean isRequest() {
	    return isRequest;
    }

}
