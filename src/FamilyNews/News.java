package FamilyNews;

import Application.CoreInfo;

public class News extends CoreInfo {
	protected String newsEvent;
	protected String famMember;
	
	
	public News(int lineNumber, String newsEvent, int turnOccurred, String famMember) {
		super(lineNumber, turnOccurred);
		this.famMember = famMember;
		this.newsEvent = newsEvent;
		this.turnOccurred = turnOccurred;
		this.lineNumber = lineNumber;
			}
	
	//get set
	public String getFamMember() {
		return famMember;
	}
	public void setFamMember(String famMember) {
		this.famMember = famMember;
	}
	
	public String getNewsEvent() {
		return newsEvent;
	}
	public void setNewsEvent(String newsEvent) {
		this.newsEvent = newsEvent;
	}
	
	

}
