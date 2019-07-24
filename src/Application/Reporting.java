package Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import FamilyNews.AidNews;
import FamilyNews.AidSummary;
import FamilyNews.PlanetNews;
import recentReports.Buildings;
import recentReports.Units;

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
	
	public static String countAndPrintFrequenciesUnits(ArrayList<Units> newsArray) {
		String outReport = "";
		outReport = appendString(outReport,"<br>--------------------<br>-     Unit Summary     -<br>--------------------<br>");
		
		Map<String, Integer> hm = new HashMap<String, Integer>();

		for (Units i : newsArray) {
			Integer j = hm.get(i.getUnitType());
			hm.put(i.getUnitType(), (j == null) ? i.getAmount() : j + i.getAmount());
		}
		// displaying the occurrence of elements in the arraylist
		for (Map.Entry<String, Integer> val : hm.entrySet()) {
			outReport = appendString(outReport,"<br>"+val.getValue() + " " + val.getKey() + " built");
			//System.out.println(val.getValue() + " " + text + " " + "#" + val.getKey());
		}
		return outReport;
	}
	
	public static String countAndPrintFrequenciesUnitsLost(ArrayList<Units> unitArray) {
		String outReport = "";
		outReport = appendString(outReport,"<br><br>--------------------<br>- Unit Lost Detailed Report  -<br>--------------------<br>");
		int previousTurn = 0;
		int soldiers = 0;
		int droids = 0;
		int fighter = 0;
		for (Units i : unitArray) {
			if (previousTurn == i.getTurnOccurred())
			{	
					if (i.getUnitType().equals("soldiers")) {
						soldiers = soldiers + i.getAmount();
					}
					if (i.getUnitType().equals("droids")) {
						droids = droids + i.getAmount();
					}
					if (i.getUnitType().equals("fighters")) {
						fighter = fighter + i.getAmount();
					}
			}
			else
			{
				if (soldiers > 0 || fighter > 0 || droids > 0)
				{
					outReport = appendString(outReport,"<br>"+fighter+ " Fighters "+soldiers+" Soldiers "+droids+ " Droids (T-" + previousTurn+")");
				}
				
				soldiers = 0;
				droids = 0;
				fighter = 0;
				previousTurn = i.getTurnOccurred();
				if (i.getUnitType().equals("soldiers")) {	
					soldiers = soldiers + i.getAmount();
				//outReport = appendString(outReport,"<br>"+i.getAmount()+ " Soldiers Lost (T-" + i.getTurnOccurred()+")");
				}
				if (i.getUnitType().equals("droids")) {
					droids = droids + i.getAmount();
				//outReport = appendString(outReport,"<br>"+i.getAmount()+ " Droids Lost (T-" + i.getTurnOccurred()+")");
				}
				if (i.getUnitType().equals("fighters")) {
					fighter = fighter + i.getAmount();
				//outReport = appendString(outReport,"<br>"+i.getAmount()+ " Fighters Lost (T-" + i.getTurnOccurred()+")");
				}
			}			
		}
		if (soldiers > 0 || fighter > 0 || droids > 0)
			{outReport = appendString(outReport,"<br>"+fighter+ " Fighters "+soldiers+" Soldiers "+droids+ " Droids (T-" + previousTurn+")");}
			return outReport;
	}
	
	
	public static String countAndPrintFrequenciesUnitsLostSummary(ArrayList<Units> newsArray) {
		String outReport = "";
		outReport = appendString(outReport,"<br><br>--------------------<br>-  Unit Lost Summary  -<br>--------------------<br>");
		
		Map<String, Integer> hm = new HashMap<String, Integer>();

		for (Units i : newsArray) {
			Integer j = hm.get(i.getUnitType());
			hm.put(i.getUnitType(), (j == null) ? i.getAmount() : j + i.getAmount());
		}
		// displaying the occurrence of elements in the arraylist
		for (Map.Entry<String, Integer> val : hm.entrySet()) {
			outReport = appendString(outReport,"<br>"+val.getValue() + " " + val.getKey());
			//System.out.println(val.getValue() + " " + text + " " + "#" + val.getKey());
		}
		return outReport;
	}
	
	
	
	public static String countAndPrintFrequenciesBuildings(ArrayList<Buildings> newsArray) {
		String outReport = "";
		outReport = appendString(outReport,"<br><br>--------------------<br>-     Building Summary     -<br>--------------------<br>");
		
		Map<String, Integer> hm = new HashMap<String, Integer>();

		for (Buildings i : newsArray) {
			Integer j = hm.get(i.getBuildingType());
			hm.put(i.getBuildingType(), (j == null) ? i.getAmount() : j + i.getAmount());
		}
		// displaying the occurrence of elements in the arraylist
		for (Map.Entry<String, Integer> val : hm.entrySet()) {
			outReport = appendString(outReport,"<br>"+val.getValue() + " " + val.getKey() + " built");
			//System.out.println(val.getValue() + " " + text + " " + "#" + val.getKey());
		}
		return outReport;
	}
	
	public static String laserReport(ArrayList<Buildings> newsArray) {
		String outReport = "";
		outReport = appendString(outReport,"<br>--------------------<br>-     Laser Report     -<br>--------------------<br>");
		
		for (Buildings i : newsArray) {
			if (i.getBuildingType().equals("Laser")) {
				outReport = appendString(outReport,"<br>"+i.getAmount()+ " Laser(s) built on "+i.getPlanetCoords() +"   (T-" + i.getTurnOccurred()+")");
			}
		}
		return outReport;
	}
	
	public static String portalReport(ArrayList<Buildings> newsArray) {
		String outReport = "";
		outReport = appendString(outReport,"<br>--------------------<br>-     Portal Report     -<br>--------------------<br>");
		
		for (Buildings i : newsArray) {
			if (i.getBuildingType().equals("Portal")) {
				outReport = appendString(outReport,"<br>Portal built on "+i.getPlanetCoords() +"  (T-" + i.getTurnOccurred()+")");
			}
		}
		return outReport;
	}
	
	
	
	public static void printArrayUnits(ArrayList<Units> unitArray) {
		for (Units unitNews : unitArray) {
			System.out.println(unitNews.getLineNumber() + " " + unitNews.getUnitType() + " " + unitNews.getAmount());
		}
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
		outReport = appendString(outReport,"<br><br>--------------------<br>- List of explored planets - <br>--------------------<br>");
		for (PlanetNews planetNews : newsArray) {
			outReport = appendString(outReport,"<br>"+planetNews.getPlanetCoords()+" "+planetNews.getFamMember()+" tick "+ planetNews.getTurnOccurred());
		}
		return outReport;
	}
	
	public static String appendString(String text1, String text2) {
		//System.out.println(text2);
		String combinedText = text1 + text2;
		return combinedText;
	}
	
	
	public static String unportaledPlanets(ArrayList<Buildings> portalList, ArrayList<PlanetNews> exploreArray) {
		Boolean match = false;
		String outReport = "<br><br>--------------------<br>- List of unportalled planets (explored only) - <br>--------------------<br>";		
		for (PlanetNews explored : exploreArray) {
				for (Buildings portals : portalList) {
					if (portals.getPlanetCoords().equals(explored.getPlanetCoords()) && portals.getLineNumber() < explored.getLineNumber()) {
						match = true;
						break;
						}
				}
				if (!match) {
					outReport = appendString(outReport,"<br>" + explored.getPlanetCoords() +" - Explored Tick "+ explored.getTurnOccurred());
				}		
				match = false;
			}
		return outReport;
	}	
	

	
	
	
}
