package main;

public class Divide extends Operation {
	/**
	 * Calls super-constructor
	 * 
	 * @param opVal Super-constructor argument
	 */
	public Divide(double opVal) {
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
	public double execute(double searchVal) {
		return searchVal / this.value;
	}
	
	@Override
	public String toString() {
		return "/ " + value;
	}
}
