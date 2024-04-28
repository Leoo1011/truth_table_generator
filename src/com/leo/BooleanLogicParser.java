package com.leo;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.leo.TokenType.*;

public class BooleanLogicParser {
    private static Token[] tokens;
    private static int current = 0;
    private static Set<String> propositionNames;
    private BooleanLogicParser() {
    }

    public static class ParseError extends Exception {
        ParseError(String message) {
            super(message);
        }
    }

    public static Set<String> getPropositionNames() {
        return Collections.unmodifiableSet(propositionNames);
    }

    /**
     * Tries to "parse" the tokens provided by matching each to a rule in the context-free grammar
     * (see the grammar in README.md).
     * This is an LL(1) (Left to right, Leftmost derivation, one token of lookahead) recursive descent parser.
     *
     * @param tokenArray the array of tokens to parse, produced by the {@link BooleanLogicLexer}.
     * @return a child instance of {@link Expr} representing the Abstract Syntax Tree (AST) constructed.
     * @throws ParseError if the tokens provided in the constructor don't match the grammar.
     */
    public static Expr parse(final Token[] tokenArray) throws ParseError {
        current = 0;
        tokens = tokenArray;
        propositionNames = new LinkedHashSet<>();

        Expr result = formula();
        if (!isAtEnd()) {
            if (match(RIGHT_PAREN)) {
                throw new ParseError("Unmatched ')'.");
            }
            throw new ParseError("Unexpected token at end of input.");
        }
        return result;
    }

    private static Token peek() {
        return tokens[current];
    }

    private static Token previous() {
        return tokens[current - 1];
    }

    private static Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private static boolean isAtEnd() {
        return peek().type == EOL;
    }

    private static boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private static boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private static Token consume(TokenType type, String message) throws ParseError {
        if (check(type)) return advance();
        throw new ParseError(message);
    }

    private static Expr formula() throws ParseError {
        if (isAtEnd()) return null;
        return term();
    }

    private static Expr term() throws ParseError {
        Expr binaryOp = binaryOperation();
        while (match(OR, AND, XOR, THEN, IFF)) { // to match multiple operators
            Token operator = previous();
            Expr right = term();
            return new Expr.BinaryOperation(binaryOp, operator, right);
        }
        return binaryOp;
    }

    private static Expr binaryOperation() throws ParseError {
        Expr expr = unaryOperation();
        while (match(OR, AND, XOR, THEN, IFF)) {
            Token operator = previous();
            if (match(RIGHT_PAREN) || isAtEnd()) {
                throw new ParseError(
                        "Expected expression or proposition name after operator '" + operator.type.prettyName + "'");
            }
            Expr rightOperator = unaryOperation();
            expr = new Expr.BinaryOperation(expr, operator, rightOperator);
        }
        return expr;
    }

    private static Expr unaryOperation() throws ParseError {
        if (match(NOT)) {
            if (match(RIGHT_PAREN, AND, OR, XOR, THEN, IFF)) {
                throw new ParseError("Expected expression or proposition after '!'.");
            }
            Expr unaryOp = unaryOperation();
            return new Expr.UnaryOperation(unaryOp);
        }
        Expr prop = proposition();
        if (match(LEFT_PAREN, NOT)) {
            throw new ParseError("'" + previous().type.prettyName + "' not allowed after proposition/expression.");
        }
        return prop;
    }

    private static Expr proposition() throws ParseError {
        if (match(PROP_NAME) && previous() instanceof PropositionName propName) {
            propositionNames.add(propName.name);
            return new Expr.Proposition(propName);
        }
        if (match(LEFT_PAREN)) {
            Expr expr = term();
            consume(RIGHT_PAREN, "Expected ')' after expression.");
            if (match(LEFT_PAREN, NOT)) {
                throw new ParseError("'" + previous().type.prettyName + "' not allowed after ')'.");
            }
            return new Expr.Grouping(expr);
        }
        throw new ParseError("Invalid syntax");
    }
}
