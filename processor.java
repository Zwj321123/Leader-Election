import java.util.ArrayList;
//this class defines the main functions of a processor in a ring
public class processor {
	
	private int myID;
	private int inID;
	private int readID;
	private String status;
	private int Leader;//store the leader's ID
	private String mesDir; //message direction: left(clockwise) or right(counter clockwise)
	private String messageStatus;//in or out (for HS algorithm)
	private boolean isTerminate;//terminate after knowing the leader
	private int survive;//0:processor survives in the phase; 1:died in the phase (for HS algorithm)
	//constructor
	processor(int MyID){
		myID = MyID;
		inID = 0;
		readID = 0;
		Leader = 0;
		status = "unknown";
		mesDir = " ";
		setTerminate(false);
		messageStatus = null;
		survive = 0;
	}
	
	//getters and getters:
	public int getMyID() {
		return myID;
	}
	public String getStatus() {
		return status;
	}
	public int getInID() {
		return inID;
	}
	public void setStatus(String s) {
		status = s;
	}
	public void setInID(int i) {
		inID = i;
	}
	public int getReadID() {
		return readID;
	}
	public void setReadID(int readID) {
		this.readID = readID;
	}
	public int getLeader() {
		return Leader;
	}
	public void setLeader(int Leader) {
		this.Leader = Leader;
	}
	public String getMesDir() {
		return mesDir;
	}
	public void setMesDir(String mesDir) {
		this.mesDir = mesDir;
	}
	public boolean isTerminate() {
		return isTerminate;
	}

	public void setTerminate(boolean isTerminate) {
		this.isTerminate = isTerminate;
	}
	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}
	public int getSurvive() {
		return survive;
	}

	public void setSurvive(int survive) {
		this.survive = survive;
	}

	//methods:
	//find neighbor on the right
	public processor nextNode(ArrayList <processor> r) {
		int currentIndex = 0;
		processor returnP;
		for (int i=0; i <r.size(); i++) {
			if (r.get(i)==this) {
				currentIndex = i;
			}
		}
		if (currentIndex < r.size()-1) {
			returnP = r.get(currentIndex+1);
		}
		else {
			returnP = r.get(0);
		}
		return returnP;
	}
	//find neighbor on the left
	public processor prevNode(ArrayList <processor> r) {
		int currentIndex = 0;
		processor returnP;
		for (int i=0; i <r.size(); i++) {
			if (r.get(i)==this) {
				currentIndex = i;
			}
		}
		if (currentIndex > 0) {
			returnP = r.get(currentIndex-1);
		}
		else {
			returnP = r.get(r.size()-1);
		}
		return returnP;
	}
	public void send(int sendID, processor p) {
		p.setReadID(sendID);
	}
	public void read() {
		setInID(readID);
		setReadID(0);
	}
	public void sendLeader(String dir, int leaderID, processor p) {
		p.Leader=leaderID;
		p.setMesDir(p.getMesDir()+dir);
	}
	public void clearLeader(ArrayList <processor> ring) {
		for (int i = 0; i<ring.size(); i++) {
			ring.get(i).setMesDir(" ");
		}
	}
	//receive messages in HS algorithm:
	public void sendHSMessage(String dir, int sendID, processor p, int hopCount, String messageStatus, ArrayList<processor> r, messageCounter mc) {
		
		p.setInID(sendID);
		//System.out.println(p.getMyID()+" RECEIVES "+ p.getInID());
		mc.addCounter();
		p.processHS(r, messageStatus, dir, hopCount, mc);
	}
	//execute HS algorithm:
	public void processHS(ArrayList<processor> r, String messageStatus, String dir, int hopCount, messageCounter mc) {
		//send message counterclockwise
		if(dir == "R") {
			if (messageStatus == "out") {
				if (getInID()>getMyID() && hopCount >1) {
					sendHSMessage("R", getInID(), this.nextNode(r), hopCount-1, "out", r, mc);
				}
				else if (getInID()>getMyID() && hopCount ==1) {
					sendHSMessage("R", getInID(), this.prevNode(r), 1, "in", r, mc);
				}
				//Find leader
				else if (getInID()==getMyID()) {
					setStatus("Leader");
					System.out.println("Leader is processor "+this.getIndex(r));
					mc.setLeaderFlag(broadcastLeader(r, mc));
				}
			}
			else if (messageStatus == "in" && hopCount == 1) {
				if (getInID()!=getMyID()) {
					sendHSMessage("R", getInID(), this.prevNode(r), 1, "in", r, mc);
				}
				else if (getInID() == getMyID() && dir == "R") {
					setSurvive(getSurvive()+1);
				}
			}
		}
		//send message clockwise
		else if (dir == "L") {
			if (messageStatus == "out") {
				if (getInID()>getMyID() && hopCount >1) {
					//mc.addCounter();
					sendHSMessage("L", getInID(), this.prevNode(r), hopCount-1, "out", r, mc);
				}
				else if (getInID()>getMyID() && hopCount ==1) {
					//mc.addCounter();
					sendHSMessage("L", getInID(), this.nextNode(r), 1, "in", r, mc);
				}
		
			}
			else if (messageStatus == "in" && hopCount == 1) {
				if (getInID()!=getMyID()) {
					//mc.addCounter();
					sendHSMessage("L", getInID(), this.nextNode(r), 1, "in", r, mc);
				}
				else if (getInID() == getMyID() && dir == "L") {
					setSurvive(getSurvive()+1);
				}
			}
		
		}
	}
	//the elected leader will broadcast its status and terminate other processors from BOTH directions
	public boolean broadcastLeader(ArrayList <processor> ring, messageCounter mc) {
		boolean fl = false;
		processor nex = this;
		processor prev = this;
		System.out.println("------------------------------");
		System.out.println("processor "+this.getIndex(ring)+" starts to broadcast:");
		while(!fl) {
			nex.sendLeader("R", nex.getLeader(), nex.nextNode(ring));
			mc.addExtraMessage();
			//System.out.println("processor "+nex.nextNode(ring).getIndex(ring)+ " terminates.");
			prev.sendLeader("L", prev.getLeader(), prev.prevNode(ring));
			//System.out.println("processor "+prev.prevNode(ring).getIndex(ring)+ " terminates.");
			mc.addExtraRound();
			mc.addExtraMessage();
			
			//check if all nodes know the leader
			for(int i = 0; i<ring.size(); i++) {
				//System.out.println(ring.get(i).getLeader());
				if (ring.get(i).getMesDir().contains("RL")) {
					fl = true;
					System.out.println("all nodes know the leader is "+" processor "+(this.getIndex(ring)));
					System.out.println("------------------------------------");
				}
				
			}
			nex = nex.nextNode(ring);
			prev = prev.prevNode(ring);
			
		}
		mc.setLeaderFlag(fl);
		clearLeader(ring);
		return fl;
	}
	
	public int getIndex(ArrayList <processor> ring) {
		int nodeNum = 0;
		for(int i = 0; i < ring.size(); i++) {
			if (ring.get(i).getMyID() == this.getMyID()) {nodeNum = i+1;}
		}
		return nodeNum;
	}


	
}

