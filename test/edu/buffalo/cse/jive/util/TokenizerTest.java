package edu.buffalo.cse.jive.util;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.buffalo.cse.jive.finiteStateMachine.util.Tokenizer;

class TokenizerTest {

	@Test
	final void testTokenize() {
		String input = new String("G[ Database:1.w == Database:1.r + 3 ]");
		try {
			List<String> tokens = Tokenizer.tokenize(input);
			for (String s : tokens)
				System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	final void testTokenize2() {
		String input = "G [w == 0 -> r == 1] ";
		try {
			List<String> tokens = Tokenizer.tokenize(input);
			for (String s : tokens)
				System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
