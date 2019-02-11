import static java.util.stream.Collectors.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AidNews  extends News implements Comparable<AidNews> {

	private String resource;
	private String receipient;
	private int amount;
	public static ArrayList<AidSummary> summaryAidSent = new ArrayList<AidSummary>();

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
	
	
	public static ArrayList<AidSummary> sumSentAid(ArrayList<AidNews> newsArray) {
	
		newsArray.stream().collect(groupingBy(Function.identity(),
				  ()->new TreeMap<>(

				    Comparator.<AidNews,String>comparing(aid->aid.famMember).thenComparing(aid->aid.resource)
				  ), 
				  Collectors.summingInt(aid->aid.amount)))
				.forEach((group,targetCostSum) ->	
					//System.out.println(group.famMember+" sent "+targetCostSum+" "+group.resource));
				summaryAidSent = AidSummary.addSummary(group.famMember,group.resource,targetCostSum));
		return summaryAidSent;
	}
	
}
