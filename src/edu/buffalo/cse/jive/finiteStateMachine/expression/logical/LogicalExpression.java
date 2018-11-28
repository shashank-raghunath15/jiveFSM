package edu.buffalo.cse.jive.finiteStateMachine.expression.logical;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public abstract class LogicalExpression extends Expression {

	@Override
	public Expression getExpressionA() {
		return this;
	}
	
	public abstract boolean evaluate(UnaryContext ct);
	
}
