package recentReports;

import java.util.ArrayList;
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
		ArrayList<PlanetNews> exploreArray = new ArrayList<PlanetNews>();
		ArrayList<Buildings> buildingArray = new ArrayList<Buildings>();
		//units
		//Pattern unitPattern = Pattern.compile("(?s)\\s(\\d+) T-\\d+: Construction completed[\\s]+We have built (\\d+) (\\w+).");	//works infil
		//Pattern unitPattern = Pattern.compile("(?s)\\s(\\d+)[\\s]+T-\\d+\\w[\\s]+Construction completed[\\s]+We have built (\\d+) (\\w+).");	//works recent report
		Pattern unitPattern = Pattern.compile("(?s)\\s(\\d+) +T-\\d+.[\\s]+Construction completed[\\s]+We have built (\\d+) (\\w+).");	//works with both
		
		//exploration
		//T-424	Exploration	We have explored the planet 1 in the 232:226 system.
		//T-604: Explored a planet
		//We have explored the planet 2 in the 157:28 system.
		//Pattern explorePattern = Pattern.compile("(?s)\\s()(\\d+) +T-(\\d+)()[\\s]+Exploration[\\s]+We have explored the planet (\\d+) in the (\\d+):(\\d+) system.()()"); //works recent report
		//Pattern explorePattern = Pattern.compile("(?s)\\s()(\\d+) +T-(\\d+).()[\\s]+Explored a planet[\\s]+We have explored the planet (\\d+) in the (\\d+):(\\d+) system.()()"); //works infil
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
		
		
		//T-552: Buildings complete
		//We have built 2868 Cash factory on 183,85:4.
		//T-430	Infrastructure	We have built 1 Laser on 251,196:9.	
		Pattern buildingPattern = Pattern.compile("(?s)\\s(\\d+) +T-(\\d+)[:\\s]+[Infrastructure|Buildings complete]+[\\s]+We have built (\\d+) ([\\w+ ]+) on (\\d+,\\d+:\\d+)."); //works with both
		buildingArray = ExtractData.extractBuildingData(buildingPattern, infil);
		String buildingReport = Reporting.countAndPrintFrequenciesBuildings(buildingArray);
		report = Reporting.appendString(report,buildingReport);
		String laserReport = Reporting.laserReport(buildingArray);
		report = Reporting.appendString(report,laserReport);
		
		
		return report;
		}
	
	
}
