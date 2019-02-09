import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FamilyNewsAnalyser {
	//input file
	static String famNews = readFileLineByLine("famNewsCash.txt");
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
		ArrayList<PlanetNews> blownArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> defeatsArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> exploreArray = new ArrayList<PlanetNews>();
		ArrayList<PlanetNews> retakesArray = new ArrayList<PlanetNews>();
		
		Pattern explorePattern = Pattern.compile("(?s)"+lineRegx+eventTick+playerNameRegex+" explored"+planetRegex+"()()");		
		Pattern capturePattern = Pattern.compile("(?s)"+lineRegx+eventTick+"The forces of "+playerNameRegex+" took"+planetRegex+" from "+playerNameRegex+" .(\\d+)+..");	
		Pattern defeatPattern = Pattern.compile("(?s)"+lineRegx+eventTick+"After a brave fight our family member "+playerNameRegex+" had to flee the planet"+planetRegex+" which was attacked by "+playerNameRegex+" of family (\\d+)+.");
		Pattern blownSAPattern = Pattern.compile("(?s)"+lineRegx+eventTick+playerNameRegex+" attacked "+playerNameRegex+" .(\\d+). on"+planetRegex+", and the heavy battle made the planet uninhabitable; an exploration ship will have to be sent there.");
		
		//explored
		exploreArray = ExtractData.extractPlanetData(explorePattern, famNews);
		Reporting.printSummaryPlanets(exploreArray, "Explored");
		
		//Capture
		captureArray = ExtractData.extractPlanetData(capturePattern, famNews);
		Reporting.printSummaryPlanets(captureArray, "Captures");
		
		//blow ups
		blownArray =ExtractData.extractDataBlown(blownSAPattern, famNews);
		Reporting.printSummaryPlanets(blownArray, "blow up");
		
		//Defeat
		defeatsArray = ExtractData.extractPlanetData(defeatPattern, famNews);
		Reporting.printSummaryPlanets(defeatsArray, "Defeats");
		
		if (blownArray != null)
			captureArray.addAll( blownArray );

		retakesArray = Reporting.findOpenRetakes(captureArray, defeatsArray);
		Collections.sort(retakesArray);
		Reporting.printSummaryPlanets(retakesArray, "missing");
		Reporting.printOpenRetakes(retakesArray);
		
	}
	
	//todo
	public static void reportAidSections() {
		ArrayList<AidNews> aidArray = new ArrayList<AidNews>();
		//ArrayList<AidNews> aidArray2 = new ArrayList<AidNews>();
		
		//A	T-690		In the name of family cooperation TIF has sent a shipment of 20000000 Cash to Blood Eagle.
		//A	T-667		In the name of family cooperation TIF has sent a shipment of 4019148 Cash 263956 Octarine to Biscuit.
		//A	T-666		In the name of family cooperation Who has sent a shipment of 10608984 Food 217158 Iron 67233 Octarine 85800 Endurium to TIF.
		//A	T-643		In the name of family cooperation TIF has sent a shipment of 15077245 Cash 12495581 Iron 8776 Octarine to Biscuit.
		
		Pattern aidPattern1 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+"has sent a shipment of (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern2 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+"has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern3 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+"has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern4 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+"has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		Pattern aidPattern5 = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation "+playerNameRegex+"has sent a shipment of (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) (\\d+) (\\w+) to "+playerNameRegex+".");
		aidArray   = ExtractData.extractAid1(aidPattern1, famNews);
		//aidArray.addAll(extractAid2(aidPattern2, famNews));
		//aidArray2 = ExtractData.extractAid2(aidPattern2, famNews);
		aidArray.addAll(ExtractData.extractAid2(aidPattern2, famNews));
		aidArray.addAll(ExtractData.extractAid3(aidPattern3, famNews));
		aidArray.addAll(ExtractData.extractAid4(aidPattern4, famNews));
		aidArray.addAll(ExtractData.extractAid5(aidPattern5, famNews));
		
		
		Reporting.printSummaryAid(aidArray, "aid");
	}


	
	private static String readFileLineByLine(String filePath)
	{
  	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
	    {
	    	stream.forEach(s -> contentBuilder.append(i++).append(" "+s).append("\n"));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
}
   