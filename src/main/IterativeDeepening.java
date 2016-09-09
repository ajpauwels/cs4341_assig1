package main;

import java.util.ArrayList;
import java.util.Date;

public class IterativeDeepening {
	private int iterativeDepth; //holds the current maximum search depth
	
	private ArrayList<Operation> currentPath; //stores the current sequence of operations
	
	private Integer runningBestResult;
	private ArrayList<Operation> runningBestPath;
	
	private Configuration setup;
	
	private boolean resultFound = false;
	
	private int numberOfNodes = 0;
	private int maxDepth = 0;
	
	public IterativeDeepening(Configuration config) {
		this.setup = config;
		currentPath = new ArrayList<Operation>();
		runningBestPath = new ArrayList<Operation>();
	}
	
	/**
	 * Run the iterative depth first search algorithm
	 */
	
	public boolean run() {
		Date start = new Date();
		Date end = new Date();
		iterativeDepth = 1;
		
		//while time has not run out and result hasn't been found
		while(end.getTime() - start.getTime() < setup.getTimeLimit() * 1000 && !resultFound){
			
			if( loop(setup.getBeginVal(), 0) == setup.getEndVal() ){ //run the recursive loop
					resultFound = true;
			}
		
			iterativeDepth++;
			end = new Date();
		}

		//print out the results
		int tempVal = setup.getBeginVal();
		for(Operation op: runningBestPath){
			System.out.println(tempVal + " " + op.toString() + " = " + op.execute(tempVal));
			tempVal = op.execute(tempVal);
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
	private int loop(int val, int currentDepth) {
		ArrayList<Operation> ops = setup.getOperations();
		
		for (Operation op : ops) {
			numberOfNodes++;
			
			int result = op.execute(val);
			
			currentPath.add(op);
			
			//update the running best result and path
			if(runningBestResult == null || Math.abs(setup.getEndVal() - result) < Math.abs(setup.getEndVal() - runningBestResult)){
				runningBestResult = result;
				runningBestPath = (ArrayList<Operation>) currentPath.clone();
			}
			
			if (result == setup.getEndVal()) {
				resultFound = true;
				return result;
			}else if(currentDepth < iterativeDepth){ //if not at result, and not past max iterative depth, recurse into next node
				if ( currentDepth > maxDepth) maxDepth = currentDepth;
				loop(result, ++currentDepth);
			}
			
			currentPath.remove(currentPath.size()-1); //remove the last operation added to the node to keep current path up to date
		}
		
		return 0;
	}
}