import java.util.ArrayList;

public class AidNews  extends News implements Comparable<AidNews> {

	private String resource;
	private String receipient;
	private int amount;

	public AidNews(int lineNumber, String newsEvent, int turnOccurred, String famMember, int amount, String resource, String receipient) {
		super(lineNumber, newsEvent, turnOccurred, famMember);
		this.resource = resource;
		this.receipient = receipient;
		this.amount = amount;
	}
	
	//get set
	public String getResource() {
		return resource;
	}
	public void setresource(String resource) {
		this.resource = resource;
	}	
	public String getReceipient() {
		return receipient;
	}
	public void setReceipient(String receipient) {
		this.receipient = receipient;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int compareTo(AidNews compareLine) {
		
		int compareQuantity = ((AidNews) compareLine).getLineNumber(); 
		
		//ascending order
		//return this.lineNumber - compareQuantity;
		//descending order
		return compareQuantity - this.lineNumber;
	}
}
