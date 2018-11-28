package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.util.Map;

public class UnaryContext {

	public State state;
	protected Map<String, Integer> variableLookup;
	public State state2;

	public UnaryContext(Map<String, Integer> variableNameLookup, State node, State previousState) {
		this.state = node;
		this.variableLookup = variableNameLookup;
		this.state2 = previousState;
	}

	public String getValue(String varname) {
		int index = variableLookup.get(varname);
		if (state == null)
			return "";

		return state.keyVar.get(index);
	}
}
