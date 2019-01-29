package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class State implements Serializable {

	private static final long serialVersionUID = -4135264377873998847L;
	public ArrayList<String> keyVar = new ArrayList<String>();
	public String method;
	public boolean hashed;
	public int time = 0;

	public ArrayList<String> getKeyVar() {
		return keyVar;
	}

	public void setKeyVar(ArrayList<String> keyVar) {
		this.keyVar = keyVar;
	}

	public boolean isHashed() {
		return hashed;
	}

	public void setHashed(boolean hashed) {
		this.hashed = hashed;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getSize() {
		return keyVar.size();
	}

	public void set(int index, String value) {
		keyVar.add(index, value);
	}

	public void set2(int index, String value) {
		keyVar.set(index, value);
	}

	public String get(int index) {
		return keyVar.get(index);
	}

	public void setMethod(String m) {
		method = m;
	}

	public String getMethod() {
		return method;
	}

	public void remove(int index) {
		keyVar.remove(index);
	}

	public void copy(State s) {
		for (int j = 0; j < s.getSize(); j++) {
			keyVar.add(j, s.get(j));
		}
		if (s.method != null)
			method = new String(s.method);
		time = s.time;
	}

	public String toString() {
		StringBuffer sbKeys = new StringBuffer();
		sbKeys.append(keyVar.get(0));
		for (int j = 1; j < keyVar.size(); j++) {
			sbKeys.append(",");
			sbKeys.append(keyVar.get(j));
		}
		return sbKeys.toString();
	}

	public void print(int flag) {
		if (flag == 0)
			System.out.println(toString());
		else if (flag == 1)
			System.out.println(toString() + " (" + method + ") " + hashed + " time=" + time);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State))
			return false;
		State newState = (State) obj;
		return toString().equals(newState.toString());
	}

	public State copy() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream stream = new ObjectOutputStream(outputStream);
			stream.writeObject(this);
			stream.flush();
			stream.close();
			outputStream.close();
			byte[] byteArray = outputStream.toByteArray();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			State state = (State) objectInputStream.readObject();
			inputStream.close();
			objectInputStream.close();
			return state;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (String key : keyVar) {
			hash *= key.hashCode();
		}
		return hash;
	}
}
