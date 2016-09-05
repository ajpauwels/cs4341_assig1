package main;

public class Divide extends Operation {
	/**
	 * Calls super-constructor
	 * 
	 * @param opVal Super-constructor argument
	 */
	public Divide(int opVal) {
		super(opVal);
	}

	/**
	 * Divides the given value by the class's static value
	 * 
	 * @param searchVal The value that will be divided
	 * 
	 * @return The quotient
	 */
	@Override
	public int execute(int searchVal) {
		return searchVal / this.value;
	}
}
