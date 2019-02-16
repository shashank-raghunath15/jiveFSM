package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import edu.buffalo.cse.jive.finiteStateMachine.expression.value.ValueExpression;

public class State implements Serializable {

	private static final long serialVersionUID = -4135264377873998847L;
	Map<String, ValueExpression> map = new HashMap<>();

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : map.keySet()) {
			stringBuilder.append(map.get(key));
			stringBuilder.append(",");
		}
		return stringBuilder.substring(0, stringBuilder.length() - 1).toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State))
			return false;
		State newState = (State) obj;
		return toString().equals(newState.toString());
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (String s : map.keySet()) {
			hash ^= s.hashCode() ^ map.get(s).hashCode();
		}
		return hash;
	}

	public Map<String, ValueExpression> getMap() {
		return map;
	}

	public void setMap(Map<String, ValueExpression> map) {
		this.map = map;
	}

}
