package edu.buffalo.cse.jive.finiteStateMachine.expression;

import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

public interface IExpression {

	public Expression getExpressionA();
	public void setExpressionA(Expression expression);
	public String getValue(UnaryContext ct);
	
}
