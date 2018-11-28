package edu.buffalo.cse.jive.finiteStateMachine.expression.arithmetic;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Quantifiable;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class AdditionExpression extends ArithmeticExpression {
	
	public boolean evaluate(UnaryContext ct) {
	
		return true;
	}
	public String getValue(UnaryContext ct)
	{
		boolean isA = getExpressionA() instanceof Quantifiable;
		boolean isB = getExpressionB() instanceof Quantifiable;
		if (isA && isB) {
			String valueA = getExpressionA().getValue(ct);
			String valueB = getExpressionB().getValue(ct);
			if (isNumeric(valueA) && isNumeric(valueB))
				return  String.valueOf(Integer.parseInt(valueA) + Integer.parseInt(valueB));
		}
		return "Invalid";
	}
}
