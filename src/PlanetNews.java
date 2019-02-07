import java.util.ArrayList;

public class PlanetNews  extends News implements Comparable<PlanetNews> {

	private String enemyFam;
	private String enemyPlayer;
	protected String planetCoords;

	public PlanetNews(int lineNumber, String newsEvent, int turnOccurred, String famMember, String planetCoords, String enemyFam, String enemyPlayer) {
		super(lineNumber, newsEvent, turnOccurred, famMember);
		this.enemyFam = enemyFam;
		this.enemyPlayer = enemyPlayer;
		this.planetCoords = planetCoords;
	}
	
	//get set
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
	public String getenemyPlayer() {
		return enemyPlayer;
	}
	public void setEnemyPlayer(String enemyPlayer) {
		this.enemyPlayer = enemyPlayer;
	}
	
	public int compareTo(PlanetNews compareLine) {
		
		int compareQuantity = ((PlanetNews) compareLine).getLineNumber(); 
		
		//ascending order
		//return this.lineNumber - compareQuantity;
		//descending order
		return compareQuantity - this.lineNumber;
	}
	
	public void printArray(ArrayList<PlanetNews> newsArray) {
		for (PlanetNews planetNews : newsArray) {
			System.out.println(planetNews.getLineNumber() + " " + planetNews.getNewsEvent() + " " + planetNews.getTurnOccurred() + " "
					+ planetNews.getFamMember() + " " + planetNews.getPlanetCoords() + " " + planetNews.getEnemyFam()+ " "
					+ planetNews.getenemyPlayer());
		}
	}
	
	
	

	
	
	
	
	
}
