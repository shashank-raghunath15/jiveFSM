package edu.buffalo.cse.jive.finiteStateMachine.expression.value;

import java.io.Serializable;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;

public abstract class ValueExpression extends Expression implements Serializable {

	private static final long serialVersionUID = 1L;
	private Context context;

	@Override
	public Boolean evaluate(Context context) {
		this.context = context;
		return true;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
