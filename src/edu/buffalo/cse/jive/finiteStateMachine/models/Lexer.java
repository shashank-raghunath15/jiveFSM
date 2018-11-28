package edu.buffalo.cse.jive.finiteStateMachine.models;
/**
 * @author Bharat Jayaraman
 * @email bharat@buffalo.edu
 *
 */
public class Lexer {

	static public char ch = ' ';
	static public String ident = "";
	private Buffer buffer;
	static public int nextToken;
	static public char nextChar;
	static public int intValue;

	public Lexer(Buffer buffer) {
		super();
		this.buffer = buffer;
	}

	public int lex() {
		while (Character.isWhitespace(ch))
			ch = buffer.getChar();
		if (Character.isLetter(ch)) {
			ident = getIdent();
			switch (ident) {
			case "if":
				nextToken = Token.KEY_IF;
				break;
			case "int":
				nextToken = Token.KEY_INT;
				break;
			case "else":
				nextToken = Token.KEY_ELSE;
				break;
			case "end":
				nextToken = Token.KEY_END;
				break;
			case "while":
				nextToken = Token.KEY_WHILE;
				break;
			case "return":
				nextToken = Token.KEY_RETURN;
				break;
			default:
				nextToken = Token.ID;
			}
		} else if (Character.isDigit(ch)) {
			nextToken = getNumToken(); // intValue would be set
		} else {
			nextChar = ch;
			switch (ch) {
			case ';':
				nextToken = Token.SEMICOLON;
				ch = buffer.getChar();
				break;
			case ',':
				nextToken = Token.COMMA;
				ch = buffer.getChar();
				break;
			case '+':
				nextToken = Token.ADD_OP;
				ch = buffer.getChar();
				break;
			case '-':
				ch = buffer.getChar();
				if (ch == '>') {
					nextToken = Token.IMPLIES_OP;
					ch = buffer.getChar();
				} else {
					nextToken = Token.SUB_OP;
				}
				break;
			case '*':
				nextToken = Token.MULT_OP;
				ch = buffer.getChar();
				break;
			case '/':
				nextToken = Token.DIV_OP;
				ch = buffer.getChar();
				break;
			case '=':
				ch = buffer.getChar();
				if (ch == '=') {
					nextToken = Token.EQ_OP;
					ch = buffer.getChar();
				}
				if (ch == '>') {
					nextToken = Token.EDGE_OP;
					ch = buffer.getChar();
				} else
					nextToken = Token.ASSIGN_OP;
				break;
			case '<':
				ch = buffer.getChar();
				if (ch == '=') {
					nextToken = Token.LESSEQ_OP;
					ch = buffer.getChar();
				} else
					nextToken = Token.LESSER_OP;
				break;
			case '>':
				ch = buffer.getChar();
				if (ch == '=') {
					nextToken = Token.GREATEREQ_OP;
					ch = buffer.getChar();
				} else
					nextToken = Token.GREATER_OP;
				ch = buffer.getChar();
				break;
			case '!':
				ch = buffer.getChar(); // '='
				if (ch == '=') {
					nextToken = Token.NOT_EQ;
					ch = buffer.getChar();
				} else
					nextToken = Token.NOT_OP;
				ch = buffer.getChar();
				break;
			case '(':
				nextToken = Token.LEFT_PAREN;
				ch = buffer.getChar();
				break;
			case ')':
				nextToken = Token.RIGHT_PAREN;
				ch = buffer.getChar();
				break;
			case '{':
				nextToken = Token.LEFT_BRACE;
				ch = buffer.getChar();
				break;
			case '}':
				nextToken = Token.RIGHT_BRACE;
				ch = buffer.getChar();
				break;
			case '[':
				nextToken = Token.LEFT_SQ_BRACE;
				ch = buffer.getChar();
				break;
			case ']':
				nextToken = Token.RIGHT_SQ_BRACE;
				ch = buffer.getChar();
				break;
			case 'A':
				nextToken = Token.A_OP;
				ch = buffer.getChar();
				break;
			case 'G':
				nextToken = Token.G_OP;
				ch = buffer.getChar();
				break;
			case '&':
				ch = buffer.getChar();
				nextToken = Token.AND_OP;
				ch = buffer.getChar();
				break;
			case '|':
				ch = buffer.getChar();
				nextToken = Token.OR_OP;
				ch = buffer.getChar();
				break;
			default:
				error("Illegal character " + ch);
				break;
			}
		}
		return nextToken;
	} // lex

	private String getIdent() {
		// ch is declared in class Lexer
		String ident = "";
		do {
			ident = ident + ch;
			ch = buffer.getChar();
		} while (Character.isLetter(ch) || Character.isDigit(ch));
		return ident;
	}

	private int getNumToken() {
		int num = 0;
		do {
			num = num * 10 + Character.digit(ch, 10);
			ch = buffer.getChar();
		} while (Character.isDigit(ch));
		intValue = num;
		return Token.INT_LIT;
	}

	public int number() {
		return intValue;
	} // number

	public String identifier() {
		return ident;
	} // letter

	public static void error(String msg) {
		System.err.println(msg);
		System.exit(1);
	} // error

}
