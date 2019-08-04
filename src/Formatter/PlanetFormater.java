package Formatter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlanetFormater {
	
	
public static String runPlanetFormaterr(String planetList) {
	    Pattern standardPlanetPattern = Pattern.compile("(?s)(\\d+,\\d+:\\d+)");
	    String report = formatStandardList(standardPlanetPattern, planetList);	
	    
	    Pattern newsPlanetPattern = Pattern.compile("(?s) planet (\\d+) in the (\\d+)[,:](\\d+) system");
	    report = report +formatNewsList(newsPlanetPattern, planetList);	
	    
	    //Our planet 11 at x:68, y:147
	    Pattern newsLostPlanetPattern = Pattern.compile("(?s)Our planet (\\d+) at x:(\\d+), y:(\\d+)");
	    report = report +formatNewsList(newsLostPlanetPattern, planetList);	
	    
		return report;
	}

public static String formatStandardList(Pattern planetPattern, String famNews) {
	Matcher news = planetPattern.matcher(famNews);
	String report = "";
	while (news.find()) {
		String planet = news.group(1);
		report = report+planet+"<br>";}
	return report;
	}
	
public static String formatNewsList(Pattern planetPattern, String famNews) {
		Matcher news = planetPattern.matcher(famNews);
		String report = "";
		while (news.find()) {
			int planetNo = Integer.parseInt(news.group(1));
			int planetX = Integer.parseInt(news.group(2));
			int planetY = Integer.parseInt(news.group(3));
			String planetCoords = (planetX + "," + planetY + ":" + planetNo);
			report = report+planetCoords+"<br>";
		}
	return report;
}

}
