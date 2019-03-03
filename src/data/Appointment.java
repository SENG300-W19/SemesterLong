package data;
import java.time.*;
import java.util.LinkedList;

import users.*;

public class Appointment {
	
	final String FORMAT_ERROR = "Invalid Formatting";
	final String OOR = "Out of Range";
	
	protected LocalDateTime start;
	protected LocalDateTime finish;
	protected Patient patient;
	protected Doctor doctor; 

	public Appointment(int day, Month month, int year, int hour, int minute) throws Exception {
		try {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime start = LocalDateTime.of(year, month, day, hour, minute);
			if (start.isBefore(now)) throw new Exception(OOR); // edit this for determining how soon in advance a person can book
			LocalDateTime finish = start.plusHours(1);
			this.start = start;
			this.finish = finish;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setParticipants(Patient patient, Doctor doctor) throws Exception {
		LinkedList<Patient> toCompare = this.doctor.returnPatients();
		if (toCompare.contains(patient)) {
			this.patient = patient;
			this.doctor = doctor;
		} else {
			throw new Exception("Patient Not Assigned to Doctor");
		}
	}
}
