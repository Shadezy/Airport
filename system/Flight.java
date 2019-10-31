package system;

import java.time.LocalDate;
import java.util.LinkedList;

public class Flight {

	private String aName;
	private String orig;
	private String dest;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;
	private String flID;
	private String cruiseAirline;

	public Flight(String aName, String orig, String dest, int year, int month, int day, int hour, int min, String flID, String cruiseAirline) {
		this.aName = aName;
		this.orig = orig;
		this.dest = dest;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.flID = flID;
		this.cruiseAirline = cruiseAirline;
	}
	
	public String toString() {
		return aName + " " + flID + ": " + "From " + orig + " to " + dest + " at " + hour + ":" + min +
				" on " + month + "/" + day + "/" + year + "\n";
	}
	
	public String getOrig() {
		return orig;
	}
	
	public String getDest() {
		return dest;
	}
	
	public String getDate() {
		return year + ", " + month + ", " + day + ", " + hour + ", " + min;
	}

	public boolean check(LinkedList<Flight> flightLL, LinkedList<Airline> airlineLL, LinkedList<Airport> portLL) {
		boolean airline = false;
		boolean isOrigExist = false;
		boolean isDestExist = false;
		
		for (Airline ll: airlineLL) {
			if ((ll.toString().compareTo(aName) == 0)) {
				airline = true;
				break;
			}
		}
		
		if (!airline) {
			System.out.println(cruiseAirline + " " + aName + " doesn't exist");
			return false;
		}
		
		for (Airport port: portLL) {
			if (port.toString().compareTo(orig) == 0)
				isOrigExist = true;
			if (port.toString().compareTo(dest) == 0)
				isDestExist = true;
		}
		
		if (!isOrigExist) {
			System.out.println("Origin " + orig + " doesn't exist");
			return false;
		}
		
		if (!isDestExist) {
			System.out.println("Destination " + dest + " doesn't exist");
			return false;
		}
		
		if (orig.compareTo(dest) == 0) {
			System.out.println("Origin " + orig + " and destination " + dest + " cannot be the same");
			return false;
		}	
		
		try {
			LocalDate.of(year, month, day);
		} catch(Exception e) {
			System.out.println("Invalid date format: "+ day + "/" + month + "/" + year);
			return false;
		}
		
		for (Flight ll: flightLL)
			if ((ll.flID.compareTo(flID) == 0)) {
				System.out.println(aName + " " + ll.flID + " already exists");
				return false;
			}
		
		return true;
	}

	public String getAirlineName() {
		return aName;
	}

	public String getflID() {
		return flID;
	}
}
