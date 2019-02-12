import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Reporting {

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
		
	public static void printSummaryPlanets(ArrayList<PlanetNews> news, String text) {		
		System.out.println("-------------------\r\n" + "-    "+text+"    -\r\n" + "-------------------");
		countAndPrintFrequenciesPlanets(news, " planet(s) "+text);
		System.out.println("-------------------");
		System.out.println(news.size() + " planet(s) "+text+".");
	}
	
	public static void printSummaryAid(ArrayList<AidNews> news, String text) {		
		ArrayList<AidSummary> summary = new ArrayList<AidSummary>();
		System.out.println("-------------------\r\n" + "-    "+text+"    -\r\n" + "-------------------");
		summary = AidNews.sumSentAid(news);
		
		
//		Print aid summary, for loop read first name, print name and first resource and and amount, read next line 
//		compare name if same add to same line
	// if different add new line print name repeat
		System.out.print(summary.get(0).getFamMember()+ " sent "+summary.get(0).getResource()+ " "+summary.get(0).getAmount());
		for (int x = 1; x < summary.size(); x++) {
			if (summary.get(x).getFamMember().equals(summary.get(x-1).getFamMember())) {
				System.out.print(" "+summary.get(x).getResource()+ " "+summary.get(x).getAmount());
			}
			else {
				System.out.print("\n"+summary.get(x).getFamMember()+ " sent "+summary.get(x).getResource()+ " "+summary.get(x).getAmount());
			}
				
		}
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

	public static void countAndPrintFrequenciesPlanets(ArrayList<PlanetNews> newsArray, String text) {
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
	

	
	
	
}
