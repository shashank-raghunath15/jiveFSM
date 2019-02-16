/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.expression.value;

import edu.buffalo.cse.jive.finiteStateMachine.expression.core.VariableExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class DoubleValueExpression extends ValueExpression implements Comparable<Expression> {

	private static final long serialVersionUID = 1L;
	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public DoubleValueExpression(Double value) {
		super();
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int compareTo(Expression arg0) {
		if (arg0 instanceof VariableExpression) {
			VariableExpression vexp = (VariableExpression) arg0;
			return getValue().compareTo(((DoubleValueExpression) vexp.getExpression()).getValue());
		} else if (arg0 instanceof DoubleValueExpression)
			return getValue().compareTo(((DoubleValueExpression) arg0).getValue());
		else {
			System.out.println("Invalid");
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DoubleValueExpression) {
			DoubleValueExpression d = (DoubleValueExpression) obj;
			return getValue().equals(d.getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}
}
