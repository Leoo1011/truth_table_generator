package com.leo.main;

import java.util.Objects;

public class TruthTable {
    /**
     * A truth table generator.
     * @param expression The boolean logic formula from which to generate the truth table.
     *
     * @throws BooleanLogicLexer.LexError if the formula has invalid tokens.
     * @throws BooleanLogicParser.ParseError if the formula doesn't match the grammar.
     */
    public TruthTable(String expression) throws BooleanLogicLexer.LexError, BooleanLogicParser.ParseError {
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
     * @throws IllegalArgumentException if truthRepresentation.length != 2 or if expression is empty or blank.
     */
    public TruthTable(String expression, String[] truthRepresentation)
            throws BooleanLogicLexer.LexError, BooleanLogicParser.ParseError, IllegalArgumentException {
        formula = Objects.requireNonNull(expression);
        if (truthRepresentation.length != 2) {
            throw new IllegalArgumentException("'truthRepresentation' should have exactly 2 values.");
        }
        if (formula.isBlank()) {
            throw new IllegalArgumentException("'expression' shouldn't be empty or blank.");
        }
        truth = truthRepresentation[0];
        falsity = truthRepresentation[1];
        Token[] tokens = BooleanLogicLexer.tokenize(formula);
        ast = BooleanLogicParser.parse(tokens);

        int numProps = BooleanLogicParser.getPropositionNames().size();
        int rows = (int) Math.pow(2, numProps) + 1; // number of permutations + formula
        int cols = numProps + 1;                    // number of propositions + formula
        table = new String[rows][cols];
    }

    private final String truth;
    private final String falsity;
    private final String formula;
    private final Expr ast;
    private final String[][] table;
    private boolean isTableDone = false;

    private void generateHeaders() {
        int cols = table[0].length; // Propositions + formula

        // Doesn't set the last element, as the number of propositions is rows - 1
        BooleanLogicParser.getPropositionNames().toArray(table[0]);
        table[0][cols - 1] = formula;
    }

    /**
     * Generates every permutation of values for the propositions in the truth table.
     * Counting from 0 to 3 (in binary) is:
     * <ul>
     * <li>00, which is equivalent to the tuple (F, F)</li>
     * <li>01 (F, T)</li>
     * <li>10 (T, F)</li>
     * <li>11 (T, T)</li>
     * </ul>
     * Which, effectively, are all the possible permutations for a truth table with two propositions.
     * This algorithm uses this fact, along with bitwise operations (masking) to extract each individual
     * bit (truth value).
     *
     * @return int[][] with each value as a 0 (false) or 1 (true). The internal table maintained by the class
     * will be updated with strings corresponding to the custom values of 'truth' and 'falsity'.
     */
    private int[][] generateAndStoreTruthValuePermutations() {
        int nPerms = table.length - 1;
        int nProps = table[0].length - 1;
        int[][] result = new int[nPerms][nProps];
        for (int num = 1; num < nPerms + 1; num++) {  // Binary counter
            for (int i = 0; i < nProps; i++) {
                int mask = 1 << (nProps - 1) - i;     // To extract bits from right to left.
                int counter = num - 1;                // Because num starts at 1

                // Dividing by 'mask' trims trailing 0s (same as right-shifting by mask/2).
                int extractedBit = (counter & mask) / mask;
                result[num - 1][i] = extractedBit;
                table[num][i] = extractedBit == 1 ? truth : falsity;
            }
        }
        return result;
    }

    /**
     * Interprets the formula, by feeding the {@link BooleanLogicInterpreter} every sub-array of truthValuePermutations,
     * and updating the table accordingly.
     * @param truthValuePermutations the truth values to be used as input to the {@link BooleanLogicInterpreter}.
     */
    private void generateInterpretedValues(int[][] truthValuePermutations) {
        int rows = table.length;
        int cols = table[0].length - 1;
        for (int i = 1; i < rows; i++) {
            int evaluated = BooleanLogicInterpreter.interpret(ast, truthValuePermutations[i - 1]);
            table[i][cols] = evaluated == 1 ? truth : falsity;
        }
    }

    private void generateTable() {
        if (isTableDone) return;
        generateHeaders();
        int[][] truthValuePermutations = generateAndStoreTruthValuePermutations();
        generateInterpretedValues(truthValuePermutations);
        isTableDone = true;
    }

    public void printTable() {
        generateTable();
        for (String[] row : table) {
            for (String element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    public String[][] getTable() {
        generateTable();
        return table;
    }
}
