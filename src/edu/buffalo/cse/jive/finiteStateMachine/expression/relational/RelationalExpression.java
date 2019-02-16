package edu.buffalo.cse.jive.finiteStateMachine.expression.relational;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.BinaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public abstract class RelationalExpression
		extends BinaryExpression<Expression, Expression> {

	public RelationalExpression() {
		super();
	}

	public RelationalExpression(Expression expressionA, Expression expressionB) {
		super(expressionA, expressionB);
	}

}
