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
		
		// Testing information gathering
		System.out.println("[INFO] Type: "
				+ "" + config.getType() + ", Begin: " + config.getBeginVal() + ", End: " + config.getEndVal() + ", Time limit: " + config.getTimeLimit());
		
		ArrayList<Operation> ops = config.getOperations();
		
		if(config.getType() == 0){
			IterativeDeepening search = new IterativeDeepening(config);
			
			search.run();
		}else{
			Greedy search = new Greedy(config);
		}
	}

}
