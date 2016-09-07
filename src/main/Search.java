package main;

import java.util.ArrayList;

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
		
		System.out.println("[INFO] First op in array list: " + ops.get(0).toString());
		
		// Testing information gathering
//		System.out.println("[INFO] Type: " + config.getType() + ", Begin: " + config.getBeginVal() + ", End: " + config.getEndVal() + ", Time limit: " + config.getTimeLimit());
//		
//		ArrayList<Operation> ops = config.getOperations();
//		
//		System.out.println("[INFO] 2 + 3: " + ops.get(0).execute(2));
//		System.out.println("[INFO] 2 - 1: " + ops.get(1).execute(2));
//		System.out.println("[INFO] 2 / 2: " + ops.get(2).execute(2));
//		System.out.println("[INFO] 2 * 5: " + ops.get(3).execute(2));
//		System.out.println("[INFO] 2 ^ 2: " + ops.get(4).execute(2));
	}

}
