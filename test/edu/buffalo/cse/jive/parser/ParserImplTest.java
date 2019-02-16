package edu.buffalo.cse.jive.parser;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.buffalo.cse.jive.finiteStateMachine.expression.expression.Expression;
import edu.buffalo.cse.jive.finiteStateMachine.parser.ParserImpl;
import edu.buffalo.cse.jive.finiteStateMachine.util.Tokenizer;

class ParserImplTest {

	ParserImpl parser;

	@BeforeEach
	final void before() {
		this.parser = new ParserImpl();
	}

	@Test
	final void testParse() {
		String input = new String("G [w == w' -> r == '0']");
		try {
			List<String> postFix = parser.convertToPostfix(Tokenizer.tokenize(input));
			Expression expression = parser.parsePreOrder(parser.buildPrecedenceTree(postFix), null);
			expression.evaluate(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	final void testParse2() {
		String input = new String("G [w == 0 -> r == 1] ");
		try {
			List<String> postFix = parser.convertToPostfix(Tokenizer.tokenize(input));
			Expression expression = parser.parsePreOrder(parser.buildPrecedenceTree(postFix), null);
			expression.evaluate(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	final void testParse3() {
		String input = new String(
				"G[Database:1.w == 1 && Database:1.r == 0 ] -> F [ Database:1.r < Database:1.r' - 2 ]");
		try {
			List<String> postFix = parser.convertToPostfix(Tokenizer.tokenize(input));
			Expression expression = parser.parsePreOrder(parser.buildPrecedenceTree(postFix), null);
			expression.evaluate(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	final void testParse4() {
		String input = new String("G[ Database:1.w == 0 -> F[ Database:1.r == Database:1.r' ] ]");
		try {
			List<String> postFix = parser.convertToPostfix(Tokenizer.tokenize(input));
			Expression expression = parser.parsePreOrder(parser.buildPrecedenceTree(postFix), null);
			expression.evaluate(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	final void testBuildPrecedenceTree() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testConvertToPostfix() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testParsePreOrder() {
		fail("Not yet implemented"); // TODO
	}

}
