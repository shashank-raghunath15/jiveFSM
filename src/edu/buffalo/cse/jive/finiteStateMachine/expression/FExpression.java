/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.expression;

import edu.buffalo.cse.jive.finiteStateMachine.models.OnlineMonitor;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class FExpression extends Expression {
	private Expression expression;

	public FExpression() {
		super();
	}

	public FExpression(Expression expression) {
		super();
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public Expression getExpressionA() {
		return expression;
	}

	@Override
	public void setExpressionA(Expression expression) {
		this.expression = expression;
	}

	@Override
	public boolean evaluate(UnaryContext ct) {
		if (expression.evaluate(ct)) {
			OnlineMonitor.fExpressions.remove(this);
		} else {
			OnlineMonitor.fExpressions.add(this);
		}
		return true;
	}

	@Override
	public String getValue(UnaryContext ct) {
		return null;
	}
}
