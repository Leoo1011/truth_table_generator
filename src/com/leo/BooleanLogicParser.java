package com.leo;

import static com.leo.TokenType.*;

public class BooleanLogicParser {
    private final Token[] tokens;
    private int current = 0;
    public BooleanLogicParser(Token[] tokens) {
        this.tokens = tokens;
    }

    // TODO: Use the right return type
    public static String[] parse() {
        return new String[0];
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

    private Expr formula() {
        return term();
    }

    private Expr term() {
        Expr binaryOp = binaryOperation();
        while (match(OR, AND, XOR, THEN, IFF)) {
            Token operator = previous();
            Expr right = term();
            return new Expr.BinaryOperation(binaryOp, operator, right);
        }
        return binaryOp;
    }

    private Expr binaryOperation() {
        Expr expr = unaryOperation();
        while (match(OR, AND, XOR, THEN, IFF)) {
            Token operator = previous();
            Expr rightOperator = unaryOperation();
            expr = new Expr.BinaryOperation(expr, operator, rightOperator);
        }
        return expr;
    }

    private Expr unaryOperation() {
        if (match(NOT)) {
            return unaryOperation();
        }
        return proposition();
    }

    private Expr proposition() {
    }
}
