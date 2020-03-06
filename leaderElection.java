import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
//main class
public class leaderElection {
	
	public static void main(String []args) {
		LCR_algorithm LCR_simulator = new LCR_algorithm();
		HSalgorithm HS_simulator = new HSalgorithm();
		//generator a new ring
		ArrayList <processor> ring = networkGenerator();
		/*
		 * write the message number into .csv file
		 * remove the comment to execute this code
		 */
		/*
		File file = new File("Message_HS.csv");
		try {
			FileWriter out = new FileWriter(file);
		for(int j = 250; j <1000; j= j+50) {
			List <String> outputMess = new ArrayList<String>();
			for (int i = 0; i<10; i++) {
				HSalgorithm HS_simulator = new HSalgorithm();
				//LCR_algorithm LCR_simulator = new LCR_algorithm();
				HS_simulator.HS(randomRing(j));
				//LCR_simulator.LCR(randomRing(j));
				outputMess.add(Integer.toString(HS_simulator.mc.getMessage()));
				//System.out.println(HS_simulator.mc.getMessage());
				
			}
			for (int i = 0; i < outputMess.size(); i++) {
				out.write(outputMess.get(i));
				out.append(",");
				//System.out.println(outputMess.get(i));
			}
			out.append("\n");
		}
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		*/
	    //LCR algorithm:
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>");
		LCR_simulator.LCR(ring);
		//HS algorithm
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>");
		HS_simulator.HS(ring);
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	//---------------------------------------------------------------------------------------------------------
	//generate harmonic number (not used in this simulator; only for making graphs)
	public static void Harmonic(double n) {	
		double sum = 0;
		double in = 0.0;
		for (int i=1; i<n+1; i++) {
			in = in+1.0;
			sum += 1/in;
		}
		sum = sum*n;
		System.out.println(sum);
	}
	//initialize the ring network:
	public static ArrayList<processor> networkGenerator(){
		int n = 0;
		int t = 0;
		Scanner sc = new Scanner(System.in);
		//user inputs the size of the network
		System.out.println("Enter the size of the network(1 to 1100):");
		try {
			n = sc.nextInt();
		}
		catch(InputMismatchException e){
			System.out.println(e.getMessage());
		}
		if (n>1100 || n <= 0) {
			throw new IllegalArgumentException("size should be in the range of 1 to 1100");
		}
		//user inputs the type of the network
		System.out.println("Enter the type of the network:");
		System.out.println("1. Random\t2.Ascending\t3.Descending");
		try {
			t = sc.nextInt();
		}
		catch(InputMismatchException e){
			System.out.println(e.getMessage());
		}
		if (t>3 || n <= 0) {
			throw new IllegalArgumentException("the number should be in the range of 1 to 3");
		}
		else if (t == 1) {return randomRing(n);}
		else if (t == 2) {return ascendingRing(n);}
		else { return descendingRing(n);}		
	}
	
	//generate random rings
	public static ArrayList<processor> randomRing(int n){
		 //generate n unique ids from the range 1-5n
		 ArrayList<Integer> list = new ArrayList<Integer>();
	        for (int i=1; i<5*n; i++) {
	            list.add(new Integer(i));
	        }
	        Collections.shuffle(list);
	       //generate a ring with n processors.
	        ArrayList <processor> ring = new ArrayList<processor>();
	        for (int i=0; i<n; i++) {
	        	ring.add(new processor(list.get(i)));
	            System.out.println("processor"+(i+1) +"'s ID: " +list.get(i));
	        }
	        return ring;
	}
	//generate ascending clockwise rings
	public static ArrayList<processor> ascendingRing(int size){
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<size+1; i++) {
            list.add(new Integer(i));
        }
        ArrayList <processor> ring = new ArrayList<processor>();
        for (int i=0; i<size; i++) {
        	ring.add(new processor(list.get(i)));
            System.out.println("processor"+(i+1) +"'s ID: " +list.get(i));
        }
        return ring;
	}
	//generate descending clockwise rings
	public static ArrayList<processor> descendingRing(int size){
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<size+1; i++) {
            list.add(new Integer(i));
        }
        ArrayList <processor> ring = new ArrayList<processor>();
        for (int i=0; i<size; i++) {
        	ring.add(new processor(list.get(size-1-i)));
            System.out.println("processor"+(i+1) +"'s ID: " +list.get(size-1-i));
        }
        return ring;
	}
}
