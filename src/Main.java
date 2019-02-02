import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws IOException {
			
	String famNews = readLineByLineJava8("famNews2.txt");
	String planetScreen = readLineByLineJava8("planetList.txt");
	String famCouncilScreen = readLineByLineJava8("famCouncil.txt");
	
	exploredPlanetSummary(famNews);
	//planetScreenFormater(planetScreen);
	}		

			
	public static void exploredPlanetSummary(String famNews) {
		Multimap<String, String> exploredPlanets = ArrayListMultimap.create();
		
		Pattern planetPattern = Pattern.compile("(?s)E	T-\\d{1,4}		([\\w+\\s*\\w*]*) explored planet (\\d+) in the (\\d+),(\\d+) system.");
		Matcher planet = planetPattern.matcher(famNews);
		
		System.out.println("-------------------\r\n" + 
				"-   EXPLORATION   -\r\n" + 
				"-------------------");

		while (planet.find())	{
				 String playerName = planet.group(1);	 
				 int planetNo = Integer.parseInt(planet.group(2));
				 int planetX = Integer.parseInt(planet.group(3));
				 int planetY = Integer.parseInt(planet.group(4));
				 String planetCoords = (planetX+","+planetY+":"+planetNo);
				 
				 exploredPlanets.put(playerName, planetCoords);				 			 
			 }
		
		   for (String key : exploredPlanets.keySet())
		    {
		        for (String value : exploredPlanets.get(key))
		        {
		            //System.out.printf("%s %s\n", key, value);
		        }
		        System.out.println(exploredPlanets.get(key).size() +" planet(s) explored by "+ key );
		    }
		   System.out.println("-------------------");
		   System.out.println(exploredPlanets.size() +" planet(s) have been explored.");
	    }

	
	
	
	public static void famCouncilScreenAnalyser(String famCouncilScreen) {
		//Pattern PlanetPattern = Pattern.compile("(?s)empire=\\d{6}\\\">([\\w+\\s*\\w*]*)</a>.</td>\\s*<td><a href=..view_race.php.id=\\d+.>[\\w+\\s*\\w*!*'*$*\\.*]*</a></td>\\s*<td>((\\d{0,3},)?(\\d{3},)?\\d{0,3})</td>\\s{5,6}<td>(\\d*)</td> ");
		int exploredPlanetCount = 0;
		Pattern planetPattern = Pattern.compile("(?s)explored planet (\\d+) in the (\\d+),(\\d+) system.");
		Matcher planet = planetPattern.matcher(famCouncilScreen);
		System.out.println("Explored Planets");
		while (planet.find())	{
				 int planetNo = Integer.parseInt(planet.group(1));
				 int planetX = Integer.parseInt(planet.group(2));;
				 int planetY = Integer.parseInt(planet.group(3));; 
				 System.out.println(planetX+","+planetY+":"+planetNo);
				 exploredPlanetCount++;
			 }
		System.out.println("Total Explored = " + exploredPlanetCount);
	}
	
	
	public static void planetScreenFormater(String planetScreen) {
		int planetCount = 0;
		Pattern planetPattern = Pattern.compile("(?s)(\\d+),(\\d+):(\\d+)	\\d+ ");
		
		Matcher planet = planetPattern.matcher(planetScreen);
		System.out.println("Planets Screen formatted");
		while (planet.find())	{
				 int planetX = Integer.parseInt(planet.group(1));
				 int planetY = Integer.parseInt(planet.group(2));;
				 int planetNo = Integer.parseInt(planet.group(3));; 
				 System.out.println(planetX+","+planetY+":"+planetNo);
				 planetCount++;
			 }
		System.out.println("Planet Screen Total = " + planetCount);
	
	}
	
	
	
	
	private static String readLineByLineJava8(String filePath)
	{
	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
	    {
	        stream.forEach(s -> contentBuilder.append(s).append("\n"));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
			
}
   