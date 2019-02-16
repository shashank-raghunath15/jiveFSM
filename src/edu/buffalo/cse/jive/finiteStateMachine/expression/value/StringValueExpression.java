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
public class StringValueExpression extends ValueExpression implements Comparable<Expression> {

	private static final long serialVersionUID = 1L;
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public StringValueExpression(String value) {
		super();
		this.value = value;
	}

	@Override
	public int compareTo(Expression o) {
		if (o instanceof VariableExpression) {
			VariableExpression vexp = (VariableExpression) o;
			return getValue().compareTo(((StringValueExpression) vexp.getExpression()).getValue());
		} else if (o instanceof StringValueExpression)
			return getValue().compareTo(((StringValueExpression) o).getValue());
		else {
			System.out.println("Invalid");
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StringValueExpression) {
			StringValueExpression d = (StringValueExpression) obj;
			return getValue().equals(d.getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

	@Override
	public String toString() {
		return value;
	}
}
