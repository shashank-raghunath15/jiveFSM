/**
 * 
 */
package edu.buffalo.cse.jive.finiteStateMachine.expression.value;

import java.io.Serializable;

/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class DoubleValueExpression extends ValueExpression implements Serializable {

	private static final long serialVersionUID = 4906468591690502101L;

	public DoubleValueExpression(Double value) {
		super(value);
	}
}
