package edu.buffalo.cse.jive.finiteStateMachine.models;

public class Transition {
	String current;
	String next;
	String method;
	int count;

	Transition(String c, String n, String m) {
		current = c;
		next = n;
		method = m;
		count = 1;
	}

	public String toString() {
		return "\"" + current + "\"" + " --> " + "[" + count + "]" + "\"" + next + "\"";
	}
}
