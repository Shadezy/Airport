package system;

import java.util.LinkedList;

public class FlightSection {

	private SeatClass s;
	private String flID;
	private int rows;
	private int columns;
	private String aName;
	private int seatCount;
	private int price;
	private String size;
	private String tripFlight;
	private String cabinSeat;
	private LinkedList<Seat> seatLL;

	public FlightSection(SeatClass s, String flID, String aName, int rows, int columns, int price, String size, String tripFlight, String cabinSeat) {
		this.s = s;
		this.flID = flID;
		this.aName = aName;
		this.rows = rows;
		this.columns = columns;
		seatCount = rows*columns;
		this.price = price;
		this.size = size;
		this.tripFlight = tripFlight;
		this.cabinSeat = cabinSeat;
		seatLL = null;
	}
	
	public boolean hasAvailableSeats() {
		return (seatCount != 0);
	}
	
	public void bookSeat(int row, char column) {
		for (Seat ll: seatLL) {
			if (ll.getRow() == row && ll.getCol() == column) {
				if (!ll.isAvailable())
					System.out.println(cabinSeat +": " + row + column + " isn't available for " + tripFlight + " " + aName + " " + flID);
				else {
					ll.bookSeat();
					System.out.println(cabinSeat + ": " + row + column + " booked!");
					seatCount--;
				}
				
				break;
			}
		}
	}
	
	public boolean bookAvailableSeat() {//not tested
		if (hasAvailableSeats()) {
			for (Seat ll: seatLL) {
				if (ll.isAvailable()) {
					ll.bookSeat();
					seatCount--;
					System.out.println(cabinSeat + ": " + ll.getRow() + ll.getCol() + " booked!");
					return true;
				}
			}
		}
		else {
			System.out.println("No " + cabinSeat + "s available for " + tripFlight + " " + aName + " " + flID);
			return false;
		}
		return false;
	}
	
	public void createSeat() {
		seatLL = new LinkedList<Seat>();
		boolean aisle = true;
		boolean window = true;
		
		for (int i = 1; i <= rows; i++)
			for (int j = 1; j <= columns; j++) {
				if (size.compareTo("S") == 0 && j == 1)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), aisle, window, tripFlight));
				else if (size.compareTo("S") == 0 && j == 2)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), aisle, !window, tripFlight));
				else if (size.compareTo("S") == 0 && j == 3)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), !aisle, window, tripFlight));
				
				else if (size.compareTo("M") == 0 && j == 1)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), !aisle, window, tripFlight));
				else if (size.compareTo("M") == 0 && j == 2)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), aisle, !window, tripFlight));
				else if (size.compareTo("M") == 0 && j == 3)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), aisle, !window, tripFlight));
				else if (size.compareTo("M") == 0 && j == 4)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), !aisle, window, tripFlight));
				
				else if (size.compareTo("W") == 0 && j == 1)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), !aisle, window, tripFlight));
				else if (size.compareTo("W") == 0 && j == 2)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), !aisle, !window, tripFlight));
				else if (size.compareTo("W") == 0 && j == 3)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), aisle, !window, tripFlight));
				else if (size.compareTo("W") == 0 && j == 4)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), aisle, !window, tripFlight));
				else if (size.compareTo("W") == 0 && j == 5)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), !aisle, !window, tripFlight));
				else if (size.compareTo("W") == 0 && j == 6)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), !aisle, !window, tripFlight));
				else if (size.compareTo("W") == 0 && j == 7)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), aisle, !window, tripFlight));
				else if (size.compareTo("W") == 0 && j == 8)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), aisle, !window, tripFlight));
				else if (size.compareTo("W") == 0 && j == 9)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), !aisle, !window, tripFlight));
				else if (size.compareTo("W") == 0 && j == 10)
					seatLL.add(new Seat(aName, flID, s, i, convert(j), !aisle, window, tripFlight));
				
				else if (size.compareTo("O") == 0)
					seatLL.add(new Seat (aName, flID, s, i, convert(j), !aisle, !window, tripFlight
							)); //for the client garbage
			}
	}
	
	private char convert(int num) {
		if (num == 1)
			return 'A';
		if (num == 2)
			return 'B';
		if (num == 3)
			return 'C';
		if (num == 4)
			return 'D';
		if (num == 5)
			return 'E';
		if (num == 6)
			return 'F';
		if (num == 7)
			return 'G';
		if (num == 8)
			return 'H';
		if (num == 9)
			return 'I';
		if (num == 10)
			return 'J';
		
		return 'Z';
	}
	
	public boolean check(LinkedList<Flight> flightLL, LinkedList<FlightSection> fsLL) {
		boolean airline = false;
		for (Flight ll: flightLL) {
			if (ll.getAirlineName().compareTo(aName) == 0 && ll.getflID().compareTo(flID) == 0) {
				airline = true;
				break;
			}
		}
		
		if (!airline) {
			System.out.println(tripFlight + " " + aName + " " + flID + " doesn't exist");
			return false;
		}
		
		if (rows > 100 || rows < 0) {
			System.out.println(rows + " is too many rows");
			return false;
		}
		
		if (columns > 10 || columns < 0) {
			System.out.println(columns + " is too many columns");
			return false;
		}
		
		for (FlightSection ll: fsLL) {
			if (ll.s == s && ll.flID.compareTo(flID) == 0) {
				System.out.println(s + " class section already exists for the " + aName + " " + flID + " " + tripFlight);
				return false;
			}
		}
		
		return true;
	}
	
	public String getAvailableSeats() {
		String result = "";
		
		for (Seat s: seatLL) {
			if (s.isAvailable())
				result += String.valueOf(s.getRow()) + String.valueOf(s.getCol()) + ", ";	
		}
		
		if (!result.isEmpty())
			result = result.substring(0, result.length() - 2);
		else
			result = "None";
		
		return result;
	}
	
	public String getBookedSeats() {
		String result = "";
		
		for (Seat s: seatLL) {
			if (!s.isAvailable())
				result += String.valueOf(s.getRow()) + String.valueOf(s.getCol()) + ", ";	
		}
		
		if (!result.isEmpty())
			result = result.substring(0, result.length() - 2);
		else
			result = "None";
		
		return result;
	}
	
	public String toString() {
		return aName + " " + flID + ": " + s + " class, " + rows + " rows " + columns + " columns."
				+ "\nPrice per " + cabinSeat + ": $" +  price + "\nAvailable " + cabinSeat + "s:\n" + getAvailableSeats()
				+ "\nBooked " + cabinSeat + "s:\n" + getBookedSeats() + "\n\n"; 
	}

	public String getAirlineName() {
		return aName;
	}

	public String getflID() {
		return flID;
	}
	
	public String getSize() {
		return size;
	}
	
	public LinkedList<Seat> getSeats() {
		return seatLL;
	}

	public SeatClass getSeatClass() {
		return s;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int newPrice) {
		price = newPrice;
	}
	
	public int getRows(){
		return rows;
	}
	
	public boolean isAvailable(int row, char column) {
		for (Seat ll: seatLL) {
			if (ll.getRow() == row && ll.getCol() == column) {
				if (!ll.isAvailable())
					System.out.println(cabinSeat + " " + row + column + " is not available for " + s + " class on " + tripFlight + " " + aName + " " + flID);
				return ll.isAvailable();
			}
		}
		
		System.out.println(cabinSeat + " " + row + column + " doesn't exist");
		return false;
	}
}
