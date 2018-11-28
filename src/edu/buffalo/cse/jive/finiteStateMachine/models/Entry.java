package edu.buffalo.cse.jive.finiteStateMachine.models;

public class Entry {
	protected int kind; // 0 = Event, 1 = Node

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

}
