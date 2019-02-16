/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.expression.core;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.UnaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;
import edu.buffalo.cse.jive.finiteStateMachine.monitor.Monitor;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class FExpression extends UnaryExpression<Expression> {

	public FExpression() {
		super();
	}

	public FExpression(Expression expression) {
		super(expression);
	}

	@Override
	public Boolean evaluate(Context context) {
		if (getExpression().evaluate(context)) {
			Monitor.fExpressions.remove(this);
		} else {
			Monitor.fExpressions.add(this);
		}
		return true;
	}
}
