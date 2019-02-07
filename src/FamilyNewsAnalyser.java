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
	static String famNews = readFileLineByLine("famNews3.txt");
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
		exploreArray = extractData(explorePattern, famNews);
		printSummary(exploreArray, "Explored");
		
		//Capture
		captureArray = extractData(capturePattern, famNews);
		printSummary(captureArray, "Captures");
		
		//blow ups
		blownArray =extractDataBlown(blownSAPattern, famNews);
		printSummary(blownArray, "blow up");
		
		//Defeat
		defeatsArray = extractData(defeatPattern, famNews);
		printSummary(defeatsArray, "Defeats");
		
		if (blownArray != null)
			captureArray.addAll( blownArray );

		retakesArray = findOpenRetakes(captureArray, defeatsArray);
		Collections.sort(retakesArray);
		printSummary(retakesArray, "missing");
		printOpenRetakes(retakesArray);
		
	}
	
	//todo
	public static void reportAidSections() {
		ArrayList<AidNews> aidArray = new ArrayList<AidNews>();
		
		//A	T-690		In the name of family cooperation TIF has sent a shipment of 20000000 Cash to Blood Eagle.
		Pattern aidPattern = Pattern.compile("(?s)"+lineRegx+eventTick+"In the name of family cooperation  "+playerNameRegex+"has sent a shipment of (\\d+) (\\w+) to "+playerNameRegex+".");	
		aidArray = extractAid(aidPattern, famNews);
		//printSummaryAid(aidArray, "aid");
	}

	public static void printOpenRetakes(ArrayList<PlanetNews> retakesArray) {
	System.out.println("--------------------\r\n" + 
			"-   OPEN RETAKES   -\r\n" + 
			"--------------------");
	for (PlanetNews retake : retakesArray) {
		System.out.println(retake.getPlanetCoords()+" (#"+retake.getEnemyFam()+", lost Tick "+retake.getTurnOccurred()+")");
		//System.out.println(retake.getPlanetCoords()+" (#"+retake.getEnemyFam()+", "+retake.getTurnOccurred()+" week(s) ago)");
		}
	System.out.println("-------------------");
	System.out.println(retakesArray.size() + " planet(s) missing in action");		
	
	}
	
	public static ArrayList<PlanetNews> extractData(Pattern planetPattern, String famNews) {
		ArrayList<PlanetNews> newsArray = new ArrayList<PlanetNews>();
		Matcher news = planetPattern.matcher(famNews);
		while (news.find()) {
			int line = Integer.parseInt(news.group(1));
			String event = news.group(2);
			int turn = Integer.parseInt(news.group(3));
			String player = news.group(4);
			int planetNo = Integer.parseInt(news.group(5));
			int planetX = Integer.parseInt(news.group(6));
			int planetY = Integer.parseInt(news.group(7));
			String planetCoords = (planetX + "," + planetY + ":" + planetNo);
			String enemy = news.group(8);
			String family = news.group(9);
			PlanetNews nextLineOfNews = new PlanetNews(line, event, turn, player, planetCoords, family, enemy);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}
	
	public static ArrayList<AidNews> extractAid(Pattern aidPattern, String famNews) {
		ArrayList<AidNews> newsArray = new ArrayList<AidNews>();
		Matcher news = aidPattern.matcher(famNews);
		while (news.find()) {
			int line = Integer.parseInt(news.group(1));
			String event = news.group(2);
			int turn = Integer.parseInt(news.group(3));
			String player = news.group(4);
			int amount = Integer.parseInt(news.group(5));
			String resource = news.group(6);
			String receipient = news.group(6);
			AidNews nextLineOfNews = new AidNews(line, event, turn, player, amount, resource, receipient);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}

	public static ArrayList<PlanetNews> extractDataBlown(Pattern planetPattern, String famNews) {
		ArrayList<PlanetNews> newsArray = new ArrayList<PlanetNews>();
		Matcher news = planetPattern.matcher(famNews);
		while (news.find()) {
			int line = Integer.parseInt(news.group(1));
			String event = news.group(2);
			int turn = Integer.parseInt(news.group(3));
			String player = news.group(4);
			int planetNo = Integer.parseInt(news.group(7));
			int planetX = Integer.parseInt(news.group(8));
			int planetY = Integer.parseInt(news.group(9));
			String planetCoords = (planetX + "," + planetY + ":" + planetNo);
			String enemy = news.group(5);
			String family = news.group(6);
			PlanetNews nextLineOfNews = new PlanetNews(line, event, turn, player, planetCoords, family, enemy);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}
	
	
	public static void printSummary(ArrayList<PlanetNews> news, String text) {		
		System.out.println("-------------------\r\n" + "-    "+text+"    -\r\n" + "-------------------");
		countAndPrintFrequencies(news, " planet(s) "+text);
		System.out.println("-------------------");
		System.out.println(news.size() + " planet(s) "+text+".");
	}

	public static ArrayList<PlanetNews> findOpenRetakes(ArrayList<PlanetNews> captureArray, ArrayList<PlanetNews> defeatsArray) {
		Boolean match = false;
		ArrayList<PlanetNews> retakesArray = new ArrayList<PlanetNews>();
		for (PlanetNews defeats : defeatsArray) {
				for (PlanetNews captures : captureArray) {
					if (defeats.getPlanetCoords().equals(captures.getPlanetCoords()) && captures.getLineNumber() < defeats.getLineNumber()) {
						match = true;
						break;
						}
				}
				if (!match) {
					retakesArray.add(defeats);
				}		
				match = false;
			}
		return retakesArray;
	}	

	public static void countAndPrintFrequencies(ArrayList<PlanetNews> newsArray, String text) {
		Map<String, Integer> hm = new HashMap<String, Integer>();

		for (PlanetNews i : newsArray) {
			Integer j = hm.get(i.getFamMember());
			hm.put(i.getFamMember(), (j == null) ? 1 : j + 1);
		}
		// displaying the occurrence of elements in the arraylist
		for (Map.Entry<String, Integer> val : hm.entrySet()) {
			System.out.println(val.getValue() + " " + text + " " + "#" + val.getKey());
		}
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
   