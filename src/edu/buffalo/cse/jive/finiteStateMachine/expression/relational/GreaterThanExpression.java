package edu.buffalo.cse.jive.finiteStateMachine.expression.relational;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Quantifiable;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 * 
 */
public class GreaterThanExpression extends RelationalExpression {

	@Override
	public boolean evaluate(UnaryContext ct) {
		if (getExpressionA() instanceof Quantifiable && getExpressionB() instanceof Quantifiable) {
			String valueA = getExpressionA().getValue(ct);
			String valueB = getExpressionB().getValue(ct);
			if (valueA.compareTo(valueB) > 0)
				return true;
		}
		return false;
	}
}
