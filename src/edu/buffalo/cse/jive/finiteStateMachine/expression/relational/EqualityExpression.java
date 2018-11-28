package edu.buffalo.cse.jive.finiteStateMachine.expression.relational;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.Quantifiable;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class EqualityExpression extends RelationalExpression {
	
	
	public EqualityExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EqualityExpression(Expression a, Expression b) {
		super(a,b);
	}

	@Override
	public boolean evaluate(UnaryContext ct) {
		if(getExpressionA() instanceof Quantifiable && getExpressionB() instanceof Quantifiable){
			String valueA = getExpressionA().getValue(ct);
			String valueB = getExpressionB().getValue(ct);
			if(valueA.equals(valueB))
				return true;
		}
		return getExpressionA().evaluate(ct) == getExpressionB().evaluate(ct);
	}
	
}
