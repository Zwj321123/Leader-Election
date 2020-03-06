import java.util.ArrayList;
import java.util.Collections;

public class HSalgorithm {
	public messageCounter mc;
	//constructor
	public HSalgorithm() {
		mc = new messageCounter();
	}
	 //start HS algorithm
	public void HS(ArrayList <processor> ring) {
		
			int phase = 0;
	        int hopCount = (int)Math.pow(2, (double)phase); 
	        System.out.println("------------------------------------");
    		System.out.println("HS algorithm starts");
    		System.out.println("------------------------------------");

	        while (!mc.isLeaderFlag()) {
	        	
	        	
	        	 for (int i = 0; i<ring.size(); i++) {
		        		ring.get(i).setSurvive(0);
	        	}
	        	 for (int i = 0; i<ring.size(); i++) {
	        		processor curNode = ring.get(i);
	 	        	processor nex = curNode.nextNode(ring);
	 	        	processor prev = curNode.prevNode(ring);
	 	        	 
	 	        	if (!curNode.isTerminate()){
	 	        		curNode.setTerminate(true);
		 	        	curNode.sendHSMessage("R", curNode.getMyID(), nex, hopCount, "out", ring, mc);
		 	        	 if (mc.isLeaderFlag()) {
		 	        		 
			        		 break;
		 	        	 }
		 	        	 else {
		 	        		 curNode.sendHSMessage("L", curNode.getMyID(), prev, hopCount, "out", ring, mc);
		 	        		
		 	        	}
		 	        	if (curNode.getSurvive() == 2) {
		 	        		curNode.setTerminate(false);
		 	        		/*
		 	        		System.out.println(curNode.getMyID()+" survive");
		 	        		System.out.println("-------");
		 	        		*/
		 	        	}
		 	        	else {
		 	        		curNode.setTerminate(true);
		 	        		/*
		 	        		System.out.println(curNode.getMyID()+" died");
		 	        		System.out.println("-------");
		 	        		*/
		 	        	}
	        	 	}
	 	        	
	        	 }
	        	 if (mc.isLeaderFlag()) {
	        		 mc.setRoundCounter(mc.getRoundCounter()+ring.size());
	        		 System.out.println("complexity for leader election:");
	        		 System.out.println("\tphases: "+phase);
	     		     System.out.println("\trounds: "+mc.getRoundCounter());
	     		     System.out.println("\tmessages: "+ mc.getMessage());
	     		     System.out.println("complexity for termination:");
	     			 System.out.println("\tExtra rounds: "+ mc.getExtraRound());
	     			 System.out.println("\tExtra messages: "+ mc.getExtraMessage());
	        	 }
	        	 else {
	        		// System.out.println(mc.getMessage());
	     			// System.out.println("----------");
		        	 phase++;
		        	 mc.setRoundCounter(mc.getRoundCounter()+ hopCount*2);
		        	 hopCount = (int)Math.pow(2, (double)phase);
	        	 }
	        }
	}	

}