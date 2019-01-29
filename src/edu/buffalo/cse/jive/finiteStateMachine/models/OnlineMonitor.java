package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;

public class OnlineMonitor implements Runnable {

	private Map<String, Integer> attributeMap = new HashMap<>();
	private Map<State, Set<State>> states = new HashMap<>();
	private State previousState;
	private List<Expression> expressions;
	private Map<String, Integer> variableMap = new HashMap<>();
	public static Set<Expression> fExpressions = new HashSet<>();
	private TransitionBuilder transitionBuilder;
	private ConcurrentLinkedQueue<String> incomingStates;

	public OnlineMonitor(ConcurrentLinkedQueue<String> incomingStates, List<Attribute> keyAttributes,
			List<Expression> expressions, TransitionBuilder transitionBuilder) {
		this.incomingStates = incomingStates;
		this.expressions = expressions;
		this.transitionBuilder = transitionBuilder;
		buildMaps(keyAttributes);
		previousState = new State();
		for (int i = 0; i < keyAttributes.size(); i++) {
			previousState.set(i, "null");
		}
		previousState.setTime(0);
		previousState.setMethod("init");
		states.put(previousState, new HashSet<>());
		transitionBuilder.addInitialState(previousState);
		printAttributeMap();
	}

	public Map<String, Integer> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, Integer> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public Map<State, Set<State>> getStates() {
		return states;
	}

	public void setStates(Map<State, Set<State>> states) {
		this.states = states;
	}

	public State getPreviousState() {
		return previousState;
	}

	public void setPreviousState(State previousState) {
		this.previousState = previousState;
	}

	@Override
	public void run() {
		String message;
		while (true) {
			while ((message = incomingStates.poll()) != null) {
				String[] tokens = message.split(",");
				String object = tokens[0].substring(tokens[0].indexOf("=") + 1).replace("\"", "").trim();
				String field = tokens[1].substring(0, tokens[1].indexOf("=")).replace("\"", "").trim();
				String value = tokens[1].substring(tokens[1].indexOf("=") + 1).replace("\"", "").trim();

				if (value.equals(""))
					value = " ";
				if (value.equals("start"))
					value = "_start";
				State newState = previousState.copy();
				newState.set2(attributeMap.get(object + "," + field), value);
				boolean result = true;
				if (states.get(previousState).add(newState) && !newState.keyVar.contains("null")) {
					if(expressions !=null)
						result = validate(newState);
				}
				addTransition(newState, result);
				if (!states.containsKey(newState)) {
					states.put(newState, new HashSet<>());
				}
				previousState = newState;
			}
		}
	}

	private void buildMaps(List<Attribute> attributes) {
		for (int i = 0; i < attributes.size(); i++) {
			attributeMap.put(attributes.get(i).object + "," + attributes.get(i).field, i);
			variableMap.put(attributes.get(i).field, i);
		}
	}

	private void printAttributeMap() {
		for (String s : attributeMap.keySet()) {
			System.out.println(s + " " + attributeMap.get(s));
		}
	}

	public boolean validate(State state) {
		boolean valid = true;
		for (Expression expression : expressions) {
			if (!expression.evaluate(new UnaryContext(variableMap, previousState, state))) {
				valid = false;
				break;
			}
		}
		for (Expression expression : fExpressions) {
			if (!expression.evaluate(new UnaryContext(variableMap, previousState, state))) {
				fExpressions.remove(expression);
			}
		}
		return valid;
	}

	public void addTransition(State state, boolean valid) {
		if (valid) {
			transitionBuilder.addTransition(previousState, state);
		} else {
			transitionBuilder.addColorTransition(previousState, state, "red");
		}
	}
}
