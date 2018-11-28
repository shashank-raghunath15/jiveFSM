package edu.buffalo.cse.jive.finiteStateMachine.expression.logical;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.relational.RelationalExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class NotExpression extends LogicalExpression {

	private Expression relationalExpression;

	public NotExpression(Expression relationalExpression) {
		super();
		this.relationalExpression = relationalExpression;
	}

	public NotExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Expression getRelationalExpression() {
		return relationalExpression;
	}

	public void setRelationalExpression(Expression relationalExpression) {
		this.relationalExpression = relationalExpression;
	}

	@Override
	public void setExpressionA(Expression expression) {
		// TODO Auto-generated method stub
		this.relationalExpression = (RelationalExpression) expression;
	}
	
	public boolean evaluate(UnaryContext ct) {
		return !relationalExpression.evaluate(ct);
	}

}
