import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Multimap;

public class NewsItem {
	
	private String planetCoords;
	private String famMember;
	private String enemyFam;
	private String newsEvent;
	private int turnOccurred;
	private int lineNumber;
	private String enemyPlayer;
	
	public NewsItem(int line, String event,int turn, String player, String coords, String family, String enemy ) {
	
		planetCoords = coords;
		famMember = player;
		enemyFam = family;
		newsEvent = event;
		turnOccurred = turn;
		lineNumber = line;
		enemyPlayer = enemy;
	}
	
	public void printArray(ArrayList<NewsItem> newsArray) {
		for (NewsItem newsItem : newsArray) {
			System.out.println(newsItem.lineNumber+" "+newsItem.newsEvent+" "+newsItem.turnOccurred+" "+newsItem.famMember+" "+newsItem.planetCoords+" "+newsItem.enemyFam+" "+newsItem.enemyPlayer);
		}
	}
	
	public void printSummaryByEvent(String event, ArrayList<NewsItem> newsArray) {
		ArrayList<String> families = new ArrayList<String>();
		ArrayList<String> players = new ArrayList<String>();
		switch(event) {
		   case "Defeats" :
			   for (NewsItem newsItem : newsArray) {
				   if (newsItem.newsEvent.equals("EA")) {
					   families.add(newsItem.enemyFam);
					   players.add(newsItem.famMember);
				   }
			   }
			   countFrequencies(players, "lost by");
			   System.out.println("-------------------");
			   countFrequencies(families, "to");
			   System.out.println("-------------------");
			   System.out.println(players.size() +" planet(s) have been lost.");
			   
		      break; 
		   
		   case "Captures" :
		      // Statements
		      break; 
		   
		   default : // explored
		      // Statements
		
		
			   }
		}
		
		public static void countFrequencies(ArrayList<String> families, String text) 
			    { 
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
