package edu.buffalo.cse.jive.finiteStateMachine.expression.literal;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public interface Literal<T> {

	public T getLiteral();

	public void setLiteral(T value);
}
