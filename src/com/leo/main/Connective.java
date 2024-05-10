package com.leo.main;

public class Connective extends Token {
    Connective(TokenType tokenType) {
        super(tokenType);
    }

    @Override
    public String toString() {
        return "Connective{ type = " + this.type + " }";
    }
}
