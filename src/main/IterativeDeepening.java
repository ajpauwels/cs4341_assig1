package main;

import java.util.ArrayList;

public class IterativeDeepening {
	private Configuration setup;
	
	public IterativeDeepening(Configuration config) {
		this.setup = config;
	}
	
	public ArrayList<Operation> run() {
//		Date start = new Date();
//		Date end = new Date();
		int limit = 0;
		
		if (setup.getBeginVal() == setup.getEndVal()) {
			return new ArrayList<Operation>();
		} else {
			ArrayList<Operation> result = new ArrayList<Operation>();
			
			while (result.size() == 0) {
				limit++;
				result = loop(setup.getBeginVal(), limit);
			}
			
			return result;
		}
	}
	
	private ArrayList<Operation> loop(int val, int limit) {
		ArrayList<Operation> ops = setup.getOperations();
		
		for (Operation op : ops) {
			int result = op.execute(val);
			
			if (result == setup.getEndVal()) {
				ArrayList<Operation> successfulOp = new ArrayList<Operation>();
				successfulOp.add(op);
				return successfulOp;
			} else {
				if (limit > 1) {
					ArrayList<Operation> opSequence = loop(result, limit - 1);
					if (opSequence.size() > 0) {
						opSequence.add(op);
						return opSequence;
					}
				}
			}
		}
		
		return new ArrayList<Operation>();
	}
}
