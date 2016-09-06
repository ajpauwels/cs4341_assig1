package main;

public class RaiseToPower extends Operation {
	/**
	 * Calls super-constructor
	 * 
	 * @param opVal Super-constructor argument
	 */
	public RaiseToPower(int opVal) {
		super(opVal);
	}

	/**
	 * Raises the given value to the power of the class's value
	 * 
	 * @param searchVal The value to raise by a power
	 * 
	 * @return The product
	 */
	@Override
	public int execute(int searchVal) {
		return (int)Math.pow(searchVal, this.value);
	}
	
	@Override
	public String toString() {
		return "^ " + value;
	}
}
