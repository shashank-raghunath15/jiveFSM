package edu.buffalo.cse.jive.finiteStateMachine.expression.arithmetic;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.IBinaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.Quantifiable;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

public abstract class ArithmeticExpression extends Expression implements IBinaryExpression,Quantifiable {
	
	String value;

	private Expression expressionA;
	private Expression expressionB;

	public ArithmeticExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArithmeticExpression(Expression expressionA, Expression expressionB) {
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
	
	@Override
	public boolean evaluate(UnaryContext ct) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
}
