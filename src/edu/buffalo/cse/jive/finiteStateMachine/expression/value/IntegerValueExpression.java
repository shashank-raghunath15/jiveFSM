package edu.buffalo.cse.jive.finiteStateMachine.expression.value;

import edu.buffalo.cse.jive.finiteStateMachine.expression.core.VariableExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;

public class IntegerValueExpression extends ValueExpression implements Comparable<Expression> {

	private static final long serialVersionUID = 1L;
	private Integer value;

	public IntegerValueExpression(Integer value) {
		super();
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int compareTo(Expression o) {
		if (o instanceof VariableExpression) {
			VariableExpression vexp = (VariableExpression) o;
			return getValue().compareTo(((IntegerValueExpression) vexp.getExpression()).getValue());
		} else if (o instanceof IntegerValueExpression)
			return getValue().compareTo(((IntegerValueExpression) o).getValue());
		else {
			System.out.println("Invalid");
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IntegerValueExpression) {
			IntegerValueExpression d = (IntegerValueExpression) obj;
			return getValue().equals(d.getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

}
