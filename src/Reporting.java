
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Reporting {

	public static String printOpenRetakes(ArrayList<PlanetNews> retakesArray) {
		String outReport = "";
		outReport = appendString(outReport,"<br>--------------------<br>OPEN RETAKES<br>--------------------");
	for (PlanetNews retake : retakesArray) {
		outReport =  appendString(outReport, "<br>"+ retake.getPlanetCoords()+" (#"+retake.getEnemyFam()+", lost Tick "+retake.getTurnOccurred()+")");
		}
		
	return outReport;
	}
	
	public static String printOpenRetakesClean(ArrayList<PlanetNews> retakesArray) {
		String outReport = "";
		outReport = appendString(outReport,"<br>--------------------<br>OPEN RETAKES Clean List<br>--------------------");
	int i = 0;
	for (PlanetNews retake : retakesArray) {
		outReport =  appendString(outReport, "<br>"+ retake.getPlanetCoords());
		i++;

	}
	outReport =  appendString(outReport, "<br>-------------------<br> "+retakesArray.size() + " planet(s) missing in action<br>");
	return outReport;
	}
		
	public static String printSummaryPlanets(ArrayList<PlanetNews> news, String text) {		
		String outReport = "";
		outReport = appendString(outReport,"<br>--------------------<br>&nbsp&nbsp&nbsp "+text+"&nbsp&nbsp&nbsp<br>--------------------");
		outReport = appendString(outReport,(countAndPrintFrequenciesPlanets(news, " planet(s) "+text)));
		outReport =  appendString(outReport, "<br>-------------------<br> "+news.size() + " planet(s) "+text+".<br>");
		return outReport;
	}
	
	public static String printSummaryAidSent(ArrayList<AidNews> news, String text) {		
		String outReport = "";
		
		ArrayList<AidSummary> summary = new ArrayList<AidSummary>();
		outReport = appendString(outReport,"<br>--------------------<br>-     Aid Sent     -<br>--------------------<br>");
		summary = AidNews.sumSentAid(news);
		
		int size = summary.size();
		if (size == 0) {
			
		}
		else {
		
		outReport =  appendString(outReport, summary.get(0).getFamMember()+ " sent "+summary.get(0).getResource()+ " "+summary.get(0).getAmount());
		//System.out.print(summary.get(0).getFamMember()+ " sent "+summary.get(0).getResource()+ " "+summary.get(0).getAmount());
		for (int x = 1; x < summary.size(); x++) {
			if (summary.get(x).getFamMember().equals(summary.get(x-1).getFamMember())) {
				outReport =  appendString(outReport, " "+summary.get(x).getResource()+ " "+summary.get(x).getAmount());
				//System.out.print(" "+summary.get(x).getResource()+ " "+summary.get(x).getAmount());
			}
			else {
				outReport =  appendString(outReport, "<br>"+summary.get(x).getFamMember()+ " sent "+summary.get(x).getResource()+ " "+summary.get(x).getAmount());
				//System.out.print("\n"+summary.get(x).getFamMember()+ " sent "+summary.get(x).getResource()+ " "+summary.get(x).getAmount());
			}
		}
		}
		return outReport;

	}
		
		public static String printSummaryAidReceived(ArrayList<AidNews> news, String text) {		
			String outReport = "";
			ArrayList<AidSummary> receivedSummary = new ArrayList<AidSummary>();
			outReport = appendString(outReport,"<br>--------------------<br>-     Aid Received     -<br>--------------------<br>");
			//System.out.println("\r\n-------------------\r\n" + "-  Aid Received   -\r\n" + "-------------------");
			receivedSummary = AidNews.sumAidReceived(news);
			int size = receivedSummary.size();
			if (size == 0) {
				
			}
			else {
			
			outReport =  appendString(outReport, receivedSummary.get(0).getFamMember()+ " received "+receivedSummary.get(0).getResource()+ " "+receivedSummary.get(0).getAmount());	
			//System.out.print(receivedSummary.get(0).getFamMember()+ " received "+receivedSummary.get(0).getResource()+ " "+receivedSummary.get(0).getAmount());
			for (int x = 1; x < receivedSummary.size(); x++) {
				if (receivedSummary.get(x).getFamMember().equals(receivedSummary.get(x-1).getFamMember())) {
					outReport =  appendString(outReport, " "+receivedSummary.get(x).getResource()+ " "+receivedSummary.get(x).getAmount());
					//System.out.print(" "+receivedSummary.get(x).getResource()+ " "+receivedSummary.get(x).getAmount());
				}
				else {
					outReport =  appendString(outReport, "<br>"+receivedSummary.get(x).getFamMember()+ " received "+receivedSummary.get(x).getResource()+ " "+receivedSummary.get(x).getAmount());
					//System.out.print("\n"+receivedSummary.get(x).getFamMember()+ " received "+receivedSummary.get(x).getResource()+ " "+receivedSummary.get(x).getAmount());
				}
			}
			}
			return outReport;
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
	
	public static ArrayList<PlanetNews> findOutstandingBlowPLanets(ArrayList<PlanetNews> captureArray, ArrayList<PlanetNews> blownEaArray, ArrayList<PlanetNews> exploreArray) {
		Boolean match = false;
		ArrayList<PlanetNews> retakesArray = new ArrayList<PlanetNews>();
		for (PlanetNews defeats : blownEaArray) {
				for (PlanetNews captures : captureArray) {
					if (defeats.getPlanetCoords().equals(captures.getPlanetCoords()) && captures.getLineNumber() < defeats.getLineNumber()) {
						match = true;
						break;
						}					
					}
				for (PlanetNews expo : exploreArray) {
					if (defeats.getPlanetCoords().equals(expo.getPlanetCoords()) && expo.getLineNumber() < defeats.getLineNumber()) {
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

	public static String countAndPrintFrequenciesPlanets(ArrayList<PlanetNews> newsArray, String text) {
		String outReport = "";
		//outReport = appendString(outReport,"<br>--------------------");
		
		
		Map<String, Integer> hm = new HashMap<String, Integer>();

		for (PlanetNews i : newsArray) {
			Integer j = hm.get(i.getFamMember());
			hm.put(i.getFamMember(), (j == null) ? 1 : j + 1);
		}
		// displaying the occurrence of elements in the arraylist
		for (Map.Entry<String, Integer> val : hm.entrySet()) {
			outReport = appendString(outReport,"<br>"+val.getValue() + " " + text + " " + "#" + val.getKey());
			//System.out.println(val.getValue() + " " + text + " " + "#" + val.getKey());
		}
		return outReport;
	}
	
	public static String printArray(ArrayList<PlanetNews> newsArray) {
		String outReport = "";
		outReport = appendString(outReport,"--------------------<br>- List of destroyed planets, not re-explored or retaken: ");
		//System.out.println("-------------------");
		//System.out.println("List of destroyed planets, not re-explored or retaken:");
		for (PlanetNews planetNews : newsArray) {
			outReport = appendString(outReport,"<br>"+planetNews.getPlanetCoords());
			//System.out.println(planetNews.getPlanetCoords());
		}
		return outReport;
	}
	
	public static String printArrayExplored(ArrayList<PlanetNews> newsArray) {
		String outReport = "";
		outReport = appendString(outReport,"--------------------<br>- List of explored planets ");
		for (PlanetNews planetNews : newsArray) {
			outReport = appendString(outReport,"<br>"+planetNews.getPlanetCoords()+" "+planetNews.getFamMember());
		}
		return outReport;
	}
	
	public static String appendString(String text1, String text2) {
		//System.out.println(text2);
		String combinedText = text1 + text2;
		return combinedText;
	}
	

	
	
	
}
