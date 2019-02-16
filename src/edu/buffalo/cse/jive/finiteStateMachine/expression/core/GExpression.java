/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.expression.core;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.UnaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class GExpression extends UnaryExpression<Expression> {

	
	public GExpression() {
		super();
	}

	public GExpression(Expression expression) {
		super(expression);
	}

	@Override
	public Boolean evaluate(Context context) {
		return getExpression().evaluate(context);
	}

}
