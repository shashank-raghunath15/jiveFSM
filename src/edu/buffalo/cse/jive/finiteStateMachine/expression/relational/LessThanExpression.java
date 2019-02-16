package edu.buffalo.cse.jive.finiteStateMachine.expression.relational;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class LessThanExpression extends RelationalExpression {

	public LessThanExpression() {
		super();
	}

	public LessThanExpression(Expression expressionA, Expression expressionB) {
		super(expressionA, expressionB);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Boolean evaluate(Context context) {
		try {
			Comparable comparableA = (Comparable) getExpressionA();
			Comparable comparableB = (Comparable) getExpressionB();
			getExpressionA().evaluate(context);
			getExpressionB().evaluate(context);
			return comparableA.compareTo(comparableB) < 0;
		} catch (ClassCastException e) {
			return true;
		}
	}
}
