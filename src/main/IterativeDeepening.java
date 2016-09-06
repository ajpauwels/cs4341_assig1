package main;

import java.util.ArrayList;
import java.util.Date;

public class IterativeDeepening {
	private int level;
	private Configuration setup;
	
	public IterativeDeepening(Configuration config) {
		this.setup = config;
	}
	
	public boolean run() {
		Date start = new Date();
		level = 0;
		
		return false;
	}
	
	private Operation loop(int val) {
		ArrayList<Operation> ops = setup.getOperations();
		
		for (Operation op : ops) {
			int result = op.execute(val);
			
			if (result == setup.getEndVal()) {
				return op;
			}
		}
		
		return null;
	}
}
