package edu.buffalo.cse.jive.finiteStateMachine.models;

public class Context {

	private State currentState;
	private State nextState;

	public Context(State currentState, State nextState) {
		this.currentState = currentState;
		this.nextState = nextState;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public State getNextState() {
		return nextState;
	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(currentState.toString());
		builder.append("\n");
		builder.append(nextState.toString());
		return builder.toString();
	}
}
