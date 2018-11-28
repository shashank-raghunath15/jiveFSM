package edu.buffalo.cse.jive.finiteStateMachine.models;

public class Event extends Entry{
		private String thread;
		private int id;
		private String source;
		private int line;
		private String type;
		private String d1; // The value of 
		private String d2; // these fields
		private String d3; // depends upon
		private String d4; // the type
		
		Event(String strLine) {
			//System.out.println(strLine);
			int idx = 0;
			kind = 0; // Event ==> Entry.kind = 0
			String[] tokens = strLine.split(",");
			thread = tokens[idx].trim(); idx++;
			id = Integer.parseInt(tokens[idx].trim()); idx++;
			if (tokens[idx].trim().equals("SYSTEM")) {
				source = tokens[idx].trim(); 
				line = -1; idx++;
				type = tokens[idx].trim(); idx++;
			}
			else {
				source = tokens[idx].trim().substring(0,tokens[idx].indexOf(':')); 
				line = Integer.parseInt(tokens[idx].substring(tokens[idx].indexOf(':')+1)); idx++;
				type = tokens[idx].trim(); idx++;
			}
			switch (type) {
			case "System Start":
				// do nothing
				break;
				
			case "System End":
				// do nothing
				break;

			case "Thread Start": 	// Thread name of the form "main (1)" or "Thread-0 (34)"
				tokens[idx] = tokens[idx].trim();
				d1 = tokens[idx].substring(0,tokens[idx].indexOf(' '));  // removes " (?)"
				break;
				
			case "Thread End":
				// do nothing
				break;
				
			case "Type Load":	// class that was loaded
				d1 = tokens[idx].trim().substring(6); // removes "class="
				break;
				
			case "New Object":	// object that was instantiated "classname:instance"
				d1 = tokens[idx].trim().substring(7); // removes "object="
				break;

			case "Method Call":
				tokens[idx] = tokens[idx].trim().substring(7); // removes "caller="
				if (tokens[idx].equals("SYSTEM")) {
					d1 = "SYSTEM";	// caller object
					d2 = "SYSTEM";	// caller method
				}
				else {
					if (tokens[idx].contains("#") ) {
						d1 = tokens[idx].substring(0,tokens[idx].indexOf('#')).trim(); 	// caller object
						d2 = tokens[idx].substring(tokens[idx].indexOf('#')+1).trim();	// caller method
					}
					else {
						d1 = tokens[idx].substring(0,tokens[idx].indexOf('.')).trim(); 	// caller object
						d2 = tokens[idx].substring(tokens[idx].indexOf('.')+1).trim();	// caller method				
					}
				}
				idx++;
				tokens[idx] = tokens[idx].trim().substring(7); // removes "target="
				if (tokens[idx].contains("#") ) {
					d3 = tokens[idx].substring(0,tokens[idx].indexOf('#')).trim(); 	// target object
					d4 = tokens[idx].substring(tokens[idx].indexOf('#')+1).trim();	// target method
				}
				else {
					d3 = tokens[idx].substring(0,tokens[idx].indexOf('.')).trim();	// target object
					d4 = tokens[idx].substring(tokens[idx].indexOf('.')+1).trim();	// target method
					
				}
				//System.out.println(d1 + "   " + d2 + "   " + d3 + "   " + d4);
				break;
				
			case "Method Exit":
				tokens[idx] = tokens[idx].trim().substring(9);  // removes "returner="
				if (tokens[idx].contains("#") ) {
					d1 = tokens[idx].substring(0,tokens[idx].indexOf('#')).trim();	// returner object
					d2 = tokens[idx].substring(tokens[idx].indexOf('#')+1).trim();	// returner method
				}
				else {
					d1 = tokens[idx].substring(0,tokens[idx].indexOf('.')).trim();	// returner object
					d2 = tokens[idx].substring(tokens[idx].indexOf('.')+1).trim();	// returner method				
				}
				idx++;
				tokens[idx] = tokens[idx].trim().substring(6); // removes "value="
				d3 = tokens[idx].trim();	// returned value
				//System.out.println(d1 + "   " + d2 + "   " + d3);
				break;
				
			case "Field Read":
				tokens[idx] = tokens[idx].trim().substring(8); // removes "context="
				d1 = tokens[idx]; idx++;	// object
				d2 = tokens[idx].trim();	// field
				break;
				
			case "Field Write":
				tokens[idx] = tokens[idx].trim().substring(8); // removes "context="
				d1 = tokens[idx]; idx++;	// object
				d2 = tokens[idx].substring(0,tokens[idx].indexOf('=')).trim();	// field
				d3 = tokens[idx].substring(tokens[idx].indexOf('=')+1).trim();	// value assigned
				break;
				
			case "Variable Write":
				tokens[idx] = tokens[idx].trim().substring(8);  // removes "context="
				d1 = tokens[idx].substring(0,tokens[idx].indexOf('#')).trim();	// object
				d2 = tokens[idx].substring(tokens[idx].indexOf('#')+1).trim();	// method
				idx++;
				d3 = tokens[idx].substring(0,tokens[idx].indexOf('=')).trim();	// local variable
				d4 = tokens[idx].substring(tokens[idx].indexOf('=')+1).trim();	// value assigned
				
			case "Line Step":
				//do nothing
				break;
			}
		}
		
		public String getThread() { return thread; }
		public int getId() { return id; }
		public String getSource() { return source; }
		public int getLine() { return line; }
		public String getEventType() { return type; }
		public String getD1() { return d1; }
		public String getD2() { return d2; }
		public String getD3() { return d3; }
		public String getD4() { return d4; }
		
		public void print() {
			System.out.println(thread
							+ "," + id
							+ "," + source
							+ "," + line
							+ "," + type
							+ "," + d1
							+ "," + d2
							+ "," + d3
							+ "," + d4
							);
		}
}
