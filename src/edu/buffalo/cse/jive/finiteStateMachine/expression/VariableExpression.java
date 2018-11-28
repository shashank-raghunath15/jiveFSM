package edu.buffalo.cse.jive.finiteStateMachine.expression;

import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class VariableExpression extends Expression implements Quantifiable {

	private String name;

	public VariableExpression() {
		super();
		// TODO Auto-generated constructor stub
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
	public Expression getExpressionA() {
		return this;
	}

	@Override
	public void setExpressionA(Expression expression) {
		
		
	}
	
	public String getValue(UnaryContext ct) {  
		
		String value = ct.getValue(name);  
		return value;
	}

	@Override
	public boolean evaluate(UnaryContext ct) {
		// TODO Auto-generated method stub
		return true;  // not important for Variable Expressions
	}


	
}
