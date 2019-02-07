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

	
	//get set
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
	
	
	

	
	
	
	
	
}
