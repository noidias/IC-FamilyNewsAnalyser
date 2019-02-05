import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Multimap;

public class NewsItem {

	private String planetCoords;
	private String famMember;
	private String enemyFam;
	private String newsEvent;
	private int turnOccurred;
	private int lineNumber;
	private String enemyPlayer;
	
	private static NewsItem nextLineOfNews;
	static ArrayList<NewsItem> newsArray = new ArrayList<NewsItem>();

	public NewsItem(int line, String event, int turn, String player, String coords, String family, String enemy) {

		planetCoords = coords;
		famMember = player;
		enemyFam = family;
		newsEvent = event;
		turnOccurred = turn;
		lineNumber = line;
		enemyPlayer = enemy;
	}

	public static void printArray(ArrayList<NewsItem> newsArray) {
		for (NewsItem newsItem : newsArray) {
			System.out.println(newsItem.lineNumber + " " + newsItem.newsEvent + " " + newsItem.turnOccurred + " "
					+ newsItem.famMember + " " + newsItem.planetCoords + " " + newsItem.enemyFam + " "
					+ newsItem.enemyPlayer);
		}
	}

	public static ArrayList<NewsItem> extractAttackDefenceData(Pattern planetPattern, String famNews) {
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
			nextLineOfNews = new NewsItem(line, event, turn, player, planetCoords, family, enemy);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}

	public static void printSummaryByEvent(String event) {
		ArrayList<String> families = new ArrayList<String>();
		ArrayList<String> players = new ArrayList<String>();
		switch (event) {
		
		case "E":
			for (NewsItem newsItem : newsArray) {
				if (newsItem.newsEvent.equals("E")) {
					players.add(newsItem.famMember);
				}
			}
			System.out.println("-------------------\r\n" + "-    EXPLORATION     -\r\n" + "-------------------");
			countFrequencies(players, " planet(s) explored by ");
			System.out.println("-------------------");
			System.out.println(players.size() + " planet(s) have been explored.");
		   			
			break;
		
		case "EA":
			for (NewsItem newsItem : newsArray) {
				if (newsItem.newsEvent.equals("EA")) {
					families.add(newsItem.enemyFam);
					players.add(newsItem.famMember);
				}
			}
			System.out.println("-------------------\r\n" + "-   DEFEATS       -\r\n" + "-------------------");
			countFrequencies(players, "planet(s) lost by");
			System.out.println("-------------------");
			countFrequencies(families, "to");
			System.out.println("-------------------");
			System.out.println(players.size() + " planet(s) have been lost.");

			break;

		case "SA":
			for (NewsItem newsItem : newsArray) {
				if (newsItem.newsEvent.equals("SA")) {
					families.add(newsItem.enemyFam);
					players.add(newsItem.famMember);
				}
			}
			System.out.println("-------------------\r\n" + "-   CAPTURES       -\r\n" + "-------------------");
			countFrequencies(players, "planet(s) captured by");
			System.out.println("-------------------");
			countFrequencies(families, "from");
			System.out.println("-------------------");
			System.out.println(players.size() + " planet(s) have been captured.");
			break;

		default: // explored
			// Statements

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

	
	public static void openRetakes() {
		int i = maxLineNumber();
		ArrayList<String> retakeList = new ArrayList<String>();
		Boolean match = false;
		for (NewsItem newsItemi : newsArray) {
			
			String currentPlanet = newsItemi.planetCoords;
			int currentLine = newsItemi.lineNumber;
			
			//System.out.println("line i " + currentLine);
			
			
			if (newsItemi.newsEvent.equals("EA")) {
				
				//System.out.println("defeat Line Number " + currentLine + " " + currentPlanet);
				
				for (NewsItem newsItemj : newsArray) {
					//System.out.println("line j " + newsItemj.lineNumber);
					if (newsItemj.newsEvent.equals("SA") && newsItemj.planetCoords.equals(currentPlanet)
							&& newsItemj.lineNumber < currentLine) {
						
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
			
		//logic to find open retakes
		//get max row number
		//first find planet lost add to retake list
		//check rest of file for retake remove from retake list if id is lower
		//check next planet lost
		
		//optiopns to create a new array storing planet, tick
		//or jsut record line number and print from main listarray
	}
	
	public static int maxLineNumber() {
		int maxLine = 0;
		int currentLine = 0;
		for (NewsItem newsItem : newsArray) {
			currentLine = newsItem.lineNumber;
			if (currentLine > maxLine) {
				maxLine = newsItem.lineNumber;
			}
		}
		return maxLine;
	}
	
	
	
	
}
