import java.util.ArrayList;

public class LCR_algorithm {
	public messageCounter mc;
	//constructor
	public LCR_algorithm() {mc = new messageCounter();}
	//start the LCR algorithm
	public void LCR(ArrayList<processor> ring) {
		System.out.println("------------------------------------");
		System.out.println("LCR algorithm starts");
		System.out.println("------------------------------------");
		//round <= 1
		  while(mc.getRoundCounter() <1) {
			    for (int i = 0; i <ring.size(); i++) {
				    ring.get(i).send(ring.get(i).getMyID(), ring.get(i).nextNode(ring));
				    mc.addCounter();
			    }
			    mc.addRound();
		    }
		    //round > 1
		    while(!mc.isLeaderFlag()) {
		       mc.addRound();
		       int sendID =0;
		       for (int i = 0; i <ring.size(); i++) {
				   ring.get(i).read();//load inID
				   
		       	}
		       for(int i = 0; i<ring.size(); i++) {
		     	   if (ring.get(i).getInID()>ring.get(i).getMyID()&& ring.get(i).getInID()!=0) {
		     		   sendID = ring.get(i).getInID();
		     		   ring.get(i).send(sendID, ring.get(i).nextNode(ring));
		     		   mc.addCounter();
		     		   //System.out.print("m");
		     		   
		     	   }
		     	   else if (ring.get(i).getInID()==ring.get(i).getMyID()) {
		     			ring.get(i).setStatus("leader");
		     			System.out.println("Leader is "+" processor "+(i+1));
		     			//tells every node the leader
		     			ring.get(i).setLeader(ring.get(i).getMyID());
		     			ring.get(i).broadcastLeader(ring, mc);
		     		}
		     	   ring.get(i).setInID(0);//clear InID after each round
		     	        
		        }
		     }
		     System.out.println("complexity for leader election:");
		     System.out.println("\trounds: "+mc.getRoundCounter());
		     System.out.println("\tmessages: "+ mc.getMessage());
		     System.out.println("complexity for termination:");
			 System.out.println("\tExtra rounds: "+ mc.getExtraRound());
			 System.out.println("\tExtra messages: "+ mc.getExtraMessage());
	}

}
