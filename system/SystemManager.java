package system;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class SystemManager {
	
	private LinkedList<Airport> airportLL = new LinkedList<Airport>();
	private LinkedList<Airline> airlineLL = new LinkedList<Airline>();
	private LinkedList<Flight> flightLL = new LinkedList<Flight>();
	private LinkedList<FlightSection> fsLL = new LinkedList<FlightSection>();
	private String portAirport;
	private String cabinSeat;
	private String tripFlight;
	private String cruiseAirline;
	
	public SystemManager(Vehicle v) {
		if (v == Vehicle.ship) {
			portAirport = "Port";
			cabinSeat = "Cabin";
			tripFlight = "Trip";
			cruiseAirline = "Cruise";
		}
		else {
			portAirport = "Airport";
			cabinSeat = "Seat";
			tripFlight = "Flight";
			cruiseAirline = "Airline";
		}
	}

	public void createAirport(String name) {
		Airport airport = new Airport(name, portAirport);
		
		if (airport.check(airportLL))
			airportLL.add(airport);
	}

	public void createAirline(String name) {
		Airline airline = new Airline(name, cruiseAirline);

		if (airline.check(airlineLL))
			airlineLL.add(airline);
	}
	
	public void createFlight(String aName, String orig, String dest, int year, int month, int day, String flID) {
		Flight flight = new Flight(aName, orig, dest, year, month, day, 0, 0, flID, cruiseAirline);
		
		if (flight.check(flightLL, airlineLL, airportLL))
			flightLL.add(flight);
		
	}
	
	public void createFlight(String aName, String orig, String dest, int year, int month, int day, int hour, int min, String flID) {
		Flight flight = new Flight(aName, orig, dest, year, month, day, hour, min, flID, cruiseAirline);
		
		if (flight.check(flightLL, airlineLL, airportLL))
			flightLL.add(flight);
		
	}

	public void createSection(String aName, String flID, int rows, int columns, SeatClass s) {
		FlightSection fs = new FlightSection(s, flID, aName, rows, columns, 0, "O", tripFlight, cabinSeat);
		
		if (fs.check(flightLL, fsLL)) {
			fs.createSeat();
			fsLL.add(fs);
		}
	}
	
	public void createSection(String aName, String flID, SeatClass s, int rows, int columns, int price, String size) {
		
		FlightSection fs = new FlightSection(s, flID, aName, rows, columns, price, size, tripFlight, cabinSeat);
		
		if (fs.check(flightLL, fsLL)) {
			fs.createSeat();
			fsLL.add(fs);
		}
	}
	
	public void createSection(String aName, String flID, String[] array) {
	
		for (int i = 0; i < array.length; i++) {
			SeatClass s = null;
			int columns = 0;
			String[] string = array[i].split(":");
			
			if (string[0].compareTo("E") == 0)
				s = SeatClass.economy;
			else if (string[0].compareTo("B") == 0)
				s = SeatClass.business;
			else if (string[0].compareTo("F") == 0)
				s = SeatClass.first;
			
			if (string[2].compareTo("S") == 0)
				columns = 3;
			else if (string[2].compareTo("M") == 0)
				columns = 4;
			else if (string[2].compareTo("W") == 0)
				columns = 10;
			createSection(aName, flID, s, Integer.parseInt(string[3]), columns, Integer.parseInt(string[1]), string[2]);
		}
	}

	public void findAvailableFlights(String orig, String dest) {
		int count = 0;
		System.out.print("\nAvailable " + tripFlight + "s from " + orig + " to " + dest + ": ");
	
		for (Flight f: flightLL)
			if(f.getOrig().compareTo(orig) == 0 && f.getDest().compareTo(dest) == 0) {
				System.out.print("\n" + f);
				for (FlightSection fs: fsLL)
					if (fs.getflID().compareTo(f.getflID()) == 0)
						if (fs.hasAvailableSeats())
							System.out.println(fs.getSeatClass() + " class @ $" + fs.getPrice());
				count++;
			}
		
		System.out.println();
		
		if (count < 1)
			System.out.print("None\n\n");
		
	}
	
	public void bookSeat(String aName, String flID, SeatClass s, int row, char column) {
		boolean isExist = false;
		
		for (FlightSection ll: fsLL) {
			if (ll.getAirlineName().compareTo(aName) == 0 && ll.getflID().compareTo(flID) == 0 && ll.getSeatClass() == s) {
				isExist = true;
				if (ll.isAvailable(row, Character.toUpperCase(column))) {
					ll.bookSeat(row, Character.toUpperCase(column));
					break;
				}
			}
		}
		
		if (!isExist)
			System.out.println(tripFlight + " " + aName + " " + flID + " for " + s + " class doesn't exist");
	}
	
	public void bookSeat(String aName, String flID, SeatClass s, char pref) {
		boolean isExist = false;
		boolean isBooked = false;
		boolean isFull = false;
		pref = Character.toUpperCase(pref);
		
		for (FlightSection ll: fsLL) {
			if (!ll.hasAvailableSeats()) {
				isFull = true;
				System.out.println("No " + cabinSeat  + " is available for " + tripFlight + " " + aName + " " + flID + " in " + s + " class");
				break;
			}
				
			if (ll.getAirlineName().compareTo(aName) == 0 && ll.getflID().compareTo(flID) == 0 && ll.getSeatClass() == s) {
				isExist = true;
				for (Seat seat: ll.getSeats()) {
					if (seat.preference(pref) && seat.isAvailable()) {
						ll.bookSeat(seat.getRow(), seat.getCol());
						isBooked = true;
						break;
					}
				}
				if (!isBooked) {
					if (ll.bookAvailableSeat()) {
						System.out.println("Preference wasn't available so you got this instead");
					}
					
				}
				
			}
		}
		
		if (!isExist && !isFull)
			System.out.println(tripFlight + " " + aName + " " + flID + " for " + s + " class doesn't exist");
	}

	public void displaySystemDetails() {
		System.out.println(portAirport + "s:\n" + airportLL);
		System.out.println("\n" + cruiseAirline + "s:\n" + airlineLL);
		System.out.println("\n" + tripFlight + "s:\n" + flightLL);
		System.out.println("\nSections:\n" + fsLL);
	}
	
	public void writeFile(String dest) throws FileNotFoundException {
		//PrintWriter pw = new PrintWriter("C:\\Users\\travi\\Desktop\\Stuff\\School\\CS 349\\hw7part2\\output.txt");
		PrintWriter pw = new PrintWriter(dest);
		String fileOutput;
		String pastName = "";
		fileOutput = (airportLL + "{");
		
		for (Flight ll: flightLL) {
			
			if (pastName.compareTo(ll.getAirlineName()) != 0) {
				if (pastName.compareTo("") != 0) {
					fileOutput = fileOutput.substring(0, fileOutput.length() - 2);
					fileOutput += "], ";
				}
				
				fileOutput += ll.getAirlineName() + "[";
			}
			
			fileOutput += (ll.getflID() + "|" + ll.getDate() + "|" + ll.getOrig() + "|" + ll.getDest() + "[");
			
			for (FlightSection fs: fsLL) {
				if (ll.getflID().compareTo(fs.getflID()) == 0)
					fileOutput += (fs.getSeatClass().toString().substring(0, 1).toUpperCase() + ":" + fs.getPrice() + ":" + fs.getSize() + ":" + fs.getRows() + ",");
			}
			fileOutput = fileOutput.substring(0, fileOutput.length() - 1);
			fileOutput += "], ";
			pastName = ll.getAirlineName();
		}
		
		fileOutput = fileOutput.substring(0, fileOutput.length() - 2);
		fileOutput += "]}";
		
		//System.out.println(fileOutput);
		pw.print(fileOutput);
		
		pw.close();
	}
	
	public void changePriceSeatClass(int newPrice, SeatClass s) {
		for (FlightSection ll: fsLL) {
			if (s == ll.getSeatClass()) {
				ll.setPrice(newPrice);
				System.out.println("New price for " + ll.getAirlineName() + " " + ll.getflID() + " " + s + " class is: $" + newPrice);
			}
		}
	}
	
	public void changePriceOrigDest(String aName, SeatClass s, String orig, String dest, int newPrice) {
		for (Flight f: flightLL) {
			if (f.getAirlineName().compareTo(aName) == 0 && f.getOrig().compareTo(orig) == 0 && f.getDest().compareTo(dest) == 0)
				for (FlightSection fs: fsLL)
					if (f.getflID().compareTo(fs.getflID()) == 0 && fs.getSeatClass().compareTo(s) == 0) {
						fs.setPrice(newPrice);
						System.out.println("New price for " + fs.getAirlineName() + " " + fs.getflID() + " " + s + " class is: $" + newPrice);
					}
		}
	}
	
	public String getCabinSeat() {
		return cabinSeat;
	}
	
	public String getTripFlight() {
		return tripFlight;
	}
	
	public String getCruiseAirline() {
		return cruiseAirline;
	}
	
	public String getPortAirport() {
		return portAirport;
	}
}
