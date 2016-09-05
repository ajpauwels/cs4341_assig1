package main;

public class Subtract extends Operation {
	/**
	 * Calls super-constructor
	 * 
	 * @param opVal Super-constructor argument
	 */
	public Subtract(int opVal) {
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
	public int execute(int searchVal) {
		return searchVal - this.value;
	}
}
