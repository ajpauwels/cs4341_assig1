package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Configuration {
	// Static ints identify the type of the search
	public final static int ITERATIVE = 0;
	public final static int GREEDY = 1;
	
	// Instance variables
	private int type;
	private int begin, end;
	private double timeLimit;
	private ArrayList<Operation> ops;
	
	/**
	 * Constructor for the class reads in the search configuration and operations
	 * from the specified file name
	 * 
	 * @param fileName The file to read from
	 */
	public Configuration(String fileName) {
		ops = new ArrayList<Operation>();
		
		try {
			// Get a handle to the file
			FileReader fr = new FileReader(fileName);
			
			// Get a buffered reader on the file
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			
			// Read config parameters in
			type = (line.equals("iterative") ? Configuration.ITERATIVE : Configuration.GREEDY);
			begin = Integer.parseInt(br.readLine());
			end = Integer.parseInt(br.readLine());
			timeLimit = Double.parseDouble(br.readLine());
			
			// Read operations in
			while ((line = br.readLine()) != null) {
				Operation op;
				String opStr = line.substring(0, 1);
				String valStr = line.substring(1, 2);
				int val = Integer.parseInt(valStr);
				
				if (opStr.equals(Operation.ADD)) {
					op = new Add(val);
				}
				else if (opStr.equals(Operation.SUBTRACT)) {
					op = new Subtract(val);
				}
				else if (opStr.equals(Operation.MULTIPLY)) {
					op = new Multiply(val);
				}
				else if (opStr.equals(Operation.DIVIDE)) {
					op = new Divide(val);
				}
				else if (opStr.equals(Operation.POWER)) {
					op = new RaiseToPower(val);
				} else {
					System.out.println("[ERROR] Did not recognize operation: " + opStr);
					continue;
				}
				
				ops.add(op);
			}
			
			// Close the reader
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] Could not find file: " + fileName);
			return;
		} catch (IOException e) {
			System.out.println("[ERROR] Error reading in file:\n" + e.getMessage());
			return;
		}
		
		System.out.println("[INFO] Successfully read in search file");
		System.out.println("[INFO] Type: " + type + ", Begin: " + begin + ", End: " + end + ", Time limit: " + timeLimit);

		if (type == GREEDY) {
			new Greedy(this);
		}
	}
	
	/**
	 * Returns the type of search
	 * 
	 * @return An int representing the type of search, can be compared with
	 * 		   Configuration.ITERATIVE and Configuration.GREEDY
	 */
	public int getType() {
		return this.type;
	}
	
	/**
	 * Returns the beginning value of the search
	 * 
	 * @return An int representing the begin value
	 */
	public int getBeginVal() {
		return this.begin;
	}
	
	/**
	 * Returns the ending value of the search
	 * 
	 * @return An int representing the end value
	 */
	public int getEndVal() {
		return this.end;
	}
	
	/**
	 * Returns the amount of time allocated for the search
	 * 
	 * @return A double representing the search's max time
	 */
	public double getTimeLimit() {
		return this.timeLimit;
	}
	
	/**
	 * Returns the array of operations allowed for the search
	 * 
	 * @return An array of operations
	 */
	public ArrayList<Operation> getOperations() {
		return this.ops;
	}
}
