package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.util.Map;

public class BinaryContext extends UnaryContext {

	public State state2;

	public BinaryContext(Map<String, Integer> variableNameLookup, State node1, State node2) {
		super(variableNameLookup, node1, node2);
		this.state2 = node2;
	}

	public String getValue2(String varname) {
		int index = this.variableLookup.get(varname);
		if (state2 == null)
			return "";

		return state2.keyVar.get(index);
	}
}
