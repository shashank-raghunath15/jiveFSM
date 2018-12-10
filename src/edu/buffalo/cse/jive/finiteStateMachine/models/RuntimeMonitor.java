package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.buffalo.cse.jive.finiteStateMachine.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.expression.FExpression;
import edu.buffalo.cse.jive.finiteStateMachine.parser.Parser;
import edu.buffalo.cse.jive.finiteStateMachine.parser.ParserImpl;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.core.DiagramDescription;

public class RuntimeMonitor {

	private List<KeyAttribute> attributes = new ArrayList<>();
	private Map<String, Integer> attributeMap = new HashMap<>();
	private Map<State, Set<State>> states = new HashMap<>();
	private State previousState;
	private StringBuffer transitions = new StringBuffer();
	private Set<String> transitionSet = new HashSet<>();
	private List<Expression> expressions;
	private Map<String, Integer> variableMap = new HashMap<>();
	public static Set<Expression> fExpressions = new HashSet<>();
	private Display display;
	private Label statusLabel;

	public RuntimeMonitor(Text kvText, IStatusLineManager statusLineManager, Text propertyText, Display display,
			Label statusLabel) {
		if (readAttributes(kvText)) {
			statusLineManager.setMessage(kvText.getText());
			initialize();
			parseExpressions(propertyText);
			this.display = display;
			this.statusLabel = statusLabel;
		}
	}

	private void parseExpressions(Text propertyText) {
		if (propertyText != null && propertyText.getText().length() > 0) {
			Parser parser = new ParserImpl();
			String properties = propertyText.getText().trim();
			expressions = new CopyOnWriteArrayList<>(parser.parse(properties.split(propertyText.getLineDelimiter())));

			for (Expression expression : expressions) {
				if (expression instanceof FExpression) {
					expressions.remove(expression);
					fExpressions.add(expression);
				}
			}
		}
	}

	private boolean readAttributes(Text attributes) {
		if (attributes != null && attributes.getText().length() > 0) {
			String selected = attributes.getText();
			int j = 0;
			for (String attribute : selected.split(",")) {
				String[] values = attribute.trim().split("->");
				getAttributes().add(new KeyAttribute(values[0], values[1]));
				attributeMap.put(values[0] + "," + values[1], j);
				variableMap.put(values[1], j);
				j++;
			}
			printAttributeMap();
			return true;
		}
		return false;
	}

	private void initialize() {
		previousState = new State();
		for (int i = 0; i < this.attributes.size(); i++) {
			previousState.set(i, "null");
		}
		previousState.setTime(0);
		previousState.setMethod("init");
		states.put(previousState, new HashSet<>());
		transitions.append("@startuml\n");
		transitions.append("(*) --> " + "\"" + previousState.toString() + "\"");
		transitions.append("\n");
	}

	private void printAttributeMap() {
		for (String s : attributeMap.keySet()) {
			System.out.println(s + " " + attributeMap.get(s));
		}
	}

	public List<KeyAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<KeyAttribute> attributes) {
		this.attributes = attributes;
	}

	public Map<String, Integer> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, Integer> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public Map<State, Set<State>> getStates() {
		return states;
	}

	public void setStates(Map<State, Set<State>> states) {
		this.states = states;
	}

	public State getPreviousState() {
		return previousState;
	}

	public void setPreviousState(State previousState) {
		this.previousState = previousState;
	}

	public StringBuffer getTransitions() {
		return transitions;
	}

	public void setTransitions(StringBuffer transitions) {
		this.transitions = transitions;
	}

	public boolean recordFieldWrite(String fieldWrite) {
		String[] tokens = fieldWrite.split(",");
		String object = tokens[0].substring(tokens[0].indexOf("=") + 1).replace("\"", "").trim();
		String field = tokens[1].substring(0, tokens[1].indexOf("=")).replace("\"", "").trim();
		String value = tokens[1].substring(tokens[1].indexOf("=") + 1).replace("\"", "").trim();

		if (value.equals(""))
			value = " ";
		if (value.equals("start"))
			value = "_start";
		State newState = previousState.copy();
		newState.set2(attributeMap.get(object + "," + field), value);
		boolean result = true;
		if (states.get(previousState).add(newState) && !newState.keyVar.contains("null")) {
			result = validate(newState);
		}
		result = addTransition(newState, result);
		if (!states.containsKey(newState)) {
			states.put(newState, new HashSet<>());
		}
		previousState = newState;
		return result;
	}

	public boolean validate(State state) {
		boolean valid = true;
		for (Expression expression : expressions) {
			if (!expression.evaluate(new UnaryContext(variableMap, previousState, state))) {
				valid = false;
				break;
			}
		}
		for (Expression expression : fExpressions) {
			if (!expression.evaluate(new UnaryContext(variableMap, previousState, state))) {
				fExpressions.remove(expression);
			}
		}
		return valid;
	}

	public boolean addTransition(State state, boolean valid) {
		String s = null;
		if (valid) {
			s = "\"" + previousState.toString() + "\"" + " --> " + "\"" + state.toString() + "\"";
		} else {
			s = "\"" + previousState.toString() + "\"" + " --> " + "\"" + state.toString() + "\"" + " #red";
		}

		if (transitionSet.add(s)) {
			transitions.append(s);
			transitions.append("\n");
			return true;
		}
		return false;
	}

	public String exportToPlantUML(boolean transitionCount) {
		return transitions.toString() + "@enduml\n";
	}

	public void generateSVG(String source, Text hcanvasText, Text vcanvasText, Browser browser,
			Composite imageComposite, ScrolledComposite rootScrollComposite, Composite mainComposite) {
		IPath path = ResourcesPlugin.getPlugin().getStateLocation();
		System.out.println(path);
		SourceStringReader reader = new SourceStringReader(source);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			@SuppressWarnings({ "unused" })
			DiagramDescription description = reader.outputImage(os, new FileFormatOption(FileFormat.SVG));
			os.close();
		} catch (IOException ioe) {
			System.out.println("Unable to generate SVG");
		}
		String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));
		GridData browserLData = new GridData();
		display.asyncExec(new DisplayThread(browserLData, hcanvasText, vcanvasText, browser, svg, imageComposite,
				rootScrollComposite, mainComposite));
		if (fExpressions.size() > 0) {
			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					statusLabel.setText("FExpressions not validated");
				}
			});
		}else {
			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					statusLabel.setText("FExpressions valid");
				}
			});
		}
	}

	public void kill() {
		// TODO Kill display logic
	}
}

class DisplayThread extends Thread {

	private GridData browserLData;
	private Text hcanvasText;
	private Text vcanvasText;
	private Browser browser;
	private String svg;
	private Composite imageComposite;
	private ScrolledComposite rootScrollComposite;
	private Control mainComposite;

	public DisplayThread(GridData browserLData, Text hcanvasText, Text vcanvasText, Browser browser, String svg,
			Composite imageComposite, ScrolledComposite rootScrollComposite, Control mainComposite) {
		super();
		this.browserLData = browserLData;
		this.hcanvasText = hcanvasText;
		this.vcanvasText = vcanvasText;
		this.browser = browser;
		this.svg = svg;
		this.imageComposite = imageComposite;
		this.rootScrollComposite = rootScrollComposite;
		this.mainComposite = mainComposite;
	}

	@Override
	public void run() {
		browserLData.widthHint = Integer.parseInt(hcanvasText.getText());
		browserLData.heightHint = Integer.parseInt(vcanvasText.getText());
		browser.setLayoutData(browserLData);
		browser.setText(svg);
		imageComposite.pack();
		rootScrollComposite.setMinSize(mainComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

}