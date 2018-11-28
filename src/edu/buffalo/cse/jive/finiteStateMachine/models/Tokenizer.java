package edu.buffalo.cse.jive.finiteStateMachine.models;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Shashank Raghunath
 * @email sraghuna@buffalo.edu
 *
 */
public class Tokenizer {

	private Lexer lexer;
	public static ArrayList<String> idList;

	public Tokenizer(Buffer buffer) {
		lexer = new Lexer(buffer);
		idList = new ArrayList<>();
	}

	public Lexer getLexer() {
		return lexer;
	}

	public void setLexer(Lexer lexer) {
		this.lexer = lexer;
	}

	public ArrayList<String> getIdList() {
		return idList;
	}

	public void setIdList(ArrayList<String> idList) {
		Tokenizer.idList = idList;
	}

	public List<String> tokenize() {
		ArrayList<String> output = new ArrayList<>();
		lexer.lex();
		while (Lexer.nextToken != 0) {
			if (Lexer.nextToken == Token.ID) {
				output.add(Lexer.ident);
				idList.add(Lexer.ident);
			} else if (Lexer.nextToken == Token.INT_LIT)
				output.add(String.valueOf(Lexer.intValue));
			else
				output.add(Operators.getOperator(Lexer.nextToken));
			lexer.lex();
		}
		return output;
	}

}
