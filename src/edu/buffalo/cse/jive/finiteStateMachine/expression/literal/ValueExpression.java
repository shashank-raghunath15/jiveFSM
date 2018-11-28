package edu.buffalo.cse.jive.finiteStateMachine.expression.literal;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.Quantifiable;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

public abstract class ValueExpression extends Expression implements Quantifiable{

	public ValueExpression() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Expression getExpressionA() {
		return this;
	}
	
	//public abstract String getValue(UnaryContext ct);
	
	@Override
	public boolean evaluate(UnaryContext ct) {
		// TODO Auto-generated method stub
		return true; // not important for Value Expressions
	}
}
