package system;

import java.util.LinkedList;

public class Airline {

	private String name;
	private String cruiseAirline;

	public Airline(String name, String cruiseAirline) {
		this.name = name;
		this.cruiseAirline = cruiseAirline;
	}

	public String toString() {
		return name;
	}
	
	public boolean check(LinkedList<Airline> names) {
		for (Airline ll: names) {
			if (ll.name.compareTo(name) == 0) {
				System.out.println(cruiseAirline + " " + name + " already exists");
				return false;
			}
		}
		
		if (name.length() > 5) {
			System.out.println("Invalid " + cruiseAirline + " format for: " + name);
			return false;
		}

		return true;
	}
}
