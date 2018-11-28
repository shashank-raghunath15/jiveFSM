package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.util.Arrays;
/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Operators {

	// TODO - Set precedence value
	public static final Map<String, Integer> OPERATOR_PRECEDENCE;
	static {
		Map<String, Integer> map = new HashMap<>();
		map.put("+", 11);
		map.put("-", 11);
		map.put("/", 12);
		map.put("*", 12);
		map.put("^", 13);
		map.put("->", 16);
		map.put("||", 17);
		map.put("&&", 17);
		map.put("==", 17);
		map.put("!=", 17);
		map.put(">=", 9);
		map.put("<=", 9);
		map.put("<", 17);
		map.put(">", 17);
		map.put("!", 14);
		map.put("=", 18);
		map.put("=>", 18);
		map.put("A", 18);
		map.put("G", 18);
		OPERATOR_PRECEDENCE = Collections.unmodifiableMap(map);
	}

	public static boolean isOperator(String operator) {
		return OPERATOR_PRECEDENCE.containsKey(operator);
	}

	public static boolean isUnaryOperator(String input) {
		return Arrays.asList("A", "G", "!").contains(input);
	}

	public static boolean isBinaryOperator(String input) {
		return Arrays.asList("+", "-", "*", "/", "->","=>", "=", "==", ">=", "<=", ">", "<", "!=", "&&", "||")
				.contains(input);
	}

	public static String getOperator(int value) {
		switch (value) {
		case 0:
			return ";";
		case 1:
			return ",";
		case 2:
			return "+";
		case 3:
			return "-";
		case 4:
			return "*";
		case 5:
			return "/";
		case 6:
			return "=";
		case 7:
			return ">";
		case 8:
			return "<";
		case 9:
			return "<=";
		case 10:
			return ">=";
		case 11:
			return "==";
		case 12:
			return "!=";
		case 13:
			return "(";
		case 14:
			return ")";
		case 15:
			return "{";
		case 16:
			return "}";
		case 25:
			return "G";
		case 26:
			return "A";
		case 27:
			return "->";
		case 28:
			return "[";
		case 29:
			return "]";
		case 30:
			return "&&";
		case 31:
			return "||";
		case 32:
			return "!";
		case 33:
			return "=>";
		default:
			return null;
		}
	}
}
