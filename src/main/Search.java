package main;

import java.util.ArrayList;
import java.util.Collections;

public class Search {

	/**
	 * Launcher function to begin the search program
	 * @param args Command-line argument should be a single string containing the file name
	 * 			   which contains the searching instructions and operations
	 */
	public static void main(String[] args) {
		// Command-line arguments extraction
		String fileName = args[0];
		
		// Instantiate the configuration object to define the search
		Configuration config = new Configuration(fileName);
		
		IterativeDeepening id = new IterativeDeepening(config);
		ArrayList<Operation> ops = id.run();
		Collections.reverse(ops);
		
		Integer val = config.getBeginVal();
		Integer result;
		for (Operation op : ops) {
			result = op.execute(val);
			System.out.println(val.toString() + " " + op.toString() + " = " + result);
			val = result;
		}
	}
}
