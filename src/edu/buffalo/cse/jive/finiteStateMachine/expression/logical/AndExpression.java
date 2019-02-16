package edu.buffalo.cse.jive.finiteStateMachine.expression.logical;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.BinaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.relational.RelationalExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class AndExpression extends BinaryExpression<RelationalExpression, RelationalExpression> {

	public AndExpression() {
		super();
	}

	public AndExpression(RelationalExpression expressionA, RelationalExpression expressionB) {
		super(expressionA, expressionB);
	}

	public Boolean evaluate(Context context) {
		return getExpressionA().evaluate(context) && getExpressionB().evaluate(context);
	}

}
