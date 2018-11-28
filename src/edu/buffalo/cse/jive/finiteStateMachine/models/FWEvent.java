package edu.buffalo.cse.jive.finiteStateMachine.models;

public class FWEvent {

	public String thread;
	public String object;
	public String field;
	public String value;
	public String method;

	public FWEvent(String t, String o, String f, String v, String m) {
		this.thread = t;
		this.object = o;
		this.field = f;
		this.value = v;
		this.method = m;
	}

	public String toString() {
		return object + "->" + field + "=" + value + "   (" + method + "," + thread + ")";
	}

	public String getThread() {
		return thread;
	}

	public String getObject() {
		return object;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}

	public String getMethod() {
		return method;
	}
}