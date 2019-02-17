/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.expression.core;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.UnaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;
import edu.buffalo.cse.jive.finiteStateMachine.models.State;
import edu.buffalo.cse.jive.finiteStateMachine.util.Pair;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class GExpression extends UnaryExpression<Expression> {

	private boolean result;

	public GExpression() {
		super();
		this.result = true;
	}

	public GExpression(Expression expression) {
		super(expression);
	}

	@Override
	public Boolean evaluate(Context context) {
		evaluate(null, context.getCurrentState(), new HashSet<Pair<State, State>>(), getExpression(),
				context.getStates());
		return result;
	}

	private State evaluate(State prev, State curr, Set<Pair<State, State>> visited, Expression expression,
			Map<State, Set<State>> states) {
		for (State next : states.get(curr)) {
			if (visited.add(new Pair<State, State>(curr, next)))
				result = expression.evaluate(
						new Context(curr, evaluate(curr, next, visited, expression, states), states)) && result;
		}
		curr.setValid(result);
		return curr;
	}
}
