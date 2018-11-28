package edu.buffalo.cse.jive.finiteStateMachine.expression.relational;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.IBinaryExpression;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public abstract class RelationalExpression extends Expression implements IBinaryExpression{

	private Expression expressionA;
	private Expression expressionB;

	public RelationalExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RelationalExpression(Expression expressionA, Expression expressionB) {
		super();
		this.expressionA = expressionA;
		this.expressionB = expressionB;
	}

	public Expression getExpressionA() {
		return expressionA;
	}

	public void setExpressionA(Expression expressionA) {
		this.expressionA = expressionA;
	}

	public Expression getExpressionB() {
		return expressionB;
	}

	public void setExpressionB(Expression expressionB) {
		this.expressionB = expressionB;
	}
	

}
