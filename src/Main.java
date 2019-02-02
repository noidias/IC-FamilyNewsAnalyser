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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

	//input files
	static String famNews = readLineByLineJava8("famNews2.txt");
	static String planetScreen = readLineByLineJava8("planetList.txt");
	static String famCouncilScreen = readLineByLineJava8("famCouncil.txt");
	
	//Regex
	static String playerNameRegex = "([\\w+\\s*\\w*]*)";
	static String turnRegex = "[\\s]+T-\\d{1,4}[\\s]+";
	static String planetRegex = " planet (\\d+) in the (\\d+),(\\d+) system";
	
	public static void main(String[] args) throws IOException {
	
	exploredPlanetSummary();
	capturedPlanetSummary();
	defeatedPlanetSummary();
	//planetScreenFormater(planetScreen);
	}		
			
	public static void exploredPlanetSummary() {
		Multimap<String, String> exploredPlanets = ArrayListMultimap.create();
		
		//Pattern planetPattern = Pattern.compile("(?s)E	T-\\d{1,4}		([\\w+\\s*\\w*]*) explored planet (\\d+) in the (\\d+),(\\d+) system.");
		Pattern planetPattern = Pattern.compile("(?s)E"+turnRegex+playerNameRegex+" explored"+planetRegex);
		Matcher news = planetPattern.matcher(famNews);
		
		System.out.println("-------------------\r\n" + 
				"-   EXPLORATION   -\r\n" + 
				"-------------------");

		while (news.find())	{
				 String playerName = news.group(1);	 
				 String planetCoords= extractPlanet(news);
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
	
	public static void capturedPlanetSummary() {
		Multimap<String, String> capturedPlanets = ArrayListMultimap.create();
		ArrayList<String> families = new ArrayList<String>();
		
		//SA	T-657		The forces of Blood Eagle took planet 12 in the 65,83 system from Shredder (6368).
		Pattern planetPattern = Pattern.compile("(?s)SA"+turnRegex+"The forces of "+playerNameRegex+" took"+planetRegex+" from "+playerNameRegex+" .(\\d+)+..");
		Matcher news = planetPattern.matcher(famNews);
		
		System.out.println("-------------------\r\n" + 
				"-   CAPTURES      -\r\n" + 
				"-------------------");

		while (news.find())	{
				
				 String playerName = news.group(1);	 
				 String planetCoords= extractPlanet(news);
				 capturedPlanets.put(playerName, planetCoords);				
				 families.add(news.group(6));
			 }
		
		   for (String key : capturedPlanets.keySet())
		    {
		        for (String value : capturedPlanets.get(key))
		        {
		            //System.out.printf("%s %s\n", key, value);
		        }
		        System.out.println(capturedPlanets.get(key).size() +" planet(s) captured by "+ key );
		    }
		   System.out.println("-------------------");
		   System.out.println(capturedPlanets.size() +" planet(s) have been captured.");
		   System.out.println("-------------------");
		   countFrequencies(families);
	    }
	
	public static void defeatedPlanetSummary() {
		Multimap<String, String> defeatedPlanets = ArrayListMultimap.create();
		ArrayList<String> families = new ArrayList<String>();
		
		//SA	T-657		The forces of Blood Eagle took planet 12 in the 65,83 system from Shredder (6368).
		Pattern planetPattern = Pattern.compile("(?s)EA"+turnRegex+"After a brave fight our family member "+playerNameRegex+" had to flee the planet"+planetRegex+" which was attacked by "+playerNameRegex+" of family (\\d+)+.");
		Matcher news = planetPattern.matcher(famNews);
		
		System.out.println("-------------------\r\n" + 
				"-   DEFEATS       -\r\n" + 
				"-------------------");

		while (news.find())	{
				
				 String playerName = news.group(1);	 
				 String planetCoords= extractPlanet(news);
				 defeatedPlanets.put(playerName, planetCoords);				
				 families.add(news.group(6));
			 }
		
		   for (String key : defeatedPlanets.keySet())
		    {
		        for (String value : defeatedPlanets.get(key))
		        {
		            //System.out.printf("%s %s\n", key, value);
		        }
		        System.out.println(defeatedPlanets.get(key).size() +" planet(s) lost by "+ key );
		    }
		   System.out.println("-------------------");
		   System.out.println(defeatedPlanets.size() +" planet(s) have been lost.");
		   System.out.println("-------------------");
		   countFrequencies(families);
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
	
	public static String extractPlanet(Matcher result) {		 
		 int planetNo = Integer.parseInt(result.group(2));
		 int planetX = Integer.parseInt(result.group(3));
		 int planetY = Integer.parseInt(result.group(4));
		 String planetCoords = (planetX+","+planetY+":"+planetNo);	
		 return planetCoords;
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
	
	public static void countFrequencies(ArrayList<String> families) 
    { 
        // hashmap to store the frequency of element 
        Map<String, Integer> hm = new HashMap<String, Integer>(); 
  
        for (String i : families) { 
            Integer j = hm.get(i); 
            hm.put(i, (j == null) ? 1 : j + 1); 
        } 
  
        // displaying the occurrence of elements in the arraylist 
        for (Map.Entry<String, Integer> val : hm.entrySet()) { 
            System.out.println(val.getValue() + " "
                               + "from "
                               +"#"+ val.getKey()); 
        } 
    } 
	
			
}
   