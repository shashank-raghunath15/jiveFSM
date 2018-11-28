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
public class StringValueExpression extends ValueExpression implements Literal<String> {

	private String literal;

	public StringValueExpression() {
		// TODO Auto-generated constructor stub
	}

	public StringValueExpression(String literal) {
		super();
		this.literal = literal;
	}

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	@Override
	public void setExpressionA(Expression expression) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getValue(UnaryContext ct) {
		return literal;
	}

}
