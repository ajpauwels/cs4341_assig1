package main;

import java.util.ArrayList;
import java.util.Date;

public class Greedy {

	public Greedy(Configuration config) {
		ArrayList<Operation> opsHistory = new ArrayList<Operation>(); //operations used to reach goal

		int currentVal = config.getBeginVal(); //value of current node being expanded
		
		double startTime = (new Date()).getTime(); //used for timer
		
		while (true) { //runs until time has run out or solution has been found
			if ((new Date()).getTime() - startTime > config.getTimeLimit() * 1000) { //check to make sure time has not run out
				System.out.println("[INFO] Time has run out! Last value: " + currentVal);
				break;
			}
			
			int tempVal = Integer.MAX_VALUE; //this way the heuristic for the first operation will always be less
			Operation tempOp = config.getOperations().get(0);
			
			for (Operation op : config.getOperations()) { //check each operation
				int val = op.execute(currentVal);
				
				if (Math.abs(val - config.getEndVal()) < Math.abs(tempVal - config.getEndVal())) { //if operation has better heuristic than previous, set tempOp to op
					tempVal = val;
					tempOp = op;
				}
			}

			System.out.println(currentVal + " " + tempOp.toString() + " = " + tempVal);
			
			currentVal = tempVal;
			opsHistory.add(tempOp);
			
			if (currentVal == config.getEndVal()) { //if goal has been reached
				break;
			}
		}
		
		System.out.println("\nError: " + Math.abs(config.getEndVal() - currentVal));
		System.out.println("Number of steps required: " + opsHistory.size());
		System.out.println("Search required: " + (((new Date()).getTime() - startTime) / 1000) + " sec");
		System.out.println("Nodes expanded: " + opsHistory.size());
		System.out.println("Maximum search depth: " + opsHistory.size());
		return;
	}
}
