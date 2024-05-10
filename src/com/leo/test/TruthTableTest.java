package com.leo.test;

import com.leo.main.BooleanLogicLexer;
import com.leo.main.BooleanLogicParser;
import com.leo.main.TruthTable;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TruthTableTest {
    @Test
    void getTable() throws BooleanLogicParser.ParseError, BooleanLogicLexer.LexError {
        String formula = "A<->!B";
        TruthTable t = new TruthTable(formula, new String[]{"1", "0"});
        String[][] table = t.getTable();
        String[][] expected = new String[][]{
                {"A", "B", formula},
                {"0", "0", "0"},
                {"0", "1", "1"},
                {"1", "0", "1"},
                {"1", "1", "0"}
        };
        assertArrayEquals(table, expected);
    }

    @Test
    void compareEquivalentTables() throws BooleanLogicParser.ParseError, BooleanLogicLexer.LexError {
        String[][] t1 = new TruthTable("A^(A->A)").getTable(); // This formula is equivalent to !A
        String[][] t2 = new TruthTable("!A").getTable();

        var t1MinusHeader = Arrays.stream(t1, 1, t1.length).toArray();
        var t2MinusHeader = Arrays.stream(t2, 1, t2.length).toArray();

        assertArrayEquals(t1MinusHeader, t2MinusHeader, "Tables of equivalent formulae should be equal.");
    }

    @Test
    void throwsOnIllegalArguments() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new TruthTable(""));
        assertThrowsExactly(IllegalArgumentException.class, () -> new TruthTable("A", new String[]{}));
        assertThrowsExactly(NullPointerException.class, () -> new TruthTable(null));
    }
}
