# Leader-Election
Ring based leader election simulaor (LCR and HS algorithms)
This is a leader election simulator based on LCR and HS algorithms.
author: Wenjun Zeng
-------------------------------------------------------------------------------------
How to run:
Please run the leaderElection.java file to start the simulator. The system 
would ask the user to enter the size of the ring and only an integer from
 1 to 1100 is acceptable. Then, the system would ask to choose the type
 of the ring. Once again, only the correct number of the option is acceptable. 
At the end of the program, each processor with its ID, the round number 
and message number of both algorithms would be displayed on the screen.

--------------------------------------------------------------------------------------
five classes are constructed in this simulator. The main functionalities of the five 
classes are described as follows:

The “Processor” class is constructed to serve as a processor in rings; 

The “HSalgorithm” and “LCR_algorithms” realize the functionalities of terminating HS and LCR algorithms, respectively. 

The “messageCounter” class is responsible for counting the numbers of rounds and messages in every execution.

The “leaderElection” class containing the main function can provide a user interface to implement the two algorithms 
and output the information about time and communication complexities. An ArrayList storing several “Processor” objects 
is initialized as a ring. There are three types of rings to choose: ascending clockwise rings, descending clockwise 
rings and random rings.

