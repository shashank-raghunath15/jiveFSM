package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputFileParser {

	private Set<Attribute> allAttributes;
	private List<FWEvent> events;

	public InputFileParser(String fileName) {
		this.allAttributes = new HashSet<Attribute>();
		this.events = new ArrayList<FWEvent>();
		parseFile(fileName);
	}

	private void parseFile(String fileName) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("Field Write")) {
					String[] tokens = line.split(",");
					String object = tokens[4].substring(tokens[4].indexOf("=") + 1).replace("\"", "").trim();
					String field = tokens[5].substring(0, tokens[5].indexOf("=")).replace("\"", "").trim();
					String value = tokens[5].substring(tokens[5].indexOf("=") + 1).replace("\"", "").trim();
					Attribute attribute = new Attribute(object, field);
					events.add(new FWEvent(null, object, field, value, null));
					allAttributes.add(attribute);
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Set<Attribute> getAllAttributes() {
		return allAttributes;
	}

	public List<FWEvent> getEvents() {
		return events;
	}

}
