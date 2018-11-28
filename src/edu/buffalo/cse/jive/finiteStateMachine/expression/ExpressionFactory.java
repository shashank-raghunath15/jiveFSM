package edu.buffalo.cse.jive.finiteStateMachine.expression;

import edu.buffalo.cse.jive.finiteStateMachine.expression.arithmetic.AdditionExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.arithmetic.DivisionExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.arithmetic.MultiplicationExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.arithmetic.SubtractionExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.edge.EdgeExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.edge.VectorExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.literal.BooleanValueExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.literal.NumberValueExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.literal.StringValueExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.logical.AndExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.logical.OrExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.relational.EqualityExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.relational.GreaterThanExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.relational.LessThanExpression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.relational.NotEqualityExpression;
import edu.buffalo.cse.jive.finiteStateMachine.models.Tokenizer;

public class ExpressionFactory {

	public ExpressionFactory() {
		// TODO Auto-generated constructor stub
	}

	public static Expression getExpression(String input) {
		switch (input) {
		case "A":
			return new AExpression();
		case "G":
			return new GExpression();
		case "+":
			return new AdditionExpression();
		case "-":
			return new SubtractionExpression();
		case "/":
			return new DivisionExpression();
		case "*":
			return new MultiplicationExpression();
		case "->":
			return new ImplicationExpression();
		case "==":
			return new EqualityExpression();
		case "!=":
			return new NotEqualityExpression();
		case "&&":
			return new AndExpression();
		case "||":
			return new OrExpression();
		case "=":
			return new AssignmentExpression();
		case ">":
			return new GreaterThanExpression();
		case "<":
			return new LessThanExpression();
		case "true":
			return new BooleanValueExpression(Boolean.TRUE);
		case "false":
			return new BooleanValueExpression(Boolean.FALSE);
		case "=>":
			return new EdgeExpression();
		default:
			if (input.matches("([+-]?\\d*\\.\\d+)(?![-+0-9\\.])")) {
				return new NumberValueExpression(Float.valueOf(input));
			} else if (input.matches("\\d+")) {
				return new NumberValueExpression(Integer.valueOf(input));
			} else if (Tokenizer.idList.contains(input)) {
				return new VariableExpression(input);
			} else if (input.startsWith("<")) {
				return new VectorExpression(input);
			} else {
				return new StringValueExpression(input);
			}
		}
	}

}
