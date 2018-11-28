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
public class AExpression extends Expression {

	private Expression expression;

	public AExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AExpression(Expression expression) {
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
		// TODO Auto-generated method stub
		this.expression = expression;
	}

	@Override
	public boolean evaluate(UnaryContext ct) {
		//setContext(ct);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getValue(UnaryContext ct) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
