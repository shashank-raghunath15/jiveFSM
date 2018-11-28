/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.expression;

import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class GExpression extends Expression {

	private Expression expression;

	public GExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GExpression(Expression expression) {
		super();
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpressionA(Expression expression) {
		this.expression = expression;
	}

	@Override
	public Expression getExpressionA() {
		return expression;
	}

	@Override
	public boolean evaluate(UnaryContext ct) {
		return expression.evaluate(ct);
	}

}
