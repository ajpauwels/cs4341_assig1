package main;

public class Add extends Operation {
	/**
	 * Calls super-constructor
	 * 
	 * @param opVal Super-constructor argument
	 */
	public Add(int opVal) {
		super(opVal);
	}

	@Override
	/**
	 * Adds the given value to the class's static value
	 * 
	 * @param searchVal The value to add
	 * 
	 * @return The sum
	 */
	public int execute(int searchVal) {
		return searchVal + this.value;
	}
}
