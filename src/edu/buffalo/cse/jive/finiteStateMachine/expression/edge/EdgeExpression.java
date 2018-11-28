package edu.buffalo.cse.jive.finiteStateMachine.expression.edge;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.IBinaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.State;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

public class EdgeExpression extends Expression implements IBinaryExpression {
	private VectorExpression expressionA;
	private VectorExpression expressionB;

	public Expression getExpressionA() {
		// TODO Auto-generated method stub
		return expressionA;
	}

	public void setExpressionA(Expression expression) {
		this.expressionA = (VectorExpression) expression;

	}

	public Expression getExpressionB() {
		// TODO Auto-generated method stub
		return expressionB;
	}

	public void setExpressionB(Expression expression) {
		this.expressionB = (VectorExpression) expression;
	}

	@Override
	public boolean evaluate(UnaryContext ct) {
		State nextState = ct.state;
		State previousState = ct.state2;
		if(nextState == null || previousState == null) return false;
		return nextState.keyVar.equals(expressionA.getVectorValue())
				&& previousState.getKeyVar().equals(expressionB.getVectorValue());
	}

}
