package main;

import java.util.ArrayList;
import java.util.Date;

public class IterativeDeepening {
	private int iterativeDepth; //holds the current maximum search depth
	
	private ArrayList<Operation> currentPath; //stores the current sequence of operations
	
	private Double runningBestResult;
	private ArrayList<Operation> runningBestPath;
	
	private Configuration setup;
	
	private boolean resultFound = false;
	
	private int numberOfNodes = 0;
	private int maxDepth = 0;
	
	Date start;
	Date end;
	
	public IterativeDeepening(Configuration config) {
		this.setup = config;
		currentPath = new ArrayList<Operation>();
		runningBestPath = new ArrayList<Operation>();
	}
	
	/**
	 * Run the iterative depth first search algorithm
	 */
	
	public boolean run() {
		iterativeDepth = 1;
		
		start = new Date();
		end = new Date();
		
		//while time has not run out and result hasn't been found
		while(end.getTime() - start.getTime() < setup.getTimeLimit() * 1000 && !resultFound){
			
			if( loop((int)setup.getBeginVal(), 0) == (int)setup.getEndVal() ){ //run the recursive loop
					resultFound = true;
			}
		
			iterativeDepth++;
		}
		
		if(end.getTime() - start.getTime() > setup.getTimeLimit() * 1000){
			System.out.println("RAN OUT OF TIME");
		}

		//print out the results
		int tempVal = (int)setup.getBeginVal();
		for(Operation op: runningBestPath){
			System.out.println(tempVal + " " + op.toString() + " = " + op.execute(tempVal));
			tempVal = (int)op.execute(tempVal);
		}
		System.out.println("\nError: " + (Math.abs(runningBestResult - setup.getEndVal())));
		System.out.println("Number of steps required: " + runningBestPath.size());
		System.out.println("Search required: " + ((end.getTime() - start.getTime()) / 1000.0 ));
		System.out.println("Nodes expanded: " + numberOfNodes);
		System.out.println("Maximum search depth: " + (maxDepth+1));
		
		return false;
	}
	
	/**
	 * recursive depth first search
	 * @param val	Value of the current node
	 * @param currentDepth	current depth of the tree, incremented by one 
	 * 						each time the function is called recursively  
	 */
	private ArrayList<Operation> loop(double val, int currentDepth) {
		// Get the allowed operations
		ArrayList<Operation> ops = setup.getOperations();
		
		// Loop through each one individually
		for (Operation op : ops) {
			// Get the result of computation and increase number of nodes expanded
			double result = op.execute(val);
			numberOfNodes++;
			
			currentPath.add(op);
			
			// Update the running best result if the new result is better
			if(runningBestResult == null || Math.abs(setup.getEndVal() - result) < Math.abs(setup.getEndVal() - runningBestResult)){
				runningBestResult = result;
				runningBestPath = (ArrayList<Operation>) currentPath.clone();
			}
			

			end = new Date();
			
			if (result == setup.getEndVal()) {
				ArrayList<Operation> successfulOp = new ArrayList<Operation>();
				successfulOp.add(op);
//				resultFound = true;
				return successfulOp;
			}else if(currentDepth < iterativeDepth && ((end.getTime() - start.getTime() < setup.getTimeLimit()*1000))){ //if not at result, and not past max iterative depth, recurse into next node
				if ( currentDepth > maxDepth) maxDepth = currentDepth;
				loop(result, ++currentDepth);
			}
			
			currentPath.remove(currentPath.size()-1); //remove the last operation added to the node to keep current path up to date
		}
		
		return 0;
	}
}
