package data;
import users.*; 
import java.util.LinkedList;
import java.util.Scanner;
import java.io.Serializable;
import java.time.LocalDateTime; 
public class Schedule implements Serializable {
	public LinkedList<Appointment> list = new LinkedList<Appointment>(); 
	
	/**
	 * Default constructor for doctors schedule
	 */
	
	public Schedule() {
	}
	
	public void addAppointment() {
		try {
			Boolean timeValid = false;
			while (!timeValid) {
				Scanner scan = new Scanner(System.in);
				LocalDateTime now = LocalDateTime.now();
				int year = now.getYear(); // assumes that this appointment will be within the calendar year
				System.out.print("Enter the month (1-12): ");
				int month = scan.nextInt();
				System.out.print("Enter the day of the month(0-31): ");
				int day = scan.nextInt();
				System.out.print("Enter the hour of the time of day(0-23): ");
				int hour = scan.nextInt();
				System.out.print("Enter the minute of the hour: (0-59): ");
				int minute = scan.nextInt();
				Appointment toAdd = new Appointment(day, month, year, hour, minute);
				timeValid = true;
				for (Appointment app : list) {
					if (toAdd.start.isAfter(app.start) && toAdd.start.isBefore(app.finish)) {
						System.out.println("A conflict exists in the schedule");
						timeValid = false;
						break;
					}
				} if (timeValid) list.add(toAdd);
			}
			System.out.println("Appointment Added:\n("+list.getLast().start.toString()+")");
		} catch (Exception e) {
			
		}
	}
	
	public void removeAppointment () {
		
	}
	
	public void listAppointments() {
		System.out.println("List of appoinments: ");
		for (Appointment app : list) {
			System.out.print((list.indexOf(app)+1)+". ");
			System.out.print(app.start.toString()+"\n");
		}
	}
}
