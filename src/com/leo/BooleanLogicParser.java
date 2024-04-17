package com.leo;

import static com.leo.TokenType.*;

public class BooleanLogicParser {
    private final Token[] tokens;
    private int current = 0;
    public BooleanLogicParser(Token[] tokens) {
        this.tokens = tokens;
    }

    public static class ParseError extends Exception {
        ParseError(String message) {
            super(message);
        }
    }

    public Expr parse() throws ParseError {
        Expr result = formula();
        if (!isAtEnd()) {
            if (match(RIGHT_PAREN)) {
                throw new ParseError("Unmatched ')'.");
            }
            throw new ParseError("Unexpected token at end of input.");
        }
        return result;
    }

    private Token peek() {
        return tokens[current];
    }

    private Token previous() {
        return tokens[current - 1];
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOL;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) throws ParseError {
        if (check(type)) return advance();
        throw new ParseError(message);
    }

    private Expr formula() throws ParseError {
        if (isAtEnd()) return null;
        return term();
    }

    private Expr term() throws ParseError {
        Expr binaryOp = binaryOperation();
        while (match(OR, AND, XOR, THEN, IFF)) {
            Token operator = previous();
            Expr right = term();
            return new Expr.BinaryOperation(binaryOp, operator, right);
        }
        return binaryOp;
    }

    private Expr binaryOperation() throws ParseError {
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

    private Expr unaryOperation() throws ParseError {
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

    private Expr proposition() throws ParseError {
        if (match(PROP_NAME) && previous() instanceof PropositionName propName) {
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
