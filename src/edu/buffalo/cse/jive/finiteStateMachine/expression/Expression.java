/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.expression;

import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public abstract class Expression implements IExpression, Cloneable {

	// UnaryContext ct;

	// public abstract void setContext(UnaryContext ct);

	public abstract boolean evaluate(UnaryContext ct);

	@Override
	public String getValue(UnaryContext ct) {
		// TODO Auto-generated method stub
		return null;
	}

}
