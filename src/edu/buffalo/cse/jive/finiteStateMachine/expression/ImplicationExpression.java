package edu.buffalo.cse.jive.finiteStateMachine.expression;

import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class ImplicationExpression extends Expression implements IBinaryExpression {

	private Expression expressionA;
	private Expression expressionB;

	public ImplicationExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImplicationExpression(Expression expressionA, Expression expressionB) {
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

	public boolean evaluate(UnaryContext ct) {
		if (expressionA.evaluate(ct)) {
			if (expressionB.evaluate(ct)) {
				return true;
			}
			return false;
		}
		return true;
		// boolean isA = expressionA.evaluate(ct);
		// boolean isB = expressionB.evaluate(ct);
		// //System.out.println("expr A:"+isA+" expr B:"+isB);
		// if(isA && isB)
		// return true;
		// else if(!isA)
		// return true;
		// return false;
	}

	@Override
	public String getValue(UnaryContext ct) {
		// TODO Auto-generated method stub
		return null;
	}

}
