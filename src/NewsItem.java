import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NewsItem implements Comparable<NewsItem> {

	private String planetCoords;
	private String famMember;
	private String enemyFam;
	private String newsEvent;
	private int turnOccurred;
	private int lineNumber;
	private String enemyPlayer;
	
	private static NewsItem nextLineOfNews;
	static ArrayList<NewsItem> newsArray = new ArrayList<NewsItem>();

	public NewsItem(int lineNumber, String newsEvent, int turnOccurred, String famMember, String planetCoords, String enemyFam, String enemyPlayer) {
		super();
		this.planetCoords = planetCoords;
		this.famMember = famMember;
		this.enemyFam = enemyFam;
		this.newsEvent = newsEvent;
		this.turnOccurred = turnOccurred;
		this.lineNumber = lineNumber;
		this.enemyPlayer = enemyPlayer;
	}
		
	public String getFamMember() {
		return famMember;
	}
	public void setFamMember(String famMember) {
		this.famMember = famMember;
	}
	public String getPlanetCoords() {
		return planetCoords;
	}
	public void setNewsItem(String planetCoords) {
		this.planetCoords = planetCoords;
	}
	public String getEnemyFam() {
		return enemyFam;
	}
	public void setEnemyFam(String enemyFam) {
		this.enemyFam = enemyFam;
	}
	public String getNewsEvent() {
		return newsEvent;
	}
	public void setNewsEvent(String newsEvent) {
		this.newsEvent = newsEvent;
	}
	public int getTurnOccurred() {
		return turnOccurred;
	}
	public void setTurnOccurred(int turnOccurred) {
		this.turnOccurred = turnOccurred;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getenemyPlayer() {
		return enemyPlayer;
	}
	public void setEnemyPlayer(String enemyPlayer) {
		this.enemyPlayer = enemyPlayer;
	}
	
	public int compareTo(NewsItem compareLine) {
		
		int compareQuantity = ((NewsItem) compareLine).getLineNumber(); 
		
		//ascending order
		return this.lineNumber - compareQuantity;
		//descending order
		//return compareQuantity - this.lineNumber;
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

		Collections.sort(newsArray);

		
		int i=0;
		for(NewsItem temp: newsArray){
		   System.out.println("line: " + temp.getLineNumber() + 
			", turn : " + temp.getTurnOccurred());
		}
		
		
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

	//TODO remove blown planets after adding
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
		System.out.println("-------------------");
		System.out.println(retakeList.size() + " planet(s) missing in action");
			

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
