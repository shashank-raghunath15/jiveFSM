package edu.buffalo.cse.jive.finiteStateMachine.expression.relational;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class EqualityExpression extends RelationalExpression {

	public EqualityExpression() {
		super();
	}

	public EqualityExpression(Expression expressionA, Expression expressionB) {
		super(expressionA, expressionB);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Boolean evaluate(Context context) {
		try {
			Comparable comparableA = (Comparable) getExpressionA();
			Comparable comparableB = (Comparable) getExpressionB();
			getExpressionA().evaluate(context);
			getExpressionB().evaluate(context);
			return comparableA.compareTo(comparableB) == 0;
		} catch (ClassCastException e) {
			return getExpressionA().evaluate(context).equals(getExpressionB().evaluate(context));
		}
	}
}
