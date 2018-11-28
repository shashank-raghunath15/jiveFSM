package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
	
	private Map<String,Set<String>> edges = new HashMap<String,Set<String>>();
	
	public void addEdge(String head, String tail) {
		Set<String> transitions=edges.get(head);
		if (transitions == null) {
			transitions=new HashSet<String>();
			edges.put(head, transitions);
		}
		transitions.add(tail);
	}
	
	public Set<String> getNextStates(String curState) {
		return edges.get(curState);
	}
	
	public String toString() {
		String retVal="";
		for (String key: edges.keySet()) {
			retVal+=key+"->[";
			Set<String> vals = edges.get(key);
			for (String dest: vals) {
				retVal=retVal+" "+dest+" ";
			}
			retVal+="]\n";
		}
		return retVal;
	}

}
