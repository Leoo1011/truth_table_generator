package com.leo;

public class TruthTable {
    /**
     * A truth table generator.
     * @param expression The boolean logic formula from which to generate the truth table.
     *
     * @throws BooleanLogicLexer.LexError if the formula has invalid tokens.
     * @throws BooleanLogicParser.ParseError if the formula doesn't match the grammar.
     */
    TruthTable(String expression) throws BooleanLogicLexer.LexError, BooleanLogicParser.ParseError {
        this(expression, new String[]{"T", "F"});
    }

    /**
     * A truth table generator.
     * @param expression The boolean logic formula from which to generate the truth table.
     * @param truthRepresentation An array, of length 2, whose first element will be used to represent
     *                            true values, and the second will represent false values.
     *
     * @throws BooleanLogicLexer.LexError if the formula has invalid tokens.
     * @throws BooleanLogicParser.ParseError if the formula doesn't match the grammar.
     * @throws IllegalStateException if truthRepresentation.length != 2.
     */
    TruthTable(String expression, String[] truthRepresentation)
            throws BooleanLogicLexer.LexError, BooleanLogicParser.ParseError, IllegalStateException {
        if (truthRepresentation.length != 2) {
            throw new IllegalStateException("'truthRepresentation' should have exactly 2 values.");
        }
        truth = truthRepresentation[0];
        falsity = truthRepresentation[1];
        formula = expression;
        Token[] tokens = BooleanLogicLexer.tokenize(formula);
        ast = BooleanLogicParser.parse(tokens);
    }

    private final String truth;
    private final String falsity;
    private final String formula;
    private final Token[] tokens;
    private final Expr ast;

    /**
     * Generates every permutation of values for the propositions in the truth table.
     * @return array of arrays of ints containing each permutation in the form of 1's (true) and 0's (false).
     *
     * @implNote Counting from 0 to 3 (in binary) is:
     * <ul>
     * <li>00, which is equivalent to the tuple (F, F)</li>
     * <li>01 (F, T)</li>
     * <li>10 (T, F)</li>
     * <li>11 (T, T)</li>
     * </ul>
     * Which, effectively, are all the possible permutations for a truth table with two propositions.
     * This algorithm uses this fact, along with bitwise operations (and masking) to extract each individual
     * bit (truth value).
     */
    private static int[][] generateTruthValuePermutations() {
        int nProps = BooleanLogicParser.getPropositionNames().size();
        int nPerms = ((int) Math.pow(2, nProps));
        int[][] permutations = new int[nPerms][nProps];
        for (int num = 0; num < nPerms; num++) {   // Binary counter
            for (int i = 0; i < nProps; i++) {
                int index = 1 << (nProps - 1) - i; // To extract bits from right to left.
                // Dividing by 'index' trims trailing 0s (same as right-shifting by index/2).
                int extractedBit = (num & index) / index;
                permutations[num][i] = extractedBit;
            }
        }
        return permutations;
    }
}
