package Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import recentReports.RecentReports;
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
		String infil = readFileLineByLine("infil4.txt");
		//String infil = readFileLineByLine("RecentReport.txt");
		String planetList = readFileLineByLine("planetList2.txt");
		
		
		//print results in Console
		//String debugConsole = runFamNewsAnalyser(famNews);
		String debugConsole = runRecentReportAnalyser(infil);
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
		String report = RecentReports.reportRecentReport(infil);
		
		return report;
	}
	
	
		
	private static String readFileLineByLineZOLD(String filePath)
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
	private static String readFileLineByLine(String filePath)
		{
		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			//System.out.println("Contents of file:");
			return stringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return e.toString();
		}
		
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

   