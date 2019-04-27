package Application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import FamilyNews.AidNews;
import FamilyNews.PlanetNews;
import Formatter.PlanetFormater;
import recentReports.Units;

public class NewsAnalyser {
	//Regex for extracting data
	public static String playerNameRegex = "([\\w+\\s*\\w*]*)";
	public static String planetRegex = " planet (\\d+) in the (\\d+),(\\d+) system";
	public static String lineRegx = "(\\d+) ";
	public static String eventTick = "(\\w+)[\\s]+"+lineRegx+"T-(\\d{1,4})[\\s]+";
	
	public static void main(String[] args) throws IOException {
		//sample reports for debug only
		String famNews = readFileLineByLine("famNews5.txt");
		//String infil = readFileLineByLine("infil3.txt");
		String infil = readFileLineByLine("RecentReport.txt");
		String planetList = readFileLineByLine("planetList2.txt");
		
		
		//print results in Console
		String debugConsole = runFamNewsAnalyser(famNews);
		//String debugConsole = runRecentReportAnalyser(infil);
		//String debugConsole=  PlanetFormater.runPlanetFormaterr(planetList); 
		
		
		debugConsole = debugConsole.replace("<br>","\r\n");
		debugConsole = debugConsole.replace("&nbsp","\t");
		System.out.println(debugConsole);
	
	}
	
	public static String runFamNewsAnalyser(String famNews) {
		
		famNews = addLineNumber(famNews);
		String report = PlanetNews.reportPlanetSections(famNews);
		String reportAid = AidNews.reportAidSections(famNews);
		
		report = Reporting.appendString(report,reportAid);
		return report;
	}
	
	public static String runRecentReportAnalyser(String infil) {
		
		infil = addLineNumber(infil);
		String report = reportRecentReport(infil);
		
		return report;
	}
	
	public static String reportRecentReport(String infil) {
		String report = "";
		ArrayList<Units> unitArray = new ArrayList<Units>();
		ArrayList<PlanetNews> exploreArray = new ArrayList<PlanetNews>();
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
		Pattern explorePattern = Pattern.compile("(?s)\\s()(\\d+) +T-(\\d+)()[:\\s]+[Exploration|Explored a planet]+[\\s]+We have explored the planet (\\d+) in the (\\d+):(\\d+) system.()()"); //works with both
		unitArray = ExtractData.extractUnitData(unitPattern, infil);
		String unitReport = Reporting.countAndPrintFrequenciesUnits(unitArray);
		report = Reporting.appendString(report,unitReport);
		
		exploreArray = ExtractData.extractPlanetData(explorePattern, infil);
		Reporting.printArray(exploreArray);
		String exploreReport = Reporting.printSummaryPlanets(exploreArray, "Explored");
		report = Reporting.appendString(report,exploreReport);
		String exploreList = Reporting.printArrayExplored(exploreArray);	
		report = Reporting.appendString(report,exploreList);
		
		//T-552: Buildings complete
		//We have built 2868 Cash factory on 183,85:4.
		//T-430	Infrastructure	We have built 1 Laser on 251,196:9.	
		Pattern buildingPattern = Pattern.compile("(?s)\\s(\\d+) +T-(\\d+)[:\\s]+[Infrastructure|Buildings complete]+[\\s]+We have explored the planet (\\d+) in the (\\d+):(\\d+) system."); //works with both
		
		return report;
		}
	
		
	private static String readFileLineByLine(String filePath)
	{
		//AtomicInteger i = new AtomicInteger();
  	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
	    {
	    	//stream.forEach(s -> contentBuilder.append(i.getAndIncrement()).append(" "+s).append("\n"));
	    	stream.forEach(s -> contentBuilder.append(" "+s).append("\n"));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
	public static String addLineNumber(String famNews) {		
		int count = 1;
		String[] lines = famNews.replace("T-","# T-").split("#");
		//String[] lines = famNews.split("T-");
		
		
		int rows = lines.length;
		String t = "";
        for (String line : lines) {
        	t = t + count++ +line;
        	}
		return t;
	}
	
}

   