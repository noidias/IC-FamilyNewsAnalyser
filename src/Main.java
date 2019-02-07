
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

	static ArrayList<NewsItem> newsArray = new ArrayList<NewsItem>();


	//input files
	static String famNews = readFileLineByLine("famNews3.txt");
	
	
	static int i = 0;
	
	//Regex
	static String playerNameRegex = "([\\w+\\s*\\w*]*)";
	static String turnRegex = "[\\s]+T-\\d{1,4}[\\s]+";
	static String planetRegex = " planet (\\d+) in the (\\d+),(\\d+) system";
	static String lineRegx = "(\\d+) ";
	static String eventTick = "(\\w+)[\\s]+T-(\\d{1,4})[\\s]+";
		
	public static void main(String[] args) throws IOException {
	//Pattern planetPattern = Pattern.compile("(?s)E	T-\\d{1,4}		([\\w+\\s*\\w*]*) explored planet (\\d+) in the (\\d+),(\\d+) system.");
	Pattern explorePattern = Pattern.compile("(?s)"+lineRegx+eventTick+playerNameRegex+" explored"+planetRegex+"()()");		
	Pattern capturePattern = Pattern.compile("(?s)"+lineRegx+eventTick+"The forces of "+playerNameRegex+" took"+planetRegex+" from "+playerNameRegex+" .(\\d+)+..");	
	Pattern defeatPattern = Pattern.compile("(?s)"+lineRegx+eventTick+"After a brave fight our family member "+playerNameRegex+" had to flee the planet"+planetRegex+" which was attacked by "+playerNameRegex+" of family (\\d+)+.");
	
	//explored
	newsArray = extractData(explorePattern, famNews);
	printSummaryByEvent("E");
	
	//Capture
	newsArray = extractData(capturePattern, famNews);
	printSummaryByEvent("SA");
	
	//Defeat
	newsArray = extractData(defeatPattern, famNews);
	printSummaryByEvent("EA");
	//NewsItem.printArray(newsArray);
	
	openRetakes();
	}		
			
	
	public static ArrayList<NewsItem> extractData(Pattern planetPattern, String famNews) {
		ArrayList<NewsItem> newsArray = new ArrayList<NewsItem>();
		Matcher news = planetPattern.matcher(famNews);
		while (news.find()) {
			
			int line = Integer.parseInt(news.group(1));
			String event = news.group(2);
			int turn = Integer.parseInt(news.group(3));
			String player = news.group(4);

			int planetNo = Integer.parseInt(news.group(5));
			int planetX = Integer.parseInt(news.group(6));
			int planetY = Integer.parseInt(news.group(7));
			String planetCoords = (planetX + "," + planetY + ":" + planetNo);
			String enemy = news.group(8);
			String family = news.group(9);
			NewsItem nextLineOfNews = new NewsItem(line, event, turn, player, planetCoords, family, enemy);
			newsArray.add(nextLineOfNews);
		}
		
		return newsArray;
	}
	
	
	public static ArrayList<NewsItem> printSummaryByEvent(String event) {
		ArrayList<String> families = new ArrayList<String>();
		ArrayList<String> players = new ArrayList<String>();

		Collections.sort(newsArray);

		/*
		int i=0;
		for(NewsItem temp: newsArray){
		   System.out.println("line: " + temp.getLineNumber() + 
			", turn : " + temp.getTurnOccurred());
		}
		*/
		
		switch (event) {
		
		case "E":
			for (NewsItem newsItem : newsArray) {
				if (newsItem.getNewsEvent().equals("E")) {
					players.add(newsItem.getFamMember());
				}
			}
			System.out.println("-------------------\r\n" + "-    EXPLORATION     -\r\n" + "-------------------");
			countFrequencies(players, " planet(s) explored by ");
			System.out.println("-------------------");
			System.out.println(players.size() + " planet(s) have been explored.");
		   			
			return newsArray;
		
		case "EA":
			for (NewsItem newsItem : newsArray) {
				if (newsItem.getNewsEvent().equals("EA")) {
					families.add(newsItem.getEnemyFam());
					players.add(newsItem.getFamMember());
				}
			}
			System.out.println("-------------------\r\n" + "-   DEFEATS       -\r\n" + "-------------------");
			countFrequencies(players, "planet(s) lost by");
			System.out.println("-------------------");
			countFrequencies(families, "to");
			System.out.println("-------------------");
			System.out.println(players.size() + " planet(s) have been lost.");
			return newsArray;


		case "SA":
			for (NewsItem newsItem : newsArray) {
				if (newsItem.getNewsEvent().equals("SA")) {
					families.add(newsItem.getEnemyFam());
					players.add(newsItem.getFamMember());
				}
			}
			System.out.println("-------------------\r\n" + "-   CAPTURES       -\r\n" + "-------------------");
			countFrequencies(players, "planet(s) captured by");
			System.out.println("-------------------");
			countFrequencies(families, "from");
			System.out.println("-------------------");
			System.out.println(players.size() + " planet(s) have been captured.");
			return newsArray;

		default: 
			return newsArray;

		}
	}

	//TODO replace previous method
	public void countFrequenciesObject(ArrayList<NewsItem> newsArray, String text) {
		Map<String, Integer> hm = new HashMap<String, Integer>();

		for (NewsItem i : newsArray) {
			Integer j = hm.get(i.getFamMember());
			hm.put(i.getFamMember(), (j == null) ? 1 : j + 1);
		}

		// displaying the occurrence of elements in the arraylist
		for (Map.Entry<String, Integer> val : hm.entrySet()) {

			System.out.println(val.getValue() + " " + text + " " + "#" + val.getKey());
		}
	}
	
	
	public static void countFrequencies(ArrayList<String> families, String text) {
		Map<String, Integer> hm = new HashMap<String, Integer>();

		for (String i : families) {
			Integer j = hm.get(i);
			hm.put(i, (j == null) ? 1 : j + 1);
		}

		// displaying the occurrence of elements in the arraylist
		for (Map.Entry<String, Integer> val : hm.entrySet()) {

			System.out.println(val.getValue() + " " + text + " " + "#" + val.getKey());
		}
	}

	//TODO remove blown planets after adding
	public static void openRetakes() {
		int i = maxLineNumber();
		ArrayList<String> retakeList = new ArrayList<String>();
		Boolean match = false;
		for (NewsItem newsItemi : newsArray) {
			
			String currentPlanet = newsItemi.getPlanetCoords();
			int currentLine = newsItemi.getLineNumber();
			
			//System.out.println("line i " + currentLine);
			
			
			if (newsItemi.getNewsEvent().equals("EA")) {
				
				//System.out.println("defeat Line Number " + currentLine + " " + currentPlanet);
				
				for (NewsItem newsItemj : newsArray) {
					//System.out.println("line j " + newsItemj.lineNumber);
					if (newsItemj.getNewsEvent().equals("SA") && newsItemj.getPlanetCoords().equals(currentPlanet)
							&& newsItemj.getLineNumber() < currentLine) {
						
						//System.out.println("retake " + newsItemj.lineNumber + " "  + newsItemj.planetCoords);
						match = true;
					}

				}
				if (match) {
					//System.out.println("retake ");
				}		
				else {
					retakeList.add(currentPlanet);
					//System.out.println("open retake " + newsItemi.lineNumber + " " + currentPlanet);
				}
				match = false;

			}
		}
		int k = 0;
		System.out.println("--------------------\r\n" + 
				"-   OPEN RETAKES   -\r\n" + 
				"--------------------");
		for (String planet : retakeList) {
			System.out.println(retakeList.get(k));
			k++;
		}
		System.out.println("-------------------");
		System.out.println(retakeList.size() + " planet(s) missing in action");
			

	}
	
	public void printArray(ArrayList<NewsItem> newsArray) {
		for (NewsItem newsItem : newsArray) {
			System.out.println(newsItem.getLineNumber() + " " + newsItem.getNewsEvent() + " " + newsItem.getTurnOccurred() + " "
					+ newsItem.getFamMember() + " " + newsItem.getPlanetCoords() + " " + newsItem.getEnemyFam()+ " "
					+ newsItem.getenemyPlayer());
		}
	}
	
	public static int maxLineNumber() {
		int maxLine = 0;
		int currentLine = 0;
		for (NewsItem newsItem : newsArray) {
			currentLine = newsItem.getLineNumber();
			if (currentLine > maxLine) {
				maxLine = newsItem.getLineNumber();
			}
		}
		return maxLine;
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
	
	
			
}
   