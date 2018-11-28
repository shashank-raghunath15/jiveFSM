package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class StateDiagram {

	private String traceFile;
	// private String keyAttributes; // Not necessary to set. Getting from the UI
	// now.
	private TreeSet<String> attributes = new TreeSet<>(); // Set of attributes
	private TreeMap<String, Integer> attributesMap = new TreeMap<String, Integer>();
	private ArrayList<FWEvent> fwevents = new ArrayList<FWEvent>(); // All field write events
	private ArrayList<KeyAttribute> keys = new ArrayList<KeyAttribute>(); // Key attributes -comma separated
	ArrayList<State> states = new ArrayList<State>(); // Sequence of original states
	ArrayList<State> paStates = new ArrayList<State>(); // Sequence of predicated states
	private LinkedHashMap<String, Integer> transitions = new LinkedHashMap<String, Integer>(); // Transitions
	private ArrayList<String> allTransitions = new ArrayList<String>();

	public String getTraceFile() {
		return traceFile;
	}

	public void setTraceFile(String traceFile) {
		this.traceFile = traceFile;
	}

	public TreeSet<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(TreeSet<String> attributes) {
		this.attributes = attributes;
	}

	public TreeMap<String, Integer> getAttributesMap() {
		return attributesMap;
	}

	public void setAttributesMap(TreeMap<String, Integer> attributesMap) {
		this.attributesMap = attributesMap;
	}

	public ArrayList<FWEvent> getFwevents() {
		return fwevents;
	}

	public void setFwevents(ArrayList<FWEvent> fwevents) {
		this.fwevents = fwevents;
	}

	public ArrayList<KeyAttribute> getKeys() {
		return keys;
	}

	public void setKeys(ArrayList<KeyAttribute> keys) {
		this.keys = keys;
	}

	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	public ArrayList<State> getPaStates() {
		return paStates;
	}

	public void setPaStates(ArrayList<State> paStates) {
		this.paStates = paStates;
	}

	public LinkedHashMap<String, Integer> getTransitions() {
		return transitions;
	}

	public void setTransitions(LinkedHashMap<String, Integer> transitions) {
		this.transitions = transitions;
	}

	public ArrayList<String> getAllTransitions() {
		return allTransitions;
	}

	public void setAllTransitions(ArrayList<String> allTransitions) {
		this.allTransitions = allTransitions;
	}

	public void readEvents(int numEvents, Text repeatText, Combo attributeList, Button[] granularity) {

		ArrayList<String> sortedAttributes = new ArrayList<String>();
		HashMap<String, Stack<String>> callStacks = new HashMap<String, Stack<String>>();
		String lastMethod = null;

		String strLine = null;
		try {
			Scanner sc = new Scanner(new File(traceFile));
			int counter = 0; // Added to distinguish method instance for Jevitha's BCI
			while (sc.hasNext()) {
				strLine = sc.nextLine().trim();
				// Inserting the logic for detecting loops - not complete
				// Event e = new Event(strLine.replace("\"","")); // replace quotations
				// e.print();
				// allEvents.add(e); // allEvents will be processed further to make call tree
				// Logic for detecting loops ends here

				// Logic for method consolidation
				if (strLine.contains("Method Call")) {
					String thrd = strLine.substring(0, strLine.indexOf(',')).replace("\"", "").trim();
					lastMethod = strLine.substring(strLine.indexOf("target=") + 7);
					lastMethod = lastMethod.substring(lastMethod.indexOf('#') + 1).replace("\"", "").trim();
					lastMethod = lastMethod + ":" + counter++;
					// lastMethod = lastMethod + "#" + thrd;
					if (callStacks.containsKey(thrd)) {
						Stack<String> threadStack = callStacks.get(thrd);
						threadStack.push(lastMethod);
						callStacks.put(thrd, threadStack);
					} else {
						Stack<String> threadStack = new Stack<String>();
						threadStack.push(lastMethod);
						callStacks.put(thrd, threadStack);
						// System.out.println("lastMethod =" + lastMethod);
					}
				}
				if (strLine.contains("Field Write")) {
					String[] tokens = strLine.split(",");
					String thread = tokens[0].replace("\"", "").trim();
					String object = tokens[4].substring(tokens[4].indexOf("=") + 1).replace("\"", "").trim();
					String field = tokens[5].substring(0, tokens[5].indexOf("=")).replace("\"", "").trim();
					String value = tokens[5].substring(tokens[5].indexOf("=") + 1).replace("\"", "").trim();

					if (value.equals("")) // Added to fix the error caused by plantuml which cannot take
						value = " "; // strings of length 0 ("") as a state
					if (value.equals("start")) // Plantuml has trouble drawing the state "start"
						value = "_start";

					// System.out.println(object + "->" + field + "=" + value + " : " +
					// lastMethod);// + " (" + callStacks.get(thread).peek() + ")");
					String entry = object + "->" + field;
					fwevents.add(new FWEvent(thread, object, field, value, lastMethod));
					if (!(attributes.contains(entry))) {
						attributes.add(entry);
						attributesMap.put(entry, 1);
						// attributeList.add(entry);
						if (Integer.parseInt(repeatText.getText()) == 1)
							sortedAttributes.add(entry);
					} else {
						int count = attributesMap.get(entry);
						attributesMap.put(entry, ++count);

						// System.out.println("repeatText = " + repeatText.getText());
						if (count == Integer.parseInt(repeatText.getText())) { // Add to drop-down occurrence hits 2
							sortedAttributes.add(entry);
						}
					}
				}
				if (strLine.contains("Method Returned")) {
					String thrd = strLine.substring(0, strLine.indexOf(','));
					Stack<String> threadStack = callStacks.get(thrd);
					callStacks.put(thrd, threadStack);
				}
			}
			sc.close();
			sortedAttributes.sort(null);
			for (int i = 0; i < sortedAttributes.size(); i++) {
				attributeList.add(sortedAttributes.get(i));
				// for (int j=0; j<attributeList.getItemCount(); j++)
				// System.out.println(sortedAttributes.size() + " " +
				// attributeList.getItemCount());
			}
			// Added to disable method level granularity if method call information not
			// available
			granularity[1].setEnabled(true);
			granularity[0].setSelection(true);
			for (int e = 1; e < fwevents.size(); e++) {
				if (fwevents.get(e).getMethod() == null) {
					granularity[1].setEnabled(false);
					granularity[0].setSelection(true);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// constructCallTree();
		// printCallTree();
	}

	public void printAttributes() {
		Iterator<String> it = attributes.iterator();
		while (it.hasNext())
			System.out.println(it.next());
	}

	public void readKeyAttributes(Text kvText, IStatusLineManager statusLineManager) {

		String userChoices = "";
		userChoices = kvText.getText(); // From the UI
		if (userChoices.equals(""))
			return;
		String[] uc = userChoices.split(",");
		for (int i = 0; i < uc.length; i++) {
			// System.out.println(uc[i]);
			if (attributes.contains(uc[i])) {
				String[] of = uc[i].split("->");
				keys.add(new KeyAttribute(of[0], of[1]));
			}
		}
		statusLineManager.setMessage(kvText.getText());
		printKeyAttributes();
	}

	public void printKeyAttributes() {
		Iterator<KeyAttribute> it = keys.iterator();
		while (it.hasNext())
			System.out.println(it.next());
	}

	public void createStates() {
		int time = 0;
		State currentState = new State();
		for (int s = 0; s < keys.size(); s++)
			currentState.set(s, "null");
		currentState.method = "init";
		currentState.time = time;
		states.add(currentState);

		State nextState = new State();
		nextState.copy(currentState);

		for (int i = 0; i < fwevents.size(); i++) {
			for (int j = 0; j < keys.size(); j++) {
				if (fwevents.get(i).getObject().equals(keys.get(j).getObject())
						&& fwevents.get(i).getField().equals(keys.get(j).getField())) {
					nextState.remove(j);
					nextState.set(j, fwevents.get(i).getValue());
					nextState.setMethod(fwevents.get(i).getMethod());
					nextState.time = ++time;

					State st = new State();
					st.copy(nextState);
					states.add(st);
				}
			}
			currentState = new State();
			currentState.copy(nextState);
		}
		printStates();
	}

	public void printStates() {
		for (int s = 0; s < states.size(); s++)
			states.get(s).print(1);
	}

	public void methodConsolidation() {
		int s = 0;
		while (s < states.size() - 1) {
			if (states.get(s).getMethod().equals(states.get(s + 1).getMethod()))
				states.remove(s);
			else
				s++;
		}
		printStates();
	}

	public void abstraction(Text paText) {

		paStates = new ArrayList<State>();
		for (int s = 0; s < states.size(); s++) {
			State paState = new State();
			paState.copy(states.get(s));
			paStates.add(paState);
		}

		String paStr = paText.getText().trim();
		if (paStr.equals(""))
			return;

		String[] paEntries = paStr.split(",");
		boolean reductionFlag = false;
		int s = 0;
		while (s < paStates.size()) {
			for (int k = 0; k < paEntries.length && k < paStates.get(s).keyVar.size(); k++) {
				if (!paEntries[k].trim().equals("")) {
					String absVal = applyAbstraction(paStates.get(s).keyVar.get(k), paEntries[k]);
					if (absVal.equals("")) {
						reductionFlag = true;
						break;
					} else
						paStates.get(s).keyVar.set(k, absVal);
				}
			}
			if (reductionFlag) {
				State hState = new State();
				hState.copy(paStates.get(s));
				hState.hashed = true;
				// paStates.remove(s);
				paStates.set(s, hState);
				reductionFlag = false;
				s++; // latest
			} else
				s++;
		}
		printPaStates();
	}

	public String applyAbstraction(String value, String predicate) {
		// Get the predicates from the UI
		if (value.equals("null"))
			return value;

		int choice;
		char lhs = predicate.trim().charAt(0);
		if (!(lhs == '=' || lhs == '>' || lhs == '<' || lhs == '[' || lhs == '#' || lhs == '~'))
			return value; // Invalid predicate - return 'value' as it is

		String rhs = predicate.trim().substring(1).trim();
		if (lhs == '#')
			choice = 4;
		else if (rhs.matches("^[0-9]+$"))
			choice = 0; // integer
		else if (rhs.matches("^[0-9]+.[0-9]+$"))
			choice = 1; // decimal
		else if (rhs.endsWith("]"))
			choice = 2; // range
		else
			choice = 3; // string

		switch (choice) {
		case 0: // Integer
			int n = Integer.parseInt(predicate.substring(1).trim());
			if (predicate.trim().startsWith("<")) { // <n
				if (Integer.parseInt(value) < n)
					return "<" + n;
				else
					return ">=" + n;
			} else if (predicate.trim().startsWith(">")) { // >n
				if (Integer.parseInt(value) > n)
					return ">" + n;
				else
					return "<=" + n;
			} else if (predicate.trim().startsWith("=")) { // =n
				if (Integer.parseInt(value) == n)
					return "" + n;
				else
					return "~" + n;
			} else if (predicate.trim().startsWith("~")) { // ~n
				if (Integer.parseInt(value) != n)
					return "~" + n;
				else
					return "" + n;
			}
			break;

		case 1: // Double
			double d = Double.parseDouble(predicate.substring(1).trim());
			if (predicate.trim().startsWith("<")) { // <n
				if (Double.parseDouble(value) < d)
					return "<" + d;
				else
					return ">=" + d;
			} else if (predicate.trim().startsWith(">")) { // >n
				if (Double.parseDouble(value) > d)
					return ">" + d;
				else
					return "<=" + d;
			} else if (predicate.trim().startsWith("=")) { // =n
				if (Double.parseDouble(value) == d)
					return "" + d;
				else
					return "~" + d;
			} else if (predicate.trim().startsWith("~")) { // ~n
				if (Double.parseDouble(value) != d)
					return "~" + d;
				else
					return "" + d;
			}
			break;

		case 2: // Range abstraction
			String[] range = predicate.substring(1, predicate.indexOf(']')).split(":");
			try {
				if (Integer.parseInt(value) < Integer.parseInt(range[0]))
					return "<" + Integer.parseInt(range[0]);
				for (int i = 0; i < range.length - 1; i++) {
					if (Integer.parseInt(value) >= Integer.parseInt(range[i])
							&& Integer.parseInt(value) < Integer.parseInt(range[i + 1]))
						return Integer.parseInt(range[i]) + ":" + Integer.parseInt(range[i + 1]);
				}
				if (Integer.parseInt(value) >= Integer.parseInt(range[range.length - 1]))
					return ">=" + Integer.parseInt(range[range.length - 1]);
			} catch (NumberFormatException nfe) {
				return value;
			}
			break;

		case 3: // String
			String s = predicate.substring(1).trim();
			if (predicate.trim().startsWith("=")) {
				if (value.equals(s))
					return s;
				else
					return "~" + s;
			} else if (predicate.trim().startsWith("~")) {
				if (!value.equals(s))
					return "~" + s;
				else
					return s;
			}

		case 4: // Reduction
			String ss = predicate.substring(1).trim();
			if (predicate.trim().startsWith("#") && value.equals(ss))
				return ss;
			else
				return "";
		}
		return value; // If nothing matches return 'value' as is
	}

	public void printPaStates() {
		/*
		 * for (int s=0; s<paStates.size(); s++) paStates.get(s).print(1);
		 */
		paStates.forEach((k) -> k.print(1));
	}

	public void createTransitions(int count) {
		transitions = new LinkedHashMap<String, Integer>();
		String transition = "(*) --> " + "\"" + paStates.get(0).toString() + "\"";
		transitions.put(transition, 1);
		int s = 0;
		String lastTransition = "";
		while (s < count && s < paStates.size() - 1) {
			if (paStates.get(s).hashed && paStates.get(s + 1).hashed) // skip the transition
				; // Both from and to states do not have the selection
			else if (!paStates.get(s).hashed && paStates.get(s + 1).hashed) // skip the transition
				; // To node does not have the selection
			else if (paStates.get(s).hashed && !paStates.get(s + 1).hashed) { // Add self-loop and make color white
				transition = new String("\"" + paStates.get(s + 1).toString() + "\"" + " -[#white]-> " + "\""
						+ paStates.get(s + 1).toString() + "\"");

				transitions.merge(transition, 1, Integer::sum);
			} else {
				transition = new String("\"" + paStates.get(s).toString() + "\"" + " --> " + "\""
						+ paStates.get(s + 1).toString() + "\"");

				transitions.merge(transition, 1, Integer::sum);
				lastTransition = new String(transition);
			}
			s++;
		}
		if (s >= 1) { // Added for animation while rendering step-by-step
			if (transitions.containsKey(lastTransition)) {
				int value = transitions.get(lastTransition);
				transitions.remove(lastTransition);
				transitions.put(lastTransition + " #red", value);
			}
		}

		printTransitions();
	}

	public void printTransitions() { // Uses lambda
		transitions.forEach((k, v) -> System.out.println(k.replaceAll("-->", "--> [" + v + "]")));
	}

	public String exportToPlantUML(boolean transitionCount) {
		StringBuffer sb = new StringBuffer();
		sb.append("@startuml\n");

		// sb.append("title Finite State Model for [" + kvText.getText() + "]\n");

		if (transitionCount)
			transitions.forEach((k, v) -> sb.append(k.replaceAll("-->", "--> [" + v + "]") + "\n"));
		else
			transitions.forEach((k, v) -> sb.append(k + "\n"));

		// sb.replace(sb.lastIndexOf("\n"), sb.lastIndexOf("\n")+1, " #red\n"); // to
		// highlight the last state
		sb.append("@enduml\n");
		return sb.toString();
	}

	public String exportToVisJS(boolean transitionCount, int count) {

		LinkedHashMap<String, Integer> lhm = new LinkedHashMap<String, Integer>();

		StringBuffer vsb = new StringBuffer();
		vsb.append("<!doctype html>\n" + "<html>\n" + "  <head>\n" + "    <title>Finite State Machine</title>\n"
				+ "    <script type=\"text/javascript\" src=\"http://visjs.org/dist/vis.js\"></script>\n"
				+ "    <link href=\"http://visjs.org/dist/vis-network.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
				+ "    <style type=\"text/css\">\n" + "      #mynetwork {\n" + "        width: 600px; \n"
				+ "        height: 400px; \n" + "        border: 1px solid lightgray; \n " + "      }\n"
				+ "    </style>\n" + "  </head>\n" + "<body>\n");

		vsb.append("\n<p>Create a simple network with some nodes and edges.</p>\n");
		vsb.append("\n<div id=\"mynetwork\"></div>\n");

		vsb.append("<script type=\"text/javascript\">\n");
		vsb.append("// create an array with nodes\n");
		vsb.append("  var nodes = new vis.DataSet([\n");

		int s = 0;
		int num = 0;
		vsb.append("{id: 0, label: 'Init'}");
		while (s < count && s < paStates.size()) {
			if (!lhm.containsKey(paStates.get(s).toString())) {
				lhm.put(paStates.get(s).toString(), ++num);
				vsb.append(",\n{id: " + num + ", label: '" + paStates.get(s).toString() + "'}");
			}
			s++;
		}
		/*
		 * {id: 1, label: 'Node 1'}, {id: 2, label: 'Node 2'}, {id: 3, label: 'Node 3'},
		 * {id: 4, label: 'Node 4"}
		 */
		vsb.append("\n ]);\n");

		vsb.append("// create an array with edges\n");
		vsb.append("var edges = new vis.DataSet([\n");
		vsb.append("{from: " + 0 + ", to: " + 1 + "}");
		s = 1;
		while (s < count && s < paStates.size() - 1) {
			if (paStates.get(s).hashed && paStates.get(s + 1).hashed) // skip the transition
				; // Both from and to states do not have the selection
			else if (!paStates.get(s).hashed && paStates.get(s + 1).hashed) // skip the transition
				; // To node does not have the selection
			else if (paStates.get(s).hashed && !paStates.get(s + 1).hashed) { // Add self-loop and make color white
				vsb.append(",\n{from: " + lhm.get(paStates.get(s + 1).toString()) + ", to: "
						+ lhm.get(paStates.get(s + 1).toString()) + "}");
			} else {
				vsb.append(",\n{from: " + lhm.get(paStates.get(s).toString()) + ", to: "
						+ lhm.get(paStates.get(s + 1).toString()) + "}");
			}
			s++;
		}
		/*
		 * {from: 1, to: 3}, {from: 2, to: 3}, {from: 3, to: 4}
		 */
		vsb.append("\n ]);\n");

		vsb.append("// create a network\n");
		vsb.append("var container = document.getElementById('mynetwork');\n");
		vsb.append("  var data = {\n");
		vsb.append("    nodes: nodes,\n");
		vsb.append("    edges: edges\n");
		vsb.append("  };\n");

		vsb.append("  var options = {};\n");
		vsb.append("  var network = new vis.Network(container, data, options);\n");
		vsb.append("</script>\n");
		vsb.append("</body>\n");
		vsb.append("</html>\n");

		return vsb.toString();
	}

	public String exportRun(int count) {
		String[] direction = { "right", "right", "right", "right", "right", "right", "right", "right", "down", "left",
				"left", "left", "left", "left", "left", "left", "left", "down" };
		StringBuffer runsb = new StringBuffer();
		runsb.append("@startuml\n");

		// runsb.append("title A Run for [" + kvText.getText() + "]\n");
		runsb.append(
				"(*) -right-> " + "\"t=" + paStates.get(0).time + "\\n" + paStates.get(0).toString() + "\"" + "\n");
		// runsb.append("(*)");

		// paStates.forEach((s) -> runsb.append(" -" + direction[d%5] + "-> " + "\"t=" +
		// s.time + "\n" + s.toString() + "\"" + "\n"));

		for (int s = 0; s < count && s < paStates.size() - 1; s++) {
			runsb.append("\"t=" + paStates.get(s).time + "\\n" + paStates.get(s).toString() + "\"" + " -"
					+ direction[s % 18] + "-> " + "\"t=" + paStates.get(s + 1).time + "\\n"
					+ paStates.get(s + 1).toString() + "\"" + "\n");
		}

		runsb.append("@enduml\n");
		// runsb.replace(runsb.indexOf("::"), runsb.indexOf("::")+2, "\n");
		// String runStr = new String(runsb.toString());
		// runStr.replaceAll(" :: ", "\\n");
		// return runStr;
		return runsb.toString();
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public void generateSVG(String source, Browser browser, Text hcanvasText, Text vcanvasText,
			ScrolledComposite rootScrollComposite, Composite mainComposite, Composite imageComposite) {
		IPath path = ResourcesPlugin.getPlugin().getStateLocation();
		System.out.println(path);
		SourceStringReader reader = new SourceStringReader(source);

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(path.toFile().getPath() + File.separator + "state.svg");
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe);
		}
		// Write the first image to "os"
		try {
			String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
			String desc2 = reader.generateImage(fos, new FileFormatOption(FileFormat.SVG));
			os.close();
			fos.close();
		} catch (IOException ioe) {
			System.out.println("Unable to generate SVG");
		}

		// The XML is stored into svg
		String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));

		// browser = new Browser(imageComposite, SWT.NONE);
		GridData browserLData = new GridData();
		browserLData.widthHint = Integer.parseInt(hcanvasText.getText()); // 1000;
		browserLData.heightHint = Integer.parseInt(vcanvasText.getText()); // 600;
		browser.setLayoutData(browserLData);
		browser.setText(svg);
		imageComposite.pack();
		rootScrollComposite.setMinSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	@SuppressWarnings("deprecation")
	public void generateImage(String source) {
		IPath path = ResourcesPlugin.getPlugin().getStateLocation();
		System.out.println("path = " + path.toFile().getPath().toString());
		OutputStream png = null;
		try {
			png = new FileOutputStream(path.toFile().getPath() + File.separator + "state.png");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		SourceStringReader reader = new SourceStringReader(source);

		try {
			@SuppressWarnings("unused")
			String desc = reader.generateImage(png);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// public void displayImage() {
	// IPath path = ResourcesPlugin.getPlugin().getStateLocation();
	// try {
	// File stateDiagFile = new File(path.toFile().getPath() + File.separator +
	// "state.png");
	// Image image = new Image(display, stateDiagFile.getPath());
	// imageLabel.setImage(image);
	// } catch (Exception ie) {
	// MessageDialog.openError(new Shell(Display.getCurrent()), "Exception",
	// "An exception occured: " + ie.getMessage());
	// }
	//
	// imageComposite.pack();
	// rootScrollComposite.setMinSize(mainComposite.computeSize(SWT.DEFAULT,
	// SWT.DEFAULT));
	// }
	//
	// public void displayHTML(String text) {
	// System.out.println(text);
	//
	// GridData browserLData = new GridData();
	// browserLData.widthHint = Integer.parseInt(hcanvasText.getText()); // 1000;
	// browserLData.heightHint = Integer.parseInt(vcanvasText.getText()); // 600;
	// browser.setLayoutData(browserLData);
	// browser.setText(text);
	// imageComposite.pack();
	// rootScrollComposite.setMinSize(mainComposite.computeSize(SWT.DEFAULT,
	// SWT.DEFAULT));
	// }
}