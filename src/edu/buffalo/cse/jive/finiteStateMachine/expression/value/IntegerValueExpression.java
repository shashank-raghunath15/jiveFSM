package edu.buffalo.cse.jive.finiteStateMachine.expression.value;

import java.io.Serializable;

public class IntegerValueExpression extends ValueExpression implements Serializable {

	private static final long serialVersionUID = 4418477864084466159L;

	public IntegerValueExpression(Integer value) {
		super(value);
	}
}
