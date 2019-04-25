
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
	

	
	
	
	

	
	
	
	
	
}
