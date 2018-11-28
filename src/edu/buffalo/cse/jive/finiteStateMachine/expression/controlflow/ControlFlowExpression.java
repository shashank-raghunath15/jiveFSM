package edu.buffalo.cse.jive.finiteStateMachine.expression.controlflow;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

public class ControlFlowExpression extends Expression {
	private Expression expression;
	private RangeValue rangeValue;

	@Override
	public Expression getExpressionA() {
		// TODO Auto-generated method stub
		return expression;
	}

	@Override
	public void setExpressionA(Expression expression) {
		this.expression = expression;

	}

	public RangeValue getRangeValue() {
		return rangeValue;
	}

	public void setRangeValue(RangeValue rangeValue) {
		this.rangeValue = rangeValue;
	}

	@Override
	public boolean evaluate(UnaryContext ct) {
		// TODO Auto-generated method stub
		return false;
	}
}
