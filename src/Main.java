

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
			
	String input = 
	"A	T-10		In the name of family cooperation Botased has sent a shipment of 3467 Cash to CITYHUNTER.\r\n" + 
	"E	T-9		CITYHUNTER explored planet 5 in the 90,10 system.\r\n" + 
	"E	T-9		CITYHUNTER explored planet 8 in the 90,10 system.\r\n" + 
	"A	T-9		In the name of family cooperation Botased has sent a shipment of 3925 Cash to CITYHUNTER.\r\n" + 
	"E	T-7		CITYHUNTER explored planet 10 in the 88,8 system.\r\n" + 
	"E	T-7		CITYHUNTER explored planet 8 in the 88,8 system.\r\n" + 
	"A	T-6		In the name of family cooperation Protagonist has sent a shipment of 50315 Cash 1154 Iron 230 Endurium to CITYHUNTER.\r\n" + 
	"A	T-6		In the name of family cooperation Botased has sent a shipment of 4213 Cash to CITYHUNTER.\r\n" + 
	"A	T-3		In the name of family cooperation Botased has sent a shipment of 49473 Cash 1169 Iron 248 Octarine 233 Endurium to Protagonist.\r\n" + 
	"A	T-1		In the name of family cooperation Kriad has sent a shipment of 16820 Cash 3108 Iron 265 Endurium to CITYHUNTER.\r\n" + 
	"A	T-1		In the name of family cooperation Protagonist has sent a shipment of 92200 Cash 3405 Iron 413 Endurium to CITYHUNTER.\r\n" + 
	"E	T-1		Rama has joined our glorious family!\r\n" + 
	"E	T-1		Sector12 has joined our glorious family!\r\n" + 
	"E	T-1		pretty has joined our glorious family!\r\n" + 
	"E	T-1		Protagonist has joined our glorious family!\r\n" + 
	"E	T-1		Mybalzich has joined our glorious family!\r\n" + 
	"E	T-1		Kriad has joined our glorious family!\r\n" + 
	"E	T-1		Botased has joined our glorious family!";
	
	planetFormater(input);
	
	}		
	
			
	public static void planetFormater(String input) {
		//Pattern PlanetPattern = Pattern.compile("(?s)empire=\\d{6}\\\">([\\w+\\s*\\w*]*)</a>.</td>\\s*<td><a href=..view_race.php.id=\\d+.>[\\w+\\s*\\w*!*'*$*\\.*]*</a></td>\\s*<td>((\\d{0,3},)?(\\d{3},)?\\d{0,3})</td>\\s{5,6}<td>(\\d*)</td> ");
		Pattern planetPattern = Pattern.compile("(?s)planet (\\d+) in the (\\d+),(\\d+) system.");
		Matcher planet = planetPattern.matcher(input);
		
		while (planet.find())	{
				 int planetNo = Integer.parseInt(planet.group(1));
				 int planetX = Integer.parseInt(planet.group(2));;
				 int planetY = Integer.parseInt(planet.group(3));; 
				 System.out.println(planetX+","+planetY+":"+planetNo);
			 }
	}
			
}
   