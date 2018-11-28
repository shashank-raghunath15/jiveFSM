package edu.buffalo.cse.jive.finiteStateMachine.expression.relational;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.Quantifiable;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

public class NotEqualityExpression extends RelationalExpression {
	
	public NotEqualityExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotEqualityExpression(Expression a, Expression b) {
		super(a,b);
	}
	
	@Override
	public boolean evaluate(UnaryContext ct) {
		if (getExpressionA() instanceof Quantifiable && getExpressionB() instanceof Quantifiable) {
			String valueA = getExpressionA().getValue(ct);
			String valueB = getExpressionB().getValue(ct);
			if (valueA.compareTo(valueB) != 0)
				return true;
		}
		return getExpressionA().evaluate(ct) != getExpressionB().evaluate(ct);
	}

}
