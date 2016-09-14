package main;

public class Subtract extends Operation {
	/**
	 * Calls super-constructor
	 * 
	 * @param opVal Super-constructor argument
	 */
	public Subtract(double opVal) {
		super(opVal);
	}

	/**
	 * Subtracts the class's static value from the given value
	 * 
	 * @param searchVal The value to subtract from
	 * 
	 * @return The sum
	 */
	@Override
	public double execute(double searchVal) {
		return searchVal - this.value;
	}
	
	@Override
	public String toString() {
		return "- " + value;
	}
}
