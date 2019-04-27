
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

public class NewsAnalyser {
	//input file
	//static int i = 0;
	//Regex for extracting data
	static String playerNameRegex = "([\\w+\\s*\\w*]*)";
	static String planetRegex = " planet (\\d+) in the (\\d+),(\\d+) system";
	static String lineRegx = "(\\d+) ";
	static String eventTick = "(\\w+)[\\s]+"+lineRegx+"T-(\\d{1,4})[\\s]+";
	
	public static void main(String[] args) throws IOException {
		//sample reports for debug only
		String famNews = readFileLineByLine("famNews5.txt");
		//String infil = readFileLineByLine("infil3.txt");
		String infil = readFileLineByLine("RecentReport.txt");
		String planetList = readFileLineByLine("planetList2.txt");
		
		
		//print results in Console
		//String debugConsole = runFamNewsAnalyser(famNews);
		//String debugConsole = runRecentReportAnalyser(infil);
		String debugConsole=  PlanetFormaterr.runPlanetFormaterr(planetList); 
		
		
		debugConsole = debugConsole.replace("<br>","\r\n");
		debugConsole = debugConsole.replace("&nbsp","\t");
		System.out.println(debugConsole);
	
	}
	
	public static String runFamNewsAnalyser(String famNews) {
		
		famNews = addLineNumber(famNews);
		String report = reportPlanetSections(famNews);
		String reportAid =reportAidSections(famNews);
		
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
		
		
		return report;
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
		String captureReport = Reporting.printSummaryPlanets(captureArray, "Captures");
		//System.out.println(captureReport);
		report = Reporting.appendString(report,captureReport);
		//String capList = Reporting.printArrayExplored(captureArray);	
		//report = Reporting.appendString(report,capList);
		
		defeatsArray = ExtractData.extractPlanetData(defeatPattern, famNews);
		String defeatsReport = Reporting.printSummaryPlanets(defeatsArray, "Defeats");
		//System.out.println(defeatsReport);
		report = Reporting.appendString(report,defeatsReport);
	
		//blow ups Attacks
		blownSaArray =ExtractData.extractDataBlownSA(blownSAPattern, famNews);
		String blownSaReport = Reporting.printSummaryPlanets(blownSaArray, "blown ups by");
		//System.out.println(blownSaReport);
		report = Reporting.appendString(report,blownSaReport);
			
		//blow ups defeats
		blownEaArray =ExtractData.extractDataBlownEA(blownEAPattern, famNews);
		lostBlownArray = Reporting.findOutstandingBlowPLanets(captureArray, blownEaArray, exploreArray);
		String lostBlownReport = Reporting.printSummaryPlanets(blownEaArray, "blown ups lost");
		//System.out.println(lostBlownReport);
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
	

	public static String reportAidSections(String famNews) {
		String report = "";
		ArrayList<AidNews> aidArray = new ArrayList<AidNews>();
		
		Pattern aidPattern1 = Pattern.compile("(?s)"+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern2 = Pattern.compile("(?s)"+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern3 = Pattern.compile("(?s)"+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern4 = Pattern.compile("(?s)"+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern5 = Pattern.compile("(?s)"+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		aidArray   = ExtractData.extractAid1(aidPattern1, famNews);
		aidArray.addAll(ExtractData.extractAid2(aidPattern2, famNews));
		aidArray.addAll(ExtractData.extractAid3(aidPattern3, famNews));
		aidArray.addAll(ExtractData.extractAid4(aidPattern4, famNews));
		aidArray.addAll(ExtractData.extractAid5(aidPattern5, famNews));
		
		String aidSentReport = Reporting.printSummaryAidSent(aidArray, "aid");
		report = Reporting.appendString(report,aidSentReport);
		//System.out.println(aidSentReport);
		
		
		String aidReceivedReport = Reporting.printSummaryAidReceived(aidArray, "aid");
		report = Reporting.appendString(report,aidReceivedReport);
		//System.out.println(aidReceivedReport);
		
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

   