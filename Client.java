import java.io.FileNotFoundException;
import java.util.Scanner;

import system.*;

public class Client {

	public static void main(String[] args) throws FileNotFoundException {
		SystemManager res;
		//String path = "C:\\Users\\travi\\Desktop\\Stuff\\School\\CS 349\\hw7part2\\input.txt";
		FileStuff fs = new FileStuff();
		Scanner kb;
		Vehicle v;
		int result;
		
		kb = new Scanner(System.in);
		System.out.println("Ship or Plane?");
		
		String answer = kb.next().toUpperCase();
		
		while (answer.compareTo("SHIP") != 0 && answer.compareTo("PLANE") != 0) {
			System.out.println("Pick one of the two");
			answer = kb.next().toUpperCase();
		}
		
		if (answer.compareTo("SHIP") == 0) {
			v = Vehicle.ship;
		}
		else {
			v = Vehicle.airplane;
		}
		res = new SystemManager(v);
		
		System.out.println("\n[0] Run old client\n[1] input file\n[2] Change the price associated with " + res.getCabinSeat() + "s in a "
		+ res.getTripFlight() + " section\n" + "[3] Change price for a " + res.getCabinSeat() + " class for an origin and destination for a "
		+ res.getCruiseAirline() + "\n[4] Find " + res.getTripFlight() + "\n[5] Book " + res.getCabinSeat() + "\n[6] Book "
		+ res.getCabinSeat() + " with preference\n[7]" + " Display system details\n[8] Store output\n[9] Create " + res.getPortAirport()
		+ "\n[10] Create " + res.getCruiseAirline() + "\n[11] Create " + res.getTripFlight() + "\n[12] Create section\n[13] Quit\n\n");
		
		result = kb.nextInt();
		kb.nextLine();
		
		while (result != 13) {
			if (result == 0) {
				OldClient old = new OldClient();
				old.runOldClient(res);
			}
				
			if (result == 1) {
				System.out.println("Please enter a valid path: ");
				answer = kb.nextLine();
				System.out.println(answer);
				fs.readFile(answer);
				fs.createSystem(res);
			}
			
			else if (result == 2) {
				int price;
				int input;
				String s = "w";
				
				System.out.println("Please enter a new price (int):");
				
				while (!kb.hasNextInt()) {
					System.out.println("Please enter a valid price (int):");
					kb.next();
				}
				
				price = kb.nextInt();

				System.out.println("Please enter a class to change:\n[1] Economy\n[2] Business\n[3] First\n");
				while (s.compareTo("s") != 0) {
					while (!kb.hasNextInt()) {
						System.out.println("Please enter a valid number: 1, 2, or 3");
						kb.next();
					}
					
					input = kb.nextInt();

					if (input > 0 && input < 4) {
						s = "s";
						
						if (input == 1)
							res.changePriceSeatClass(price, SeatClass.economy);
						else if (input == 2)
							res.changePriceSeatClass(price, SeatClass.business);
						else
							res.changePriceSeatClass(price, SeatClass.first);
					}
					else
						System.out.println("Please enter a valid number: 1, 2, or 3");
				}
			}
			
			else if (result == 3) {
				String answerA[];
				SeatClass s;
				int count = 0;
				int price = -1;
				
				while (count != 4) {
					count = 0;
					System.out.println("Please enter the " + res.getCruiseAirline() + " name, class, origin, destination, and new price in that order, "
					+ "seperated by white space: ");
					
					answer = kb.nextLine();
					
					for (int i = 0; i < answer.length(); i++)
						if (answer.charAt(i) == ' ')
							count++;
					if (count != 4)
						System.out.println("Invalid format");
				}
				
				answerA = answer.split(" ");
				if (answerA[1].equalsIgnoreCase("economy"))
					s = SeatClass.economy;
				else if (answerA[1].equalsIgnoreCase("business"))
					s = SeatClass.business;
				else if (answerA[1].equalsIgnoreCase("first"))
					s = SeatClass.first;
				else {
					System.out.println(answerA[1] + " class doesn't exist");
					s = null;
				}
				
				try {
					price = Integer.parseInt(answerA[4]);
				} catch (NumberFormatException e) {
					System.out.println("price is not valid format");
					price = -1;
				}
				
				if (price != -1 && s != null) {
					res.changePriceOrigDest(answerA[0].toUpperCase(), s, answerA[2].toUpperCase(), answerA[3].toUpperCase(), price);
				}
			}
			
			else if (result == 4) {
				String orig = "";
				String dest = "";
				System.out.println("Please enter the origin:");
				orig = kb.nextLine().toUpperCase();
				System.out.println("Please enter the destination:");
				dest = kb.nextLine().toUpperCase();
				
				res.findAvailableFlights(orig, dest);
			}

			else if (result == 5) {
				String answerA[];
				SeatClass s;
				int count = 0;
				int row = -1;
				char column;
				
				while (count != 4) {
					count = 0;
					System.out.println("Please enter the " + res.getCruiseAirline() + " name, " + res.getTripFlight() + " id, class, row, "
					+ "and column in that order, seperated by white space: ");
					answer = kb.nextLine();
					
					for (int i = 0; i < answer.length(); i++)
						if (answer.charAt(i) == ' ')
							count++;
					if (count != 4)
						System.out.println("Invalid format");
				}
				answerA = answer.split(" ");
				if (answerA[2].equalsIgnoreCase("economy"))
					s = SeatClass.economy;
				else if (answerA[2].equalsIgnoreCase("business"))
					s = SeatClass.business;
				else if (answerA[2].equalsIgnoreCase("first"))
					s = SeatClass.first;
				else {
					System.out.println(answerA[2] + " class doesn't exist");
					s = null;
				}
				
				try {
					row = Integer.parseInt(answerA[3]);
				} catch (NumberFormatException e) {
					System.out.println("row is not valid format");
					row = -1;
				}
				
				if (row != -1 && s != null) {
					column = answerA[4].charAt(0);
					res.bookSeat(answerA[0].toUpperCase(), answerA[1].toUpperCase(), s, row, column);
				}
			}
			
			else if (result == 6) {
				String answerA[];
				SeatClass s;
				int count = 0;
				char pref = 'N';
				
				while (count != 3) {
					count = 0;
					System.out.println("Please enter the " + res.getCruiseAirline() + " name, " + res.getTripFlight() + " id, class, "
					+ "and either window or aisle, seperated by white space: ");
					answer = kb.nextLine();
					
					
					for (int i = 0; i < answer.length(); i++)
						if (answer.charAt(i) == ' ')
							count++;
					if (count != 3)
						System.out.println("Invalid format");
				}
				answerA = answer.split(" ");
				if (answerA[2].equalsIgnoreCase("economy"))
					s = SeatClass.economy;
				else if (answerA[2].equalsIgnoreCase("business"))
					s = SeatClass.business;
				else if (answerA[2].equalsIgnoreCase("first"))
					s = SeatClass.first;
				else {
					System.out.println(answerA[2] + " class doesn't exist");
					s = null;
				}
				
				if (answerA[3].equalsIgnoreCase("window"))
					pref = 'W';
				else if (answerA[3].equalsIgnoreCase("aisle"))
					pref = 'A';
				
				if (s != null && pref != 'N')
					res.bookSeat(answerA[0].toUpperCase(), answerA[1].toUpperCase(), s, pref);
			}
			
			else if (result == 7) {
				res.displaySystemDetails();
			}
	
			else if (result == 8) {
				System.out.println("Please enter output destination:");
				answer = kb.nextLine();
				res.writeFile(answer);
				System.out.println("Done");
			}
			
			else if (result == 9) {
				System.out.println("Please enter new " + res.getPortAirport() + ":");
				answer = kb.nextLine().toUpperCase();
				
				System.out.println("Creating...Done");
				res.createAirport(answer);
			}
			
			else if (result == 10) {
				System.out.println("Please enter new " + res.getCruiseAirline() + ":");
				answer = kb.nextLine().toUpperCase();
				
				System.out.println("Creating...Done");
				res.createAirline(answer);
			}
			
			else if (result == 11) {
				String answerA[];
				int count = 0;
				int year = -1;
				int month = -1;
				int day = -1;
				int hour = -1;
				int min = -1;
				
				while (count != 8) {
					count = 0;
					System.out.println("Please enter " + res.getCruiseAirline() + " name, " + res.getTripFlight() + " id, origin, destination, "
					+ "year, month, day, hour, and minute in that order, separated by white space: ");
					answer = kb.nextLine().toUpperCase();
					
					
					for (int i = 0; i < answer.length(); i++)
						if (answer.charAt(i) == ' ')
							count++;
					if (count != 8)
						System.out.println("Invalid format");
				}
				answerA = answer.split(" ");
				
				try {
					year = Integer.parseInt(answerA[4]);
					month = Integer.parseInt(answerA[5]);
					day = Integer.parseInt(answerA[6]);
					hour = Integer.parseInt(answerA[7]);
					min = Integer.parseInt(answerA[8]);
					
				} catch (NumberFormatException e) {
					System.out.println("date is not in a valid format");
				}
				
				if (year != -1 && month != -1 && day != -1 && hour != -1 && min != -1) {
					System.out.println("Creating...");
					res.createFlight(answerA[0], answerA[2], answerA[3], year, month, day, hour, min, answerA[1]);
				}
			}
			
			else if (result == 12) {
				String answerA[];
				SeatClass s;
				int count = 0;
				int columns = -1;
				int rows = -1;
				int price = -1;
				
				while (count != 5) {
					count = 0;
					System.out.println("Please enter the " + res.getCruiseAirline() + " name, " + res.getTripFlight() + " id, class, "
					+ "number of rows, size (s, m, w), and price, seperated by white space: ");
					answer = kb.nextLine().toUpperCase();
					
					
					for (int i = 0; i < answer.length(); i++)
						if (answer.charAt(i) == ' ')
							count++;
					if (count != 5)
						System.out.println("Invalid format");
				}
				answerA = answer.split(" ");
				if (answerA[2].equalsIgnoreCase("economy"))
					s = SeatClass.economy;
				else if (answerA[2].equalsIgnoreCase("business"))
					s = SeatClass.business;
				else if (answerA[2].equalsIgnoreCase("first"))
					s = SeatClass.first;
				else {
					System.out.println(answerA[2] + " class doesn't exist");
					s = null;
				}
				
				if (answerA[4].toUpperCase().compareTo("S") == 0)
					columns = 3;
				else if (answerA[4].toUpperCase().compareTo("M") == 0)
					columns = 4;
				else if (answerA[4].toUpperCase().compareTo("W") == 0)
					columns = 10;
				
				try {
					rows = Integer.parseInt(answerA[3]);
					price = Integer.parseInt(answerA[5]);
					
				} catch (NumberFormatException e) {
					System.out.println("row/price not in a valid format");
				}
				
				if (columns != -1 && rows != -1 && price != -1 && s != null) {
					System.out.println("Creating...Done");
					res.createSection(answerA[0], answerA[1], s, rows, columns, price, answerA[4].toUpperCase());
				}
			}
			
			System.out.println("\n[0] Run old client\n[1] input file\n[2] Change the price associated with " + res.getCabinSeat() + "s in a "
			+ res.getTripFlight() + " section\n" + "[3] Change price for a " + res.getCabinSeat() + " class for an origin and destination for an "
			+ res.getCruiseAirline() + "\n[4] Find " + res.getTripFlight() + "\n[5] Book " + res.getCabinSeat() + "\n[6] Book "
			+ res.getCabinSeat() + " with preference\n[7]" + " Display system details\n[8] Store output\n[9] Create " + res.getPortAirport()
			+ "\n[10] Create " + res.getCruiseAirline() + "\n[11] Create " + res.getTripFlight() + "\n[12] Create section\n[13] Quit\n\n");
			
			result = kb.nextInt();
			kb.nextLine();
		}	
		kb.close();
	}

}
