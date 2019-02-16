package edu.buffalo.cse.jive.finiteStateMachine.expression.arithmetic;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.BinaryExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.value.ValueExpression;

public abstract class ArithmeticExpression extends BinaryExpression<ValueExpression, ValueExpression> {

	public ArithmeticExpression() {
		super();
	}

	public ArithmeticExpression(ValueExpression expressionA, ValueExpression expressionB) {
		super(expressionA, expressionB);
	}

}
