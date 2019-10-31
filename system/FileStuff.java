package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileStuff {
	private String fileInfo = "";
	
	public void readFile(String path) throws FileNotFoundException {
		File file = new File(path);
		Scanner sc = new Scanner(file);
		
		while (sc.hasNextLine())
			fileInfo += sc.nextLine().toUpperCase();
		
		sc.close();
		fileInfo = fileInfo.replaceAll("\\s", "");
	}
	
	public void createSystem(SystemManager res) {
		String airports = "";
		String airline = "";
		String flID = "";
		String date = "";
		String orig = "";
		String dest = "";
		String section = "";
		String[] airportsA;
		String[] dateA;
		String[] sectionA;
		
		airports += fileInfo.substring(1, fileInfo.indexOf(']'));

		fileInfo = fileInfo.substring(fileInfo.indexOf(']') + 2);

		
		airportsA = airports.split(",");
		for (String s: airportsA)
			res.createAirport(s);
		
		while (!fileInfo.isEmpty())
		{
			if (fileInfo.charAt(0) == ',')
				fileInfo = fileInfo.substring(1);
			
			airline = fileInfo.substring(0, fileInfo.indexOf('['));
			fileInfo = fileInfo.substring(fileInfo.indexOf('[') + 1);
			res.createAirline(airline);
			
			while (fileInfo.charAt(0) != ',') {
				
				flID = fileInfo.substring(0, fileInfo.indexOf('|'));
				fileInfo = fileInfo.substring(fileInfo.indexOf('|') + 1);
				
				date = fileInfo.substring(0, fileInfo.indexOf('|'));
				fileInfo = fileInfo.substring(fileInfo.indexOf('|') + 1);
				dateA = date.split(",");
				
				orig = fileInfo.substring(0, fileInfo.indexOf('|'));
				fileInfo = fileInfo.substring(fileInfo.indexOf('|') + 1);
				
				dest = fileInfo.substring(0, fileInfo.indexOf('['));
				fileInfo = fileInfo.substring(fileInfo.indexOf('[') + 1);
				
				section = fileInfo.substring(0, fileInfo.indexOf(']'));
				fileInfo = fileInfo.substring(fileInfo.indexOf(']') + 2);
				sectionA = section.split(",");
				
				res.createFlight(airline, orig, dest, Integer.parseInt(dateA[0]), Integer.parseInt(dateA[1]), Integer.parseInt(dateA[2]), Integer.parseInt(dateA[3]), Integer.parseInt(dateA[4]), flID);
				res.createSection(airline, flID, sectionA);
				
				if (fileInfo.charAt(0) == '}')
					break;
			}
			if (fileInfo.charAt(0) == '}')
				break;
		}
	}
}
