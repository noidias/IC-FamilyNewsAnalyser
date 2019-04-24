
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Units  implements Comparable<Units> {

	protected int lineNumber;
	protected int amount;
	protected String unitType;
	
	
	public Units(int lineNumber, int amount, String unitType) {
		this.lineNumber = lineNumber;
		this.amount = amount;
		this.unitType = unitType;
		}
	
	//get set
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setsetEnemyFam(String unitType) {
		this.unitType = unitType;
	}	
	
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	
	public int compareTo(Units compareLine) {
		int compareQuantity = ((Units) compareLine).getLineNumber(); 
		return compareQuantity - this.lineNumber;
	}
	
	public void printArray(ArrayList<Units> unitArray) {
		for (Units unitNews : unitArray) {
			System.out.println(unitNews.getLineNumber() + " " + unitNews.getUnitType() + " " + unitNews.getAmount());
		}
	}
	
	public static ArrayList sumAidReceived(ArrayList unitArray) {
        ArrayList summaryAidReceived = new ArrayList<>(unitArray.size());
        unitArray.stream().collect(groupingBy(Function.identity(),
                () -> new TreeMap<>(
                        Comparator.<AidNews, String> comparing(aid -> aid.receipient).thenComparing(aid -> aid.resource)),
                Collectors.summingLong(aid -> aid.amount)))
                .forEach((group, targetCostSum) ->
        AidSummary.addSummary(group.receipient, group.resource, targetCostSum, summaryAidReceived));
        return summaryAidReceived;
    }
	
	
	

	
	
	
	
	
}
