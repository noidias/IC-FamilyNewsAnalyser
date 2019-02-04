import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	static String famNews = readFileLineByLine("famNews3.txt");
	static String planetScreen = readFileLineByLine("planetList.txt");
	static String famCouncilScreen = readFileLineByLine("famCouncil.txt");
	
	static int i = 0;
	static ArrayList<NewsItem> newsArray = new ArrayList<NewsItem>();
	static NewsItem nextLineOfNews;
	
	//Regex
	static String playerNameRegex = "([\\w+\\s*\\w*]*)";
	static String turnRegex = "[\\s]+T-\\d{1,4}[\\s]+";
	static String planetRegex = " planet (\\d+) in the (\\d+),(\\d+) system";
	static String lineRegx = "(\\d+) ";
	
	
	
	
	
	public static void main(String[] args) throws IOException {
	
	//exploredPlanetSummary();
	//capturedPlanetSummary();
	//defeatedPlanetSummary();
		defeatedPlanetSummaryV2();

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
		
		printSummary(exploredPlanets," planet(s) explored by ");
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
		
		   printSummary(capturedPlanets," planet(s) captured by ");
		   
		   System.out.println("-------------------");
		   System.out.println(capturedPlanets.size() +" planet(s) have been captured.");
		   System.out.println("-------------------");
		   countFrequencies(families, "from");
	    }
	
	public static void defeatedPlanetSummary() {
		Multimap<String, String> defeatedPlanets = ArrayListMultimap.create();
		ArrayList<String> families = new ArrayList<String>();
		
		Pattern planetPattern = Pattern.compile("(?s)(EA"+turnRegex+"After a brave fight our family member "+playerNameRegex+" had to flee the planet"+planetRegex+" which was attacked by "+playerNameRegex+" of family (\\d+)+.");
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
		   printSummary(defeatedPlanets," planet(s) lost by ");
		   System.out.println("-------------------");
		   System.out.println(defeatedPlanets.size() +" planet(s) have been lost.");
		   System.out.println("-------------------");
		   countFrequencies(families, "to");
	}
	
	public static void defeatedPlanetSummaryV2() {
		
		Pattern planetPattern = Pattern.compile("(?s)(\\d+) (\\w+)[\\s]+T-(\\d{1,4})[\\s]+After a brave fight our family member "+playerNameRegex+" had to flee the planet"+planetRegex+" which was attacked by "+playerNameRegex+" of family (\\d+)+.");
		Matcher news = planetPattern.matcher(famNews);
		System.out.println("-------------------\r\n" + 
				"-   DEFEATS       -\r\n" + 
				"-------------------");
		
		while (news.find())	{
				 int line = Integer.parseInt(news.group(1));
				 String event = news.group(2);
				 int turn = Integer.parseInt(news.group(3));
				 String player = news.group(4);
				 
				 int planetNo = Integer.parseInt(news.group(5));
				 int planetX = Integer.parseInt(news.group(6));
				 int planetY = Integer.parseInt(news.group(7));
				 String planetCoords = (planetX+","+planetY+":"+planetNo);
				 String enemy = news.group(8);
				 String family = news.group(9);
				 nextLineOfNews = new  NewsItem(line, event, turn, player, planetCoords,family,enemy);
				 newsArray.add(nextLineOfNews);

		}
		nextLineOfNews.printSummaryByEvent("Defeats",newsArray);

	}
	
	public static String extractPlanet(Matcher result) {		 
		 int planetNo = Integer.parseInt(result.group(2));
		 int planetX = Integer.parseInt(result.group(3));
		 int planetY = Integer.parseInt(result.group(4));
		 String planetCoords = (planetX+","+planetY+":"+planetNo);	
		 return planetCoords;
	}
	
	public static void printSummary(Multimap<String, String> multimap, String text)
	{
		 for (String key : multimap.keySet())
		    {
		        for (String value : multimap.get(key))
		        {
		        	//code if want list of planets
		            //System.out.printf("%s %s\n", key, value);
		        }
		        System.out.println(multimap.get(key).size() +text+ key );
		    }
	}
	
	private static String readFileLineByLine(String filePath)
	{
		
	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
	    {
	    	stream.forEach(s -> contentBuilder.append(i++).append(" "+s).append("\n"));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
	public static void countFrequencies(ArrayList<String> families, String text) 
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
                               + text+" "
                               +"#"+ val.getKey()); 
        } 
    } 
	
			
}
   