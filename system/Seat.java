package system;

import java.util.LinkedList;

public class Seat {
	private String aName;
	private String flID;
	private SeatClass s;
	private int row;
	private char column;
	private String tripFlight;
	private boolean isAvailable;
	private boolean isAisle;
	private boolean isWindow;
	
	public Seat(String aName, String flID, SeatClass s, int row, char column, boolean aisle, boolean window, String tripFlight) {
		this.aName = aName;
		this.flID = flID;
		this.s = s;
		this.row = row;
		this.column = column;
		this.tripFlight = tripFlight;
		isAisle = aisle;
		isWindow = window;
		isAvailable = true;
	}

	public boolean check(LinkedList<FlightSection> fsLL) {
		boolean airline = false;
		
		for (FlightSection ll: fsLL) {
			if (ll.getAirlineName().compareTo(aName) == 0 && ll.getflID().compareTo(flID) == 0) {
				airline = true;
				break;
			}
		}
		
		if (!airline) {
			System.out.println(tripFlight + " " + aName + " " + flID + " doesn't exist");
			return false;
		}
		
		return true;
	}

	public int getRow() {
		return row;
	}
	public char getCol() {
		return column;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void bookSeat() {
		isAvailable = false;	
	}
	
	public boolean preference(char c) {
		if (c == 'W')
			return isWindow;
		if (c == 'A')
			return isAisle;
		return false;
	}
}
