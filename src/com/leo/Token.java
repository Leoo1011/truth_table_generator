package com.leo;

public abstract class Token {

    public final TokenType type;
    Token(TokenType tokenType) {
        type = tokenType;
    }
}
