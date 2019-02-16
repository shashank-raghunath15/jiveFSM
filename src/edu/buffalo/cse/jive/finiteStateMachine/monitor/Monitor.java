package edu.buffalo.cse.jive.finiteStateMachine.monitor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;
import edu.buffalo.cse.jive.finiteStateMachine.models.Event;
import edu.buffalo.cse.jive.finiteStateMachine.models.State;
import edu.buffalo.cse.jive.finiteStateMachine.models.TransitionBuilder;
import edu.buffalo.cse.jive.finiteStateMachine.util.DeepCopy;
import edu.buffalo.cse.jive.finiteStateMachine.util.Pair;

public abstract class Monitor implements Runnable {

	private Set<String> keyFields;
	private BlockingQueue<Event> source;
	private Map<State, Set<State>> states;
	private State rootState;
	private State previousState;

	public Monitor(Set<String> keyFields, BlockingQueue<Event> source) {
		this.keyFields = keyFields;
		this.source = source;
		this.states = new HashMap<State, Set<State>>();
		previousState = new State();
		for (String field : getKeyFields()) {
			previousState.getMap().put(field, null);
		}
	}

	protected boolean buildStates(Event event) {
		boolean result = false;
		if (keyFields.contains(event.getField())) {
			State newState = DeepCopy.deepCopy(previousState);
			newState.getMap().put(event.getField(), event.getValue());
			if (!newState.getMap().values().contains(null) && !previousState.getMap().values().contains(null)) {
				result = states.get(previousState).add(newState);
				if (!states.containsKey(newState))
					states.put(newState, new LinkedHashSet<State>());
			} else if (!newState.getMap().values().contains(null) && previousState.getMap().values().contains(null)) {
				states.put(newState, new LinkedHashSet<State>());
				rootState = newState;
			}
			previousState = newState;
		}
		return result;
	}

	private boolean validate(State current, State next, List<Expression> expressions) {
		boolean valid = true;
		for (Expression expression : expressions) {
			if (!expression.evaluate(new Context(current, next, states))) {
				valid = false;
				break;
			}
		}
		return valid;
	}

	public void buildTransitions(TransitionBuilder transitionBuilder) {
		transitionBuilder.addInitialState(rootState);
		buildTransitions(rootState, new HashSet<Pair<State, State>>(), transitionBuilder);
	}

	private void buildTransitions(State curr, Set<Pair<State, State>> visited, TransitionBuilder transitionBuilder) {
		for (State next : states.get(curr)) {
			if (visited.add(new Pair<State, State>(curr, next))) {
				buildTransitions(next, visited, transitionBuilder);
				transitionBuilder.addTransition(curr, next, true);
			}
		}
	}

	public void validateAndBuildTransitions(List<Expression> expressions, TransitionBuilder transitionBuilder) {
		transitionBuilder.addInitialState(rootState);
		validateAndBuildTransitions(null, rootState, new HashSet<Pair<State, State>>(), expressions, transitionBuilder);
	}

	private State validateAndBuildTransitions(State prev, State curr, Set<Pair<State, State>> visited,
			List<Expression> expressions, TransitionBuilder transitionBuilder) {
		boolean result = true;
		for (State next : states.get(curr)) {
			if (visited.add(new Pair<State, State>(curr, next)))
				if (!validate(curr, validateAndBuildTransitions(curr, next, visited, expressions, transitionBuilder),
						expressions))
					result = false;
		}
		if (prev != null) {
			transitionBuilder.addTransition(prev, curr, result);
		}
		return curr;
	}

	protected void printStates() {
		for (State key : states.keySet()) {
			System.out.print(key + " : ");
			for (State state : states.get(key)) {
				System.out.print(state + " ");
			}
			System.out.println();
		}
	}

	public Set<String> getKeyFields() {
		return keyFields;
	}

	public void setKeyFields(Set<String> keyFields) {
		this.keyFields = keyFields;
	}

	public BlockingQueue<Event> getSource() {
		return source;
	}

	public void setSource(BlockingQueue<Event> source) {
		this.source = source;
	}

	public Map<State, Set<State>> getStates() {
		return states;
	}

	public void setStates(Map<State, Set<State>> states) {
		this.states = states;
	}

	public State getRootState() {
		return rootState;
	}

	public void setRootState(State rootState) {
		this.rootState = rootState;
	}
}
