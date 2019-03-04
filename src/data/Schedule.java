package data;
import users.*; 
import java.util.LinkedList;
import java.util.Scanner;
import java.time.LocalDateTime; 
public class Schedule {
	public LinkedList<Appointment> list;
	public User owner;
	
	/**
	 * Default constructor for doctors schedule
	 */
	
	public Schedule(User owner) {
		this.owner = owner;
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
				}
			}
			System.out.println("Appointment Added");
		} catch (Exception e) {
			
		}
	}
	
	public void removeAppointment () {
		
	}
	
	public void listAppointments() {
		System.out.println("List of appoinments of "+this.owner.getFirstName());
		for (Appointment app : list) {
			String toPrint = String.valueOf(list.indexOf(app)+1);
			toPrint.concat("."
					+ 	"\nStart: "+app.start.toString()
					+	"\nFinish: "+app.finish.toString()
					);
		}
	}
}
