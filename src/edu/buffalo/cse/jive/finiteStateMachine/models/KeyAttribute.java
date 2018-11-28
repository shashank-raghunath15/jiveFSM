package edu.buffalo.cse.jive.finiteStateMachine.models;

public class KeyAttribute {
	public String object;
	public String field;

	public KeyAttribute(String o, String f) {
		this.object = o;
		this.field = f;
	}

	public String toString() {
		return object + "->" + field;
	}

	public String getObject() {
		return object;
	}

	public String getField() {
		return field;
	}
}
