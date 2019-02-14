import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FamilyNewsAnalyser {
	//input file
	static String famNews = readFileLineByLine("famNews5.txt");
	static int i = 0;
	//Regex for extracting data
	static String playerNameRegex = "([\\w+\\s*\\w*]*)";
	static String planetRegex = " planet (\\d+) in the (\\d+),(\\d+) system";
	static String lineRegx = "(\\d+) ";
	static String eventTick = "(\\w+)[\\s]+T-(\\d{1,4})[\\s]+";
	
	public static void main(String[] args) throws IOException {
		reportPlanetSections();
		reportAidSections();
	}
	
	public static void reportPlanetSections() {
		ArrayList<PlanetNews> captureArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> blownSaArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> blownEaArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> defeatsArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> exploreArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> retakesArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> lostBlownArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> missingArray = new ArrayList<PlanetNews>();
		
		Pattern explorePattern = Pattern.compile("(?s)"+lineRegx+eventTick+playerNameRegex+" explored"+planetRegex+"()()");		
		Pattern capturePattern = Pattern.compile("(?s)"+lineRegx+eventTick+"The forces of "+playerNameRegex+" took"+planetRegex+" from "+playerNameRegex+" .(\\d+)+..");	
		Pattern defeatPattern = Pattern.compile("(?s)"+lineRegx+eventTick+"After a brave fight our family member "+playerNameRegex+" had to flee the planet"+planetRegex+" which was attacked by "+playerNameRegex+" of family (\\d+)+.");
		Pattern blownSAPattern = Pattern.compile("(?s)"+lineRegx+eventTick+playerNameRegex+" attacked "+playerNameRegex+" .(\\d+). on"+planetRegex+", and the heavy battle made the planet uninhabitable; an exploration ship will have to be sent there.");
		Pattern blownEAPattern = Pattern.compile("(?s)"+lineRegx+eventTick+"An overwhelming force from "+playerNameRegex+", family (\\d+) attacked "+playerNameRegex+"'s"+planetRegex+". The defenders for "+playerNameRegex+" managed to set off a nuclear blast which made the planet uninhabitable.");
		
		//explored
		exploreArray = ExtractData.extractPlanetData(explorePattern, famNews);
		Reporting.printSummaryPlanets(exploreArray, "Explored");
		
		//Capture
		captureArray = ExtractData.extractPlanetData(capturePattern, famNews);
		Reporting.printSummaryPlanets(captureArray, "Captures");
		
		//blow ups Attacks
		blownSaArray =ExtractData.extractDataBlownSA(blownSAPattern, famNews);
		Reporting.printSummaryPlanets(blownSaArray, "blow ups");
	
		//
		defeatsArray = ExtractData.extractPlanetData(defeatPattern, famNews);
		Reporting.printSummaryPlanets(defeatsArray, "Defeats");
		
		//blow ups defeats
		blownEaArray =ExtractData.extractDataBlownEA(blownEAPattern, famNews);
		lostBlownArray = Reporting.findOutstandingBlowPLanets(captureArray, blownEaArray, exploreArray);
		
		Reporting.printSummaryPlanets(blownEaArray, "blow ups lost");
		Reporting.printArray(lostBlownArray);
		if (lostBlownArray != null)
			missingArray.addAll( lostBlownArray );
		
		if (blownSaArray != null)
			captureArray.addAll( blownSaArray );
		
		Collections.sort(retakesArray);
		retakesArray = Reporting.findOpenRetakes(captureArray, defeatsArray);

		if (retakesArray != null)
			missingArray.addAll( retakesArray );
		
		
		Reporting.printSummaryPlanets(missingArray, "missing");
		Reporting.printOpenRetakes(retakesArray);
		
		Reporting.printOpenRetakesClean(retakesArray);
		}
	
	//todo
	public static void reportAidSections() {
		ArrayList<AidNews> aidArray = new ArrayList<AidNews>();
		
		Pattern aidPattern1 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern2 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern3 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern4 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern5 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+" has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		aidArray   = ExtractData.extractAid1(aidPattern1, famNews);
		aidArray.addAll(ExtractData.extractAid2(aidPattern2, famNews));
		aidArray.addAll(ExtractData.extractAid3(aidPattern3, famNews));
		aidArray.addAll(ExtractData.extractAid4(aidPattern4, famNews));
		aidArray.addAll(ExtractData.extractAid5(aidPattern5, famNews));
		
		Reporting.printSummaryAidSent(aidArray, "aid");
		Reporting.printSummaryAidReceived(aidArray, "aid");
	}


	
	private static String readFileLineByLine(String filePath)
	{
		AtomicInteger i = new AtomicInteger();
  	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
	    {
	    	stream.forEach(s -> contentBuilder.append(i.getAndIncrement()).append(" "+s).append("\n"));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
}
   