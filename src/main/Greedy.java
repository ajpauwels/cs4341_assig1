package main;

import java.util.ArrayList;
import java.util.Date;

public class Greedy {

	public Greedy(Configuration config) {
		ArrayList<Operation> opsHistory = new ArrayList<Operation>();

		int currentVal = config.getBeginVal();
		
		double startTime = (new Date()).getTime();
		
		while (true) {
			if ((new Date()).getTime() - startTime > config.getTimeLimit() * 1000) {
				System.out.println("[INFO] Time has run out! Last value: " + currentVal);
				break;
			}
			
			int tempVal = Integer.MAX_VALUE; //this way the heuristic for the first operation will always be less
			Operation tempOp = config.getOperations().get(0);
			
			for (Operation op : config.getOperations()) {
				int val = op.execute(currentVal);
				
				if (Math.abs(val - config.getEndVal()) < Math.abs(tempVal - config.getEndVal())) {
					tempVal = val;
					tempOp = op;
				}
			}

			System.out.println(currentVal + " " + tempOp.toString() + " = " + tempVal);
			
			currentVal = tempVal;
			opsHistory.add(tempOp);
			
			if (currentVal == config.getEndVal()) {
				System.out.println("Hooray we reached the goal!");
				System.out.println("Time taken: " + (((new Date()).getTime() - startTime) / 1000) + " sec");
				System.out.println("Nodes expanded: " + opsHistory.size());
				return;
			}
		}
	}
}
