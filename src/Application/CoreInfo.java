package Application;

public class CoreInfo {
	protected int lineNumber;
	protected int turnOccurred;
	
	public static String playerNameRegex = "([\\w+\\s*\\w*]*)";
	public static String planetRegex = " planet (\\d+) in the (\\d+)[,:](\\d+) system";
	public static String lineRegx = "(\\d+) ";
	public static String eventTick = "(\\w+)[\\s]+"+lineRegx+"T-(\\d{1,4})[\\s]+";
	
	public CoreInfo(int lineNumber, int turnOccurred) {
		this.turnOccurred = turnOccurred;
		this.lineNumber = lineNumber;
			}
	
	//get set
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
