
public class News {
	protected int lineNumber;
	protected String newsEvent;
	protected int turnOccurred;
	protected String famMember;
	
	
	public News(int lineNumber, String newsEvent, int turnOccurred, String famMember) {
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
	public int getTurnOccurred() {
		return turnOccurred;
	}
	public void setTurnOccurred(int turnOccurred) {
		this.turnOccurred = turnOccurred;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
