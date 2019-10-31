package system;

import java.util.LinkedList;

public class Airport {
	private String name;
	private String portAirport;
	
	public Airport (String name, String portAirport) {
		this.name = name;
		this.portAirport = portAirport;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean check(LinkedList<Airport> names) {
		for (Airport ll: names) {
			if (ll.name.compareTo(name) == 0) {
				System.out.println(ll.portAirport + " " + name + " already exists");
				return false;
			}
		}
		
		if (name.length() > 3) {
			System.out.println("Invalid " + portAirport + " format for: " + name);
			return false;
		}

		return true;
	}
}
