package edu.buffalo.cse.jive.finiteStateMachine.expression.arithmetic;

import edu.buffalo.cse.jive.finiteStateMachine.models.Context;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class MultiplicationExpression extends ArithmeticExpression {

	public MultiplicationExpression() {
		super();
	}

	@Override
	public Boolean evaluate(Context context) {
		getExpressionA().evaluate(context);
		getExpressionB().evaluate(context);
		return true;
	}

	@Override
	public Object getValue() {
		return getExpressionA().multiply(getExpressionB());
	}

}
