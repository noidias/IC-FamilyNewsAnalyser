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

public class Main {

	static ArrayList<NewsItem> newsArray = new ArrayList<NewsItem>();
	static ArrayList<NewsItem> captureArray = new ArrayList<NewsItem>();
	static ArrayList<NewsItem> defeatsArray = new ArrayList<NewsItem>();
	static ArrayList<NewsItem> exploreArray = new ArrayList<NewsItem>();
	static ArrayList<NewsItem> retakeArray = new ArrayList<NewsItem>();
	static int i = 0;


	//input files
	static String famNews = readFileLineByLine("famNews3.txt");
		
	//Regex
	static String playerNameRegex = "([\\w+\\s*\\w*]*)";
	static String turnRegex = "[\\s]+T-\\d{1,4}[\\s]+";
	static String planetRegex = " planet (\\d+) in the (\\d+),(\\d+) system";
	static String lineRegx = "(\\d+) ";
	static String eventTick = "(\\w+)[\\s]+T-(\\d{1,4})[\\s]+";
		
	public static void main(String[] args) throws IOException {
	//Pattern planetPattern = Pattern.compile("(?s)E	T-\\d{1,4}		([\\w+\\s*\\w*]*) explored planet (\\d+) in the (\\d+),(\\d+) system.");
	Pattern explorePattern = Pattern.compile("(?s)"+lineRegx+eventTick+playerNameRegex+" explored"+planetRegex+"()()");		
	Pattern capturePattern = Pattern.compile("(?s)"+lineRegx+eventTick+"The forces of "+playerNameRegex+" took"+planetRegex+" from "+playerNameRegex+" .(\\d+)+..");	
	Pattern defeatPattern = Pattern.compile("(?s)"+lineRegx+eventTick+"After a brave fight our family member "+playerNameRegex+" had to flee the planet"+planetRegex+" which was attacked by "+playerNameRegex+" of family (\\d+)+.");
	
	//explored
	exploreArray = extractData(explorePattern, famNews);
	printSummaryByEventObject(exploreArray, "Explored");
	
	//Capture
	captureArray = extractData(capturePattern, famNews);
	printSummaryByEventObject(captureArray, "Captured");
	
	//Defeat
	defeatsArray = extractData(defeatPattern, famNews);
	printSummaryByEventObject(defeatsArray, "Defeated");
	//NewsItem.printArray(newsArray);
	
	newsArray = new ArrayList<NewsItem>();
	if (captureArray != null)
		newsArray.addAll( captureArray );
	if (defeatsArray!= null)
		newsArray.addAll( defeatsArray);
	
	//openRetakes();
	openRetakesV2(captureArray, defeatsArray);
	}		
			
	
	public static ArrayList<NewsItem> extractData(Pattern planetPattern, String famNews) {
		ArrayList<NewsItem> newsArray = new ArrayList<NewsItem>();
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
			NewsItem nextLineOfNews = new NewsItem(line, event, turn, player, planetCoords, family, enemy);
			newsArray.add(nextLineOfNews);
		}
		
		return newsArray;
	}
	
	public static void printSummaryByEventObject(ArrayList<NewsItem> news, String text) {
		Collections.sort(news);
		System.out.println("-------------------\r\n" + "-    "+text+"    -\r\n" + "-------------------");
		countFrequenciesObject(news, " planet(s) "+text+" by ");
		System.out.println("-------------------");
		System.out.println(news.size() + " planet(s) have been "+text+".");

	}

	public static void openRetakesV2(ArrayList<NewsItem> captureArray, ArrayList<NewsItem> defeatsArray) {
		//ArrayList<String> retakeArray = new ArrayList<String>();
		Boolean match = false;
		for (NewsItem defeats : defeatsArray) {
			System.out.println("defeat line " + defeats.getLineNumber()+ " "+ defeats.getPlanetCoords());
				for (NewsItem captures : captureArray) {
					
					//System.out.println("captures line " + captures.getLineNumber());
					if (defeats.getPlanetCoords().equals(captures.getPlanetCoords()) && captures.getLineNumber() < defeats.getLineNumber()) {
						System.out.println("retake " + captures.getLineNumber() + " "  + captures.getPlanetCoords());
						match = true;
						break;
						}
				}
				if (match) {
					System.out.println("retake ");
				}		
				else {
					retakeArray.add(defeats);
					System.out.println("open retake " +  defeats.getLineNumber() + " " + defeats.getPlanetCoords());
				}
				match = false;
			}
		
		int k = 0;
		System.out.println("--------------------\r\n" + 
				"-   OPEN RETAKES   -\r\n" + 
				"--------------------");
		for (NewsItem retake : retakeArray) {
			System.out.println(retake.getPlanetCoords()+" (#"+retake.getEnemyFam()+", lost Tick "+retake.getTurnOccurred()+")");
			k++;
			}
		System.out.println("-------------------");
		System.out.println(retakeArray.size() + " planet(s) missing in action");
	}	
	
	//TODO replace previous method
	public static void countFrequenciesObject(ArrayList<NewsItem> newsArray, String text) {
		Map<String, Integer> hm = new HashMap<String, Integer>();

		for (NewsItem i : newsArray) {
			Integer j = hm.get(i.getFamMember());
			hm.put(i.getFamMember(), (j == null) ? 1 : j + 1);
		}
		// displaying the occurrence of elements in the arraylist
		for (Map.Entry<String, Integer> val : hm.entrySet()) {

			System.out.println(val.getValue() + " " + text + " " + "#" + val.getKey());
		}
	}
	
	//TODO remove blown planets after adding
	
	public void printArray(ArrayList<NewsItem> newsArray) {
		for (NewsItem newsItem : newsArray) {
			System.out.println(newsItem.getLineNumber() + " " + newsItem.getNewsEvent() + " " + newsItem.getTurnOccurred() + " "
					+ newsItem.getFamMember() + " " + newsItem.getPlanetCoords() + " " + newsItem.getEnemyFam()+ " "
					+ newsItem.getenemyPlayer());
		}
	}
	
	public static int maxLineNumber() {
		int maxLine = 0;
		int currentLine = 0;
		for (NewsItem newsItem : newsArray) {
			currentLine = newsItem.getLineNumber();
			if (currentLine > maxLine) {
				maxLine = newsItem.getLineNumber();
			}
		}
		return maxLine;
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
   