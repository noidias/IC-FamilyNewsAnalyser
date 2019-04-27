
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractData {

	public static ArrayList<PlanetNews> extractPlanetData(Pattern planetPattern, String famNews) {
		ArrayList<PlanetNews> newsArray = new ArrayList<PlanetNews>();
		Matcher news = planetPattern.matcher(famNews);
		while (news.find()) {
			
			
			//int line = Integer.parseInt(news.group(2));
			int line = getIntValue(news,2);
			String event = news.group(1);
			//int turn = Integer.parseInt(news.group(3));
			int turn = getIntValue(news,3);
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
	
	public static ArrayList<AidNews> extractAid1(Pattern aidPattern, String famNews) {
		ArrayList<AidNews> newsArray = new ArrayList<AidNews>();
		Matcher news = aidPattern.matcher(famNews);
		while (news.find()) {
			int line = getIntValue(news, 2);
			String event = getStringValue(news, 1);
			int turn = getIntValue(news, 3);
			String player = getStringValue(news, 4);
			int amount = getIntValue(news, 5);
			String resource = getStringValue(news, 6);
			String receipient = getStringValue(news, 7);
			AidNews nextLineOfNews = new AidNews(line, event, turn, player, amount, resource, receipient);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}
	
	public static ArrayList<AidNews> extractAid2(Pattern aidPattern, String famNews) {
		ArrayList<AidNews> newsArray = new ArrayList<AidNews>();
		Matcher news = aidPattern.matcher(famNews);
		while (news.find()) {
			int line = getIntValue(news, 2);
			String event = getStringValue(news, 1);
			int turn = getIntValue(news, 3);
			String player = getStringValue(news, 4);
			int amount = getIntValue(news, 5);
			String resource = getStringValue(news, 6);
			int amount2 = getIntValue(news, 7);
			String resource2 = getStringValue(news, 8);
			String receipient = getStringValue(news, 9);
			AidNews nextLineOfNews = new AidNews(line, event, turn, player, amount, resource, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount2, resource2, receipient);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}
	
	public static ArrayList<AidNews> extractAid3(Pattern aidPattern, String famNews) {
		ArrayList<AidNews> newsArray = new ArrayList<AidNews>();
		Matcher news = aidPattern.matcher(famNews);
		while (news.find()) {
			int line = getIntValue(news, 2);
			String event = getStringValue(news, 1);
			int turn = getIntValue(news, 3);
			String player = getStringValue(news, 4);
			int amount = getIntValue(news, 5);
			String resource = getStringValue(news, 6);
			int amount2 = getIntValue(news, 7);
			String resource2 = getStringValue(news, 8);
			int amount3 = getIntValue(news, 9);
			String resource3 = getStringValue(news, 10);
			String receipient = getStringValue(news, 11);
			AidNews nextLineOfNews = new AidNews(line, event, turn, player, amount, resource, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount2, resource2, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount3, resource3, receipient);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}
	
	public static ArrayList<AidNews> extractAid4(Pattern aidPattern, String famNews) {
		ArrayList<AidNews> newsArray = new ArrayList<AidNews>();
		Matcher news = aidPattern.matcher(famNews);
		while (news.find()) {
			int line = getIntValue(news, 2);
			String event = getStringValue(news, 1);
			int turn = getIntValue(news, 3);
			String player = getStringValue(news, 4);
			int amount = getIntValue(news, 5);
			String resource = getStringValue(news, 6);
			int amount2 = getIntValue(news, 7);
			String resource2 = getStringValue(news, 8);
			int amount3 = getIntValue(news, 9);
			String resource3 = getStringValue(news, 10);
			int amount4 = getIntValue(news, 11);
			String resource4 = getStringValue(news, 12);
			String receipient = getStringValue(news, 13);
			AidNews nextLineOfNews = new AidNews(line, event, turn, player, amount, resource, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount2, resource2, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount3, resource3, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount4, resource4, receipient);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}
	
	public static ArrayList<AidNews> extractAid5(Pattern aidPattern, String famNews) {
		ArrayList<AidNews> newsArray = new ArrayList<AidNews>();
		Matcher news = aidPattern.matcher(famNews);
		while (news.find()) {
			int line = getIntValue(news, 2);
			String event = getStringValue(news, 1);
			int turn = getIntValue(news, 3);
			String player = getStringValue(news, 4);
			int amount = getIntValue(news, 5);
			String resource = getStringValue(news, 6);
			int amount2 = getIntValue(news, 7);
			String resource2 = getStringValue(news, 8);
			int amount3 = getIntValue(news, 9);
			String resource3 = getStringValue(news, 10);
			int amount4 = getIntValue(news, 11);
			String resource4 = getStringValue(news, 12);
			int amount5 = getIntValue(news, 13);
			String resource5 = getStringValue(news, 14);
			String receipient = getStringValue(news, 15);
			AidNews nextLineOfNews = new AidNews(line, event, turn, player, amount, resource, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount2, resource2, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount3, resource3, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount4, resource4, receipient);
			newsArray.add(nextLineOfNews);
			nextLineOfNews = new AidNews(line, event, turn, player, amount5, resource5, receipient);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}
	
	
	public static int getIntValue(Matcher news, int groupNumber) {
		int value;
		if (news.group(groupNumber) == "")
			{value = 0;}
		else {
			value = Integer.parseInt(news.group(groupNumber));
			}
		
		return value;
	}
	
	public static String getStringValue(Matcher news, int groupNumber) {
		String value = news.group(groupNumber);
		return value;
	}
	
	public static ArrayList<PlanetNews> extractDataBlownSA(Pattern planetPattern, String famNews) {
		ArrayList<PlanetNews> newsArray = new ArrayList<PlanetNews>();
		Matcher news = planetPattern.matcher(famNews);
		while (news.find()) {
			int line = Integer.parseInt(news.group(2));
			String event = news.group(1);
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
	
	public static ArrayList<PlanetNews> extractDataBlownEA(Pattern planetPattern, String famNews) {
		//Pattern blownEAPattern = Pattern.compile("(?s)"+lineRegx+eventTick+"An overwhelming force from "+playerNameRegex+", family (\\d+) attacked "+playerNameRegex+planetRegex+". The defenders for "+playerNameRegex+" managed to set off a nuclear blast which made the planet uninhabitable.");
		ArrayList<PlanetNews> newsArray = new ArrayList<PlanetNews>();
		Matcher news = planetPattern.matcher(famNews);
		while (news.find()) {
			int line = Integer.parseInt(news.group(2));
			String event = news.group(1);
			int turn = Integer.parseInt(news.group(3));
			String player = news.group(10);
			int planetNo = Integer.parseInt(news.group(7));
			int planetX = Integer.parseInt(news.group(8));
			int planetY = Integer.parseInt(news.group(9));
			String planetCoords = (planetX + "," + planetY + ":" + planetNo);
			String enemy = news.group(5);
			String family = news.group(4);
			PlanetNews nextLineOfNews = new PlanetNews(line, event, turn, player, planetCoords, family, enemy);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}

	public static ArrayList<Units> extractUnitData(Pattern unitPattern, String infil) {
		ArrayList<Units> newsArray = new ArrayList<Units>();
		Matcher news = unitPattern.matcher(infil);
		while (news.find()) {
			int line = Integer.parseInt(news.group(1));
			int amount = Integer.parseInt(news.group(2));
			String units = news.group(3);
			Units nextLineOfNews = new Units(line, amount, units);
			newsArray.add(nextLineOfNews);
		}
		return newsArray;
	}
	
	

		
	
	
	
}
