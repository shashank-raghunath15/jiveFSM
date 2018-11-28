package edu.buffalo.cse.jive.finiteStateMachine.expression;

import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;
import edu.buffalo.cse.jive.finiteStateMachine.models.BinaryContext;
import edu.buffalo.cse.jive.finiteStateMachine.models.State;

public class StateExpression extends Expression {

	private State state1;
	private State state2;
	private Expression expressionA;

	@Override
	public Expression getExpressionA() {
		return expressionA;
	}

	@Override
	public void setExpressionA(Expression expression) {
		this.expressionA = expression;
	}

	public boolean evaluate(BinaryContext ct) {

		return false;
	}

	public State getState1() {
		return state1;
	}

	public void setState1(State state1) {
		this.state1 = state1;
	}

	public State getState2() {
		return state2;
	}

	public void setState2(State state2) {
		this.state2 = state2;
	}

	@Override
	public boolean evaluate(UnaryContext ct) {
		// TODO Auto-generated method stub
		return false;
	}

}
