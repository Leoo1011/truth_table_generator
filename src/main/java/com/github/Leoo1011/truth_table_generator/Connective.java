package com.github.Leoo1011.truth_table_generator;

public class Connective extends Token {
    public Connective(TokenType tokenType) {
        super(tokenType);
    }

    @Override
    public String toString() {
        return "Connective{ type = " + this.type + " }";
    }
}
