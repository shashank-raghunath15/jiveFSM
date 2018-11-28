package edu.buffalo.cse.jive.finiteStateMachine.expression;

import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */

// treating assignment as equality for now
public class AssignmentExpression extends Expression implements IBinaryExpression { 

	private VariableExpression variable;
	private Expression expression;

	public AssignmentExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AssignmentExpression(VariableExpression variable, Expression expression) {
		super();
		this.variable = variable;
		this.expression = expression;
	}

	public VariableExpression getVariable() {
		return variable;
	}

	public void setVariable(VariableExpression variable) {
		this.variable = variable;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public Expression getExpressionA() {
		return variable;
	}

	@Override
	public Expression getExpressionB() {
		// TODO Auto-generated method stub
		return expression;
	}

	@Override
	public void setExpressionA(Expression expression) {
		// TODO Auto-generated method stub
		this.variable = (VariableExpression) expression;
	}

	@Override
	public void setExpressionB(Expression expression) {
		// TODO Auto-generated method stub
		this.expression = expression;
	}

	@Override
	public boolean evaluate(UnaryContext ct) {
		boolean isA =  getExpressionA() instanceof Quantifiable;
		boolean isB =  getExpressionB() instanceof Quantifiable;
		if(isA && isB){
			String valueA = getExpressionA().getValue(ct);
			String valueB = getExpressionB().getValue(ct);
			/*System.out.println("value for A "+valueA);
			System.out.println("value for B " +valueB);*/
			
			if(valueA.equals(valueB))
				return true;
			else
				return false;
		}
		System.out.println("not quantifiable A:"+getExpressionA().evaluate(ct)+" B:"+getExpressionB().evaluate(ct));
		return getExpressionA().evaluate(ct) == getExpressionB().evaluate(ct);
	}

	@Override
	public String getValue(UnaryContext ct) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
