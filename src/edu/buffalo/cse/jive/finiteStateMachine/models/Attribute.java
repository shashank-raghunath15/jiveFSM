package edu.buffalo.cse.jive.finiteStateMachine.models;

public class Attribute {
	public String object;
	public String field;

	public Attribute(String o, String f) {
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

	@Override
	public boolean equals(Object obj) {
		return this.toString().equalsIgnoreCase(((Attribute) obj).toString());
	}

	@Override
	public int hashCode() {
		return object.hashCode() * field.hashCode();
	}
}
