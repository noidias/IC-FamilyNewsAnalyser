package recentReports;

import Application.CoreInfo;

public class Buildings extends CoreInfo implements Comparable<Buildings> {

	protected int amount;
	protected String buildingType;
	protected String planetCoords;
	
	
	public Buildings(int lineNumber, int turnOccurred,int amount, String buildingType, String planetCoords)  {
		super(lineNumber, turnOccurred);
		this.lineNumber = lineNumber;
		this.amount = amount;
		this.buildingType = buildingType;
		this.planetCoords = planetCoords;
		this.turnOccurred = turnOccurred;
		}
	
	//get set
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getBuildingType() {
		return buildingType;
	}
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}	
	
	public String getPlanetCoords() {
		return planetCoords;
	}
	public void setPlanetCoords(String planetCoords) {
		this.planetCoords = planetCoords;
	}
	
	public int compareTo(Buildings compareLine) {
		int compareQuantity = ((Buildings) compareLine).getLineNumber(); 
		return compareQuantity - this.lineNumber;
	}
	
	

	
	
	
	

	
	
	
	
	
}
