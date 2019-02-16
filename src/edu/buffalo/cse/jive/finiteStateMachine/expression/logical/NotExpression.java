package edu.buffalo.cse.jive.finiteStateMachine.expression.logical;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.UnaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.relational.RelationalExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class NotExpression extends UnaryExpression<RelationalExpression> {

	public NotExpression() {
		super();
	}

	public NotExpression(RelationalExpression expression) {
		super(expression);
	}

	public Boolean evaluate(Context ct) {
		return !getExpression().evaluate(ct);
	}

}
