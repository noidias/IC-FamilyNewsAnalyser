package recentReports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import Application.ExtractData;
import Application.Reporting;
import FamilyNews.PlanetNews;
import Application.CoreInfo;

public class RecentReports extends CoreInfo {

	public RecentReports(int lineNumber, int turnOccurred) {
		super(lineNumber, turnOccurred);
		// TODO Auto-generated constructor stub
	}

	public static String reportRecentReport(String infil) {
		String report = "";
		ArrayList<Units> unitArray = new ArrayList<Units>();
		ArrayList<Units> unitsLostPatternArray = new ArrayList<Units>();
		ArrayList<PlanetNews> exploreArray = new ArrayList<PlanetNews>();
		ArrayList<Buildings> buildingArray = new ArrayList<Buildings>();
		ArrayList<Buildings> portalArray = new ArrayList<Buildings>();
		//units
		Pattern unitPattern = Pattern.compile("(?s)\\s(\\d+) +T-\\d+.[\\s]+Construction completed[\\s]+We have built (\\d+) (\\w+).");	
		
		
		
		//exploration
		Pattern explorePattern = Pattern.compile("(?s)\\s()(\\d+) +T-(\\d+)()[:\\s]+[Exploration|Explored a planet]+[\\s]+We have explored the"+planetRegex+".()()"); //works with both
		unitArray = ExtractData.extractUnitData(unitPattern, infil);
		String unitReport = Reporting.countAndPrintFrequenciesUnits(unitArray);
		report = Reporting.appendString(report,unitReport);
		
		exploreArray = ExtractData.extractPlanetData(explorePattern, infil);
		Reporting.printArray(exploreArray);
		//String exploreReport = Reporting.printSummaryPlanets(exploreArray, "Explored");
		//report = Reporting.appendString(report,exploreReport);
		String exploreList = Reporting.printArrayExplored(exploreArray);	
		report = Reporting.appendString(report,exploreList);
		
		//unitsLost
		//Our planet 12 at x:79, y:42 was attacked by Atomic_Tangerine of family 7074. Our defending forces fought bravely but I am sorry to say, after a long and bloody fight, they had to flee the planet.
		//In the fight we also lost 762 soldiers 457 droids 11 laser turrets
		
		//Our planet 12 at x:119, y:50 was attacked by Outrageous_Orange of family 7074, I am pleased to say that we with our forces extraordinary fighting skill were able to fight the attackers off our planet.
		//In the fight we lost 326 fighters 197 laser turrets
		
		//Our planet 1 at x:108, y:64 was attacked by Outrageous_Orange of family 7074. When we first spotted the overwhelming enemy force approaching we already suspected defeat and managed to prepare a nuclear blast which made the planet uninhabitable when we left.
		//In the fight we also lost 522 fighters 18 soldiers 1466 droids
		infil = infil.replace(" turrets", "");
		
		String attackedText = "was attacked by [\\w+\\s]+ of family \\d+";
		//String attackedText = "was attacked by [[a-zA-Z0-9_]\\w+\\s*[a-zA-Z0-9_]]* of family \\d+";
		String lostPLanetText = ". Our defending forces fought bravely but I am sorry to say, after a long and bloody fight, they had to flee the planet";
		String LostPlanetBlownText = ". When we first spotted the overwhelming enemy force approaching we already suspected defeat and managed to prepare a nuclear blast which made the planet uninhabitable when we left";
		String foughtOffText = ", I am pleased to say that we with our forces extraordinary fighting skill were able to fight the attackers off our planet";	
		String unitsLostCommon = "";
		
		for (int i = 1; i < 4; i++) {
			switch (i) {
			case 1:
				unitsLostCommon ="(?s)(\\d+) +T-(\\d+). Defense Report[\\s]+Our planet \\d+ at x:\\d+, y:\\d+ "+attackedText+foughtOffText;
				break;
			case 2:
				unitsLostCommon ="(?s)(\\d+) +T-(\\d+). Defense Report[\\s]+Our planet \\d+ at x:\\d+, y:\\d+ "+attackedText+lostPLanetText;
				break;
			case 3:
				unitsLostCommon ="(?s)(\\d+) +T-(\\d+). Defense Report[\\s]+Our planet \\d+ at x:\\d+, y:\\d+ "+attackedText+LostPlanetBlownText;
				break;
			}
			Pattern unitsLostPattern1 = Pattern.compile(unitsLostCommon+".[\\s]+In the fight we [\\w ]{0,5}lost (\\d+) (fighters|soldiers|droids|laser)[^ ]{1}()()()()()()()()");
			Pattern unitsLostPattern2 = Pattern.compile(unitsLostCommon+".[\\s]+In the fight we [\\w ]{0,5}lost (\\d+) (fighters|soldiers|droids|laser) (\\d+) (soldiers|droids|laser)[^ ]{1}()()()()()()");
			Pattern unitsLostPattern3 = Pattern.compile(unitsLostCommon+".[\\s]+In the fight we [\\w ]{0,5}lost (\\d+) (fighters|soldiers|droids|laser) (\\d+) (soldiers|droids|laser) (\\d+) (droids|laser)[^ |]{1}()()()()");
			Pattern unitsLostPattern4 = Pattern.compile(unitsLostCommon+".[\\s]+In the fight we [\\w ]{0,5}lost (\\d+) (fighters|soldiers|droids|laser) (\\d+) (soldiers|droids|laser) (\\d+) (droids|laser) (\\d+) (laser)()()");
			unitsLostPatternArray.addAll(ExtractData.extractLostUnitData(unitsLostPattern1, infil));
			unitsLostPatternArray.addAll(ExtractData.extractLostUnitData(unitsLostPattern2, infil));
			unitsLostPatternArray.addAll(ExtractData.extractLostUnitData(unitsLostPattern3, infil));
			unitsLostPatternArray.addAll(ExtractData.extractLostUnitData(unitsLostPattern4, infil));
		}		
		
		Collections.sort(unitsLostPatternArray);
		
		String unitsLostReport = Reporting.countAndPrintFrequenciesUnitsLostSummary(unitsLostPatternArray);
		report = Reporting.appendString(report,unitsLostReport);
		
		unitsLostReport = Reporting.countAndPrintFrequenciesUnitsLost(unitsLostPatternArray);
		report = Reporting.appendString(report,unitsLostReport);
		
		
		//T-552: Buildings complete
		Pattern buildingPattern = Pattern.compile("(?s)\\s(\\d+) +T-(\\d+)[:\\s]+[Infrastructure|Buildings complete]+[\\s]+We have built (\\d+) ([\\w+ ]+) on (\\d+,\\d+:\\d+)."); //works with both
		buildingArray = ExtractData.extractBuildingData(buildingPattern, infil);
		String buildingReport = Reporting.countAndPrintFrequenciesBuildings(buildingArray);
		report = Reporting.appendString(report,buildingReport);
		String laserReport = Reporting.laserReport(buildingArray);
		report = Reporting.appendString(report,laserReport);
		
		//T-629: Portal completed
		Pattern portalPattern = Pattern.compile("(?s)\\s(\\d+) +T-(\\d+)[:\\s]+[Portal completed]+[\\s]+Our workers have finished constructing a portal on"+planetRegex+"."); //works with both
		portalArray = ExtractData.extractPortalData(portalPattern, infil);
		String portalReport = Reporting.portalReport(portalArray);
		String unportaledPlanets = Reporting.unportaledPlanets(portalArray,exploreArray);
		report = Reporting.appendString(report,portalReport);
		report = Reporting.appendString(report,unportaledPlanets);
		return report;
		}
	
	
}
