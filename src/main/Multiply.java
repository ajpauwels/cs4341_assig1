package main;

public class Multiply extends Operation {
	/**
	 * Calls super-constructor
	 * 
	 * @param opVal Super-constructor argument
	 */
	public Multiply(int opVal) {
		super(opVal);
	}

	/**
	 * Multiplies the given value by the class's static value
	 * 
	 * @param searchVal The value to multiply by
	 * 
	 * @return The product
	 */
	@Override
	public int execute(int searchVal) {
		return searchVal * this.value;
	}

	@Override
	public String toString() {
		return "* " + value;
	}
}
