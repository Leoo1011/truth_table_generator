package com.leo.test;

import com.leo.main.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BooleanLogicParserTest {
    @Test
    void parse() throws BooleanLogicParser.ParseError, BooleanLogicLexer.LexError {
        Token[] tokens = BooleanLogicLexer.tokenize("!A->(B|C)");
        Expr ast = BooleanLogicParser.parse(tokens);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(byteArrayOutputStream)) {
            ExprPrinter.printExpr(ast, out);
            String astString = byteArrayOutputStream.toString();

            assertEquals("(then (not A) (grouping (or B C)))", astString);
        }
    }

    @Test
    void invalidSyntaxThrowsParseError() {
        String[] invalidFormulae = new String[]{"A->)", "()A", "A!->C", "()->A", "!A->(!B"};
        for (String formula : invalidFormulae) {
            assertThrowsExactly(BooleanLogicParser.ParseError.class, () -> new TruthTable(formula));
        }
    }
}
