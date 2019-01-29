package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;

public class OfflineMonitor {

	private Map<String, Integer> attributeMap = new HashMap<>();
	private Map<State, Set<State>> states = new HashMap<>();
	private List<Expression> expressions;
	private TransitionBuilder transitionBuilder;
	private Map<String, Integer> variableMap = new HashMap<>();
	public static Set<Expression> fExpressions = new HashSet<>();
	private State previousState;

	public OfflineMonitor(List<FWEvent> list, List<Expression> expressions, List<Attribute> keyAttributes,
			TransitionBuilder transitionBuilder) {
		this.expressions = expressions;
		this.transitionBuilder = transitionBuilder;
		buildMaps(keyAttributes);
		printAttributeMap();
		createTransitions(list, keyAttributes);
	}

	private void buildMaps(List<Attribute> attributes) {
		for (int i = 0; i < attributes.size(); i++) {
			attributeMap.put(attributes.get(i).object + "," + attributes.get(i).field, i);
			variableMap.put(attributes.get(i).field, i);
		}
	}

	private void createTransitions(List<FWEvent> events, List<Attribute> keyAttributes) {
		previousState = new State();
		for (int i = 0; i < keyAttributes.size(); i++) {
			previousState.set(i, "null");
		}
		previousState.setTime(0);
		previousState.setMethod("init");
		states.put(previousState, new HashSet<>());
		transitionBuilder.addInitialState(previousState);
		for (FWEvent event : events) {
			if (attributeMap.containsKey(event.object + "," + event.field)) {
				State newState = previousState.copy();
				newState.set2(attributeMap.get(event.object + "," + event.field), event.value);
				boolean result = true;
				if (states.get(previousState).add(newState) && !newState.keyVar.contains("null")) {
					if (expressions != null)
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
