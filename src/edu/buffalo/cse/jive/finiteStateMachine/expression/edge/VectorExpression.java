package edu.buffalo.cse.jive.finiteStateMachine.expression.edge;

import java.util.ArrayList;
import java.util.List;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.literal.ValueExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.State;
import edu.buffalo.cse.jive.finiteStateMachine.models.UnaryContext;

public class VectorExpression extends ValueExpression {
	
	private List<String> vectorValue;
	
	public VectorExpression(String vectorString) {
		int len = vectorString.length();
		vectorString = vectorString.substring(1,len-1);
		String [] token = vectorString.split(",");
		List<String> vector = new ArrayList<>();
		for(String str : token) {
			vector.add(str);
		}
		setVectorValue(vector);
	}

	@Override
	public void setExpressionA(Expression expression) {
		// TODO Auto-generated method stub
		
	}

	public List<String> getVectorValue() {
		return vectorValue;
	}

	public void setVectorValue(List<String> vectorValue) {
		this.vectorValue = vectorValue;
	}

	@Override
	public boolean evaluate(UnaryContext ct) {

		return false;
	}

}
