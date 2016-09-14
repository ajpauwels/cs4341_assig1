package main;

public abstract class Operation {
	// Static operation types
	static final public String ADD = "+";
	static final public String SUBTRACT = "-";
	static final public String MULTIPLY = "*";
	static final public String DIVIDE = "/";
	static final public String POWER = "^";
	
	// The sub-classed operation will be performed on a given number and this value
	protected double value;
	
	/**
	 * Constructor simply sets the given value to be the static half of the operation
	 * 
	 * @param opVal Value to operate with
	 */
	public Operation(double opVal) {
		this.value = opVal;
	}
	
	public abstract double execute(double searchVal);

	@Override
	public abstract String toString();
}
