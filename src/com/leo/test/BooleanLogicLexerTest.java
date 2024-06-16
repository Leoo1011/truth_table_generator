package com.leo.test;

import com.leo.main.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BooleanLogicLexerTest {
    @Test
    void throwsOnNullArgument() {
        assertThrowsExactly(NullPointerException.class, () -> BooleanLogicLexer.tokenize(null));
    }

    @Test
    void tokenize() throws BooleanLogicLexer.LexError {
        Token[] tokens = BooleanLogicLexer.tokenize("A->B");
        String[] expected = new String[]{
                new PropositionName("A").toString(),
                new Connective(TokenType.THEN).toString(),
                new PropositionName("B").toString(),
                new EndOfLine().toString()
        };
        assertEquals(tokens.length, expected.length);

        for (int i = 0; i < tokens.length; i++) {
            assertEquals(expected[i], tokens[i].toString());
        }
    }

    @Test
    void invalidSymbolThrowsLexError() {
        String[] invalidSymbols = new String[]{
                "-", "?", "$", "%", "[", "]", "'", "\"",
                "/", "\\", "+", "-", "<", ">", "=", ".",
                ",", ":", ";", "{", "}", "#", "*", "@"
        };
        for (String symbol : invalidSymbols) {
            assertThrowsExactly(BooleanLogicLexer.LexError.class, () ->
                    BooleanLogicLexer.tokenize("A" + symbol + "B"));
        }
    }
}
