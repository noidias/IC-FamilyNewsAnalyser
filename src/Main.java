import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

	//input files
	static String famNews = readFileLineByLine("famNews3.txt");
	static String planetScreen = readFileLineByLine("planetList.txt");
	static String famCouncilScreen = readFileLineByLine("famCouncil.txt");
	
	static int i = 0;
	
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
	NewsItem.extractAttackDefenceData(explorePattern, famNews);
	NewsItem.printSummaryByEvent("E");
	
	//Capture
	ArrayList<NewsItem> newsArray = NewsItem.extractAttackDefenceData(capturePattern, famNews);
	NewsItem.printSummaryByEvent("SA");
	
	//Defeat
	newsArray = NewsItem.extractAttackDefenceData(defeatPattern, famNews);
	NewsItem.printSummaryByEvent("EA");
	//NewsItem.printArray(newsArray);
	
	NewsItem.openRetakes();
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
   