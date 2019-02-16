package edu.buffalo.cse.jive.finiteStateMachine.expression.core;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.UnaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class VariableExpression extends UnaryExpression<Expression> implements Comparable<Expression> {

	private String name;

	public VariableExpression(String name, Expression expression) {
		super(expression);
		this.name = name;
	}

	public VariableExpression(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Boolean evaluate(Context context) {
		if (name.contains("'")) {
			String n = name.substring(0, name.length() - 1);
			setExpression(context.getNextState() == null ? null : context.getNextState().getMap().get(n));
		} else {
			setExpression(context.getCurrentState().getMap().get(name));
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int compareTo(Expression o) {
		try {
			Comparable comparableA = (Comparable) getExpression();
			Comparable comparableB = (Comparable) o;
			return comparableA.compareTo(comparableB);
		} catch (ClassCastException e) {
			return 0;
		}
	}
}
