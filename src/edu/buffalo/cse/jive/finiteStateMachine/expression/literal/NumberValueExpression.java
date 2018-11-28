/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.expression.literal;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class NumberValueExpression extends ValueExpression implements Literal<Number> {

	private Number literal;

	public NumberValueExpression() {
		// TODO Auto-generated constructor stub
	}

	public NumberValueExpression(Number literal) {
		super();
		this.literal = literal;
	}

	public Number getLiteral() {
		return literal;
	}

	public void setLiteral(Number literal) {
		this.literal = literal;
	}

	@Override
	public void setExpressionA(Expression expression) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValue(UnaryContext ct) {
		return String.valueOf(literal);
	}

}
