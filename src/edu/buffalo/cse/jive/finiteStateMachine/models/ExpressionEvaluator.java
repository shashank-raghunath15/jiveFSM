package edu.buffalo.cse.jive.finiteStateMachine.models;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.literal.NumberValueExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.literal.ValueExpression;

public class ExpressionEvaluator {
	public static ValueExpression evaluate(Expression expr, UnaryContext cxt) {
		return new NumberValueExpression(3);
	}
	
}
