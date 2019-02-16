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
public class StringValueExpression extends ValueExpression implements Serializable {

	private static final long serialVersionUID = -2500056557550413594L;

	public StringValueExpression(String value) {
		super(value);
	}
}
