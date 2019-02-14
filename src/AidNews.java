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

	static AidSummary aidSummary;
	private String resource;
	private String receipient;
	private long amount;

	public AidNews(int lineNumber, String newsEvent, int turnOccurred, String famMember, long amount, String resource, String receipient) {
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
	
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public int compareTo(AidNews compareLine) {
		int compareQuantity = ((AidNews) compareLine).getLineNumber(); 
		//descending order
		return compareQuantity - this.lineNumber;
	}
	
	
	public static ArrayList<AidSummary> sumSentAid(ArrayList<AidNews> newsArray) {
		ArrayList<AidSummary> summaryAidSent = new ArrayList<>(newsArray.size());
		newsArray.stream().collect(groupingBy(Function.identity(),
				  ()->new TreeMap<>(
				    Comparator.<AidNews,String>comparing(aid->aid.famMember).thenComparing(aid->aid.resource)
				  ), 
				  Collectors.summingLong(aid->aid.amount)))
				.forEach((group,targetCostSum) ->
		AidSummary.addSummary(group.famMember, group.resource, targetCostSum, summaryAidSent));
		return summaryAidSent;
	}
	
	public static ArrayList<AidSummary> sumAidReceived(ArrayList<AidNews> newsArray) {
        ArrayList<AidSummary> summaryAidReceived = new ArrayList<>(newsArray.size());
        newsArray.stream().collect(groupingBy(Function.identity(),
                () -> new TreeMap<>(
                        Comparator.<AidNews, String> comparing(aid -> aid.receipient).thenComparing(aid -> aid.resource)),
                Collectors.summingLong(aid -> aid.amount)))
                .forEach((group, targetCostSum) ->
        AidSummary.addSummary(group.receipient, group.resource, targetCostSum, summaryAidReceived));
        return summaryAidReceived;
    }
}