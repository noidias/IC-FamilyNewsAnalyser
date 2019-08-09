package FamilyNews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import Application.ExtractData;
import Application.Reporting;
import Application.NewsAnalyser;

public class PlanetNews extends News implements Comparable<PlanetNews> {

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
	
	public static String reportPlanetSections(String famNews) {
		String report = "";
		ArrayList<PlanetNews> captureArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> blownSaArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> blownEaArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> defeatsArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> exploreArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> retakesArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> lostBlownArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> missingArray = new ArrayList<PlanetNews>();
		
		//Pattern explorePattern = Pattern.compile("(?s)"+lineRegx+eventTick+playerNameRegex+" explored"+planetRegex+"()()");		
		Pattern explorePattern = Pattern.compile("(?s)"+eventTick+playerNameRegex+" explored"+planetRegex+"()()");		
		Pattern capturePattern = Pattern.compile("(?s)"+eventTick+"The forces of "+playerNameRegex+" took"+planetRegex+" from "+playerNameRegex+" .(\\d+)+..");	
		Pattern defeatPattern = Pattern.compile("(?s)"+eventTick+"After a brave fight our family member "+playerNameRegex+" had to flee the planet"+planetRegex+" which was attacked by "+playerNameRegex+" of family (\\d+)+.");
		Pattern blownSAPattern = Pattern.compile("(?s)"+eventTick+playerNameRegex+" attacked "+playerNameRegex+" .(\\d+). on"+planetRegex+", and the heavy battle made the planet uninhabitable; an exploration ship will have to be sent there.");
		Pattern blownEAPattern = Pattern.compile("(?s)"+eventTick+"An overwhelming force from "+playerNameRegex+", family (\\d+) attacked "+playerNameRegex+"'s"+planetRegex+". The defenders for "+playerNameRegex+" managed to set off a nuclear blast which made the planet uninhabitable.");
		
		//explored
		exploreArray = ExtractData.extractPlanetData(explorePattern, famNews);
		String exploreReport = Reporting.printSummaryPlanets(exploreArray, "Explored");
		//System.out.println(exploreReport);
		report = Reporting.appendString(report,exploreReport);
		//String exploreList = Reporting.printArrayExplored(exploreArray);	
		//report = Reporting.appendString(report,exploreList);
		
		//Capture
		captureArray = ExtractData.extractPlanetData(capturePattern, famNews);
		
		String captureReport = Reporting.printSummaryPlanetsFamily(captureArray, "Family Captures");
		report = Reporting.appendString(report,captureReport);
		
		captureReport = Reporting.printSummaryPlanets(captureArray, "Captures");
		//System.out.println(captureReport);
		report = Reporting.appendString(report,captureReport);
			
		
		defeatsArray = ExtractData.extractPlanetData(defeatPattern, famNews);
		
		String defeatsReport = Reporting.printSummaryPlanetsFamily(defeatsArray, "Family Defeats");
		report = Reporting.appendString(report,defeatsReport);
		
		defeatsReport = Reporting.printSummaryPlanets(defeatsArray, "Defeats");
		//System.out.println(defeatsReport);
		report = Reporting.appendString(report,defeatsReport);
	
		//blow ups Attacks
		blownSaArray =ExtractData.extractDataBlownSA(blownSAPattern, famNews);

		String blownSaReport = Reporting.printSummaryPlanetsFamily(blownSaArray, "Captures blown up");
		report = Reporting.appendString(report,blownSaReport);

		
		blownSaReport = Reporting.printSummaryPlanets(blownSaArray, "blown ups by");
		report = Reporting.appendString(report,blownSaReport);
			
		//blow ups defeats
		blownEaArray =ExtractData.extractDataBlownEA(blownEAPattern, famNews);
		
		lostBlownArray = Reporting.findOutstandingBlowPLanets(captureArray, blownEaArray, exploreArray);
		
		String lostBlownReport = Reporting.printSummaryPlanetsFamily(blownEaArray, "defeats blown ups");
		report = Reporting.appendString(report,lostBlownReport);
		
		lostBlownReport = Reporting.printSummaryPlanets(blownEaArray, "blown ups lost");
		report = Reporting.appendString(report,lostBlownReport);
		
		
		String lostBlownplanetsReport = Reporting.printArray(lostBlownArray);
		//System.out.println(lostBlownplanetsReport);
		report = Reporting.appendString(report,lostBlownplanetsReport);
		
		
		if (lostBlownArray != null)
			missingArray.addAll( lostBlownArray );
		
		if (blownSaArray != null)
			captureArray.addAll( blownSaArray );
		
		Collections.sort(retakesArray);
		retakesArray = Reporting.findOpenRetakes(captureArray, defeatsArray);

		if (retakesArray != null)
			missingArray.addAll( retakesArray );
		
		
		String missingReport = Reporting.printSummaryPlanets(missingArray, "Planets still missing");
		//System.out.println(missingReport);
		report = Reporting.appendString(report,missingReport);
		
		String openRetakesReport =Reporting.printOpenRetakes(retakesArray);
		//System.out.println(openRetakesReport);
		report = Reporting.appendString(report,openRetakesReport);
		
		String cleanRetakesReport = Reporting.printOpenRetakesClean(retakesArray);
		//System.out.println(cleanRetakesReport);
		report = Reporting.appendString(report,cleanRetakesReport);
		
		
		return report;
		}

	
	
	
	

	
	
	
	
	
}
