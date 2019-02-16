package edu.buffalo.cse.jive.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.buffalo.cse.jive.finiteStateMachine.expression.value.IntegerValueExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.State;
import edu.buffalo.cse.jive.finiteStateMachine.util.DeepCopy;

class DeepCopyTest {

	@Test
	final void testDeepCopy() {
		State state = new State();

		state.getMap().put("a", new IntegerValueExpression(2));
		state.getMap().put("b", new IntegerValueExpression(3));
		State state2 = DeepCopy.deepCopy(state);
		System.out.println(state.getMap().entrySet().equals(state2.getMap().entrySet()));
		System.out.println(new IntegerValueExpression(2).equals(new IntegerValueExpression(2)));
		Set<State> set = new LinkedHashSet<>();
		System.out.println(set.add(state));
		System.out.println(set.add(state2));
	}

}
