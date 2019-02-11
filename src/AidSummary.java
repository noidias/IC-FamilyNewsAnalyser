import java.awt.List;
import java.util.ArrayList;

public class AidSummary {

	private String famMember;
	private String resource;
	private int amount;
	static ArrayList<AidSummary> summary = new ArrayList<AidSummary>();
	
	public AidSummary(String famMember, String resource, int amount) {
		this.famMember = famMember;
		this.resource = resource;
		this.amount = amount;
	}
	
	//get set
	public String getResource() {
		return resource;
	}
	public void setresource(String resource) {
		this.resource = resource;
	}	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getFamMember() {
		return famMember;
	}
	public void setFamMember(String famMember) {
		this.famMember = famMember;
	}
	
	public static ArrayList<AidSummary> addSummary(String famMember, String resource, int amount) {
		AidSummary summaryLine = new AidSummary(famMember,resource,amount);
		summary.add(summaryLine);
		return summary;
	}
	
}
