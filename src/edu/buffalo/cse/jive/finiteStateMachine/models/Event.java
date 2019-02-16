package edu.buffalo.cse.jive.finiteStateMachine.models;

import edu.buffalo.cse.jive.finiteStateMachine.expression.value.DoubleValueExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.value.IntegerValueExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.value.StringValueExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.value.ValueExpression;

public class Event {

	private String field;
	private ValueExpression value;

	public Event(String field, String value) {
		this.field = field;
		try {
			this.value = new IntegerValueExpression(Integer.parseInt(value));
		} catch (Exception e) {
			try {
				this.value = new DoubleValueExpression(Double.parseDouble(value));
			} catch (Exception e2) {
				this.value = new StringValueExpression(value);
			}
		}
	}

	public String getField() {
		return this.field;
	}

	public ValueExpression getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return field + " " + value;
	}

}
