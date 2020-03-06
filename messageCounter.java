//this class is used to count the number of messages and rounds in each execution 
public class messageCounter {
	private int message;// message number
	private boolean leaderFlag;//leader flag
	private int roundCounter;//round number
	private int extraRound;//extra rounds for termination
	private int extraMessage;//extra messages for termination
	
	//constructor
	public messageCounter() {
		setMessage(0);
		setLeaderFlag(false);
		setRoundCounter(0);
		setExtraRound(0);
		setExtraMessage(0);
	}
	//setters and getters
	public int getMessage() {return message;}
	public void setMessage(int message) {this.message = message;}

	public boolean isLeaderFlag() {return leaderFlag;}
	public void setLeaderFlag(boolean leaderFlag) {	this.leaderFlag = leaderFlag;}
	
	public int getRoundCounter() {return roundCounter;}
	public void setRoundCounter(int roundCounter) {this.roundCounter = roundCounter;}
	
	public int getExtraRound() {return extraRound;}
	public void setExtraRound(int extraRound) {this.extraRound = extraRound;}
	
	public int getExtraMessage() {return extraMessage;}
	public void setExtraMessage(int extraMessage) {this.extraMessage = extraMessage;}
	
	//methods
	public void addCounter() {
		message++;
	}
	
	public void addRound() {
		roundCounter++;
	}
	
	public void addExtraRound() {
		extraRound++;
	}
	
	public void addExtraMessage() {
		extraMessage++;
	}
}
