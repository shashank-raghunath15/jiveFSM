package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.util.HashSet;
import java.util.Set;

public class TransitionBuilder {

	private StringBuilder transitions;
	private Set<String> transitionSet;

	public TransitionBuilder() {
		transitions = new StringBuilder();
		transitionSet = new HashSet<String>();
		transitions.append("@startuml\n");
	}

	public void addInitialState(State state) {
		this.transitions.append("(*) --> " + "\"" + state.toString() + "\"");
		addNewLine();
	}

	public String getTransitions() {
		return new StringBuilder(transitions).append("@enduml\n").toString();
	}

	public void addTransition(State state1, State state2) {
		String s = "\"" + state1.toString() + "\"" + " --> " + "\"" + state2.toString() + "\"";
		if (transitionSet.add(s)) {
			this.transitions.append(s);
			addNewLine();
		}
	}

	public void addColorTransition(State state1, State state2, String color) {
		String s = "\"" + state1.toString() + "\"" + " --> " + "\"" + state2.toString() + "\"" + " #" + color;
		if (transitionSet.add(s)) {
			this.transitions.append(s);
			addNewLine();
		}
	}

	private void addNewLine() {
		this.transitions.append("\n");
	}
}
