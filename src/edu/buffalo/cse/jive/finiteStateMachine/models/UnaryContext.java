package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.util.Map;

public class UnaryContext {

	public State state;
	protected Map<String, Integer> variableLookup;
	public State state2;

	public UnaryContext(Map<String, Integer> variableNameLookup, State previousState, State currentState) {
		this.state = previousState;
		this.variableLookup = variableNameLookup;
		this.state2 = currentState;
	}

	public String getValue(String varname) {
		if (varname.contains("\'")) {
			varname = varname.substring(0, varname.length()-1);
			int index = variableLookup.get(varname);
			if (state == null)
				return "";
			return state2.keyVar.get(index);
		}
		int index = variableLookup.get(varname);
		if (state == null)
			return "";
		return state.keyVar.get(index);
	}
}
