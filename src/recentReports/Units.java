package recentReports;


import java.util.ArrayList;
import java.util.regex.Pattern;

import Application.CoreInfo;
import Application.ExtractData;
import Application.Reporting;
import FamilyNews.PlanetNews;

public class Units extends CoreInfo implements Comparable<Units> {

	protected int lineNumber;
	protected int amount;
	protected String unitType;
	
	
	
	public Units(int lineNumber, int amount, String unitType) {
		super(lineNumber, 0);
		this.lineNumber = lineNumber;
		this.amount = amount;
		this.unitType = unitType;
		}
	
	public Units(int lineNumber, int turn, int amount, String unitType) {
		super(lineNumber, turn);
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
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}	
	
	public int getTurn() {
		return turnOccurred;
	}
	
	public void setTurn(int turn) {
		this.turnOccurred = turn;
	}	
	public int compareTo(Units compareTurn) {
		int compareQuantity = ((Units) compareTurn).getTurnOccurred(); 
		return compareQuantity - this.turnOccurred;
	}
	
	

	
	
	
	

	
	
	
	
	
}
