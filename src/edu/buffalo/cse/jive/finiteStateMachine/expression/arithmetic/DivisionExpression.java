package edu.buffalo.cse.jive.finiteStateMachine.expression.arithmetic;

import edu.buffalo.cse.jive.finiteStateMachine.models.Context;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class DivisionExpression extends ArithmeticExpression {

	private static final long serialVersionUID = 8261862512025495113L;

	public DivisionExpression() {
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
		return getExpressionA().divide(getExpressionB());
	}

}
